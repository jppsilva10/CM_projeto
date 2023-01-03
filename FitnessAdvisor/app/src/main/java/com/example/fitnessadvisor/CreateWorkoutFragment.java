package com.example.fitnessadvisor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;
import com.example.fitnessadvisor.Database.Workout_Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;


public class CreateWorkoutFragment extends Fragment implements WorkoutTaskManager.Callback {

    protected Button butt;
    protected SharedViewModel viewmodel;
    protected WorkoutTaskManager taskManager = new WorkoutTaskManager(this);
    protected Button genAuto;
    protected boolean auto = false;

    public CreateWorkoutFragment() {

    }

    public static CreateWorkoutFragment newInstance() {
        CreateWorkoutFragment fragment = new CreateWorkoutFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_workout, container, false);

        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity act = (MainActivity) getActivity();

        butt = act.findViewById(R.id.button2);
        genAuto = act.findViewById(R.id.genAuto);
        setListener();
    }

    public void setListener(){
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auto = false;

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                AlertDialog mydialog;

                builder.setTitle("New Workout Plan");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                View v = inflater.inflate(R.layout.dialog_create_workout, null);

                builder.setView(v)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Workout workout = new Workout();
                        EditText name = v.findViewById(R.id.nameValue);
                        workout.name = name.getText().toString();
                        Spinner spinner = v.findViewById(R.id.daysValue);
                        workout.days = spinner.getSelectedItemPosition()+1;
                        viewmodel.setDay(1);
                        taskManager.executeInsertWorkout(viewmodel.getDB(), workout);
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                mydialog = builder.create();
                mydialog.show();

                EditText name = v.findViewById(R.id.nameValue);

                ((AlertDialog) mydialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                name.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (TextUtils.isEmpty(s)) {
                            ((AlertDialog) mydialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                        } else {
                            ((AlertDialog) mydialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                        }

                    }
                });

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.days, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                Spinner spinner = v.findViewById(R.id.daysValue);
                spinner.setAdapter(adapter);

            }
        });

        genAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auto = true;

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                AlertDialog mydialog;

                builder.setTitle("New Workout Plan");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                View v = inflater.inflate(R.layout.dialog_create_workout, null);

                builder.setView(v)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Workout workout = new Workout();
                                EditText name = v.findViewById(R.id.nameValue);
                                workout.name = name.getText().toString();
                                Spinner spinner = v.findViewById(R.id.daysValue);
                                workout.days = spinner.getSelectedItemPosition()+1;
                                viewmodel.setDay(1);
                                taskManager.executeCreateWorkout(viewmodel.getDB(), workout);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                mydialog = builder.create();
                mydialog.show();

                EditText name = v.findViewById(R.id.nameValue);

                ((AlertDialog) mydialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                name.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (TextUtils.isEmpty(s)) {
                            ((AlertDialog) mydialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                        } else {
                            ((AlertDialog) mydialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                        }

                    }
                });

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.days, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                Spinner spinner = v.findViewById(R.id.daysValue);
                spinner.setAdapter(adapter);

            }
        });
    }

    @Override
    public void onLoadWorkoutComplete(List<Workout> workouts) {
        viewmodel.setWorkoutId(workouts.get(workouts.size() -1 ).id);
    }

    @Override
    public void onLoadWorkoutComplete(Workout workout) {
        viewmodel.setWorkoutId(workout.id);

        if(auto){
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, WorkoutFragment.class, null)
                    .commit();
        }
        else {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, ManuallyWorkoutFragment.class, null)
                    .commit();
        }
    }

    @Override
    public void onLoadWorkout_ExerciseComplete(List<Exercise> exercises, List<Workout_Exercise> wes) {

    }

    @Override
    public void onLoadWorkout_ExerciseComplete(Workout_Exercise we, Exercise exercise) {

    }

    @Override
    public void onLoadExerciseComplete(List<Exercise> exercises) {

    }

    @Override
    public void onLoadExerciseComplete(Exercise exercise) {

    }

    @Override
    public void onAddExerciseComplete(Workout_Exercise we) {

    }
}