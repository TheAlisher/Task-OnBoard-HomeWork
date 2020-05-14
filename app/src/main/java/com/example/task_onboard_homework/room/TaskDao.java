package com.example.task_onboard_homework.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.task_onboard_homework.ui.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllLive();

    @Insert
    void insert(Task task);

    @Query("DELETE from task WHERE id IN (:ID)")
    void deleteById(int ID);

    @Update
    void update(Task task);

    @Query("UPDATE task Set title = :newTitle, `desc` = :newDesc WHERE id IN (:IDList)")
    void updateSalaryByIdList(int IDList, String newTitle, String newDesc);
}
