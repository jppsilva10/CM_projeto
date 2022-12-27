package com.example.fitnessadvisor.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WorkoutDao {
    @Query("SELECT * FROM workout")
    List<Workout> getAll();

    @Query("SELECT * FROM workout WHERE id = :id")
    Workout loadById(long id);

    @Query("SELECT * FROM workout WHERE name LIKE :name")
    List<Workout> loadByName(String name);

    @Insert
    void insert(Workout workout);

    @Delete
    void delete(Workout workout);

    @Query("UPDATE workout SET name = :name WHERE id = :id")
    void updateName(String name, long id);
}
