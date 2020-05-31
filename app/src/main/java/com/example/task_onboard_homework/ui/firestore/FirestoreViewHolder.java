package com.example.task_onboard_homework.ui.firestore;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_onboard_homework.R;
import com.example.task_onboard_homework.ui.models.Task;

public class FirestoreViewHolder extends RecyclerView.ViewHolder {

    private TextView textHeadingInFirestore;
    private TextView textDescriptionInFirestore;

    public FirestoreViewHolder(@NonNull View itemView) {
        super(itemView);
        textHeadingInFirestore = itemView.findViewById(R.id.text_Heading_inFirestore);
        textDescriptionInFirestore = itemView.findViewById(R.id.text_Description_inFireStore);
    }

    public void onBind(Task data) {
        textHeadingInFirestore.setText(data.getTitle());
        textDescriptionInFirestore.setText(data.getDesc());
    }
}
