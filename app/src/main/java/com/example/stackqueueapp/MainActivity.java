package com.example.stackqueueapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle window insets for EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the button for "STACK"
        Button buttonStack = findViewById(R.id.buttonStack);

        // Set an onClickListener to navigate to Page2 (Stack window)
        buttonStack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start Page2 (stack logic)
                Intent intent = new Intent(MainActivity.this, Page2.class);
                startActivity(intent);
            }
        });

        // Find the button for "QUEUE"
        Button buttonQueue = findViewById(R.id.buttonQueue);

        // Set an onClickListener to navigate to Page3 (Queue window)
        buttonQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start Page3 (queue logic)
                Intent intent = new Intent(MainActivity.this, activity_page3.class);
                startActivity(intent);
            }
        });
    }
}
