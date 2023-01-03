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
import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;
import com.example.fitnessadvisor.Database.Workout_Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.List;


public class ManuallyWorkoutFragment extends Fragment implements WorkoutTaskManager.Callback {

    protected ListView list;
    protected FloatingActionButton butt;
    protected TabLayout tabLayout;
    protected Workout workout;
    protected SharedViewModel viewmodel;
    protected long selected_id;
    protected WorkoutTaskManager taskManager = new WorkoutTaskManager(this);

    public ManuallyWorkoutFragment() {

    }

    public static ManuallyWorkoutFragment newInstance() {
        ManuallyWorkoutFragment fragment = new ManuallyWorkoutFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manually_workout, container, false);

        list = v.findViewById(R.id.workout_list);

        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        butt = v.findViewById(R.id.add_button);

        tabLayout = v.findViewById(R.id.tabs);

        taskManager.executeLoadWorkoutAsync(viewmodel.getDB(), viewmodel.getWorkoutId());
        taskManager.executeLoadWorkout_ExerciseByDayAsync(viewmodel.getDB(), viewmodel.getWorkoutId(), viewmodel.getDay());
        setListener();
        return v;
    }
    public void setListener(){
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewmodel.setWorkout_ExerciseId(-1);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, ExerciseListFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("stack")
                        .commit();
            }
        });
    }

    public void setListListener() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){

                //viewmodel.setExerciseId(id);
                System.out.println("--------- id: " + id);
                viewmodel.setWorkout_ExerciseId(id);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, AddExerciseFragment.class, null)
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

    public void setTabListner(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewmodel.setDay(tab.getPosition()+1);
                taskManager.executeLoadWorkout_ExerciseByDayAsync(viewmodel.getDB(), viewmodel.getWorkoutId(), viewmodel.getDay());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onLoadWorkoutComplete(List<Workout> workouts) {

    }

    @Override
    public void onLoadWorkoutComplete(Workout workout) {
        this.workout = workout;
        System.out.println("-----workout-----");
        System.out.println(workout);

        try {
            TextView text = getActivity().findViewById(R.id.WorkoutName);
            text.setText(workout.name);

            for (int i = 1; i <= workout.days; i++) {
                tabLayout.addTab(tabLayout.newTab().setText("Day " + i));
            }

            setTabListner();

        } catch (Exception e) {

        }

    }

    @Override
    public void onLoadWorkout_ExerciseComplete(List<Exercise> exercises, List<Workout_Exercise> wes) {
        try {
            MyAdapterExerciseWorkout myAdapter = new MyAdapterExerciseWorkout(getActivity().getApplicationContext(), exercises, wes);
            list.setAdapter(myAdapter);
            setListListener();
        }catch(Exception e){

        }
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