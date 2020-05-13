package com.example.task_onboard_homework.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.task_onboard_homework.ui.models.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
