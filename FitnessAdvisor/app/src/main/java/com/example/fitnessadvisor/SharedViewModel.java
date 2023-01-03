package com.example.fitnessadvisor;

import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.example.fitnessadvisor.Database.AppDatabase;
import com.example.fitnessadvisor.Database.Workout;

public class SharedViewModel extends ViewModel {
    private AppDatabase db;
    private String workout;
    private String exercise;
    private long workoutId;
    private long exerciseId;
    private long workout_exerciseId = -1;

    public void setDB(AppDatabase db){
        this.db = db;
    }

    public AppDatabase getDB(){
        return db;
    }

    public void setWorkout(String workout){
        this.workout = workout;
    }

    public String getWorkout(){
        return workout;
    }

    public void setExercise(String exercise){
        this.exercise = exercise;
    }

    public String getExercise(){
        return exercise;
    }

    public void setWorkoutId(long workout){
        this.workoutId = workout;
    }

    public long getWorkoutId(){
        return workoutId;
    }

    public void setExerciseId(long exerciseId){
        this.exerciseId = exerciseId;
    }

    public long getExerciseId(){
        return exerciseId;
    }

    public void setWorkout_ExerciseId(long workout_exerciseId){
        this.workout_exerciseId = workout_exerciseId;
    }

    public long getWorkout_ExerciseId(){
        return workout_exerciseId;
    }

}