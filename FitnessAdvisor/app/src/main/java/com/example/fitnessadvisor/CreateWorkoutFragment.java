package com.example.fitnessadvisor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;

import java.util.List;


public class CreateWorkoutFragment extends Fragment implements TaskManager.Callback {

    protected Button butt;
    protected SharedViewModel viewmodel;
    protected TaskManager taskManager = new TaskManager(this);

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
        butt = v.findViewById(R.id.button2);
        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();
        setListener();
        return v;
    }

    public void setListener(){
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertDialog mydialog;

                builder.setTitle("Workout Name:");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Workout workout = new Workout();
                        workout.name = input.getText().toString();
                        long id = taskManager.executeInsertWorkout(viewmodel.getDB(), workout);
                        viewmodel.setWorkout(workout.name);
                        viewmodel.setWorkoutId(id);
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container_view, ManuallyWorkoutFragment.class, null)
                                .commit();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                mydialog = builder.create();
                mydialog.show();

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
    public void onLoadExerciseComplete(List<Exercise> exercises) {

    }
}