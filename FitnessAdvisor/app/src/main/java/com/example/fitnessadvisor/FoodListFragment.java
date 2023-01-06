package com.example.fitnessadvisor;

import android.app.AlertDialog;
import android.content.ClipData;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.example.fitnessadvisor.Database.Hydration;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Meal_Food;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;
import com.example.fitnessadvisor.Database.Workout_Exercise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FoodListFragment extends Fragment implements NutritionTaskManager.Callback{

    protected SearchView searchFood;
    protected ListView food_list_view;
    protected EditText edit;
    SharedViewModel viewmodel;
    protected NutritionTaskManager taskManager = new NutritionTaskManager(this);
    List<Food> food_list;
    List<Food> listItemsToAdd = new ArrayList<Food>();
    Button submitBtn;
    long selected_food = -1;
    protected Parcelable state;
    Meal meal;
    float quantity = 100;

    public FoodListFragment() {
        // Required empty public constructor
    }

    public static FoodListFragment newInstance() {
        FoodListFragment fragment = new FoodListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_food_list, container, false);

        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        submitBtn = v.findViewById(R.id.submit);
        submitBtn.setVisibility(View.INVISIBLE);
        food_list_view = v.findViewById(R.id.food_list);
        searchFood = v.findViewById(R.id.searchFood);
        edit = v.findViewById(R.id.quantityValue);

        edit.addTextChangedListener(new TextWatcher() {
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

                } else {
                    quantity = Float.parseFloat(edit.getText().toString());

                    MyAdapterFood myAdapter = new MyAdapterFood(getActivity().getApplicationContext(), food_list, selected_food, quantity);
                    state = food_list_view.onSaveInstanceState();
                    food_list_view.setAdapter(myAdapter);
                    setListListener();
                    food_list_view.onRestoreInstanceState(state);
                }

            }
        });

        taskManager.executeFoodSearchAsync(viewmodel.getDB(), "%" + "" + "%");

        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                taskManager.executeInsertFoodIntoMeal(viewmodel.getDB(), viewmodel.getMealId(), selected_food, quantity);

                act.getSupportFragmentManager().popBackStack();

                act
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, MealListFragment.class, null)
                        .commit();

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
                selected_food = id;
                submitBtn.setVisibility(View.VISIBLE);

                MyAdapterFood myAdapter = new MyAdapterFood(getActivity().getApplicationContext(), food_list, selected_food, quantity);
                state = food_list_view.onSaveInstanceState();
                food_list_view.setAdapter(myAdapter);
                setListListener();
                food_list_view.onRestoreInstanceState(state);
            }
        });

    }

    @Override
    public void onLoadMealComplete(HashMap<Long, List<Meal_Food>> mealList, List<Meal> meals, HashMap<Long, Food> foods) {


    }

    @Override
    public void onLoadFoodComplete(List<Food> food) {
        food_list = food;
        MyAdapterFood myAdapter = new MyAdapterFood(getActivity().getApplicationContext(), food, selected_food, quantity);
        state = food_list_view.onSaveInstanceState();
        food_list_view.setAdapter(myAdapter);
        setListListener();
        food_list_view.onRestoreInstanceState(state);
    }

    @Override
    public void onInsertMealComplete(long mealId) {

    }

    @Override
    public void onDeleteMealComplete() {

    }

    @Override
    public void onLoadHydrationComplete(List<Hydration> hydration) {

    }


    @Override
    public void onUpdateHydrationComplete() {

    }

    @Override
    public void onLoadFoodFromMeal(Meal meal, List<Food> foodList) {
        this.meal = meal;

    }


}
