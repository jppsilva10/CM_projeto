package com.example.fitnessadvisor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.PopulateDatabase;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;
import com.example.fitnessadvisor.Database.Workout_Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;


public class WorkoutListFragment extends Fragment implements WorkoutTaskManager.Callback{

    protected ListView list;
    protected SharedViewModel viewmodel;
    protected WorkoutTaskManager taskManager = new WorkoutTaskManager(this);
    protected FloatingActionButton butt;
    protected long selected_id;
    protected Parcelable state;
    protected boolean auto = false;


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

        taskManager.executeLoadWorkoutAsync(viewmodel.getDB());

        //PopulateDatabase.populateExercises(viewmodel.getDB(), taskManager);

        registerForContextMenu(list);
        butt = v.findViewById(R.id.add_button);
        setListener();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity act = (MainActivity) getActivity();

        SearchView searchView = (SearchView) act.findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                taskManager.executeWorkoutSearchAsync(viewmodel.getDB(), "%" + newText + "%");
                return true;
            }
        });
    }

    public void setListener(){
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auto = false;

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                AlertDialog mydialog;

                builder.setTitle("New Workout Plan");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                View v = inflater.inflate(R.layout.dialog_create_workout, null);
                builder.setView(v)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CheckBox check = v.findViewById(R.id.checkbox_cheese);
                                if(!check.isChecked()){
                                    Workout workout = new Workout();
                                    EditText name = v.findViewById(R.id.nameValue);
                                    workout.name = name.getText().toString();
                                    Spinner spinner = v.findViewById(R.id.daysValue);
                                    workout.days = spinner.getSelectedItemPosition()+1;
                                    viewmodel.setDay(1);
                                    taskManager.executeInsertWorkout(viewmodel.getDB(), workout);
                                }
                                else{
                                    auto = true;
                                    Workout workout = new Workout();
                                    EditText name = v.findViewById(R.id.nameValue);
                                    workout.name = name.getText().toString();
                                    Spinner spinner = v.findViewById(R.id.daysValue);
                                    workout.days = spinner.getSelectedItemPosition()+1;
                                    viewmodel.setDay(1);
                                    taskManager.executeCreateWorkout(viewmodel.getDB(), workout);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                mydialog = builder.create();
                mydialog.show();
                EditText name = v.findViewById(R.id.nameValue);

                ((AlertDialog) mydialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                name.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (TextUtils.isEmpty(s)) {
                            ((AlertDialog) mydialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                        } else {
                            ((AlertDialog) mydialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                        }

                    }
                });

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.days, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                Spinner spinner = v.findViewById(R.id.daysValue);
                spinner.setAdapter(adapter);
            }
        });
    }

    public void setListListener() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){

                viewmodel.setWorkoutId(id);
                viewmodel.setDay(1);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, WorkoutFragment.class, null)
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
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.popup_menu_workout, menu);

    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.option1:
                taskManager.executeDeleteWorkout(viewmodel.getDB(),selected_id);
                return true;

            case R.id.option2:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                AlertDialog mydialog;

                builder.setTitle("New name for this workout:");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                taskManager.executeChangeName(viewmodel.getDB(),input.getText().toString(),selected_id);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                mydialog = builder.create();
                mydialog.show();

                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public void onLoadWorkoutComplete(List<Workout> workouts) {
        try {
            MyAdapter myAdapter = new MyAdapter(getActivity().getApplicationContext(), workouts);
            state = list.onSaveInstanceState();
            list.setAdapter(myAdapter);
            setListListener();
            list.onRestoreInstanceState(state);
        }catch(Exception e){

        }
    }

    @Override
    public void onLoadWorkoutComplete(Workout workout) {
        viewmodel.setWorkoutId(workout.id);

        if(auto){
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, WorkoutFragment.class, null)
                    .commit();
        }
        else {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, ManuallyWorkoutFragment.class, null)
                    .commit();
        }
    }

    @Override
    public void onLoadWorkout_ExerciseComplete(List<Exercise> exercises, List<Workout_Exercise> wes) {

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