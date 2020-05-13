package com.example.task_onboard_homework.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_onboard_homework.App;
import com.example.task_onboard_homework.FormActivity;
import com.example.task_onboard_homework.R;
import com.example.task_onboard_homework.ui.OnItemClickListener;
import com.example.task_onboard_homework.ui.models.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public static final String EDIT_TEXT_KEY = "edit_text_key";
    private ArrayList<Task> list;
    private OnItemClickListener onItemClickListener;

    public TaskAdapter(ArrayList<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.onBind(list.get(position));
        holder.setIsRecyclable(true);
        if (position % 2 == 1) {
            holder.itemView.setBackgroundResource(R.color.White);
        } else {
            holder.itemView.setBackgroundResource(R.color.Gray);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView textHeading;
        private TextView textDescription;

        public TaskViewHolder(@NonNull final View itemView) {
            super(itemView);
            textHeading = itemView.findViewById(R.id.text_Heading);
            textDescription = itemView.findViewById(R.id.text_Description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), FormActivity.class);
                    Task task = new Task();
                    task.setTitle(textHeading.getText().toString());
                    task.setDesc(textDescription.getText().toString());
                    intent.putExtra(EDIT_TEXT_KEY, task);
                    v.getContext().startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(v.getContext());
                    deleteDialog
                            .setIcon(R.drawable.ic_delete_black)
                            .setTitle(R.string.information_message)
                            .setMessage(R.string.are_you_sure)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int pos = list.get(getAdapterPosition()).getId();
                                    App.getInstance().getDatabase().taskDao().deleteById(pos);
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    deleteDialog.show();
                    return false;
                }
            });
        }

        public void onBind(Task task) {
            textHeading.setText(task.getTitle());
            textDescription.setText(task.getDesc());
        }
    }
}
