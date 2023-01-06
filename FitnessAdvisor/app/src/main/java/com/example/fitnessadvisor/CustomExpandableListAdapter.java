package com.example.fitnessadvisor;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Meal_Food;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Meal> meals;
    private HashMap<Long, List<Meal_Food>> meal_foods;
    private HashMap<Long, Food> foods;
    private MainActivity act;
    LayoutInflater inflater;

    public CustomExpandableListAdapter(MainActivity act, Context context, List<Meal> meals,
                                       HashMap<Long, List<Meal_Food>> meal_foods, HashMap<Long, Food> foods) {
        this.context = context;
        this.meal_foods = meal_foods;
        this.act = act;
        this.meals = meals;
        this.foods = foods;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.foods.get(meal_foods.get(meals.get(listPosition).id)
                .get(expandedListPosition).food);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return this.meal_foods.get(meals.get(listPosition).id)
                .get(expandedListPosition).id;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = ((Food)getChild(listPosition, expandedListPosition)).name;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_food, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.foodName);
        name.setText(expandedListText);

        Food food = (Food) getChild(listPosition, expandedListPosition);
        float quantity = meal_foods.get(meals.get(listPosition).id).get(expandedListPosition).weight;

        TextView info = (TextView) convertView.findViewById(R.id.foodInfo);
        String lip = String.format("%.1f", (food.fat/100)*quantity);
        String carbs = String.format("%.1f", (food.carbohydrates/100)*quantity);
        String prot = String.format("%.1f", (food.proteins/100)*quantity);
        info.setText("(Lip:" + lip + "g | Carbs:" + carbs + "g | Prot:" + prot + "g)");
        info.setText("(" + String.format("%.0f", quantity)  +"g)");

        TextView cal = (TextView) convertView.findViewById(R.id.foodCal);
        cal.setText(String.format("%.0f", (food.calories/100)*quantity) + "Kcal");

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.meal_foods.get(meals.get(listPosition).id)
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return meals.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return meals.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return meals.get(listPosition).id;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = ((Meal)getGroup(listPosition)).title;

            //System.out.println("entrou");
            //LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_meals, null);


        TextView name = (TextView) convertView.findViewById(R.id.mealName);
        name.setText(listTitle);

        TextView info = (TextView) convertView.findViewById(R.id.mealInfo);
        String items = "" + getChildrenCount(listPosition) + " items";
        String cal = String.format("%.0f", calculateMealCalories(listPosition)) + " Kcal";

        info.setText("(" + items + " | " + cal + ")");

        Button b = convertView.findViewById(R.id.addFood);
        b.setFocusable(false);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                act.getViewModel().setMealId(meals.get(listPosition).id);

                act
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, FoodListFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("stack")
                        .commit();
            }
        });

        return convertView;
    }

    public float calculateMealCalories(int listPosition){
        float result = 0;
        for(int i=0; i<getChildrenCount(listPosition); i++){
            Food food = (Food) getChild(listPosition, i);
            result += (food.calories/100) * meal_foods.get(meals.get(listPosition).id).get(i).weight;
        }
        return result;
    }

    public float calculateFoodCalories(int listPosition, int expandedListPosition){
        Food food = (Food) getChild(listPosition, expandedListPosition);
        float result = (food.calories/100) * meal_foods.get(meals.get(listPosition).id).get(expandedListPosition).weight;
        return result;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}