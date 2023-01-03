package com.example.fitnessadvisor.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Workout.class,
                parentColumns = "id",
                childColumns = "workout",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Exercise.class,
                parentColumns = "id",
                childColumns = "exercise",
                onDelete = ForeignKey.CASCADE),
        }
)
public class Workout_Exercise {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "workout")
    public long workout;

    @ColumnInfo(name = "exercise")
    public long exercise;

    @ColumnInfo(name = "sets")
    public int sets;

    @ColumnInfo(name = "repetitions")
    public int repetitions;

    @ColumnInfo(name = "weight")
    public float weight;

    @ColumnInfo(name = "day")
    public int day;
}
