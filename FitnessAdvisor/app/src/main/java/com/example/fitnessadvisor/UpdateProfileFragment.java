package com.example.fitnessadvisor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Profile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateProfileFragment extends Fragment implements TaskManager.Callback {

    protected View v;

    protected SharedViewModel viewmodel;
    protected Profile profile;

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

        Button b = act.findViewById(R.id.create_profile);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Profile p = new Profile();

                MainActivity act = (MainActivity) getActivity();
                EditText text;
                Spinner spinner;

                text = act.findViewById(R.id.nameValue);
                p.name = String.valueOf(text.getText());

                spinner = act.findViewById(R.id.genderValue);
                p.gender = spinner.getSelectedItem().toString();

                text = act.findViewById(R.id.birthDateValue);
                try {
                    p.birth_date = new SimpleDateFormat("dd/MM/yyyy").parse(text.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                spinner = act.findViewById(R.id.lifeStyleValue);
                p.life_style = spinner.getSelectedItem().toString();

                text = act.findViewById(R.id.heightValue);
                p.height = Float.parseFloat(text.getText().toString());

                text = act.findViewById(R.id.weightValue);
                p.weight = Float.parseFloat(text.getText().toString());


                if (profile == null) {
                    taskManager.executeProfileInsertionAsync(viewmodel.getDB(), p);
                } else {
                    taskManager.executeProfileUpdateAsync(viewmodel.getDB(), p);
                }
            }
        });

        Spinner spinner = act.findViewById(R.id.genderValue);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(act, R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner = act.findViewById(R.id.lifeStyleValue);
        adapter = ArrayAdapter.createFromResource(act, R.array.lifeStyles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

    }

    @Override
    public void onLoadProfileComplete(Profile profile) {
        if (profile != null) {
            this.profile = profile;

            MainActivity act = (MainActivity) getActivity();
            EditText text;
            Spinner spinner;

            text = act.findViewById(R.id.nameValue);
            text.setText(profile.name);

            spinner = act.findViewById(R.id.genderValue);
            int gender = 0;
            if (profile.gender.equals("Female")) {
                gender = 1;
            }
            spinner.setSelection(gender);

            text = act.findViewById(R.id.birthDateValue);
            Calendar cal = Calendar.getInstance();
            cal.setTime(profile.birth_date);
            text.setText(new StringBuilder().append(cal.DAY_OF_MONTH).append("/").append(cal.MONTH).append("/").append(cal.YEAR));

            spinner = act.findViewById(R.id.lifeStyleValue);
            int lifeStyle = 0;
            if (profile.gender.equals("Active")) {
                lifeStyle = 1;
            }
            spinner.setSelection(lifeStyle);

            text = act.findViewById(R.id.heightValue);
            text.setText("" + profile.height);

            text = act.findViewById(R.id.weightValue);
            text.setText("" + profile.weight);

            text = act.findViewById(R.id.targetWeightLable);
            text.setText("" + profile.target_weight);

            text = act.findViewById(R.id.goalDeadlineValue);
            cal.setTime(profile.goal_deadline);
            text.setText(new StringBuilder().append(cal.DAY_OF_MONTH).append("/").append(cal.MONTH).append("/").append(cal.YEAR));
        }
    }

    @Override
    public void onProfileUpdateComplete(Profile profile) {
        this.profile = profile;
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, AccountFragment.class, null)
                .commit();
    }
}