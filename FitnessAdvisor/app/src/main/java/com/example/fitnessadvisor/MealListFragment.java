package com.example.fitnessadvisor;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.FoodDao;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.MealDao;
import com.example.fitnessadvisor.Database.Meal_Food;
import com.example.fitnessadvisor.Database.Meal_FoodDao;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Locale;
import java.util.Map;

public class MealListFragment extends Fragment implements TaskManager.Callback{

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
    TaskManager taskManager = new TaskManager(this);
    SharedViewModel viewmodel;

    final Calendar myCalendar= Calendar.getInstance();
    EditText editText;
    Button addBtn;
    Button removeBtn;

    public MealListFragment() {
        // Required empty public constructor
    }

    public static MealListFragment newInstance() {
        MealListFragment fragment = new MealListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        taskManager.executeLoadMealAsync(viewmodel.getDB(), today);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meal_list, container, false);

        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView);

        editText=(EditText) v.findViewById(R.id.pageTitle);
        editText.setText("Refeições do dia " + today);
        addBtn = (Button) v.findViewById(R.id.addMeal);
        removeBtn = (Button) v.findViewById(R.id.removeMeal);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(act,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return v;
    }

    private void updateLabel(){
        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        String myFormat="dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
        editText.setText("Refeições do dia " + dateFormat.format(myCalendar.getTime()));
        taskManager.executeLoadMealAsync(viewmodel.getDB(), dateFormat.format(myCalendar.getTime()));
        
        if(today.equals(dateFormat.format(myCalendar.getTime()))){
            addBtn.setVisibility(View.VISIBLE);
            removeBtn.setVisibility(View.VISIBLE);
        }
        else{
            addBtn.setVisibility(View.GONE);
            removeBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadExerciseComplete(List<Exercise> exercises) {

    }

    @Override
    public void onLoadWorkoutComplete(List<Workout> workouts) {

    }

    @Override
    public void onLoadMealComplete(HashMap<String, List<String>> mealList) {
        expandableListDetail = mealList;
        fillTheScreen();
    }

    @Override
    public void onLoadProfileComplete(Profile profile, boolean empty) {

    }

    @Override
    public void onProfileUpdateComplete(Profile profile) {

    }

    public void fillTheScreen(){
        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(act.getApplicationContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(act.getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(act.getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        act.getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
    }

}

