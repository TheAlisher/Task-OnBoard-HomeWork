package com.example.task_onboard_homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task_onboard_homework.ui.home.TaskAdapter;
import com.example.task_onboard_homework.ui.models.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity {

    /*public static final String TASK_KEY = "task_key";*/   //PV…
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
        if (getIntent().getSerializableExtra(TaskAdapter.EDIT_TEXT_KEY) != null) {
            Integer position = getIntent().getIntExtra(TaskAdapter.POSITION_KEY, 1);
            App.getInstance().getDatabase().taskDao().updateSalaryByIdList
                    (position, editTitle.getText().toString(), editDesc.getText().toString());
        } else {
            String title = editTitle.getText().toString().trim();
            if (title.isEmpty()) {
                editTitle.setError("Введите задачу");
                return;
            }
            String desc = editDesc.getText().toString().trim();
            Task task = new Task(title, desc);
            App.getInstance().getDatabase().taskDao().insert(task);
            /*Intent intent = new Intent();
            intent.putExtra(TASK_KEY, task);   //PV…
            setResult(RESULT_OK, intent);*/
        }
        String title = editTitle.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        Map<String, Object> map = new HashMap<>();   //PV…
        map.put("title", title);
        map.put("desc", desc);
        FirebaseFirestore.getInstance().collection("tasks")
                .add(map)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FormActivity.this, "Успешно", Toast.LENGTH_SHORT);
                        } else {
                            Toast.makeText(FormActivity.this, "Ошибка", Toast.LENGTH_SHORT);
                        }
                    }
                });
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