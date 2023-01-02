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

public class AccountTaskManager {
    final Executor executor = Executors.newSingleThreadExecutor();
    final Handler handler = new Handler(Looper.getMainLooper());
    protected Callback calback;

    public AccountTaskManager(Callback calback) {
        this.calback = calback;
    }

    public interface Callback{
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

}
