package com.example.fitnessadvisor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class ManuallyWorkoutFragment extends Fragment {

    protected ImageButton butt;

    public ManuallyWorkoutFragment() {

    }

    public static ManuallyWorkoutFragment newInstance() {
        ManuallyWorkoutFragment fragment = new ManuallyWorkoutFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manually_workout, container, false);
        butt = v.findViewById(R.id.add_button);
        setListener();
        return v;
    }
    public void setListener(){
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, ExerciseListFragment.class, null)
                        .commit();
            }
        });
    }
}