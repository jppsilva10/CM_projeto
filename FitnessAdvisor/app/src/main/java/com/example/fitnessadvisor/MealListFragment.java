package com.example.fitnessadvisor;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
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
import com.example.fitnessadvisor.Database.Workout_Exercise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Locale;
import java.util.Map;

public class MealListFragment extends Fragment implements NutritionTaskManager.Callback{

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
    NutritionTaskManager taskManager = new NutritionTaskManager(this);
    SharedViewModel viewmodel;

    final Calendar myCalendar= Calendar.getInstance();
    EditText editText;
    Button addBtn;
    Button removeBtn;
    List<Meal> meal_list;


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

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meal_list, container, false);

        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView);

        addBtn = (Button) v.findViewById(R.id.addMeal);
        removeBtn = (Button) v.findViewById(R.id.removeMeal);

        editText=(EditText) v.findViewById(R.id.pageTitle);
        editText.setText("Refeições do dia " + today);

        if(viewmodel.getSetDate().equals("")){
            taskManager.executeLoadMealAsync(viewmodel.getDB(), today);
        }
        else{
            taskManager.executeLoadMealAsync(viewmodel.getDB(), viewmodel.getSetDate());
        }


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

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, AddMealFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("stack")
                        .commit();
            }
        });

        return v;
    }


    private void updateLabel(){
        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        String myFormat="dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
        editText.setText("Refeições do dia " + dateFormat.format(myCalendar.getTime()));
        viewmodel.setSetDate(dateFormat.format(myCalendar.getTime()));
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
    public void onLoadMealComplete(HashMap<String, List<String>> mealList, List<Meal> meals) {
        meal_list = meals;
        expandableListDetail = mealList;
        fillTheScreen();
    }

    @Override
    public void onLoadFoodComplete(List<Food> food) {

    }

    @Override
    public void onInsertMealComplete(long mealId) {

    }

    @Override
    public void onDeleteMealComplete() {
        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        if(viewmodel.getSetDate().equals("")){
            taskManager.executeLoadMealAsync(viewmodel.getDB(), today);
        }
        else{
            taskManager.executeLoadMealAsync(viewmodel.getDB(), viewmodel.getSetDate());
        }
    }

    @Override
    public void onLoadFoodFromMeal(Meal meal, List<Food> foodList) {

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

        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View v, int position, long id) {
                PopupMenu popup = new PopupMenu(act, expandableListView);
                popup.getMenuInflater().inflate(R.menu.remove_meal_popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Remover Refeição")){
                            taskManager.executeDeleteMeal(viewmodel.getDB(), meal_list.get(position).id);
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
                return false;
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

                Meal selectedMeal = meal_list.get(groupPosition);
                viewmodel.setMealId(selectedMeal.id);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, ViewMealFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("stack")
                        .commit();

                return false;
            }
        });

    }

}

