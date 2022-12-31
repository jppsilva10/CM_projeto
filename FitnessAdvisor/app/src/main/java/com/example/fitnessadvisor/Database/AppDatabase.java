package com.example.fitnessadvisor.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {Workout.class, Exercise.class, Profile.class, Workout_Exercise.class,
                    Meal.class, Food.class, Meal_Food.class},
        version = 1
)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WorkoutDao workoutDao();
    public abstract ExerciseDao exerciseDao();
    public abstract ProfileDao profileDao();
    public abstract Workout_ExerciseDao workout_exerciseDao();
    public abstract MealDao mealDao();
    public abstract FoodDao foodDao();
    public abstract Meal_FoodDao meal_foodDao();
}
