package com.example.fitnessadvisor.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Hydration {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "objective")
    public float objective;

    @ColumnInfo(name = "quantity")
    public float quantity;

    @ColumnInfo(name = "day")
    public String day;


}