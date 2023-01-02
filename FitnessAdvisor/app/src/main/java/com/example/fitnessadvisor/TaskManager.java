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
import com.example.fitnessadvisor.Database.WorkoutDao;
import com.example.fitnessadvisor.Database.Workout_Exercise;
import com.example.fitnessadvisor.Database.Workout_ExerciseDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskManager {
    final Executor executor = Executors.newSingleThreadExecutor();
    final Handler handler = new Handler(Looper.getMainLooper());
    protected Callback calback;

    public TaskManager(Callback calback) {
        this.calback = calback;
    }

    public interface Callback{
        void onLoadExerciseComplete(List<Exercise> exercises);
        void onLoadExerciseComplete(Exercise exercise);
        void onLoadWorkoutComplete(List<Workout> workouts);
        void onLoadMealComplete(HashMap<String, List<String>> mealList);
        void onLoadProfileComplete(Profile profile, boolean empty);
        void onProfileUpdateComplete(Profile profile);
    }

    public void executeLoadProfileAsync(AppDatabase db){
        executor.execute(() -> {

            ProfileDao profileDao = db.profileDao();
            List<Profile> profiles = profileDao.getAll();
            Profile profile = new Profile();
            if(profiles.size()!=0)
                profile = profiles.get(0);

            Profile finalProfile = profile;
            handler.post(() -> {
                calback.onLoadProfileComplete(finalProfile, profiles.size()==0);
            });
        });
    }

    public void executeProfileInsertionAsync(AppDatabase db, Profile profile){
        executor.execute(() -> {

            ProfileDao profileDao = db.profileDao();
            profileDao.insert(profile);

            handler.post(() -> {
                calback.onProfileUpdateComplete(profile);
            });
        });
    }

    public void executeProfileUpdateAsync(AppDatabase db, Profile profile){
        executor.execute(() -> {

            ProfileDao profileDao = db.profileDao();
            profileDao.update(profile);

            handler.post(() -> {
                calback.onProfileUpdateComplete(profile);
            });

        });
    }

    public void executeLoadWorkoutAsync(AppDatabase db){
        executor.execute(() -> {

            WorkoutDao workoutDao = db.workoutDao();
            List<Workout> workouts = workoutDao.getAll();

            handler.post(() -> {
                calback.onLoadWorkoutComplete(workouts);
            });
        });
    }

    public void executeWorkoutSearchAsync(AppDatabase db, String name){
        executor.execute(() -> {

            WorkoutDao workoutDao = db.workoutDao();
            List<Workout> workouts = workoutDao.loadByName(name);

            handler.post(() -> {
                calback.onLoadWorkoutComplete(workouts);
            });
        });
    }

    public void executeLoadExerciseAsync(AppDatabase db){
        executor.execute(() -> {

            ExerciseDao exerciseDao = db.exerciseDao();
            List<Exercise> exercises = exerciseDao.getAll();

            handler.post(() -> {
                calback.onLoadExerciseComplete(exercises);
            });
        });
    }

    public void executeLoadExerciseByIdAsync(AppDatabase db, long exerciseId){
        executor.execute(() -> {

            ExerciseDao exerciseDao = db.exerciseDao();
            Exercise exercise = exerciseDao.loadById(exerciseId);

            handler.post(() -> {
                calback.onLoadExerciseComplete(exercise);
            });
        });
    }

    public void executeExerciseSearchAsync(AppDatabase db, String name){
        executor.execute(() -> {

            ExerciseDao exerciseDao = db.exerciseDao();
            List<Exercise> exercises = exerciseDao.loadByName(name);

            handler.post(() -> {
                calback.onLoadExerciseComplete(exercises);
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

    public void executeExerciseInsertionAsync(AppDatabase db, Exercise exercise){
        executor.execute(() -> {
            Profile profile = null;
            ExerciseDao exerciseDao = db.exerciseDao();
            if(exerciseDao.loadByName(exercise.name).size() == 0){
                exerciseDao.insert(exercise);
            }
            for(int i=0;i<exerciseDao.getAll().size();i++)
                System.out.println(exerciseDao.getAll().get(i).name);

            handler.post(() -> {
                calback.onProfileUpdateComplete(profile);
            });
        });
    }

    public void executeInsertWorkout(AppDatabase db, Workout workout){
        executor.execute(() -> {


            WorkoutDao workoutDao = db.workoutDao();
            workoutDao.insert(workout);
            List<Workout> workouts = workoutDao.getAll();
            handler.post(() -> {
                calback.onLoadWorkoutComplete(workouts);
            });
        });
    }

    public void executeDeleteWorkout(AppDatabase db, long id){
        executor.execute(() -> {


            WorkoutDao workoutDao = db.workoutDao();
            Workout workout = workoutDao.loadById(id);
            workoutDao.delete(workout);

            List<Workout> workouts = workoutDao.getAll();

            handler.post(() -> {
                calback.onLoadWorkoutComplete(workouts);
            });
        });
    }

    public void executeExerciseToWorkout(AppDatabase db,long workoutId, long exerciseId){
        executor.execute(() -> {

            Workout_ExerciseDao WEDao = db.workout_exerciseDao();
            Workout_Exercise WE = new Workout_Exercise();

            //WorkoutDao workoutDao = db.workoutDao();
            //Workout workout = workoutDao.loadById(workoutId);

            WE.workout = workoutId;
            WE.exercise = exerciseId;
            WE.sets = 3;
            WE.repetitions = 12;
            WEDao.insert(WE);

            for(int i=0;i<WEDao.getAll().size();i++)
                System.out.println(WEDao.getAll().get(i).repetitions);

            handler.post(() -> {

            });
        });
    }

    public void executeLoadMealAsync(AppDatabase db, String day){
        executor.execute(() -> {
            MealDao mealDao = db.mealDao();
            List<Meal> meals = mealDao.loadByDate(day);

            Meal_FoodDao meal_foodDao = db.meal_foodDao();
            FoodDao foodDao = db.foodDao();

            HashMap<String, List<String>> mealList = new HashMap<String, List<String>>();
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
                calback.onLoadMealComplete(mealList);
            });
        });
    }


    public long executeInsertMeal(AppDatabase db, Meal meal){
        executor.execute(() -> {


            MealDao mealDao = db.mealDao();
            mealDao.insert(meal);

            handler.post(() -> {

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


}
