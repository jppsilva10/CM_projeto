package com.example.fitnessadvisor.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Workout_ExerciseDao {
    @Query("SELECT * FROM workout_exercise")
    List<Workout_Exercise> getAll();

    @Query("SELECT * FROM workout_exercise WHERE id = :id")
    Workout_Exercise loadById(long id);

    @Transaction
    @Query("SELECT * FROM workout_exercise WHERE workout = :workout")
    List<Workout_Exercise> loadByWorkout(long workout);

    @Insert
    long insert(Workout_Exercise workout_exercise);

    @Delete
    void delete(Workout_Exercise workout_exercise);

    @Update
    void update(Workout_Exercise workout_exercise);
}
