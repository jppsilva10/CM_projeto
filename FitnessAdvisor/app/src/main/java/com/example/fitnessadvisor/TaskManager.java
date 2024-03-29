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
        void onAddExerciseComplete(Workout_Exercise we);
        void onLoadWorkoutComplete(List<Workout> workouts);
        void onLoadWorkoutComplete(Workout workout);
        void onLoadWorkout_ExerciseComplete(List<Exercise> exercises, List<Workout_Exercise> wes);
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

    public void executeLoadWorkoutAsync(AppDatabase db, Long workoutId) {
        executor.execute(() -> {

            WorkoutDao workoutDao = db.workoutDao();
            Workout workout = workoutDao.loadById(workoutId);

            handler.post(() -> {
                calback.onLoadWorkoutComplete(workout);
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

    public void executeAddExerciseAsync(AppDatabase db, Workout_Exercise we){
        executor.execute(() -> {

            Workout_ExerciseDao workout_exerciseDao = db.workout_exerciseDao();
            workout_exerciseDao.insert(we);

            handler.post(() -> {
                calback.onAddExerciseComplete(we);
            });
        });
    }

    public void executeLoadWorkout_ExerciseAsync(AppDatabase db, long workoutId){
        executor.execute(() -> {

            System.out.println("-------executeLoadWorkout_ExerciseAsync-------");

            Workout_ExerciseDao workout_exerciseDao = db.workout_exerciseDao();
            List<Workout_Exercise> wes = workout_exerciseDao.loadByWorkout(workoutId);

            ExerciseDao exerciseDao = db.exerciseDao();
            List<Exercise> exercises = exerciseDao.getAll();

            System.out.println("-------wes-------");

            WorkoutDao workoutDao = db.workoutDao();
            WorkoutAndExercise workoutAndExercise = workoutDao.getWorkoutWithExercises(workoutId);

            System.out.println("-------workoutAndExercise-------");


            handler.post(() -> {
                calback.onLoadWorkout_ExerciseComplete(workoutAndExercise.exercises, wes);
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
        });
    }

    public void executeInsertWorkout(AppDatabase db, Workout workout){
        executor.execute(() -> {


            WorkoutDao workoutDao = db.workoutDao();
            workout.id = workoutDao.insert(workout);

            handler.post(() -> {
                calback.onLoadWorkoutComplete(workout);
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

    public void executeExerciseToWorkout(AppDatabase db,long workoutId, long exerciseId) {
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

            for (int i = 0; i < WEDao.getAll().size(); i++)
                System.out.println(WEDao.getAll().get(i).repetitions);

            handler.post(() -> {

            });
        });
    }

}
