package com.ai.taskmanager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final ArrayList<String> tasks;

    public TaskAdapter(ArrayList<String> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        String task = tasks.get(position);
        holder.textViewTask.setText(task);

        // Set up CheckBox state, ensuring we do not trigger the listener while checking/unchecking
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(task.startsWith("[Done]"));

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tasks.set(position, "[Done] " + tasks.get(position));
            } else {
                tasks.set(position, tasks.get(position).replace("[Done] ", ""));
            }
            notifyItemChanged(position);  // Notify only the specific item has changed
        });

        // Set up Start Button to navigate to PomodoroTimerActivity
        holder.buttonStart.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PomodoroTimerActivity.class);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTask;
        CheckBox checkBox;
        Button buttonStart;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            checkBox = itemView.findViewById(R.id.checkBoxTask);
            buttonStart = itemView.findViewById(R.id.buttonStart);
        }
    }
}