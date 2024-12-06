package com.example.personalityd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LandingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        // Find the button and set a click listener
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> {
            // Navigate to the MainActivity
            Intent intent = new Intent(LandingActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
