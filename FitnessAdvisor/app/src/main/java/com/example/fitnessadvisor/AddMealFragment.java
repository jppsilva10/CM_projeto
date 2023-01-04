package com.example.fitnessadvisor;

import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;
import com.example.fitnessadvisor.Database.Workout_Exercise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddMealFragment extends Fragment implements TaskManager.Callback{

    protected SearchView searchFood;
    protected ListView added_food_list_view;
    protected ListView food_list_view;
    SharedViewModel viewmodel;
    protected TaskManager taskManager = new TaskManager(this);
    List<Food> food_list;
    List<Food> listItemsToAdd = new ArrayList<Food>();
    Button submitBtn;
    EditText editTitle;
    long id;

    public AddMealFragment() {
        // Required empty public constructor
    }

    public static AddMealFragment newInstance() {
        AddMealFragment fragment = new AddMealFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_meal, container, false);

        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        submitBtn = v.findViewById(R.id.submit);
        food_list_view = v.findViewById(R.id.food_list);
        added_food_list_view = v.findViewById(R.id.added_food_list);
        searchFood = v.findViewById(R.id.searchFood);
        editTitle = v.findViewById(R.id.editMealTitle);

        taskManager.executeFoodSearchAsync(viewmodel.getDB(), "%" + "" + "%");

        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Meal meal = new Meal();
                meal.title = editTitle.getText().toString();
                meal.day = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                meal.time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

                taskManager.executeInsertMeal(viewmodel.getDB(), meal);


            }
        });
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        searchFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                taskManager.executeFoodSearchAsync(viewmodel.getDB(), "%" + newText + "%");
                return true;
            }
        });

    }

    public void setListListener() {
        food_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){
                Food food = food_list.get(position);
                listItemsToAdd.add(food);

                MyAdapterFood myAdapter = new MyAdapterFood(getActivity().getApplicationContext(), listItemsToAdd);
                added_food_list_view.setAdapter(myAdapter);
                setAddItemListListener();
            }
        });
        food_list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View v, int position, long id) {

                return false;
            }
        });
    }

    public void setAddItemListListener(){
        added_food_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){
                listItemsToAdd.remove(position);

                MyAdapterFood myAdapter = new MyAdapterFood(getActivity().getApplicationContext(), listItemsToAdd);
                added_food_list_view.setAdapter(myAdapter);
                setAddItemListListener();
            }
        });
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
    public void onLoadWorkoutComplete(Workout workout) {

    }

    @Override
    public void onLoadWorkout_ExerciseComplete(List<Exercise> exercises, List<Workout_Exercise> wes) {

    }

    @Override
    public void onLoadMealComplete(HashMap<String, List<String>> mealList, List<Meal> meals) {


    }

    @Override
    public void onLoadFoodComplete(List<Food> food) {
        food_list = food;
        MyAdapterFood myAdapter = new MyAdapterFood(getActivity().getApplicationContext(), food);
        food_list_view.setAdapter(myAdapter);
        setListListener();
    }

    @Override
    public void onInsertMealComplete(long mealId) {
        id = mealId;

        for(int i = 0; i < listItemsToAdd.size(); i++){
            taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), id, listItemsToAdd.get(i).id);
        }

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, MealListFragment.class, null)
                .commit();
    }

    @Override
    public void onLoadFoodFromMeal(Meal meal, List<Food> foodList) {

    }

    @Override
    public void onLoadProfileComplete(Profile profile, boolean empty) {

    }

    @Override
    public void onProfileUpdateComplete(Profile profile) {

    }

}
