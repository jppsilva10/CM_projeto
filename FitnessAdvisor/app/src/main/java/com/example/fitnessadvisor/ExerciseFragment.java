package com.example.fitnessadvisor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;
import com.example.fitnessadvisor.Database.Workout_Exercise;

import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ExerciseFragment extends Fragment implements WorkoutTaskManager.Callback{

    protected SharedViewModel viewmodel;

    protected WorkoutTaskManager taskManager = new WorkoutTaskManager(this);

    public ExerciseFragment() {
        // Required empty public constructor
    }

    public static ExerciseFragment newInstance() {
        ExerciseFragment fragment = new ExerciseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exercise, container, false);

        MainActivity act = (MainActivity) getActivity();
        viewmodel = act.getViewModel();

        taskManager.executeLoadExerciseByIdAsync(viewmodel.getDB(), viewmodel.getExerciseId());

        return v;
    }

    @Override
    public void onLoadExerciseComplete(List<Exercise> exercises) {

    }

    @Override
    public void onLoadExerciseComplete(Exercise exercise) {
        MainActivity act = (MainActivity) getActivity();

        TextView name = act.findViewById(R.id.exerciseName);
        name.setText(exercise.name);

        GifImageView img = act.findViewById(R.id.exerciseImage);
        img.setImageResource(exercise.image);

        TextView description = act.findViewById(R.id.exerciseDescription);
        description.setText(exercise.description);

        TextView muscles = act.findViewById(R.id.exerciseMuscles);
        muscles.setText(exercise.muscle_groups);
    }

    @Override
    public void onAddExerciseComplete(Workout_Exercise we) {

    }

    @Override
    public void onLoadWorkoutComplete(List<Workout> workouts) {

    }

    @Override
    public void onLoadWorkoutComplete(Workout workout) {

    }

    @Override
    public void onLoadWorkout_ExerciseComplete(List<Exercise> exercises, List<Workout_Exercise> wes) {

    }

    @Override
    public void onLoadWorkout_ExerciseComplete(Workout_Exercise we, Exercise exercise) {

    }
}