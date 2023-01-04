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
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WorkoutTaskManager {
    final Executor executor = Executors.newSingleThreadExecutor();
    final Handler handler = new Handler(Looper.getMainLooper());
    protected Callback calback;

    public WorkoutTaskManager(Callback calback) {
        this.calback = calback;
    }

    public interface Callback{
        void onLoadExerciseComplete(List<Exercise> exercises);
        void onLoadExerciseComplete(Exercise exercise);
        void onAddExerciseComplete(Workout_Exercise we);
        void onLoadWorkoutComplete(List<Workout> workouts);
        void onLoadWorkoutComplete(Workout workout);
        void onLoadWorkout_ExerciseComplete(List<Exercise> exercises, List<Workout_Exercise> wes);
        void onLoadWorkout_ExerciseComplete(Workout_Exercise we, Exercise exercise);
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

    public void executeUpdateExerciseAsync(AppDatabase db, Workout_Exercise we){
        executor.execute(() -> {

            Workout_ExerciseDao workout_exerciseDao = db.workout_exerciseDao();
            workout_exerciseDao.update(we);

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

            /*
            List<Exercise> exercises2 = new ArrayList<Exercise>();

            for(int i=0; i<wes.size(); i++){
                long exercise_id = wes.get(i).exercise;
                for(int j=0; j<exercises.size(); j++){
                    if(exercise_id==exercises.get(j).id){
                        exercises2.add(exercises.get(j));
                    }
                }
            }

             */

            System.out.println("-------wes-------");

            /*
            WorkoutDao workoutDao = db.workoutDao();
            WorkoutAndExercise workoutAndExercise = workoutDao.getWorkoutWithExercises(workoutId);

            System.out.println("-------workoutAndExercise-------");

             */


            handler.post(() -> {
                calback.onLoadWorkout_ExerciseComplete(exercises, wes);
            });
        });
    }

    public void executeLoadWorkout_ExerciseByDayAsync(AppDatabase db, long workoutId, int day){
        executor.execute(() -> {

            System.out.println("-------executeLoadWorkout_ExerciseAsync-------");

            Workout_ExerciseDao workout_exerciseDao = db.workout_exerciseDao();
            List<Workout_Exercise> wes = workout_exerciseDao.loadByDay(workoutId, day);

            ExerciseDao exerciseDao = db.exerciseDao();
            List<Exercise> exercises = exerciseDao.getAll();

            System.out.println("-------wes-------");

            /*
            WorkoutDao workoutDao = db.workoutDao();
            WorkoutAndExercise workoutAndExercise = workoutDao.getWorkoutWithExercises(workoutId);

            System.out.println("-------workoutAndExercise-------");

             */


            handler.post(() -> {
                calback.onLoadWorkout_ExerciseComplete(exercises, wes);
            });
        });
    }

    public void executeLoadWorkout_ExerciseByIdAsync(AppDatabase db, long id){
        executor.execute(() -> {

            Workout_ExerciseDao workout_exerciseDao = db.workout_exerciseDao();
            Workout_Exercise we = workout_exerciseDao.loadById(id);

            ExerciseDao exerciseDao = db.exerciseDao();
            Exercise exercise = exerciseDao.loadById(we.exercise);

            handler.post(() -> {
                calback.onLoadWorkout_ExerciseComplete(we, exercise);
            });
        });
    }

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

    protected float beginner;
    protected float novice;
    protected float intermediate;
    protected float advanced;

    public void executeCreateWorkout(AppDatabase db, Workout workout){
        executor.execute(() -> {


            WorkoutDao workoutDao = db.workoutDao();
            workout.id = workoutDao.insert(workout);

            ProfileDao profileDao = db.profileDao();
            Profile profile = profileDao.getAll().get(0);

            Workout_ExerciseDao workout_exerciseDao = db.workout_exerciseDao();

            float percent = 0.7f;

            if(workout.days == 1){
                //day 1

                //Barbell Bench Press
                Workout_Exercise we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 1;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1.25;
                    advanced = (float) 1.75;
                }
                else {
                    beginner = (float) 0.25;
                    novice = (float) 0.50;
                    intermediate = (float) 0.75;
                    advanced = (float) 1.00;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Wide-Grip Lat Pull Down
                we = new Workout_Exercise();
                we.day = 21;
                we.workout = workout.id;
                we.exercise = 1;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1.00;
                    advanced = (float) 1.5;
                }
                else {
                    beginner = (float) 0.3;
                    novice = (float) 0.45;
                    intermediate = (float) 0.7;
                    advanced = (float) 0.95;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Barbell Back Squat
                we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 25;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.75;
                    novice = (float) 1.25;
                    intermediate = (float) 1.50;
                    advanced = (float) 2.25;
                }
                else {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1.25;
                    advanced = (float) 1.50;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Standing Dumbbell Curl
                we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 42;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.1;
                    novice = (float) 0.15;
                    intermediate = (float) 0.3;
                    advanced = (float) 0.50;
                }
                else {
                    beginner = (float) 0.05;
                    novice = (float) 0.1;
                    intermediate = (float) 0.20;
                    advanced = (float) 0.35;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Dumbbell Lateral Raise
                we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 52;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.05;
                    novice = (float) 0.1;
                    intermediate = (float) 0.2;
                    advanced = (float) 0.30;
                }
                else {
                    beginner = (float) 0.05;
                    novice = (float) 0.1;
                    intermediate = (float) 0.15;
                    advanced = (float) 0.20;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

            }
            else if(workout.days == 2){
                //day 1

                //Barbell Bench Press
                Workout_Exercise we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 1;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1.25;
                    advanced = (float) 1.75;
                }
                else {
                    beginner = (float) 0.25;
                    novice = (float) 0.50;
                    intermediate = (float) 0.75;
                    advanced = (float) 1.00;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Incline Barbell Bench Press
                we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 3;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1;
                    advanced = (float) 1.5;
                }
                else {
                    beginner = (float) 0.2;
                    novice = (float) 0.4;
                    intermediate = (float) 0.65;
                    advanced = (float) 1;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Decline Barbell Bench Press
                we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 2;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.5;
                    novice = (float) 1;
                    intermediate = (float) 1.25;
                    advanced = (float) 1.75;
                }
                else {
                    beginner = (float) 0.25;
                    novice = (float) 0.50;
                    intermediate = (float) 0.75;
                    advanced = (float) 1.25;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Wide-Grip Lat Pull Down
                we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 21;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1.00;
                    advanced = (float) 1.5;
                }
                else {
                    beginner = (float) 0.3;
                    novice = (float) 0.45;
                    intermediate = (float) 0.7;
                    advanced = (float) 0.95;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Bent Over Barbell Row
                we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 13;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1;
                    advanced = (float) 1.5;
                }
                else {
                    beginner = (float) 0.25;
                    novice = (float) 0.4;
                    intermediate = (float) 0.65;
                    advanced = (float) 0.9;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //day 2

                //Barbell Back Squat
                we = new Workout_Exercise();
                we.day = 2;
                we.workout = workout.id;
                we.exercise = 25;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.75;
                    novice = (float) 1.25;
                    intermediate = (float) 1.50;
                    advanced = (float) 2.25;
                }
                else {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1.25;
                    advanced = (float) 1.50;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Standing Dumbbell Curl
                we = new Workout_Exercise();
                we.day = 2;
                we.workout = workout.id;
                we.exercise = 42;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.1;
                    novice = (float) 0.15;
                    intermediate = (float) 0.3;
                    advanced = (float) 0.50;
                }
                else {
                    beginner = (float) 0.05;
                    novice = (float) 0.1;
                    intermediate = (float) 0.20;
                    advanced = (float) 0.35;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Cable Rope Pushdown
                we = new Workout_Exercise();
                we.day = 2;
                we.workout = workout.id;
                we.exercise = 46;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.25;
                    novice = (float) 0.5;
                    intermediate = (float) 0.75;
                    advanced = (float) 1.00;
                }
                else {
                    beginner = (float) 0.15;
                    novice = (float) 0.25;
                    intermediate = (float) 0.50;
                    advanced = (float) 0.75;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Dumbbell Lateral Raise
                we = new Workout_Exercise();
                we.day = 2;
                we.workout = workout.id;
                we.exercise = 52;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.05;
                    novice = (float) 0.1;
                    intermediate = (float) 0.2;
                    advanced = (float) 0.30;
                }
                else {
                    beginner = (float) 0.05;
                    novice = (float) 0.1;
                    intermediate = (float) 0.15;
                    advanced = (float) 0.20;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Rear Delt Pull
                we = new Workout_Exercise();
                we.day = 2;
                we.workout = workout.id;
                we.exercise = 51;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.15;
                    novice = (float) 0.35;
                    intermediate = (float) 0.60;
                    advanced = (float) 0.90;
                }
                else {
                    beginner = (float) 0.15;
                    novice = (float) 0.30;
                    intermediate = (float) 0.5;
                    advanced = (float) 0.8;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

            }
            else{
                //day 1

                //Barbell Bench Press
                Workout_Exercise we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 1;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1.25;
                    advanced = (float) 1.75;
                }
                else {
                    beginner = (float) 0.25;
                    novice = (float) 0.50;
                    intermediate = (float) 0.75;
                    advanced = (float) 1.00;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Incline Barbell Bench Press
                we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 3;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1;
                    advanced = (float) 1.5;
                }
                else {
                    beginner = (float) 0.2;
                    novice = (float) 0.4;
                    intermediate = (float) 0.65;
                    advanced = (float) 1;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Decline Barbell Bench Press
                we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 2;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.5;
                    novice = (float) 1;
                    intermediate = (float) 1.25;
                    advanced = (float) 1.75;
                }
                else {
                    beginner = (float) 0.25;
                    novice = (float) 0.50;
                    intermediate = (float) 0.75;
                    advanced = (float) 1.25;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Cable Rope Pushdown
                we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 46;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.25;
                    novice = (float) 0.5;
                    intermediate = (float) 0.75;
                    advanced = (float) 1.00;
                }
                else {
                    beginner = (float) 0.15;
                    novice = (float) 0.25;
                    intermediate = (float) 0.50;
                    advanced = (float) 0.75;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Lying EZ Bar Triceps Extension
                we = new Workout_Exercise();
                we.day = 1;
                we.workout = workout.id;
                we.exercise = 45;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.2;
                    novice = (float) 0.35;
                    intermediate = (float) 0.55;
                    advanced = (float) 0.80;
                }
                else {
                    beginner = (float) 0.1;
                    novice = (float) 0.2;
                    intermediate = (float) 0.35;
                    advanced = (float) 0.55;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //day 2

                //Wide-Grip Lat Pull Down
                we = new Workout_Exercise();
                we.day = 2;
                we.workout = workout.id;
                we.exercise = 21;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1.00;
                    advanced = (float) 1.5;
                }
                else {
                    beginner = (float) 0.3;
                    novice = (float) 0.45;
                    intermediate = (float) 0.7;
                    advanced = (float) 0.95;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Bent Over Barbell Row
                we = new Workout_Exercise();
                we.day = 2;
                we.workout = workout.id;
                we.exercise = 13;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1;
                    advanced = (float) 1.5;
                }
                else {
                    beginner = (float) 0.25;
                    novice = (float) 0.4;
                    intermediate = (float) 0.65;
                    advanced = (float) 0.9;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Straight Arm Pulldown
                we = new Workout_Exercise();
                we.day = 2;
                we.workout = workout.id;
                we.exercise = 22;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.25;
                    novice = (float) 0.5;
                    intermediate = (float) 0.75;
                    advanced = (float) 1.0;
                }
                else {
                    beginner = (float) 0.1;
                    novice = (float) 0.2;
                    intermediate = (float) 0.4;
                    advanced = (float) 0.65;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Standing Dumbbell Curl
                we = new Workout_Exercise();
                we.day = 2;
                we.workout = workout.id;
                we.exercise = 42;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.1;
                    novice = (float) 0.15;
                    intermediate = (float) 0.3;
                    advanced = (float) 0.50;
                }
                else {
                    beginner = (float) 0.05;
                    novice = (float) 0.1;
                    intermediate = (float) 0.20;
                    advanced = (float) 0.35;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //EZ-Bar Preacher Curl
                we = new Workout_Exercise();
                we.day = 2;
                we.workout = workout.id;
                we.exercise = 38;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.2;
                    novice = (float) 0.35;
                    intermediate = (float) 0.60;
                    advanced = (float) 0.85;
                }
                else {
                    beginner = (float) 0.1;
                    novice = (float) 0.2;
                    intermediate = (float) 0.4;
                    advanced = (float) 0.6;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //day 3

                //Barbell Back Squat
                we = new Workout_Exercise();
                we.day = 3;
                we.workout = workout.id;
                we.exercise = 25;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.75;
                    novice = (float) 1.25;
                    intermediate = (float) 1.50;
                    advanced = (float) 2.25;
                }
                else {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1.25;
                    advanced = (float) 1.50;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Hamstring Curl
                we = new Workout_Exercise();
                we.day = 3;
                we.workout = workout.id;
                we.exercise = 35;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.25;
                    novice = (float) 0.5;
                    intermediate = (float) 0.75;
                    advanced = (float) 1.25;
                }
                else {
                    beginner = (float) 0.2;
                    novice = (float) 0.4;
                    intermediate = (float) 0.6;
                    advanced = (float) 0.85;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Leg Extensions
                we = new Workout_Exercise();
                we.day = 3;
                we.workout = workout.id;
                we.exercise = 34;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.5;
                    novice = (float) 0.75;
                    intermediate = (float) 1.25;
                    advanced = (float) 1.75;
                }
                else {
                    beginner = (float) 0.25;
                    novice = (float) 0.5;
                    intermediate = (float) 1.00;
                    advanced = (float) 1.25;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Dumbbell Lateral Raise
                we = new Workout_Exercise();
                we.day = 3;
                we.workout = workout.id;
                we.exercise = 52;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.05;
                    novice = (float) 0.1;
                    intermediate = (float) 0.2;
                    advanced = (float) 0.30;
                }
                else {
                    beginner = (float) 0.05;
                    novice = (float) 0.1;
                    intermediate = (float) 0.15;
                    advanced = (float) 0.20;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

                //Rear Delt Pull
                we = new Workout_Exercise();
                we.day = 3;
                we.workout = workout.id;
                we.exercise = 51;
                we.repetitions = 10;
                we.sets = 4;
                we.weight = (float) (profile.weight * percent);

                if(profile.gender=="Male") {
                    beginner = (float) 0.15;
                    novice = (float) 0.35;
                    intermediate = (float) 0.60;
                    advanced = (float) 0.90;
                }
                else {
                    beginner = (float) 0.15;
                    novice = (float) 0.30;
                    intermediate = (float) 0.5;
                    advanced = (float) 0.8;
                }
                we.weight *= getBodyweightRatio(profile.life_style);
                workout_exerciseDao.insert(we);

            }

            handler.post(() -> {
                calback.onLoadWorkoutComplete(workout);
            });
        });
    }

    protected float getBodyweightRatio(String life_style){
        float result = 0;
        switch(life_style){
            case "Never went to the gym":
                result = beginner;
                break;
            case "1-3 months":
                result = novice;
                break;
            case "4-12 months":
                result = intermediate;
                break;
            case "More than 12 months":
                result = advanced;
                break;
        }
        return result;
    }

    public void executeDeleteWorkout(AppDatabase db, long id){
        executor.execute(() -> {


            WorkoutDao workoutDao = db.workoutDao();
            Workout workout = workoutDao.loadById(id);
            workoutDao.delete(workout);

            Workout_ExerciseDao ex = db.workout_exerciseDao();
            List<Workout_Exercise> exercises = ex.loadByWorkout(id);
            for(int i=0;i<exercises.size();i++){
                ex.delete(exercises.get(i));
            }
            List<Workout> workouts = workoutDao.getAll();

            handler.post(() -> {
                calback.onLoadWorkoutComplete(workouts);
            });
        });
    }

    public void executeLoadByFilters(AppDatabase db, String type, String muscle){
        executor.execute(() -> {
            List<Exercise> exercises;
            ExerciseDao exerciseDao = db.exerciseDao();

            if(!type.equals("All") && !muscle.equals("All")){
                exercises = exerciseDao.loadByFilter(type,muscle);
            }
            else{
                if(!type.equals("All")){
                    exercises = exerciseDao.loadByType(type);
                }
                else if(!muscle.equals("All")){
                    exercises = exerciseDao.loadByMuscle(muscle);
                }
                else{
                    exercises = exerciseDao.getAll();
                }
            }

            handler.post(() -> {
                calback.onLoadExerciseComplete(exercises);
            });
        });
    }
    public void LoadExecutor(AppDatabase db, long id,int id2){
        executor.execute(() -> {
            Workout_ExerciseDao weDao = db.workout_exerciseDao();
            List<Workout_Exercise> wes = weDao.loadByDay(id,id2);

            ExerciseDao exerciseDao = db.exerciseDao();
            List<Exercise> exercises = exerciseDao.getAll();


            handler.post(() -> {
                calback.onLoadWorkout_ExerciseComplete(exercises,wes);
            });
        });
    }
}
