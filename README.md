# Task Manager App

## Overview
The Task Manager app is a simple Android application that allows users to manage their tasks. The app enables the user to:
- Add new tasks
- Mark tasks as completed
- View tasks in a list format
- Clear completed tasks
- Customize settings for notifications and theme

This app uses **SharedPreferences** to persist task data and user settings, ensuring that tasks are saved between app launches.

## Features
- **Task Management:**
  - Add new tasks.
  - Mark tasks as completed.
  - Remove completed tasks from the task list.
- **Settings:**
  - Toggle notifications on or off.
  - Choose between light or dark themes.
- **Persistent Storage:**
  - Tasks are saved in **SharedPreferences**, ensuring persistence even after app restart.

## Project Structure

```
TaskManagerApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── com/
│   │   │   │   │   └── ai/
│   │   │   │   │       └── taskmanager/
│   │   │   │   │           ├── AddTaskActivity.java
│   │   │   │   │           ├── EditTaskActivity.java
│   │   │   │   │           ├── SettingsActivity.java
│   │   │   │   │           ├── TaskAdapter.java
│   │   │   │   │           ├── TaskListActivity.java
│   │   │   │   │           └── PomodoroTimerActivity.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_task_list.xml
│   │   │   │   │   ├── item_task.xml
│   │   │   │   │   └── activity_settings.xml
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   └── colors.xml
│   ├── AndroidManifest.xml
└── build.gradle
```

## Activities Overview

### 1. **TaskListActivity** 
The main activity that displays all tasks in a list. Users can:
- Add new tasks by navigating to the **AddTaskActivity**.
- Mark tasks as completed or uncompleted.
- Clear all completed tasks.

### 2. **AddTaskActivity**
This activity allows users to add a new task to the list. The task is then saved and added to the task list in **TaskListActivity**.

### 3. **SettingsActivity**
This activity allows users to:
- Toggle notifications on or off.
- Choose between a light or dark theme.
Settings are saved using **SharedPreferences** and are persisted across app sessions.

### 4. **PomodoroTimerActivity**
This activity is designed for users to use a Pomodoro timer to focus on tasks. The timer is launched when the **Start** button is pressed for any task in **TaskListActivity**.

### 5. **EditTaskActivity**
This activity allows users to edit the details of a task, including changing its description or status.

## Setup and Installation

### Prerequisites
- Android Studio (latest version recommended)
- Java Development Kit (JDK) 8 or above

### Steps to Set Up the Project Locally:
1. Clone the repository or download the project files.
2. Open **Android Studio**.
3. Go to **File -> Open**, and select the project folder.
4. Wait for Gradle to sync and the project to build.
5. Once the build is complete, you can run the app either on an emulator or a physical Android device.

### Gradle Dependencies
Ensure that your `build.gradle` files include the necessary dependencies, including the ones for RecyclerView and Material Design components. Here’s an example of the dependencies section for `app/build.gradle`:

```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.3.1'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.recyclerview:recyclerview:1.2.1'
}
```

## How to Use the App
1. **Launch the App:**
   - Upon opening the app, you'll see a list of tasks. If the list is empty, you can add a new task using the floating action button.
   
2. **Add Tasks:**
   - Tap on the **+** button (floating action button) to add a new task. A new task will be added to the list once the user enters a task description.
   
3. **Mark Tasks as Completed:**
   - Tasks can be marked as completed by checking the checkbox next to each task. Completed tasks are displayed with the "[Done]" label.

4. **Clear Completed Tasks:**
   - Use the **Clear Completed** button to remove all tasks that are marked as completed from the list.

5. **Settings:**
   - Access the **Settings** by tapping the settings icon. Here, you can enable/disable notifications and toggle between light and dark themes.

## Technologies Used
- **Java** for Android development.
- **Android SDK** for building the Android app.
- **SharedPreferences** for persisting task data and app settings.
- **RecyclerView** for displaying the list of tasks.
- **Material Design** components for UI elements like buttons and floating action buttons.

## Future Improvements
- Add notifications for task reminders.
- Implement task deadlines and sorting features.
- Add user authentication (e.g., Google or email login) to save tasks across devices.
- Improve the UI with better animations and visual cues.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
