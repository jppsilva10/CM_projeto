package com.example.fitnessadvisor;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.FoodDao;
import com.example.fitnessadvisor.Database.Hydration;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.MealDao;
import com.example.fitnessadvisor.Database.Meal_Food;
import com.example.fitnessadvisor.Database.Meal_FoodDao;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;
import com.example.fitnessadvisor.Database.Workout_Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    HashMap<Long, List<Meal_Food>> expandableListDetail = new HashMap<Long, List<Meal_Food>>();
    HashMap<Long, Food> foods_list;
    NutritionTaskManager taskManager = new NutritionTaskManager(this);
    SharedViewModel viewmodel;

    final Calendar myCalendar= Calendar.getInstance();
    TextView title;
    FloatingActionButton addBtn;
    List<Meal> meal_list;
    protected Parcelable state;


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

        addBtn = v.findViewById(R.id.addMeal);

        title= v.findViewById(R.id.date);
        title.setText("" + today);

        if(viewmodel.getSetDate().equals("")){
            taskManager.executeLoadMealAsync(viewmodel.getDB(), today);
            viewmodel.setSetDate(today);
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
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(act,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertDialog mydialog;

                builder.setTitle("Meal Name: ");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Meal meal = new Meal();
                                meal.title = input.getText().toString();
                                meal.day = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                meal.time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

                                taskManager.executeInsertMeal(viewmodel.getDB(), meal, viewmodel.getSetDate());
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

                ((AlertDialog) mydialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                input.addTextChangedListener(new TextWatcher() {
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
            }
        });

        return v;
    }


    private void updateLabel(){
        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        String myFormat="dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
        title.setText("" + dateFormat.format(myCalendar.getTime()));
        viewmodel.setSetDate(dateFormat.format(myCalendar.getTime()));
        taskManager.executeLoadMealAsync(viewmodel.getDB(), dateFormat.format(myCalendar.getTime()));
        
        if(today.equals(dateFormat.format(myCalendar.getTime()))){
            addBtn.setVisibility(View.VISIBLE);
        }
        else{
            addBtn.setVisibility(View.GONE);
        }
    }


    @Override
    public void onLoadProfileComplete(Profile profile, boolean empty) {

    }

    @Override
    public void onLoadMealComplete(HashMap<Long, List<Meal_Food>> mealList, List<Meal> meals, HashMap<Long, Food> foods) {

        try {
            foods_list = foods;
            meal_list = meals;
            expandableListDetail = mealList;
            fillTheScreen();
        }catch(Exception e){

        }
    }

    @Override
    public void onLoadFoodComplete(List<Food> food) {

    }

    @Override
    public void onInsertMealComplete(long mealId) {
        //viewmodel.setMealId(mealId);
        /*
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, AddMealFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("stack")
                .commit();
         */
    }

    @Override
    public void onDeleteMealComplete() {
        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        try {
            if (viewmodel.getSetDate().equals("")) {
                taskManager.executeLoadMealAsync(viewmodel.getDB(), today);
            } else {
                taskManager.executeLoadMealAsync(viewmodel.getDB(), viewmodel.getSetDate());
            }
        }catch(Exception e){
        }
    }

    @Override
    public void onLoadHydrationComplete(List<Hydration> hydration) {

    }

    @Override
    public void onUpdateHydrationComplete() {

    }

    @Override
    public void onLoadFoodFromMeal(Meal meal, List<Food> foodList) {

    }
    @Override
    public void onLoadBMR(float BMR) {

    }


    public void fillTheScreen(){
        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        expandableListAdapter = new CustomExpandableListAdapter(act, act.getApplicationContext(), meal_list, expandableListDetail, foods_list);
        System.out.println("COUNT1: " + expandableListAdapter.getGroupCount());
        System.out.println("COUNT: " + expandableListAdapter.getChildrenCount(0));

        state = expandableListView.onSaveInstanceState();
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });


        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View v, int position, long id) {
                String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                if(viewmodel.getSetDate().equals(today)) {
                    PopupMenu popup = new PopupMenu(act, expandableListView);
                    popup.getMenuInflater().inflate(R.menu.remove_meal_popup, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals("Remove Meal")) {
                                taskManager.executeDeleteMeal(viewmodel.getDB(), meal_list.get(position).id);
                            }
                            return true;
                        }
                    });

                    popup.show();//showing popup menu
                }
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


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
        expandableListView.onRestoreInstanceState(state);

    }

}

