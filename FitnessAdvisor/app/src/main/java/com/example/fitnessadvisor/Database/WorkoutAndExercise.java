package com.example.fitnessadvisor.Database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class WorkoutAndExercise {
    @Embedded
    public Workout workout;
    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(Workout_Exercise.class)
    )
    public List<Exercise> exercises;
}
