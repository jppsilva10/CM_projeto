package com.example.fitnessadvisor;

import java.security.spec.ECField;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Hydration;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Meal_Food;
import com.example.fitnessadvisor.Database.PopulateDatabase;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;
import com.example.fitnessadvisor.Database.Workout_Exercise;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class NutritionFragment extends Fragment implements NutritionTaskManager.Callback{

    NutritionTaskManager taskManager = new NutritionTaskManager(this);
    SharedViewModel viewmodel;
    private float kcal = 0.0f;
    private float proteins = 0.0f;
    private float carbohydrates = 0.0f;
    private float fat = 0.0f;
    private ProgressBar progress;
    private TextView text;
    private TextView text2;
    private TextView hydra;

    PieChart chart;

    private TextView bmr;
    private TextView bmr2;

    float dailyCal = 2000;
    protected boolean no_profile = false;

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
        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nutrition, container, false);

        //only use the following two functions if database is empty
        //in the next iterations, new food is gonna be generated for the same meal ids and it's a problem
        //PutFoodIntoDatabase();
        //PutMealsIntoDatabase();

        //PopulateDatabase.populateFoods(viewmodel.getDB(), taskManager);

        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        taskManager.executeLoadMealAsync(viewmodel.getDB(), today);


        progress = v.findViewById(R.id.progress);
        text = v.findViewById(R.id.kcal_num);
        text2 = v.findViewById(R.id.calories);

        chart = v.findViewById(R.id.pieChartMarcronutrients);

        taskManager.executeGetBMR(viewmodel.getDB());

        progress = v.findViewById(R.id.progress);
        text = v.findViewById(R.id.kcal_num);
        text2 = v.findViewById(R.id.calories);
        bmr = v.findViewById(R.id.bmr);
        bmr2 = v.findViewById(R.id.caloriesday);
        hydra = v.findViewById(R.id.hydrationNumber);

        taskManager.executeLoadHydrationAsync(viewmodel.getDB(), today);
        taskManager.executeLoadProfileAsync(viewmodel.getDB());

        Button b = v.findViewById(R.id.goToMealList);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, MealListFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("stack")
                        .commit();
            }
        });

        Button h = v.findViewById(R.id.goToHydration);
        h.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, HydrationFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("stack")
                        .commit();
            }
        });

        return v;
    }

    private void setUpChartView(){
        chart.addPieSlice(new PieModel("Lípidos", fat, Color.parseColor("#E97451")));
        chart.addPieSlice(new PieModel("Carbohidratos", carbohydrates, Color.parseColor("#6495ED")));
        chart.addPieSlice(new PieModel("Proteínas", proteins, Color.parseColor("#228B22")));
        chart.startAnimation();
    }

    private void PutMealsIntoDatabase()
    {
        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        Meal meal = new Meal();
        meal.title = "Pequeno Almoço";
        meal.day = currentDate;
        meal.time = currentTime;
        taskManager.executeInsertMeal(viewmodel.getDB(), meal);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 1, 1, 100);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 1, 3, 100);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 1, 5, 100);

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        meal = new Meal();
        meal.title = "Almoço";
        meal.day = currentDate;
        meal.time = currentTime;
        taskManager.executeInsertMeal(viewmodel.getDB(), meal);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 2, 2, 100);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 2, 4, 100);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 2, 6, 100);

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        meal = new Meal();
        meal.title = "Jantar";
        meal.day = currentDate;
        meal.time = currentTime;
        taskManager.executeInsertMeal(viewmodel.getDB(), meal);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 3, 3, 100);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 3, 4, 100);
        taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), 3, 5, 100);
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
    public void onLoadBMR(float BMR) {
        dailyCal = BMR+400;
        if(no_profile){
            System.out.println("entrou");
            bmr.setText("Only available with a profile");
            bmr2.setVisibility(View.GONE);
        }
        else{
            bmr.setText(String.valueOf((int)BMR));
            bmr2.setText("Calories/Day");
        }
    }

    @Override
    public void onLoadProfileComplete(Profile profile, boolean empty) {
        no_profile = empty;
        System.out.println(no_profile);
    }

    @Override
    public void onLoadMealComplete(HashMap<Long, List<Meal_Food>> mealList, List<Meal> meals, HashMap<Long, Food> foods) {

        try {
            CustomExpandableListAdapter adapter = new CustomExpandableListAdapter((MainActivity) getActivity(), getActivity().getApplicationContext(), meals, mealList, foods);

            if (foods.size() == 0) {
                progress.setProgress(0);
                kcal=0;
                text.setText(String.valueOf((int) kcal));
                text2.setText("Kcal");
            } else {
                kcal = adapter.calculateTotalCalories();
                carbohydrates = adapter.calculateTotalCarbs();
                proteins = adapter.calculateTotalProteins();
                fat = adapter.calculateTotalFat();

                int value = (int)(100 *  (kcal / dailyCal));
                if(!no_profile){
                    progress.setProgress(value);
                }

                text.setText(String.valueOf((int) kcal));
                text2.setText("Kcal");
                if(no_profile){
                    //progress.setVisibility(View.INVISIBLE);
                    //text2.setVisibility(View.INVISIBLE);
                    //text.setText("(Only available with a profile)");
                }
            }

            setUpChartView();
        }catch(Exception e){

        }
    }

    @Override
    public void onLoadFoodComplete(List<Food> food) {
        List<Food> foods = food;
        if(foods.size() == 0){
            progress.setProgress(0);
        }
        else{
            kcal = 0.0f;
            for(int i=0;i<foods.size();i++){
                Food f = foods.get(i);
                kcal = kcal + (f.calories/100);
                carbohydrates = carbohydrates + (f.calories/100);
                proteins = proteins + (f.proteins/100);
                fat = fat + (f.fat/100);
            }
            int value = 100*((int) kcal)/2000;
            progress.setProgress(value);
            text.setText(String.valueOf((int)kcal));
            text2.setText("kcal");
        }

        setUpChartView();
    }

    @Override
    public void onInsertMealComplete(long mealId) {

    }

    @Override
    public void onDeleteMealComplete() {

    }

    @Override
    public void onLoadHydrationComplete(List<Hydration> hydration) {
        if(hydration.size() == 0) return;

        DecimalFormat df = new DecimalFormat("#.00");
        float waterLeftNumber = Float.valueOf(df.format(hydration.get(0).objective - hydration.get(0).quantity));
        if (waterLeftNumber < 0) {
            waterLeftNumber = 0;
        }
        hydra.setText(Float.toString(waterLeftNumber));
    }


    @Override
    public void onUpdateHydrationComplete() {

    }

    @Override
    public void onLoadFoodFromMeal(Meal meal, List<Food> foodList) {
    }

}