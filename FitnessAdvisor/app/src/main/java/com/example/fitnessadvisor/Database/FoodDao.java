package com.example.fitnessadvisor.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM food")
    List<Food> getAll();

    @Query("SELECT * FROM food WHERE id = :id")
    Food loadById(long id);

    @Query("SELECT * FROM food WHERE name LIKE :name")
    List<Food> loadByName(String name);

    @Insert
    void insert(Food food);

    @Delete
    void delete(Food food);

    @Query("UPDATE food SET name = :name WHERE id = :id")
    void updateName(String name, long id);

    @Update
    void update(Food food);
}
