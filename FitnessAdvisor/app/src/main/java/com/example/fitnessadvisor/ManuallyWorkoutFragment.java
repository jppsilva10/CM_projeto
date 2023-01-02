package com.example.fitnessadvisor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Food;

import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;


public class ManuallyWorkoutFragment extends Fragment implements TaskManager.Callback {

    protected FloatingActionButton butt;
    protected TextView workoutName;
    protected SharedViewModel viewmodel;

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

        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        butt = v.findViewById(R.id.add_button);
        workoutName = v.findViewById(R.id.WorkoutName);
        workoutName.setText(viewmodel.getWorkout());
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

    @Override
    public void onLoadProfileComplete(Profile profile, boolean empty) {

    }
    @Override
    public void onProfileUpdateComplete(Profile profile) {

    }
    @Override
    public void onLoadWorkoutComplete(List<Workout> workouts) {

    }

    @Override
    public void onLoadMealComplete(HashMap<String, List<String>> mealList) {

    }

    @Override
    public void onLoadExerciseComplete(List<Exercise> exercises) {

    }

    @Override
    public void onLoadExerciseComplete(Exercise exercise) {

    }

}