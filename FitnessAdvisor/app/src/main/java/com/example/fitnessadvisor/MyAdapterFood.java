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
    long selected;
    float quantity;

    public MyAdapterFood(Context applicationContext, List<Food> Foods, long selected, float quantity) {
        this.Foods = Foods;
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
        this.selected = selected;
        this.quantity = quantity;
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
        Food food = Foods.get(i);
        if(food.id==selected){
            view = inflater.inflate(R.layout.list_item_food_selected, null);
        }
        else{
            view = inflater.inflate(R.layout.list_item_food, null);
        }
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(food.name);

        TextView info = (TextView) view.findViewById(R.id.foodInfo);
        String lip = String.format("%.1f", (food.fat/100)*quantity);
        String carbs = String.format("%.1f", (food.carbohydrates/100)*quantity);
        String prot = String.format("%.1f", (food.proteins/100)*quantity);
        info.setText("(Lip:" + lip + "g | Carbs:" + carbs + "g | Prot:" + prot + "g)");

        TextView cal = (TextView) view.findViewById(R.id.foodCal);
        cal.setText(String.format("%.0f", (food.calories/100)*quantity) + "Kcal");

        return view;
    }
}
