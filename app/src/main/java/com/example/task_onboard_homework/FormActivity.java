package com.example.task_onboard_homework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.task_onboard_homework.ui.home.TaskAdapter;
import com.example.task_onboard_homework.ui.models.Task;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity {

    /*public static final String TASK_KEY = "task_key"; • PREVIOUS VERSION */
    EditText editTitle;
    EditText editDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("All content");
        }
        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);
        if (getIntent() != null) {
            Task task = (Task) getIntent().getSerializableExtra(TaskAdapter.EDIT_TEXT_KEY);
            editTitle.setText(task.getTitle());
            editDesc.setText(task.getDesc());
        }
    }

    public void onClick(View view) {
        String title = editTitle.getText().toString().trim();   // trim - убирание пробелов
        String desc = editDesc.getText().toString().trim();
        Task task = new Task(title, desc);
        App.getInstance().getDatabase().taskDao().insert(task);
        /*Intent intent = new Intent();
        intent.putExtra(TASK_KEY, task); • PREVIOUS VERSION…
        setResult(RESULT_OK, intent);*/
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    } // OR
    /*@Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }*/
}