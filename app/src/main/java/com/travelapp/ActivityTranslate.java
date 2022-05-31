package com.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

/**
 * @author Joxx0181
 * @version 2022.0524
 */

@SuppressWarnings("ALL")
public class ActivityTranslate extends AppCompatActivity {

    /**
     * Initializing
     * @param enterText for enter text
     * @param translateNowButton for get entered text translated to selected langauge
     * @param translatedText for displaying translated text
     * @param returnToMainBTN for the return to main button
     * @param translator for the firebase language translator
     */
    EditText enterText;
    Button translateNowButton;
    TextView translatedText;
    Button returnToMainBTN;
    FirebaseTranslator translator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_language);

        // Getting view that is identified by the android:id
        enterText = findViewById(R.id.enterText);
        translateNowButton = findViewById(R.id.translateLanguageButton);
        translatedText = findViewById(R.id.translatedLanguage);
        returnToMainBTN = findViewById(R.id.returnTranslateButton);

        // Create firebase translate options
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()

                // Specifying source language - in this case the danish language
                .setSourceLanguage(FirebaseTranslateLanguage.DA)

                // Specifying target language - in this case the english language
                .setTargetLanguage(FirebaseTranslateLanguage.EN)

                // Building the options
                .build();

        // Getting instance for firebase natural language
        translator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        // Perform click event using lambda on translateNowButton
        translateNowButton.setOnClickListener(view -> {

            // Getting text entered
            String textSTR = enterText.getText().toString();

            // Call downloadModal method to download language modal
            downloadModal(textSTR);

        });

        // Perform click event using lambda on returnToMainBTN
        returnToMainBTN.setOnClickListener(view -> {

            // Create a Intent to switch to another activity - in this case back to MainActivity
            Intent goBackToMainPage = new Intent(ActivityTranslate.this, MainActivity.class);

            // Start the activity
            startActivity(goBackToMainPage);
        });
    }

    // Method
    private void downloadModal(String textInput) {

        // Downloading the modal which is required to translate into an english language
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {

                // Call translateLanguage method to translate entered text
                translateLanguage(textInput);

            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {

                // Display this message when download failed
                Toast.makeText(ActivityTranslate.this, "Fail to download", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method
    private void translateLanguage(String input) {
        translator.translate(input).addOnSuccessListener(new OnSuccessListener<String>() {

            @Override
            public void onSuccess(String translatedOutput) {

                // Display translated text when translate successed
                translatedText.setText(translatedOutput);
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {

                // Display this message when translate failed
                Toast.makeText(ActivityTranslate.this, "Fail to translate", Toast.LENGTH_SHORT).show();
            }
        });
    }
}








