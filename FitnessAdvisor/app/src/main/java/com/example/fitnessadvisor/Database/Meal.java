package com.example.fitnessadvisor.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Meal {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "day")
    public String day;

    @ColumnInfo(name = "time")
    public String time;
}
