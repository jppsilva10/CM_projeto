package com.example.fitnessadvisor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AccountFragment extends Fragment implements TaskManager.Callback{

    protected View v;

    protected SharedViewModel viewmodel;
    protected Profile profile = null;

    protected TaskManager taskManager = new TaskManager(this);

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
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
        v = inflater.inflate(R.layout.fragment_account, container, false);

        MainActivity act = (MainActivity) getActivity();
        viewmodel = act.getViewModel();

        taskManager.executeLoadProfileAsync(viewmodel.getDB());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity act = (MainActivity) getActivity();

        Button b = act.findViewById(R.id.editProfile);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                act
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, UpdateProfileFragment.class, null)
                        .commit();
            }
        });
    }

    @Override
    public void onLoadProfileComplete(Profile profile, boolean empty) {
        if(empty){
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, NoProfileFragment.class, null)
                    .commit();
            return;
        }
        MainActivity act = (MainActivity) getActivity();

        this.profile = profile;
        TextView text;
        text = act.findViewById(R.id.username);
        text.setText(profile.name);
        text = act.findViewById(R.id.genderValue);
        text.setText(profile.gender);
        text = act.findViewById(R.id.ageValue);

        Calendar now = Calendar.getInstance();
        now.setTime(new Date(System.currentTimeMillis()));
        Calendar bd = Calendar.getInstance();
        bd.setTime(profile.birth_date);
        int age = now.get(Calendar.YEAR) - bd.get(Calendar.YEAR);
        if (now.get(Calendar.MONTH) < bd.get(Calendar.MONTH) || now.get(Calendar.MONTH) == bd.get(Calendar.MONTH) && now.get(Calendar.DAY_OF_MONTH) < bd.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }

        text.setText("" + age);

        text = act.findViewById(R.id.lifeStyleValue);
        text.setText(profile.life_style);

        text = act.findViewById(R.id.heightValue);
        text.setText(""+profile.height + " cm");

        text = act.findViewById(R.id.weightValue);
        text.setText(""+profile.weight + " Kg");

        text = act.findViewById(R.id.bmiValue);
        float bmi = (float) (profile.weight /(Math.pow(profile.height/100, 2)));
        text.setText(String.format("%.2f", bmi));
    }

    @Override
    public void onProfileUpdateComplete(Profile profile) {
        this.profile = profile;
    }

    @Override
    public void onLoadWorkoutComplete(List<Workout> workouts){

    };
    @Override
    public void onLoadExerciseComplete(List<Exercise> exercises) {

    }

    @Override
    public void onLoadExerciseComplete(Exercise exercise) {

    }

    @Override
    public void onLoadMealComplete(List<Meal> meals) {

    }
}