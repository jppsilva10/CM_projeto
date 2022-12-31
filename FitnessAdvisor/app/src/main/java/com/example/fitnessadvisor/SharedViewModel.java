package com.example.fitnessadvisor;

import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.example.fitnessadvisor.Database.AppDatabase;
import com.example.fitnessadvisor.Database.Workout;

public class SharedViewModel extends ViewModel {
    private AppDatabase db;
    private String workout;
    private long workoutId;

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

    public void setWorkoutId(long workout){
        this.workoutId = workout;
    }

    public long getWorkoutId(){
        return workoutId;
    }

}