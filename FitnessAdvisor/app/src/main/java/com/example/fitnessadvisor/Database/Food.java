package com.example.fitnessadvisor.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Food {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "calories")
    public float calories;

    @ColumnInfo(name = "carbohydrates")
    public float carbohydrates;

    @ColumnInfo(name = "fat")
    public float fat;

    @ColumnInfo(name = "proteins")
    public float proteins;

}