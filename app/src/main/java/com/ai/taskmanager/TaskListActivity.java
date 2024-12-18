package com.ai.taskmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import java.util.HashSet;
import java.util.Set;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private ArrayList<String> taskList;
    private static final String PREFS_NAME = "TaskManagerPrefs";
    private static final String TASKS_KEY = "tasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        FloatingActionButton fabAddTask = findViewById(R.id.fabAddTask);
        Button buttonClearCompleted = findViewById(R.id.buttonClearCompleted);

        taskList = loadTasks();  // Load tasks from SharedPreferences
        taskAdapter = new TaskAdapter(taskList);

        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);

        fabAddTask.setOnClickListener(v -> {
            // Navigate to AddTaskActivity to add a new task
            Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, 1);
        });

        // Clear completed tasks
        buttonClearCompleted.setOnClickListener(v -> {
            clearCompletedTasks();
            taskAdapter.notifyDataSetChanged();
        });
    }

    // Save tasks to SharedPreferences
    private void saveTasks() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(TASKS_KEY, new HashSet<>(taskList));
        editor.apply();
    }

    // Load tasks from SharedPreferences
    private ArrayList<String> loadTasks() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> savedTasks = preferences.getStringSet(TASKS_KEY, new HashSet<>());
        return new ArrayList<>(savedTasks);
    }

    // Remove completed tasks
    private void clearCompletedTasks() {
        List<String> completedTasks = new ArrayList<>();
        for (String task : taskList) {
            if (task.startsWith("[Done]")) {
                completedTasks.add(task);
            }
        }
        taskList.removeAll(completedTasks);
        saveTasks();  // Save the updated task list
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String newTask = data.getStringExtra("task");
            taskList.add(newTask);
            saveTasks();  // Save tasks after adding
            taskAdapter.notifyDataSetChanged();
        }
    }
}
