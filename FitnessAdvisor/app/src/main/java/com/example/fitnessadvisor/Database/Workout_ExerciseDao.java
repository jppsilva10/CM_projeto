package com.example.fitnessadvisor.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Workout_ExerciseDao {
    @Query("SELECT * FROM workout_exercise")
    List<Workout_ExerciseDao> getAll();

    @Query("SELECT * FROM workout_exercise WHERE id = :id")
    Workout_ExerciseDao loadById(long id);

    @Query("SELECT * FROM workout_exercise WHERE workout = :workout")
    Workout_ExerciseDao loadByWorkout(long workout);

    @Insert
    void insert(Workout_ExerciseDao workout_exercise);

    @Delete
    void delete(Workout_ExerciseDao workout_exercise);

    @Update
    void update(Workout_ExerciseDao workout_exercise);
}
