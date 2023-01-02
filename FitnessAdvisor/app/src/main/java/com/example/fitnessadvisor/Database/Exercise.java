package com.example.fitnessadvisor.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "image")
    public int image;

    @ColumnInfo(name = "muscle_group")
    public String muscle_groups;

    @ColumnInfo(name = "main_muscle")
    public String main_muscle;
}
