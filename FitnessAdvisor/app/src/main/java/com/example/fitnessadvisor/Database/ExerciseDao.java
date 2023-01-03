package com.example.fitnessadvisor.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Query("SELECT * FROM exercise")
    List<Exercise> getAll();

    @Query("SELECT * FROM exercise WHERE id = :id")
    Exercise loadById(long id);

    @Query("SELECT * FROM exercise WHERE name LIKE :name")
    List<Exercise> loadByName(String name);

    @Query("SELECT * FROM exercise WHERE type = :type AND main_muscle = :main_muscle")
    List<Exercise> loadByFilter(String type,String main_muscle);

    @Query("SELECT * FROM exercise WHERE type = :type")
    List<Exercise> loadByType(String type);

    @Query("SELECT * FROM exercise WHERE main_muscle = :muscle")
    List<Exercise> loadByMuscle(String muscle);

    @Insert
    long insert(Exercise exercise);

    @Delete
    void delete(Exercise exercise);

    @Query("UPDATE exercise SET name = :name WHERE id = :id")
    void updateName(String name, long id);

    @Update
    void update(Exercise exercise);
}
