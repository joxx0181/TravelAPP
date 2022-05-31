package com.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * @author Joxx0181
 * @version 2022.0524
 */

@SuppressWarnings("ALL")
public class ActivityWeather extends AppCompatActivity {

    /**
     * Initializing
     * @param enterCity for enter selected city
     * @param enterCountryCode for country code
     * @param recievedForecastOfToday for displaying today forecast
     * @param returnToMainBTN for the return to main button
     */
    EditText enterCity;
    EditText enterCountryCode;
    TextView recievedForecastOfToday;
    Button returnToMainBTN;

    // Using final indicate a constant value (api url) - cannot be changed
    private final String apiURL = "https://api.openweathermap.org/data/2.5/weather";

    // Using final indicate a constant value (api id) - cannot be changed
    private final String apiID = "3d076f5f6a3b55e8c5388da75155c311";

    // Using decFormat for display in decimals
    DecimalFormat decFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        // Getting view that is identified by the android:id
        enterCity = findViewById(R.id.enterCity);
        enterCountryCode = findViewById(R.id.enterCountryCode);
        recievedForecastOfToday = findViewById(R.id.forecastView);
        returnToMainBTN = findViewById(R.id.returnWeatherButton);

        // Perform click event using lambda on returnToMainBTN
        returnToMainBTN.setOnClickListener(view -> {

            // Create a Intent to switch to another activity - in this case back to MainActivity
            Intent goBackToMainPage = new Intent(ActivityWeather.this, MainActivity.class);

            // Start the activity
            startActivity(goBackToMainPage);
        });
    }

    // Method called when weatherForecastButton is clicked
    public void TodayWeatherForecast(View view) {

        // Initializing
        String fullURL = "";
        String selectedCity = enterCity.getText().toString().trim();
        String countryCode = enterCountryCode.getText().toString().trim();

        if(selectedCity.equals(""))
        {
            // Display text info to user when weatherForecastButton is clicked before any city is entered
            recievedForecastOfToday.setText("UPS...You most enter a city name");
        }
        else
        {
            if (!countryCode.equals(""))
            {
                // Using fullURL with api url, city, country code and api id in one string
                fullURL = apiURL + "?q=" + selectedCity + "," + countryCode + "&APPID=" + apiID;
            } else
            {
                // Using fullURL with api url, city and api id in one string
                fullURL = apiURL + "?q=" + selectedCity + "&APPID=" + apiID;
            }

            // Using StringRequest for requesting a string response from fullURL
            StringRequest weatherRequest = new StringRequest(Request.Method.POST, fullURL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    String forecastOutput = "";
                    try {
                        // Using JSONObject and JSONArray for getting weather info from api
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");

                        // Weather description
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");

                        // Weather temperature, presure and humidity
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        float pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");

                        // Weather wind speed
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");

                        // Weather clouds
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");

                        // Weather city and country code
                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonResponse.getString("name");

                        // Setting text color of output - in this case white
                        recievedForecastOfToday.setTextColor(Color.rgb(255, 255, 255));

                        // Output
                        forecastOutput += "Current weather of " + cityName + " (" + countryName + ")"
                                + "\n Temp: " + decFormat.format(temp) + " °C"
                                + "\n Feels Like: " + decFormat.format(feelsLike) + " °C"
                                + "\n Humidity: " + humidity + "%"
                                + "\n Description: " + description
                                + "\n Wind Speed: " + wind + "m/s (meters per second)"
                                + "\n Cloudiness: " + clouds + "%"
                                + "\n Pressure: " + pressure + " hPa";
                        recievedForecastOfToday.setText(forecastOutput);
                    }
                    catch (JSONException e)
                    {
                        // Method for handle exceptions
                        e.printStackTrace();
                    }
                }
                // Using ErrorListener in case of any Volley error
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error)
                {
                    // Using Toast to view a little error message for the user
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            // Instantiate the RequestQueue with Volley
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            // Adding request to the RequestQueue - in this case a weather request
            requestQueue.add(weatherRequest);
        }
    }
}