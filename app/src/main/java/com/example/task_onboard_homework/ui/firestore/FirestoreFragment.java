package com.example.task_onboard_homework.ui.firestore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task_onboard_homework.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirestoreFragment extends Fragment {

    public FirestoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_firestore, container, false);
    }
}
