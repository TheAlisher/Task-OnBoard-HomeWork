package com.example.task_onboard_homework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.task_onboard_homework.ui.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE = 21;
    private EditText editName;
    private CircleImageView imageProfile;
    private ProgressBar progressBar;
    private String avatarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editName = findViewById(R.id.editName);
        imageProfile = findViewById(R.id.imageProfile);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
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
                            showImage(user.getAvatar());
                        }
                    }
                });
    }

    private void showImage(String avatar) {
        Glide.with(this).load(avatar).circleCrop().into(imageProfile);
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
        User user = new User(name, 18, avatarUrl);
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
        intent.setAction(Intent.ACTION_GET_CONTENT); // or ACTION_PICK для изображений
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            Glide.with(this).load(data.getData()).circleCrop().into(imageProfile);
            upload(data.getData());
        }
    }

    private void upload(Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        String uid = /*FirebaseAuth.getInstance().getUid()*/ "alisherUserID";
        final StorageReference reference =
                FirebaseStorage.getInstance().getReference().child(uid + ".jpg");
        UploadTask uploadTask = reference.putFile(data);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUrl = task.getResult();
                    Log.e("Profile", "downloadUrl " + downloadUrl);
                    avatarUrl = downloadUrl.toString();
                    updateAvatarInfo(downloadUrl);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateAvatarInfo(Uri downloadUrl) {
        String uid = /*FirebaseAuth.getInstance().getUid()*/ "alisherUserID";
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .update("avatar", downloadUrl.toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}