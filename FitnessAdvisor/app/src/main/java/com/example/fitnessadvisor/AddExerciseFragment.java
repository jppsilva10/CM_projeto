package com.example.fitnessadvisor;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;
import com.example.fitnessadvisor.Database.Workout_Exercise;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class AddExerciseFragment extends Fragment implements TaskManager.Callback{

    protected SharedViewModel viewmodel;

    protected TaskManager taskManager = new TaskManager(this);

    public AddExerciseFragment() {
        // Required empty public constructor
    }

    public static AddExerciseFragment newInstance() {
        AddExerciseFragment fragment = new AddExerciseFragment();
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
        View v = inflater.inflate(R.layout.fragment_add_exercise, container, false);

        MainActivity act = (MainActivity) getActivity();
        viewmodel = act.getViewModel();

        taskManager.executeLoadExerciseByIdAsync(viewmodel.getDB(), viewmodel.getExerciseId());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity act = (MainActivity) getActivity();

        Button b = act.findViewById(R.id.addExercise);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Workout_Exercise we = new Workout_Exercise();
                we.workout = viewmodel.getWorkoutId();
                we.exercise = viewmodel.getExerciseId();

                MainActivity act = (MainActivity) getActivity();
                EditText text;

                text = act.findViewById(R.id.setsValue);
                we.sets = Integer.parseInt(String.valueOf(text.getText()));

                text = act.findViewById(R.id.repsValue);
                we.repetitions = Integer.parseInt(String.valueOf(text.getText()));

                text = act.findViewById(R.id.weightValue);
                we.weight = Float.parseFloat(String.valueOf(text.getText()));

                taskManager.executeAddExerciseAsync(viewmodel.getDB(), we);

            }
        });

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
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, ExerciseListFragment.class, null)
                .commit();
    }

    @Override
    public void onLoadWorkoutComplete(List<Workout> workouts) {

    }

    @Override
    public void onLoadMealComplete(HashMap<String, List<String>> mealList) {

    }

    @Override
    public void onLoadProfileComplete(Profile profile, boolean empty) {

    }

    @Override
    public void onProfileUpdateComplete(Profile profile) {

    }
}