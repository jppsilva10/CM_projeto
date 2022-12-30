package com.example.fitnessadvisor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fitnessadvisor.Database.Profile;

public class UpdateProfileFragment extends Fragment implements TaskManager.Callback{

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
        v =  inflater.inflate(R.layout.fragment_update_profile, container, false);

        MainActivity act = (MainActivity) getActivity();
        viewmodel = act.getViewModel();

        Button b = v.findViewById(R.id.create_profile);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Profile p = new Profile();

                if(profile==null){
                    taskManager.executeProfileInsertionAsync(viewmodel.getDB(), p);
                }
                else{
                    taskManager.executeProfileUpdateAsync(viewmodel.getDB(), p);
                }
            }
        });

        taskManager.executeLoadProfileAsync(viewmodel.getDB());

        return v;
    }

    @Override
    public void onLoadProfileComplete(Profile profile) {

        this.profile = profile;
    }

    @Override
    public void onProfileUpdateComplete(Profile profile) {
        this.profile = profile;
    }
}