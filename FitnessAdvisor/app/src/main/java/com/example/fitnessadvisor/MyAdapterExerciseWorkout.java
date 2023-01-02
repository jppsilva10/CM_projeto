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
    List<Workout_Exercise> wes;
    List<Exercise> exercises;
    LayoutInflater inflater;

    public MyAdapterExerciseWorkout(Context applicationContext, List<Exercise> exercises, List<Workout_Exercise> wes) {
        this.exercises = exercises;
        this.wes = wes;
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    public void setWes(List<Workout_Exercise> wes) {
        this.wes = wes;
    }

    @Override
    public int getCount() {
        return wes.size();
    }

    @Override
    public Object getItem(int i) {
        return exercises.get(i);
    }

    @Override
    public long getItemId(int i) {

        return wes.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_item, null);
        TextView title = (TextView) view.findViewById(R.id.textView);
        long exercise_id = wes.get(i).exercise;
        System.out.println(exercise_id);
        System.out.println(exercises.size());
        Exercise exercise = null;
        for(int j=0; j<exercises.size(); j++){
            if(exercises.get(j).id==exercise_id){
                exercise = exercises.get(j);
            }
        }
        title.setText(exercise.name);
        return view;
    }
}
