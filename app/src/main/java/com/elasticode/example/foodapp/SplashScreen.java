package com.elasticode.example.foodapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import com.elasticode.ElastiCode;
import java.util.Observable;
import java.util.Observer;


public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // Saving the time in order to show the splash screen for a minimum time
        final int startTimeInMillis = (int)(System.currentTimeMillis());
        Observer obs = new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                int timePassed = ((int)(System.currentTimeMillis()) - startTimeInMillis);
                // If we got True, the startSession was finished, else a restartSession maybe called
                if((Boolean)data){
                    // Default Dynamic object "FoodTypes" values
                    String[] values = new String[] {
                            "BBQ",
                            "Pizza",
                            "Quick coffee",
                            "Healthy food",
                            "Asian food"
                    };
                    // Define the dynamic object
                    ElastiCode.defineDynamicObject("FoodTypes", ElastiCode.ElastiCodeDObjType.ARRAY_OF_STRING, values);
                    // Move to main activity with
                    int delayTime = 1000 - timePassed;
                    showMainActivity(delayTime);
                }
            }
        };
        // We use a debug flag here to activate only in debug mode
        if(AppConfig.DEBUG){
            ElastiCode.activateDebugMode();
        }

        // Start elasticode session with observer
        ElastiCode.startSession(AppConfig.elasticodeKey, obs, this);
    }

    /**
     * Show the main activity after the given delay
     * @param delay - if positive integer, wait for the amount of milliseconds before showing the main activity.
     *              otherwise move directly to main activity
     */
    private void showMainActivity(int delay){
        if(delay <= 0){
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);

            // close this activity
            finish();
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, delay);
        }

    }
}

