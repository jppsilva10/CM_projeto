package com.example.fitnessadvisor.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Meal_FoodDao {
    @Query("SELECT * FROM meal_food")
    List<Meal_Food> getAll();

    @Query("SELECT * FROM meal_food WHERE id = :id")
    Meal_Food loadById(long id);

    @Query("SELECT * FROM meal_food WHERE meal = :meal")
    Meal_Food loadByMeal(long meal);

    @Insert
    void insert(Meal_Food meal_food);

    @Delete
    void delete(Meal_Food meal_food);

    @Update
    void update(Meal_Food meal_food);
}
