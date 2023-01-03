package com.example.fitnessadvisor;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.fitnessadvisor.Database.Exercise;
import com.example.fitnessadvisor.Database.Workout_Exercise;

import java.util.List;

public class MyAdapterExerciseWorkout extends BaseAdapter {
    Context context;
    List<Workout_Exercise> wes;
    List<Exercise> exercises;
    LayoutInflater inflater;
    int value;

    public MyAdapterExerciseWorkout(Context applicationContext, List<Exercise> exercises, List<Workout_Exercise> wes,int value) {
        this.exercises = exercises;
        this.wes = wes;
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
        this.value = value;
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
        return exercises.get((int) (wes.get(i).exercise-1));
    }

    @Override
    public long getItemId(int i) {

        return wes.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_item_exercise_workout, null);
        Workout_Exercise we = wes.get(i);

        LinearLayout ll = view.findViewById(R.id.llsetreps);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(0xFFFFFFFF);
        gd.setCornerRadius(1);
        gd.setStroke(7, 0xFF000000);

        TextView sets = (TextView) view.findViewById(R.id.sets);
        sets.setText("x" + we.sets);
        TextView reps = (TextView) view.findViewById(R.id.reps);
        reps.setText("" + we.repetitions + " reps");

        TextView exerciseName = (TextView) view.findViewById(R.id.exerciseName);
        long exercise_id = we.exercise;
        System.out.println(exercise_id);
        System.out.println(exercises.get((int) (exercise_id-1)).id);
        System.out.println(exercises.size());
        exerciseName.setText(exercises.get((int) (exercise_id-1)).name);
        if(value == i){
            ll.setBackground(gd);
        }

        return view;
    }
}
