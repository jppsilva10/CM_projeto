package com.example.fitnessadvisor.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {Workout.class, Exercise.class},
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WorkoutDao workoutDao();
    public abstract ExerciseDao exerciseDao();
}
