package com.example.fitnessadvisor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExerciseListFragment extends Fragment implements TaskManager.Callback {

    protected long selected_id;
    protected List exercises = new ArrayList();
    protected ListView list;
    protected SharedViewModel viewmodel;
    protected TaskManager taskManager = new TaskManager(this);
    protected ImageButton butt;
    protected long workoutId;

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

        registerForContextMenu(list);

        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();
        taskManager.executeLoadExerciseAsync(viewmodel.getDB());
        workoutId = viewmodel.getWorkoutId();
        setListListener();
        return v;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.popup_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog mydialog;

        switch(item.getItemId()) {
            case R.id.option1:
                //add exercise to the workout
                taskManager.executeExerciseToWorkout(viewmodel.getDB(),workoutId,selected_id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }

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
    public void onLoadExerciseComplete(List<Exercise> exercises) {
        MyAdapterExercise myAdapter = new MyAdapterExercise(getActivity().getApplicationContext(), exercises);
        list.setAdapter(myAdapter);
        setListListener();
    }

    @Override
    public void onLoadExerciseComplete(Exercise exercise) {

    }

    @Override
    public void onLoadMealComplete(HashMap<String, List<String>> mealList) {

    }

}