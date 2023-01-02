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
        view = inflater.inflate(R.layout.list_item_exercise_workout, null);
        Workout_Exercise we = wes.get(i);

        TextView sets = (TextView) view.findViewById(R.id.sets);
        sets.setText("x" + we.sets);
        TextView reps = (TextView) view.findViewById(R.id.reps);
        reps.setText("" + we.repetitions + " reps");

        TextView exerciseName = (TextView) view.findViewById(R.id.exerciseName);
        long exercise_id = we.exercise;
        System.out.println(exercise_id);
        System.out.println(exercises.size());
        Exercise exercise = null;
        for(int j=0; j<exercises.size(); j++){
            if(exercises.get(j).id==exercise_id){
                exercise = exercises.get(j);
            }
        }
        exerciseName.setText(exercise.name);

        return view;
    }
}
