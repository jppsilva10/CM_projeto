package com.example.fitnessadvisor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Profile;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class AccountFragment extends Fragment implements TaskManager.Callback{

    protected View v;

    protected SharedViewModel viewmodel;
    protected Profile profile;

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
    public void onLoadProfileComplete(Profile profile) {
        if(profile == null){
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, NoProfileFragment.class, null)
                    .commit();
        }
        this.profile = profile;
        TextView text;
        text = v.findViewById(R.id.genderValue);
        text.setText(profile.gender);
        text = v.findViewById(R.id.ageValue);

        Calendar now = Calendar.getInstance();
        now.setTime(new Date(System.currentTimeMillis()));
        Calendar bd = Calendar.getInstance();
        bd.setTime(profile.birth_date);
        int age = now.get(Calendar.YEAR) - bd.get(Calendar.YEAR);
        if (now.get(Calendar.MONTH) > bd.get(Calendar.MONTH) || now.get(Calendar.MONTH) == bd.get(Calendar.MONTH) && now.get(Calendar.DATE) > bd.get(Calendar.DATE))) {
            age--;
        }

        text.setText(age);
        text = v.findViewById(R.id.lifeStyleValue);
        text.setText(profile.life_style);
        text = v.findViewById(R.id.heightValue);
        text.setText(""+profile.height);
        text = v.findViewById(R.id.weightValue);
        text.setText(""+profile.weight);
    }

    @Override
    public void onProfileUpdateComplete(Profile profile) {
        this.profile = profile;
    }
}