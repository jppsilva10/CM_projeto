package com.example.fitnessadvisor;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;
import com.example.fitnessadvisor.Database.Workout_Exercise;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class ViewMealFragment extends Fragment implements TaskManager.Callback{

    protected SharedViewModel viewmodel;
    protected TaskManager taskManager = new TaskManager(this);
    TableLayout table;
    TextView mealTitle;
    TextView mealDay;
    TextView mealTime;

    public ViewMealFragment() {
        // Required empty public constructor
    }

    public static ViewMealFragment newInstance() {
        ViewMealFragment fragment = new ViewMealFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_view_meal, container, false);
        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        mealTitle = v.findViewById(R.id.mealTitle);
        mealDay = v.findViewById(R.id.mealDate);
        mealTime = v.findViewById(R.id.mealTime);

        table = (TableLayout) v.findViewById(R.id.foodTable);
        taskManager.executeLoadFoodFromMealAsync(viewmodel.getDB(), viewmodel.getMealId());
        return v;
    }

    public TextView getTemplateTableTextView(String text){
        MainActivity act = (MainActivity)getActivity();
        TextView tv = new TextView(act);

        tv.setText(text);
        tv.setTextSize(14);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);

        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1.0f);
        rowParams.gravity = Gravity.CENTER;
        tv.setLayoutParams(rowParams);
        return tv;
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

    @Override
    public void onLoadWorkoutComplete(List<Workout> workouts) {

    }

    @Override
    public void onLoadMealComplete(HashMap<String, List<String>> mealList, List<Meal> meals) {

    }

    @Override
    public void onLoadFoodComplete(List<Food> food) {

    }

    @Override
    public void onInsertMealComplete(long mealId) {

    }

    @Override
    public void onLoadFoodFromMeal(Meal meal, List<Food> foodList) {
        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        mealTitle.setText(meal.title);
        mealDay.setText(meal.day);
        mealTime.setText(meal.time);

        float totalCalories = 0;
        float totalCarbohydrates = 0;
        float totalProteins = 0;
        float totalFat = 0;
        for(int i = 0; i < foodList.size(); i++){
            TableRow row = new TableRow(act);
            TextView name = getTemplateTableTextView(foodList.get(i).name);
            TextView calories = getTemplateTableTextView(Float.toString(foodList.get(i).calories));
            TextView carbohydrates = getTemplateTableTextView(Float.toString(foodList.get(i).carbohydrates));
            TextView proteins = getTemplateTableTextView(Float.toString(foodList.get(i).proteins));
            TextView fat = getTemplateTableTextView(Float.toString(foodList.get(i).fat));

            row.addView(name);
            row.addView(calories);
            row.addView(carbohydrates);
            row.addView(proteins);
            row.addView(fat);
            table.addView(row);

            DecimalFormat df = new DecimalFormat("#.0");
            totalCalories = Float.valueOf(df.format(totalCalories + foodList.get(i).calories));
            totalCarbohydrates = Float.valueOf(df.format(totalCarbohydrates + foodList.get(i).carbohydrates));
            totalProteins = Float.valueOf(df.format(totalProteins + foodList.get(i).proteins));
            totalFat = Float.valueOf(df.format(totalFat + foodList.get(i).fat));
         }

        TableRow row = new TableRow(act);
        TextView name = getTemplateTableTextView("Total");
        TextView calories = getTemplateTableTextView(Float.toString(totalCalories));
        TextView carbohydrates = getTemplateTableTextView(Float.toString(totalCarbohydrates));
        TextView proteins = getTemplateTableTextView(Float.toString(totalProteins));
        TextView fat = getTemplateTableTextView(Float.toString(totalFat));

        row.addView(name);
        row.addView(calories);
        row.addView(carbohydrates);
        row.addView(proteins);
        row.addView(fat);
        table.addView(row);
    }

    @Override
    public void onLoadProfileComplete(Profile profile, boolean empty) {

    }

    @Override
    public void onProfileUpdateComplete(Profile profile) {

    }
}
