package com.example.fitnessadvisor.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface MealDao {
    @Query("SELECT * FROM meal")
    List<Meal> getAll();

    @Query("SELECT * FROM meal WHERE id = :id")
    Meal loadById(long id);

    @Query("SELECT * FROM meal WHERE title LIKE :title")
    List<Meal> loadByName(String title);

    @Query("SELECT * FROM meal WHERE day LIKE :day")
    List<Meal> loadByDate(String day);

    @Insert
    void insert(Meal meal);

    @Delete
    void delete(Meal meal);

    @Query("UPDATE meal SET title = :title WHERE id = :id")
    void updateName(String title, long id);
}
