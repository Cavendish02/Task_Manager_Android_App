package com.ai.taskmanager;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class FullscreenTimerActivity extends AppCompatActivity {

    private TextView textViewFullscreenTimer;
    private Button buttonFullscreenStart, buttonAdjustTime;
    private EditText editTextAdjustTime;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 600000; // Default 10 minutes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_timer);

        // Initialize views
        textViewFullscreenTimer = findViewById(R.id.textViewFullscreenTimer);
        buttonFullscreenStart = findViewById(R.id.buttonFullscreenStart);
        buttonAdjustTime = findViewById(R.id.buttonAdjustTime);
        editTextAdjustTime = findViewById(R.id.editTextAdjustTime);

        updateTimerText();

        // Button click to start or pause the timer
        buttonFullscreenStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        // Button click to adjust the time
        buttonAdjustTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editTextAdjustTime.getText().toString().trim();
                if (!input.isEmpty()) {
                    try {
                        int minutes = Integer.parseInt(input);
                        if (minutes <= 0) {
                            editTextAdjustTime.setError("Time must be greater than 0.");
                            return;
                        }
                        timeLeftInMillis = minutes * 60000; // Convert minutes to milliseconds
                        updateTimerText();
                    } catch (NumberFormatException e) {
                        editTextAdjustTime.setError("Please enter a valid number.");
                    }
                }
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                buttonFullscreenStart.setText("Start");
                // After timer finishes, rotate the screen back to portrait
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }.start();

        isTimerRunning = true;
        buttonFullscreenStart.setText("Pause");

        // Rotate the screen to landscape mode when the timer starts
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Make the timer text larger and more prominent in landscape mode
        setTimerTextSize(true);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
        buttonFullscreenStart.setText("Start");
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewFullscreenTimer.setText(timeFormatted);
    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Adjust the timer text size based on screen orientation
        if (newConfig.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            setTimerTextSize(true); // Increase the size when in landscape
        } else {
            setTimerTextSize(false); // Reset to normal size in portrait
        }
    }

    private void setTimerTextSize(boolean isLandscape) {
        if (isLandscape) {
            // Make the timer text larger in landscape
            textViewFullscreenTimer.setTextSize(100);
        } else {
            // Reset the timer text size in portrait mode
            textViewFullscreenTimer.setTextSize(64);
        }
    }
}
