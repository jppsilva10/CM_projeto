package com.example.fitnessadvisor.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProfileDao {
    @Query("SELECT * FROM profile")
    List<Profile> getAll();

    @Query("SELECT * FROM profile WHERE id = :id")
    Profile loadById(long id);

    @Insert
    void insert(Profile profile);

    @Delete
    void delete(Profile profile);

    @Update
    void update(Profile profile);
}
