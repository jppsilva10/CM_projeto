package com.example.fitnessadvisor.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Meal.class,
                parentColumns = "id",
                childColumns = "meal",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Food.class,
                parentColumns = "id",
                childColumns = "food",
                onDelete = ForeignKey.CASCADE),
}
)
public class Meal_Food {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "meal")
    public long meal;

    @ColumnInfo(name = "food")
    public long food;
}
