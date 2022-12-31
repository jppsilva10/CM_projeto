package com.example.fitnessadvisor;

import android.os.Handler;
import android.os.Looper;

import com.example.fitnessadvisor.Database.AppDatabase;
import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.ExerciseDao;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.ProfileDao;

import com.example.fitnessadvisor.Database.Workout;
import com.example.fitnessadvisor.Database.WorkoutDao;

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
        void onLoadWorkoutComplete(List<Workout> workouts);
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

    public void executeLoadExerciseAsync(AppDatabase db){
        executor.execute(() -> {

            ExerciseDao exerciseDao = db.exerciseDao();
            List<Exercise> exercises = exerciseDao.getAll();

            handler.post(() -> {
                calback.onLoadExerciseComplete(exercises);
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

            handler.post(() -> {
                calback.onProfileUpdateComplete(profile);
            });
        });
    }

}
