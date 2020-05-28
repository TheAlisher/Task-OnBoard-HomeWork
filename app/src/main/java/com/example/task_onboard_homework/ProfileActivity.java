package com.example.task_onboard_homework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task_onboard_homework.ui.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE = 21;
    private EditText editName;
    private CircleImageView imageProfile;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editName = findViewById(R.id.editName);
        imageProfile = findViewById(R.id.imageProfile);
        /*getData();*/
        getData2();
    }

    private void getData2() {
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document("alisherUserID")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            editName.setText(user.getName());
                        }
                    }
                });
    }

    private void getData() {
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document("alisherUserID")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            User user = task.getResult().toObject(User.class);
                            /*String name = task.getResult().getString("name");*/   //PV…
                            editName.setText(user.getName());
                        }
                    }
                });
    }

    public void onClick(View view) {
        String uid = FirebaseAuth.getInstance().getUid();
        String name = editName.getText().toString().trim();
        User user = new User(name, 18, null);
        /*Map<String, Object> map = new HashMap<>();   //PV…
        map.put("name", "Alisher");
        map.put("age", 18);
        map.put("android", true);*/
        FirebaseFirestore.getInstance().collection("users")
                .document("alisherUserID")
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Успешно", Toast.LENGTH_SHORT);
                        } else {
                            Toast.makeText(ProfileActivity.this, "Ошибка", Toast.LENGTH_SHORT);
                        }
                    }
                });
                /*.add(user)   //PV…
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Успешно", Toast.LENGTH_SHORT);
                        } else {
                            Toast.makeText(ProfileActivity.this, "Ошибка", Toast.LENGTH_SHORT);
                        }
                    }
                });*/
    }

    public void imageProfileClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
            } catch (IOException ioE) {
                ioE.printStackTrace();
            }
            imageProfile.setImageBitmap(bitmap);
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        }
    } // OR
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imageProfile.setImageURI(data.getData());
        }
    }*/
}