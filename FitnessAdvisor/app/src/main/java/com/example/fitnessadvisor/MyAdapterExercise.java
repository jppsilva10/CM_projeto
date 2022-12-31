package com.example.fitnessadvisor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Workout;

import java.util.List;

public class MyAdapterExercise extends BaseAdapter {
    Context context;
    List<Exercise> exercises;
    LayoutInflater inflater;

    public MyAdapterExercise(Context applicationContext, List<Exercise> exercises) {
        this.exercises = exercises;
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    public void setExercises(List<Exercise> workouts) {
        this.exercises = workouts;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Object getItem(int i) {
        return exercises.get(i).name;
    }

    @Override
    public long getItemId(int i) {

        return exercises.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_item, null);
        TextView title = (TextView) view.findViewById(R.id.textView);
        title.setText(exercises.get(i).name);
        return view;
    }
}
