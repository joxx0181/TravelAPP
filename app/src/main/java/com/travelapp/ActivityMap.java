package com.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * @author Joxx0181
 * @version 2022.0524
 */

@SuppressWarnings("ALL")
public class ActivityMap extends AppCompatActivity {

    /**
     * Initializing
     * @param returnToMainBTN for the return to main button
     */
    Button returnToMainBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);

        // Getting view that is identified by the android:id
        returnToMainBTN = findViewById(R.id.returnMapButton);

        // Perform click event using lambda on returnToMainBTN
        returnToMainBTN.setOnClickListener(view -> {

            // Create a Intent to switch to another activity - in this case back to MainActivity
            Intent goBackToMainPage = new Intent(ActivityMap.this, MainActivity.class);

            // Start the activity
            startActivity(goBackToMainPage);
        });
    }
}