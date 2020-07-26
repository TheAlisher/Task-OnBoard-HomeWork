package com.example.task_onboard_homework.ui.firestore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task_onboard_homework.R;
import com.example.task_onboard_homework.ui.models.Task;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FirestoreFragment extends Fragment {

    private FirestoreAdapter adapter;
    private ArrayList<Task> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_firestore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_firestore);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FirestoreAdapter(list);
        recyclerView.setAdapter(adapter);
        getTasks();
    }

    private void getTasks() {
        String uid = /*FirebaseAuth.getInstance().getUid()*/ "alisherUserID";
        FirebaseFirestore.getInstance().collection("tasks")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        if (snapshots != null) {
                            list.addAll(snapshots.toObjects(Task.class));
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
              /*.document(uid)   PV…
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot.exists()) {
                            Task task = documentSnapshot.toObject(Task.class);
                            list.add(task);
                            adapter.notifyDataSetChanged();
                            Log.e("anim", "getTasks " +
                                    task.getTitle() + " " + task.getDesc());
                        }
                    }
                });*/
    }
}
