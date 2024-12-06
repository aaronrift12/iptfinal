package com.example.personalityd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private Button submitButton;
    private TextView resultText;
    private TextView nameDisplay; // To show the entered name
    private String userName;  // Variable to store the user's name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Toolbar and set it as the ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Remove title from the Toolbar programmatically
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(""); // Set title to an empty string
        }

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Handle the back button click
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Initialize UI components
        inputText = findViewById(R.id.inputText);
        submitButton = findViewById(R.id.submitButton);
        resultText = findViewById(R.id.resultText);
        nameDisplay = findViewById(R.id.nameDisplay); // Reference to TextView to display name

        // Retrieve the name passed from NameInputActivity
        Intent intent = getIntent();
        userName = intent.getStringExtra("USER_NAME");  // Get the user name
        Log.d("MainActivity", "User Name: " + userName);

        if (userName != null && !userName.isEmpty()) {
            // Display the user's name in the TextView
            nameDisplay.setText("Hello, " + userName + "!");
        } else {
            // If no name passed, ask the user to enter their name
            Intent intentToNameInput = new Intent(MainActivity.this, NameInputActivity.class);
            startActivity(intentToNameInput);
        }

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://flaskdeploy-3-lbnb.onrender.com/")  // Base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        // Handle the submit button click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = inputText.getText().toString().trim();
                Log.d("MainActivity", "Submit button clicked. Input: " + userInput);

                if (!userInput.isEmpty()) {
                    // Prepare the request body
                    TextRequest textRequest = new TextRequest(userInput);

                    // Make the API call
                    apiClient.getPrediction(textRequest).enqueue(new Callback<PredictionResponse>() {
                        @Override
                        public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
                            Log.d("MainActivity", "API Response received: " + response.code());
                            if (response.isSuccessful() && response.body() != null) {
                                // Display the prediction result
                                resultText.setText("Result: " + response.body().getPrediction());
                                Log.d("MainActivity", "Prediction: " + response.body().getPrediction());
                            } else {
                                // Log the error response details for debugging
                                Log.e("API Error", "Response Code: " + response.code() + ", Message: " + response.message());
                                Toast.makeText(MainActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PredictionResponse> call, Throwable t) {
                            // Log the failure message to understand any network or other errors
                            Log.e("API Failure", "Error: " + t.getMessage(), t);
                            Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Display a message if the input is empty
                    Toast.makeText(MainActivity.this, "Please enter some text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
