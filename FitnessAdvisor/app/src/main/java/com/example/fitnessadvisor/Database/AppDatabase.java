package com.example.fitnessadvisor.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {Workout.class, Exercise.class, Profile.class},
        version = 1
)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WorkoutDao workoutDao();
    public abstract ExerciseDao exerciseDao();
    public abstract ProfileDao profileDao();
    public abstract Workout_ExerciseDao workout_exerciseDao();
}
