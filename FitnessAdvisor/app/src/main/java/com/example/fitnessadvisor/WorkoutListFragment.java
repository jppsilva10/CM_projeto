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
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;

import java.util.List;


public class WorkoutListFragment extends Fragment implements TaskManager.Callback{

    protected ListView list;
    protected SharedViewModel viewmodel;
    protected TaskManager taskManager = new TaskManager(this);
    protected ImageButton butt;
    protected Button help; //This is just a button to add exercises preemptively


    public WorkoutListFragment() {
        // Required empty public constructor
    }

    public static WorkoutListFragment newInstance(String param1, String param2) {
        WorkoutListFragment fragment = new WorkoutListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_workout_list, container, false);

        list = v.findViewById(R.id.workout_list);

        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();
        //helper = viewmodel.getHelper();
        taskManager.executeLoadWorkoutAsync(viewmodel.getDB());
        butt = v.findViewById(R.id.add_button);
        help = v.findViewById(R.id.add_exercise);
        setListener();
        return v;
    }

    public void setListener(){
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, CreateWorkoutFragment.class, null)
                        .commit();
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Edit these values to add different exercises
                Exercise exercise = new Exercise();
                exercise.name = "Standing lat push-down";
                exercise.description = "Grasp the attachment with an overhand grip and your arms fully extended. Keeping your arms straight, squeeze your lats to bring the bar down to your thighs. Pause briefly at the bottom of the rep, squeeze your lats hard, and slowly return to the starting position. Maintain tightness in your core and repeat.";
                exercise.type = "treino";
                exercise.image = String.valueOf(R.drawable.standinglat);
                exercise.muscle_group = "Back";
                taskManager.executeExerciseInsertionAsync(viewmodel.getDB(),exercise);
            }
        });
    }

    public void setListListener() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){

                viewmodel.setWorkoutId(id);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, WorkoutFragment.class, null)
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
        MyAdapter myAdapter = new MyAdapter(getActivity().getApplicationContext(), workouts);
        list.setAdapter(myAdapter);
        setListListener();
    }
    @Override
    public void onLoadExerciseComplete(List<Exercise> exercises) {

    }

}