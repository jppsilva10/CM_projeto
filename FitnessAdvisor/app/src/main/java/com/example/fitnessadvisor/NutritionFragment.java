package com.example.fitnessadvisor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class NutritionFragment extends Fragment implements TaskManager.Callback{

    TaskManager taskManager = new TaskManager(this);
    SharedViewModel viewmodel;

    public NutritionFragment() {
        // Required empty public constructor
    }

    public static NutritionFragment newInstance() {
        NutritionFragment fragment = new NutritionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nutrition, container, false);

        //only use the following two functions if database is empty
        //in the next iterations, new food is gonna be generated for the same meal ids and it's a problem
        //PutFoodIntoDatabase();
        //PutMealsIntoDatabase();

        Button b = v.findViewById(R.id.goToMealList);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, MealListFragment.class, null)
                        .commit();
            }
        });

        return v;
    }

    private void PutMealsIntoDatabase()
    {
        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        Meal meal = new Meal();
        meal.title = "Pequeno Almoço";
        meal.day = currentDate;
        meal.time = currentTime;
        taskManager.executeInsertMeal(viewmodel.getDB(), meal);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 1, 1);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 1, 3);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 1, 5);

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        meal = new Meal();
        meal.title = "Almoço";
        meal.day = currentDate;
        meal.time = currentTime;
        taskManager.executeInsertMeal(viewmodel.getDB(), meal);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 2, 2);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 2, 4);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 2, 6);

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        meal = new Meal();
        meal.title = "Jantar";
        meal.day = currentDate;
        meal.time = currentTime;
        taskManager.executeInsertMeal(viewmodel.getDB(), meal);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 3, 3);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 3, 4);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 3, 5);
    }

    private void PutFoodIntoDatabase()
    {
        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        Food food = new Food();
        food.name = "Cenoura";
        food.calories = 25;
        food.fat = (float)0.2;
        food.proteins = (float)0.9;
        food.carbohydrates = (float)9.6;
        taskManager.executeInsertFood(viewmodel.getDB(), food);

        food = new Food();
        food.name = "Carne de Vaca";
        food.calories = 265;
        food.fat = (float)19;
        food.proteins = (float)21;
        food.carbohydrates = (float)0;
        taskManager.executeInsertFood(viewmodel.getDB(), food);

        food = new Food();
        food.name = "Carne de Porco";
        food.calories = 297;
        food.fat = (float)20.8;
        food.proteins = (float)25.7;
        food.carbohydrates = (float)0;
        taskManager.executeInsertFood(viewmodel.getDB(), food);

        food = new Food();
        food.name = "Bacon";
        food.calories = 43;
        food.fat = (float)3.3;
        food.proteins = (float)3;
        food.carbohydrates = (float)0.1;
        taskManager.executeInsertFood(viewmodel.getDB(), food);

        food = new Food();
        food.name = "Couve";
        food.calories = 22;
        food.fat = (float)0.1;
        food.proteins = (float)1.1;
        food.carbohydrates = (float)5.2;
        taskManager.executeInsertFood(viewmodel.getDB(), food);

        food = new Food();
        food.name = "Batatas cozidas";
        food.calories = 161;
        food.fat = (float)1;
        food.proteins = (float)4;
        food.carbohydrates = (float)37;
        taskManager.executeInsertFood(viewmodel.getDB(), food);

    }

    @Override
    public void onLoadExerciseComplete(List<Exercise> exercises) {

    }

    @Override
    public void onLoadExerciseComplete(Exercise exercise) {

    }

    @Override
    public void onLoadWorkoutComplete(List<Workout> workouts) {

    }

    @Override
    public void onLoadMealComplete(HashMap<String, List<String>> mealList) {

    }

    @Override
    public void onLoadProfileComplete(Profile profile, boolean empty) {

    }

    @Override
    public void onProfileUpdateComplete(Profile profile) {

    }
}