package com.example.fitnessadvisor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Workout;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    Context context;
    List<Workout> workouts;
    LayoutInflater inflater;

    public MyAdapter(Context applicationContext, List<Workout> workouts) {
        this.workouts = workouts;
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    @Override
    public int getCount() {
        return workouts.size();
    }

    @Override
    public Object getItem(int i) {
        return workouts.get(i).name;
    }

    @Override
    public long getItemId(int i) {

        return workouts.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_item, null);
        TextView title = (TextView) view.findViewById(R.id.textView);
        title.setText("    " + workouts.get(i).name);
        return view;
    }
}
