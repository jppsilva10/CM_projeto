package com.example.fitnessadvisor;

import java.security.spec.ECField;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
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
    private TextView date;

    PieChart chart;

    private TextView bmr;
    private TextView bmr2;

    float dailyCal = 2000;
    protected boolean no_profile = false;

    final Calendar myCalendar= Calendar.getInstance();

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

        bmr = v.findViewById(R.id.bmr);
        bmr2 = v.findViewById(R.id.caloriesday);
        hydra = v.findViewById(R.id.hydrationNumber);
        date = v.findViewById(R.id.dateNutrition);

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


        date.setText("" + today);

        DatePickerDialog.OnDateSetListener dateCalendar = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(act,dateCalendar,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return v;
    }

    private void updateLabel(){
        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        String myFormat="dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
        date.setText("" + dateFormat.format(myCalendar.getTime()));
        viewmodel.setSetDate(dateFormat.format(myCalendar.getTime()));

        kcal = 0.0f;
        proteins = 0.0f;
        carbohydrates = 0.0f;
        fat = 0.0f;

        taskManager.executeLoadMealAsync(viewmodel.getDB(), dateFormat.format(myCalendar.getTime()));
        taskManager.executeLoadHydrationAsync(viewmodel.getDB(), dateFormat.format(myCalendar.getTime()));
    }

    private void setUpChartView(){
        chart.clearChart();
        chart.addPieSlice(new PieModel("Lípidos", fat, Color.parseColor("#E97451")));
        chart.addPieSlice(new PieModel("Carbohidratos", carbohydrates, Color.parseColor("#6495ED")));
        chart.addPieSlice(new PieModel("Proteínas", proteins, Color.parseColor("#228B22")));
        chart.startAnimation();
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
            text.setText("");
            text2.setText("");
        }
        else{
            kcal = 0.0f;
            proteins = 0.0f;
            carbohydrates = 0.0f;
            fat = 0.0f;
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