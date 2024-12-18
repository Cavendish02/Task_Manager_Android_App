package com.ai.taskmanager;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchNotifications;
    private RadioGroup radioGroupTheme;
    private RadioButton radioLightTheme, radioDarkTheme;
    private Button buttonSaveSettings;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchNotifications = findViewById(R.id.switchNotifications);
        radioGroupTheme = findViewById(R.id.radioGroupTheme);
        radioLightTheme = findViewById(R.id.radioLightTheme);
        radioDarkTheme = findViewById(R.id.radioDarkTheme);
        buttonSaveSettings = findViewById(R.id.buttonSaveSettings);

        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        loadSettings();

        buttonSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
    }

    private void loadSettings() {
        boolean notificationsEnabled = sharedPreferences.getBoolean("notifications", false);
        String theme = sharedPreferences.getString("theme", "light");

        switchNotifications.setChecked(notificationsEnabled);
        if ("light".equals(theme)) {
            radioLightTheme.setChecked(true);
        } else {
            radioDarkTheme.setChecked(true);
        }
    }

    private void saveSettings() {
        boolean notificationsEnabled = switchNotifications.isChecked();
        String theme = radioLightTheme.isChecked() ? "light" : "dark";

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notifications", notificationsEnabled);
        editor.putString("theme", theme);
        editor.apply();

        Toast.makeText(this, "Settings Saved", Toast.LENGTH_SHORT).show();
    }
}
