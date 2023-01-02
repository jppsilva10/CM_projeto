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
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;
import com.example.fitnessadvisor.Database.Workout_Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;


public class WorkoutFragment extends Fragment implements TaskManager.Callback {

    protected ListView list;
    protected FloatingActionButton butt;
    protected Workout workout;
    protected SharedViewModel viewmodel;
    protected long selected_id;
    protected TaskManager taskManager = new TaskManager(this);

    public WorkoutFragment() {

    }

    public static WorkoutFragment newInstance() {
        WorkoutFragment fragment = new WorkoutFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workout, container, false);

        list = v.findViewById(R.id.workout_list);

        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        taskManager.executeLoadWorkoutAsync(viewmodel.getDB(), viewmodel.getWorkoutId());
        taskManager.executeLoadWorkout_ExerciseAsync(viewmodel.getDB(), viewmodel.getWorkoutId());
        return v;
    }

    public void setListListener() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){

                viewmodel.setExerciseId(id);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, ExerciseFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("stack")
                        .commit();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View v, int position, long id) {

                selected_id = id;
                return false;
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
    public void onLoadWorkoutComplete(Workout workout) {
        this.workout = workout;
        System.out.println("-----workout-----");
        System.out.println(workout);

        TextView text = getActivity().findViewById(R.id.WorkoutName);
        text.setText(workout.name);

    }

    @Override
    public void onLoadWorkout_ExerciseComplete(List<Exercise> exercises, List<Workout_Exercise> wes) {
        MyAdapterExerciseWorkout myAdapter = new MyAdapterExerciseWorkout(getActivity().getApplicationContext(), exercises, wes);
        list.setAdapter(myAdapter);
        setListListener();
    }

    @Override
    public void onLoadMealComplete(HashMap<String, List<String>> mealList) {

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