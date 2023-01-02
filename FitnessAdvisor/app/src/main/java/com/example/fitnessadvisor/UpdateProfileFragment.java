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

public class UpdateProfileFragment extends Fragment implements TaskManager.Callback {

    protected View v;

    protected SharedViewModel viewmodel;
    protected Profile profile = null;

    protected TaskManager taskManager = new TaskManager(this);

    public UpdateProfileFragment() {
        // Required empty public constructor
    }

    public static UpdateProfileFragment newInstance() {
        UpdateProfileFragment fragment = new UpdateProfileFragment();
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
        v = inflater.inflate(R.layout.fragment_update_profile, container, false);

        MainActivity act = (MainActivity) getActivity();
        viewmodel = act.getViewModel();

        taskManager.executeLoadProfileAsync(viewmodel.getDB());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity act = (MainActivity) getActivity();

        Button b = act.findViewById(R.id.updateProfile);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Profile p = new Profile();

                if(profile!=null){
                    p = profile;
                }

                MainActivity act = (MainActivity) getActivity();
                EditText text;
                Spinner spinner;

                text = act.findViewById(R.id.nameValue);
                p.name = String.valueOf(text.getText());

                spinner = act.findViewById(R.id.genderValueSpinner);
                p.gender = spinner.getSelectedItem().toString();

                text = act.findViewById(R.id.birthDateValue);
                try {
                    p.birth_date = new SimpleDateFormat("dd/MM/yyyy").parse(text.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                spinner = act.findViewById(R.id.lifeStyleValueSpinner);
                p.life_style = spinner.getSelectedItem().toString();

                text = act.findViewById(R.id.heightValue);
                p.height = Float.parseFloat(text.getText().toString());

                text = act.findViewById(R.id.weightValue);
                p.weight = Float.parseFloat(text.getText().toString());

                text = act.findViewById(R.id.targetWeightValue);
                p.target_weight = Float.parseFloat(text.getText().toString());

                text = act.findViewById(R.id.goalDeadlineValue);
                try {
                    p.goal_deadline = new SimpleDateFormat("dd/MM/yyyy").parse(text.getText().toString());
                } catch (ParseException e) {
                    System.out.println("-------ERRO--------");
                    e.printStackTrace();
                }

                if (profile == null) {
                    taskManager.executeProfileInsertionAsync(viewmodel.getDB(), p);
                } else {
                    taskManager.executeProfileUpdateAsync(viewmodel.getDB(), p);
                }
            }
        });

        Spinner spinner = act.findViewById(R.id.genderValueSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(act, R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner = act.findViewById(R.id.lifeStyleValueSpinner);
        adapter = ArrayAdapter.createFromResource(act, R.array.lifeStyles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        EditText date1 = (EditText) act.findViewById(R.id.birthDateValue);
        date1.setInputType(InputType.TYPE_NULL);
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date d = new Date(System.currentTimeMillis());
                if(date1.getText().toString().length()!=0){
                    try {
                        d = new SimpleDateFormat("dd/MM/yyyy").parse(date1.getText().toString());
                    } catch (ParseException e) {

                    }
                }
                final Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(act, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date1.setText(new StringBuilder().append(day).append("/").append(month+1).append("/").append(year));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        EditText date2 = (EditText) act.findViewById(R.id.goalDeadlineValue);
        date2.setInputType(InputType.TYPE_NULL);
        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date d = new Date(System.currentTimeMillis());
                if(date2.getText().toString().length()!=0){
                    try {
                        d = new SimpleDateFormat("dd/MM/yyyy").parse(date2.getText().toString());
                    } catch (ParseException e) {

                    }
                }
                final Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(act, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date2.setText(new StringBuilder().append(day).append("/").append(month+1).append("/").append(year));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

    }

    @Override
    public void onLoadProfileComplete(Profile profile, boolean empty) {
        if (!empty) {
            this.profile = profile;

            MainActivity act = (MainActivity) getActivity();
            EditText text;
            Spinner spinner;

            text = act.findViewById(R.id.nameValue);
            text.setText(profile.name);

            spinner = act.findViewById(R.id.genderValueSpinner);
            int gender = 0;
            if (profile.gender.equals("Female")) {
                gender = 1;
            }
            spinner.setSelection(gender);

            text = act.findViewById(R.id.birthDateValue);
            Calendar cal = Calendar.getInstance();
            cal.setTime(profile.birth_date);
            text.setText(new StringBuilder().append(cal.get(Calendar.DAY_OF_MONTH)).append("/").append(cal.get(Calendar.MONTH)+1).append("/").append(cal.get(Calendar.YEAR)));

            spinner = act.findViewById(R.id.lifeStyleValueSpinner);
            int lifeStyle = 0;
            if (profile.gender.equals("Active")) {
                lifeStyle = 1;
            }
            spinner.setSelection(lifeStyle);

            text = act.findViewById(R.id.heightValue);
            text.setText("" + profile.height);

            text = act.findViewById(R.id.weightValue);
            text.setText("" + profile.weight);

            text = act.findViewById(R.id.targetWeightValue);
            text.setText("" + profile.target_weight);

            text = act.findViewById(R.id.goalDeadlineValue);
            cal.setTime(profile.goal_deadline);
            text.setText(new StringBuilder().append(cal.get(Calendar.DAY_OF_MONTH)).append("/").append(cal.get(Calendar.MONTH)+1).append("/").append(cal.get(Calendar.YEAR)));
        }
    }

    @Override
    public void onProfileUpdateComplete(Profile profile) {
        System.out.println("---------updated---------");
        this.profile = profile;
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, AccountFragment.class, null)
                .commit();
    }
    @Override
    public void onLoadWorkoutComplete(List<Workout> workouts){

    }

    @Override
    public void onLoadMealComplete(HashMap<String, List<String>> mealList) {

    }

    ;
    @Override
    public void onLoadExerciseComplete(List<Exercise> exercises) {

    }

    @Override
    public void onLoadExerciseComplete(Exercise exercise) {

    }

    @Override
    public void onAddExerciseComplete(Workout_Exercise we) {

    }

    @Override
    public void onLoadFoodComplete(List<Food> food) {

    }

    @Override
    public void onInsertMealComplete(long mealId) {

    }
}