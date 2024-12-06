package com.example.personalityd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NameInputActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_input);

        // Initialize the UI components
        nameInput = findViewById(R.id.nameInput);
        proceedButton = findViewById(R.id.proceedButton);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Optionally, you can handle the back button click:
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Go back to previous activity
            }
        });

        // Handle the proceed button click
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString().trim();
                if (!name.isEmpty()) {
                    // Proceed to the MainActivity and pass the name
                    Intent intent = new Intent(NameInputActivity.this, MainActivity.class);
                    intent.putExtra("USER_NAME", name); // Pass the name to MainActivity
                    startActivity(intent);
                } else {
                    // Display a message if name is empty
                    Toast.makeText(NameInputActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
