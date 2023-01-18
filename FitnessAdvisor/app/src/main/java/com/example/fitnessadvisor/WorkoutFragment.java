package com.example.fitnessadvisor;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


public class WorkoutFragment extends Fragment implements WorkoutTaskManager.Callback {

    protected ListView list;
    protected int[] value = {-1, -1, -1};
    protected Button butt;
    protected Button edit;
    protected TabLayout tabLayout;
    protected Workout workout;
    protected SharedViewModel viewmodel;
    protected long selected_id;
    MyAdapterExerciseWorkout myAdapter;
    protected WorkoutTaskManager taskManager = new WorkoutTaskManager(this);
    protected Parcelable state;

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
        butt = v.findViewById(R.id.button_start);
        MainActivity act = (MainActivity) getActivity();
        viewmodel = act.getViewModel();

        edit = v.findViewById(R.id.editWorkout);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                act
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, ManuallyWorkoutFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("stack")
                        .commit();
            }
        });

        tabLayout = v.findViewById(R.id.tabs);
        taskManager.executeLoadWorkoutAsync(viewmodel.getDB(), viewmodel.getWorkoutId());
        taskManager.executeLoadWorkout_ExerciseByDayAsync(viewmodel.getDB(), viewmodel.getWorkoutId(), viewmodel.getDay());
        setListener();
        return v;
    }

    public void setListListener() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                Exercise exercise = (Exercise) myAdapter.getItem(position);

                viewmodel.setExerciseId(exercise.id);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, ExerciseFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("stack")
                        .commit();
            }
        });
    }

    public void setListener() {
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(butt.getText().equals("Finish")){
                    edit.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.VISIBLE);
                    value[viewmodel.getDay()-1]=-1;
                    //alert stating the training is completed and redirect
                    Toast toast = Toast.makeText(getActivity(), "Today's training is complete", Toast.LENGTH_SHORT);
                    toast.show();
                    taskManager.LoadExecutor(viewmodel.getDB(),viewmodel.getWorkoutId(),viewmodel.getDay());
                    butt.setText("Start");
                }
                else{
                    if(butt.getText().equals("Start") && myAdapter.getCount() == 0){
                        Toast toast = Toast.makeText(getActivity(), "You need to have atleast 1 exercise to start the training!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        edit.setVisibility(View.INVISIBLE);
                        tabLayout.setVisibility(View.INVISIBLE);
                        value[viewmodel.getDay()-1]++;
                        if(value[viewmodel.getDay()-1]+1>list.getLastVisiblePosition() || value[viewmodel.getDay()-1]-1<list.getFirstVisiblePosition()){
                            list.smoothScrollToPosition(value[viewmodel.getDay()-1]);
                        }

                        taskManager.LoadExecutor(viewmodel.getDB(),viewmodel.getWorkoutId(), viewmodel.getDay());
                        if(value[viewmodel.getDay()-1] == myAdapter.getCount() - 1){
                            butt.setText("Finish");
                        }
                        else{
                            butt.setText("Next");
                        }
                    }
                }
            }
        });
    }

    public void setTabListner() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(value[viewmodel.getDay()-1]!=-1){
                    tabLayout.getTabAt(viewmodel.getDay()-1).select();
                    return;
                }

                viewmodel.setDay(tab.getPosition() + 1);
                if (value[viewmodel.getDay()-1]!=-1){
                    butt.setText("Next");
                }
                else{
                    butt.setText("Start");
                }
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

            tabLayout.getTabAt(viewmodel.getDay()-1).select();

            setTabListner();

        } catch (Exception e) {

        }

    }

    @Override
    public void onLoadWorkout_ExerciseComplete(List<Exercise> exercises, List<Workout_Exercise> wes) {
        try {
            myAdapter = new MyAdapterExerciseWorkout(getActivity().getApplicationContext(), exercises, wes, value[viewmodel.getDay() - 1]);
            state = list.onSaveInstanceState();
            list.setAdapter(myAdapter);
            setListListener();
            list.onRestoreInstanceState(state);
        }catch (Exception e){

        }
    }

    @Override
    public void onLoadWorkout_ExerciseComplete(Workout_Exercise we, Exercise exercise) {

    }

    @Override
    public void onLoadProfileComplete(Profile profile, boolean empty) {

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