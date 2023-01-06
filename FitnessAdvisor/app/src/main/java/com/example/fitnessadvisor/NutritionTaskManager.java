package com.example.fitnessadvisor;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.example.fitnessadvisor.Database.AppDatabase;
import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.ExerciseDao;
import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.FoodDao;
import com.example.fitnessadvisor.Database.Hydration;
import com.example.fitnessadvisor.Database.HydrationDao;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.MealDao;
import com.example.fitnessadvisor.Database.Meal_Food;
import com.example.fitnessadvisor.Database.Meal_FoodDao;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.ProfileDao;

import com.example.fitnessadvisor.Database.Workout;
import com.example.fitnessadvisor.Database.WorkoutAndExercise;
import com.example.fitnessadvisor.Database.WorkoutDao;
import com.example.fitnessadvisor.Database.Workout_Exercise;
import com.example.fitnessadvisor.Database.Workout_ExerciseDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NutritionTaskManager {
    final Executor executor = Executors.newSingleThreadExecutor();
    final Handler handler = new Handler(Looper.getMainLooper());
    protected Callback calback;

    public NutritionTaskManager(Callback calback) {
        this.calback = calback;
    }

    public interface Callback{
        void onLoadMealComplete(HashMap<Long, List<Meal_Food>> mealList, List<Meal> meals, HashMap<Long, Food> foods);
        void onLoadFoodComplete(List<Food> food);
        void onInsertMealComplete(long mealId);
        void onDeleteMealComplete();
        void onLoadHydrationComplete(List<Hydration> hydration);
        void onUpdateHydrationComplete();
        void onLoadFoodFromMeal(Meal meal, List<Food> foodList);
    }

    public void executeLoadFoodAsync(AppDatabase db){
        executor.execute(() -> {

            FoodDao foodDao = db.foodDao();
            List<Food> food = foodDao.getAll();

            handler.post(() -> {
                calback.onLoadFoodComplete(food);
            });
        });
    }

    public void executeLoadFoodFromMealAsync(AppDatabase db, long mealId){
        executor.execute(() -> {

            Meal_FoodDao meal_foodDao = db.meal_foodDao();
            List<Meal_Food> meal_food = meal_foodDao.loadByMeal(mealId);

            List<Food> food_list = new ArrayList<Food>();
            FoodDao foodDao = db.foodDao();
            for(int i = 0; i < meal_food.size(); i++){
                Food food = foodDao.loadById(meal_food.get(i).food);
                food_list.add(food);
            }

            MealDao mealDao = db.mealDao();
            Meal meal = mealDao.loadById(mealId);

            handler.post(() -> {
                calback.onLoadFoodFromMeal(meal, food_list);
            });
        });
    }


    public void executeLoadMealAsync(AppDatabase db, String day){
        executor.execute(() -> {
            MealDao mealDao = db.mealDao();
            List<Meal> meals = mealDao.loadByDate(day);

            Meal_FoodDao meal_foodDao = db.meal_foodDao();
            FoodDao foodDao = db.foodDao();

            LinkedHashMap<Long, List<Meal_Food>> mealList = new LinkedHashMap<Long, List<Meal_Food>>();
            LinkedHashMap<Long, Food> foodList = new LinkedHashMap<Long, Food>();
            for(int i = 0; i < meals.size(); i++){
                List<Meal_Food> meal_food_list = meal_foodDao.loadByMeal(meals.get(i).id);
                for(int j = 0; j < meal_food_list.size(); j++){
                    Food food = foodDao.loadById(meal_food_list.get(j).food);
                    foodList.put(meal_food_list.get(j).food, food);
                }
                mealList.put(meals.get(i).id, meal_food_list);
            }

            handler.post(() -> {
                calback.onLoadMealComplete(mealList, meals, foodList);
            });
        });
    }

    public void executeFoodSearchAsync(AppDatabase db, String name){
        executor.execute(() -> {

            FoodDao foodDao = db.foodDao();
            List<Food> food = foodDao.loadByName(name);

            handler.post(() -> {
                calback.onLoadFoodComplete(food);
            });
        });
    }


    public long executeInsertMeal(AppDatabase db, Meal meal){
        executor.execute(() -> {
            long id;

            MealDao mealDao = db.mealDao();
            id = mealDao.insert(meal);

            handler.post(() -> {
                calback.onInsertMealComplete(id);
            });
        });

        return meal.id;
    }

    public long executeInsertMeal(AppDatabase db, Meal meal, String day){
        executor.execute(() -> {
            long id;

            MealDao mealDao = db.mealDao();
            id = mealDao.insert(meal);

            List<Meal> meals = mealDao.loadByDate(day);

            Meal_FoodDao meal_foodDao = db.meal_foodDao();
            FoodDao foodDao = db.foodDao();

            LinkedHashMap<Long, List<Meal_Food>> mealList = new LinkedHashMap<Long, List<Meal_Food>>();
            LinkedHashMap<Long, Food> foodList = new LinkedHashMap<Long, Food>();
            for(int i = 0; i < meals.size(); i++){
                List<Meal_Food> meal_food_list = meal_foodDao.loadByMeal(meals.get(i).id);
                for(int j = 0; j < meal_food_list.size(); j++){
                    Food food = foodDao.loadById(meal_food_list.get(j).food);
                    foodList.put(meal_food_list.get(j).food, food);
                }
                mealList.put(meals.get(i).id, meal_food_list);
            }

            handler.post(() -> {
                calback.onLoadMealComplete(mealList, meals, foodList);
            });
        });

        return meal.id;
    }

    public long executeInsertFood(AppDatabase db, Food food){
        executor.execute(() -> {


            FoodDao foodDao = db.foodDao();
            foodDao.insert(food);

            handler.post(() -> {

            });
        });
        return food.id;
    }

    public void executeInsertFoodIntoMeal(AppDatabase db, long mealId, long foodId, float quantity){
        executor.execute(() -> {

            Meal_FoodDao MealFoodDao = db.meal_foodDao();
            Meal_Food meal_food = new Meal_Food();

            meal_food.food = foodId;
            meal_food.meal = mealId;
            meal_food.weight = quantity;
            MealFoodDao.insert(meal_food);

            handler.post(() -> {

            });
        });
    }

    public void executeDeleteMeal(AppDatabase db, long mealId){
        executor.execute(() -> {

            MealDao mealDao = db.mealDao();
            Meal meal = mealDao.loadById(mealId);

            Meal_FoodDao meal_foodDao = db.meal_foodDao();
            List<Meal_Food> meal_food = meal_foodDao.loadByMeal(mealId);

            for(int i = 0; i < meal_food.size(); i++){
                meal_foodDao.delete(meal_food.get(i));
            }

            mealDao.delete(meal);

            handler.post(() -> {
                calback.onDeleteMealComplete();
            });
        });
    }

    public long executeInsertHydration(AppDatabase db, Hydration hydration){
        executor.execute(() -> {


            HydrationDao hydrationDao = db.hydrationDao();
            List<Hydration> hydrationList = hydrationDao.loadByDay(hydration.day);
            if(hydrationList.size() == 0) hydrationDao.insert(hydration);

            handler.post(() -> {

            });
        });
        return hydration.id;
    }

    public void executeUpdateHydration(AppDatabase db, String day, float objective, float quantity){
        executor.execute(() -> {

            HydrationDao hydrationDao = db.hydrationDao();
            List<Hydration> hydration = hydrationDao.loadByDay(day);

            hydration.get(0).objective = objective;
            hydration.get(0).quantity = quantity;

            hydrationDao.update(hydration.get(0));

            handler.post(() -> {
                calback.onUpdateHydrationComplete();
            });
        });
    }

    public void executeLoadHydrationAsync(AppDatabase db, String day){
        executor.execute(() -> {

            HydrationDao hydrationDao = db.hydrationDao();
            List<Hydration> hydration = hydrationDao.loadByDay(day);

            handler.post(() -> {
                calback.onLoadHydrationComplete(hydration);
            });
        });
    }


}
