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
public class MainActivity extends AppCompatActivity {

    /**
     * Initializing
     * @param translateBTN for the translate button
     * @param weatherBTN for the weather forecast button
     * @param mapBTN for the map button
     */
    Button translateBTN;
    Button weatherBTN;
    Button mapBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting view that is identified by the android:id
        translateBTN = findViewById(R.id.translatePage);
        weatherBTN = findViewById(R.id.weatherPage);
        mapBTN = findViewById(R.id.mapPage);

        // Perform click event using lambda on translateBTN
        translateBTN.setOnClickListener(view -> {

            // Create a Intent to switch to another activity - in this case to ActivityTranslate
            Intent goToTranslatePage = new Intent(MainActivity.this,ActivityTranslate.class);

            // Start the activity
            startActivity(goToTranslatePage);
        });

        // Perform click event using lambda on weatherBTN
        weatherBTN.setOnClickListener(view -> {

            // Create a Intent to switch to another activity - in this case to ActivityWeather
            Intent goToWeatherPage = new Intent(MainActivity.this,ActivityWeather.class);

            // Start the activity
            startActivity(goToWeatherPage);
        });

        // Perform click event using lambda on mapBTN
        mapBTN.setOnClickListener(view -> {

            // Create a Intent to switch to another activity - in this case to ActivityMap
            Intent goToMapPage = new Intent(MainActivity.this,ActivityMap.class);

            // Start the activity
            startActivity(goToMapPage);
        });
    }
}