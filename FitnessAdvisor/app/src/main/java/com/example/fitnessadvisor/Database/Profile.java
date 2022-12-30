package com.example.fitnessadvisor.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Profile {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "birth_date")
    public Date birth_date;

    @ColumnInfo(name = "height")
    public float height;

    @ColumnInfo(name = "weight")
    public float weight;

    @ColumnInfo(name = "target_weight")
    public float target_weight;

    @ColumnInfo(name = "goal_deadline")
    public Date goal_deadline;

    @ColumnInfo(name = "life_style")
    public String life_style;



}
