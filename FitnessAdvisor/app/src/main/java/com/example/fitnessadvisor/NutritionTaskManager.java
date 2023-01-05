package com.example.fitnessadvisor;

import android.os.Handler;
import android.os.Looper;

import com.example.fitnessadvisor.Database.AppDatabase;
import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.ExerciseDao;
import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.FoodDao;
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
        void onLoadMealComplete(HashMap<String, List<String>> mealList, List<Meal> meals);
        void onLoadFoodComplete(List<Food> food);
        void onInsertMealComplete(long mealId);
        void onDeleteMealComplete();
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

    /*public void executeLoadMealAsync(AppDatabase db){
        executor.execute(() -> {

            MealDao mealDao = db.mealDao();
            List<Meal> meals = mealDao.getAll();

            handler.post(() -> {
                calback.onLoadMealComplete(meals);
            });
        });
    }*/

    public void executeLoadMealAsync(AppDatabase db, String day){
        executor.execute(() -> {
            MealDao mealDao = db.mealDao();
            List<Meal> meals = mealDao.loadByDate(day);

            Meal_FoodDao meal_foodDao = db.meal_foodDao();
            FoodDao foodDao = db.foodDao();

            LinkedHashMap<String, List<String>> mealList = new LinkedHashMap<String, List<String>>();
            for(int i = 0; i < meals.size(); i++){
                List<Meal_Food> meal_food_list = meal_foodDao.loadByMeal(meals.get(i).id);
                List<String> one_meal = new ArrayList<String>();
                for(int j = 0; j < meal_food_list.size(); j++){
                    Food food = foodDao.loadById(meal_food_list.get(j).food);
                    one_meal.add(food.name);
                }
                mealList.put(meals.get(i).title + " " + meals.get(i).day + " " + meals.get(i).time, one_meal);
            }

            handler.post(() -> {
                calback.onLoadMealComplete(mealList, meals);
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

    public long executeInsertFood(AppDatabase db, Food food){
        executor.execute(() -> {


            FoodDao foodDao = db.foodDao();
            foodDao.insert(food);

            handler.post(() -> {

            });
        });
        return food.id;
    }

    public void executeInsertFoodIntoMeal(AppDatabase db, long mealId, long foodId){
        executor.execute(() -> {

            Meal_FoodDao MealFoodDao = db.meal_foodDao();
            Meal_Food meal_food = new Meal_Food();

            meal_food.food = foodId;
            meal_food.meal = mealId;
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


}
