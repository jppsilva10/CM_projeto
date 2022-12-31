package com.example.fitnessadvisor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Workout_Exercise;

import java.util.List;

public class MyAdapterExerciseWorkout extends BaseAdapter {
    Context context;
    List<Workout_Exercise> exercises;
    LayoutInflater inflater;

    public MyAdapterExerciseWorkout(Context applicationContext, List<Workout_Exercise> exercises) {
        this.exercises = exercises;
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    public void setExercises(List<Workout_Exercise> workouts) {
        this.exercises = workouts;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Object getItem(int i) {
        return exercises.get(i);
    }

    @Override
    public long getItemId(int i) {

        return exercises.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_item_exercise_workout, null);
        TextView title = (TextView) view.findViewById(R.id.textView);
        return view;
    }
}
