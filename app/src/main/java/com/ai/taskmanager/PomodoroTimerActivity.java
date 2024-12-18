package com.ai.taskmanager;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class PomodoroTimerActivity extends AppCompatActivity {

    private static final long DEFAULT_TIME_IN_MILLIS = 1500000; // 25 minutes in milliseconds

    private TextView textViewTimer;
    private Button buttonStartPause, buttonReset, buttonApplyTime;
    private EditText editTextTimeInput;

    private CountDownTimer countDownTimer;
    private boolean isTimerRunning;
    private long timeLeftInMillis = DEFAULT_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_timer);

        textViewTimer = findViewById(R.id.textViewTimer);
        buttonStartPause = findViewById(R.id.buttonStartPause);
        buttonReset = findViewById(R.id.buttonReset);
        buttonApplyTime = findViewById(R.id.buttonApplyTime);
        editTextTimeInput = findViewById(R.id.editTextTimeInput);

        updateTimerText();

        // Start/Pause button
        buttonStartPause.setOnClickListener(v -> {
            if (isTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        // Reset button
        buttonReset.setOnClickListener(v -> resetTimer());

        // Apply time button
        buttonApplyTime.setOnClickListener(v -> {
            String input = editTextTimeInput.getText().toString().trim();
            if (!input.isEmpty()) {
                try {
                    int minutes = Integer.parseInt(input);
                    if (minutes <= 0) {
                        editTextTimeInput.setError("Time must be greater than 0.");
                        return;
                    }
                    timeLeftInMillis = minutes * 60000; // Convert minutes to milliseconds
                    updateTimerText();

                    // Stop the timer if it's running and update the time
                    if (isTimerRunning) {
                        pauseTimer(); // Pause the timer before changing the time
                        startTimer(); // Restart the timer with the new time
                    }
                } catch (NumberFormatException e) {
                    editTextTimeInput.setError("Please enter a valid number.");
                }
            } else {
                editTextTimeInput.setError("Time cannot be empty.");
            }
        });
    }

    // Start the timer
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
                buttonStartPause.setText("Start");
            }
        }.start();

        isTimerRunning = true;
        buttonStartPause.setText("Pause");
    }

    // Pause the timer
    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isTimerRunning = false;
        buttonStartPause.setText("Start");
    }

    // Reset the timer
    private void resetTimer() {
        timeLeftInMillis = DEFAULT_TIME_IN_MILLIS;
        updateTimerText();
        pauseTimer();
    }

    // Update the displayed timer text
    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewTimer.setText(timeFormatted);
    }
}
