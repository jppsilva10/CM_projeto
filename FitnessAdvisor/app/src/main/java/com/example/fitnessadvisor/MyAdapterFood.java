package com.example.fitnessadvisor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Workout;

import java.util.List;

public class MyAdapterFood extends BaseAdapter {
    Context context;
    List<Food> Foods;
    LayoutInflater inflater;

    public MyAdapterFood(Context applicationContext, List<Food> Foods) {
        this.Foods = Foods;
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    public void setFoods(List<Food> workouts) {
        this.Foods = workouts;
    }

    @Override
    public int getCount() {
        return Foods.size();
    }

    @Override
    public Object getItem(int i) {
        return Foods.get(i).name;
    }

    @Override
    public long getItemId(int i) {

        return Foods.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_item, null);
        TextView title = (TextView) view.findViewById(R.id.textView);
        title.setText(Foods.get(i).name);
        return view;
    }
}
