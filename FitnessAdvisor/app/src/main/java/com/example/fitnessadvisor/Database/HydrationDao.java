package com.example.fitnessadvisor.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HydrationDao {
    @Query("SELECT * FROM hydration")
    List<Hydration> getAll();

    @Query("SELECT * FROM hydration WHERE id = :id")
    Hydration loadById(long id);

    @Query("SELECT * FROM hydration WHERE day LIKE :day")
    List<Hydration> loadByDay(String day);

    @Insert
    void insert(Hydration hydration);

    @Delete
    void delete(Hydration hydration);

    @Update
    void update(Hydration hydration);
}
