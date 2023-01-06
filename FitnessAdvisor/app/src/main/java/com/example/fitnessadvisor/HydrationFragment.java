package com.example.fitnessadvisor;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Hydration;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Meal_Food;
import com.example.fitnessadvisor.Database.Profile;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class HydrationFragment extends Fragment implements NutritionTaskManager.Callback{

    protected SharedViewModel viewmodel;
    protected NutritionTaskManager taskManager = new NutritionTaskManager(this);
    TextView waterLeft;
    EditText waterGoal;
    EditText waterDrank;
    EditText hydrationDate;
    final Calendar myCalendar= Calendar.getInstance();
    Button updateButton;
    TextView waterGoalPast;
    TextView waterDrankPast;

    public HydrationFragment() {
        // Required empty public constructor
    }

    public static HydrationFragment newInstance() {
        HydrationFragment fragment = new HydrationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hydration, container, false);
        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        waterLeft = v.findViewById(R.id.waterLeft);
        waterGoal = v.findViewById(R.id.waterGoal);
        waterDrank = v.findViewById(R.id.waterDrank);
        waterGoalPast = v.findViewById(R.id.waterGoalPast);
        waterDrankPast = v.findViewById(R.id.waterDrankPast);
        updateButton = v.findViewById(R.id.updateBtn);

        taskManager.executeLoadHydrationAsync(viewmodel.getDB(), today);

        hydrationDate = v.findViewById(R.id.hydrationDate);
        hydrationDate.setText(today);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        hydrationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(act,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                float updatedObjective = Float.valueOf(waterGoal.getText().toString());
                float updatedQuantity = Float.valueOf(waterDrank.getText().toString());
                taskManager.executeUpdateHydration(viewmodel.getDB(), today, updatedObjective, updatedQuantity);

            }
        });

        return v;
    }

    private void updateLabel(){
        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        String myFormat="dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
        hydrationDate.setText(dateFormat.format(myCalendar.getTime()));

        taskManager.executeLoadHydrationAsync(viewmodel.getDB(), dateFormat.format(myCalendar.getTime()));

    }

    @Override
    public void onLoadBMR(float BMR) {

    }

    @Override
    public void onLoadProfileComplete(Profile profile, boolean empty) {

    }

    @Override
    public void onLoadMealComplete(HashMap<Long, List<Meal_Food>> mealList, List<Meal> meals, HashMap<Long, Food> foods) {

    }

    @Override
    public void onLoadFoodComplete(List<Food> food) {

    }

    @Override
    public void onInsertMealComplete(long mealId) {

    }

    @Override
    public void onDeleteMealComplete() {

    }

    @Override
    public void onLoadHydrationComplete(List<Hydration> hydration) {
        if(hydration.size() == 0) {
            waterDrank.setVisibility(View.GONE);
            waterGoal.setVisibility(View.GONE);
            waterDrankPast.setVisibility(View.GONE);
            waterGoalPast.setVisibility(View.GONE);
            waterLeft.setVisibility(View.GONE);
            return;
        }

        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        if(hydration.get(0).day.equals(today)) {
            waterDrank.setText(Float.toString(hydration.get(0).quantity));
            waterGoal.setText(Float.toString(hydration.get(0).objective));

            waterDrank.setVisibility(View.VISIBLE);
            waterGoal.setVisibility(View.VISIBLE);
            waterLeft.setVisibility(View.VISIBLE);
            waterDrankPast.setVisibility(View.GONE);
            waterGoalPast.setVisibility(View.GONE);
        }
        else{
            waterDrankPast.setText(Float.toString(hydration.get(0).quantity));
            waterGoalPast.setText(Float.toString(hydration.get(0).objective));

            waterDrankPast.setVisibility(View.VISIBLE);
            waterGoalPast.setVisibility(View.VISIBLE);
            waterLeft.setVisibility(View.VISIBLE);
            waterDrank.setVisibility(View.GONE);
            waterGoal.setVisibility(View.GONE);
        }

        DecimalFormat df = new DecimalFormat("#.00");
        float waterLeftNumber = Float.valueOf(df.format(hydration.get(0).objective - hydration.get(0).quantity));
        if (waterLeftNumber < 0) {
            waterLeftNumber = 0;
        }
        waterLeft.setText(Float.toString(waterLeftNumber));
    }

    @Override
    public void onUpdateHydrationComplete() {
        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        taskManager.executeLoadHydrationAsync(viewmodel.getDB(), today);
    }

    @Override
    public void onLoadFoodFromMeal(Meal meal, List<Food> foodList) {

    }


}
