package com.example.fitnessadvisor;

import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.example.fitnessadvisor.Database.AppDatabase;

public class SharedViewModel extends ViewModel {
    private AppDatabase db;
    private long workoutId;

    public void setDB(AppDatabase db){
        this.db = db;
    }

    public AppDatabase getDB(){
        return db;
    }

    public void setWorkoutId(long id){
        this.workoutId = id;
    }
}