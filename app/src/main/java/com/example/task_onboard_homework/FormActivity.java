package com.example.task_onboard_homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.task_onboard_homework.ui.home.TaskAdapter;
import com.example.task_onboard_homework.ui.models.Task;

public class FormActivity extends AppCompatActivity {

    /*public static final String TASK_KEY = "task_key"; • PREVIOUS VERSION*/
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

        if (getIntent().getSerializableExtra(TaskAdapter.EDIT_TEXT_KEY) != null) {
            Task task = (Task) getIntent().getSerializableExtra(TaskAdapter.EDIT_TEXT_KEY);
            editTitle.setText(task.getTitle());
            editDesc.setText(task.getDesc());
            Button save = findViewById(R.id.button_save);
            save.setText("Update");
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
        if (getIntent().getSerializableExtra(TaskAdapter.EDIT_TEXT_KEY) != null) {
            finish();
        }
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