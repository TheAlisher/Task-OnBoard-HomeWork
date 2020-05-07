package com.example.task_onboard_homework.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_onboard_homework.R;
import com.example.task_onboard_homework.ui.OnItemClickListener;
import com.example.task_onboard_homework.ui.models.Task;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private TextView textTitle;
    private OnItemClickListener onItemClickListener;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        textTitle = itemView.findViewById(R.id.textHeading);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        });
    }

    public void onBind(Task task) {
        textTitle.setText(task.getTitle());
    }
}
