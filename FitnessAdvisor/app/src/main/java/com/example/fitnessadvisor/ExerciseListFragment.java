package com.example.fitnessadvisor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;

import java.util.List;

public class ExerciseListFragment extends Fragment implements TaskManager.Callback {

    protected ListView list;
    protected SharedViewModel viewmodel;
    protected TaskManager taskManager = new TaskManager(this);
    protected ImageButton butt;

    public ExerciseListFragment() {

    }

    public static ExerciseListFragment newInstance() {
        ExerciseListFragment fragment = new ExerciseListFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        list = v.findViewById(R.id.exercise_list);
        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();
        taskManager.executeLoadExerciseAsync(viewmodel.getDB());
        setListListener();
        return v;
    }

    public void setListListener() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){

                viewmodel.setWorkoutId(id);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, ExerciseFragment.class, null)
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
    public void onLoadExerciseComplete(List<Exercise> exercises) {
        MyAdapterExercise myAdapter = new MyAdapterExercise(getActivity().getApplicationContext(), exercises);
        list.setAdapter(myAdapter);
        setListListener();
    }

    @Override
    public void onLoadMealComplete(List<Meal> meals) {

    }

}