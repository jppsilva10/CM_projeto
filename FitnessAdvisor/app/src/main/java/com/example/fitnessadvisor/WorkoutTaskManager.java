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
