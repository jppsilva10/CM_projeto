package com.example.fitnessadvisor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Hydration;
import com.example.fitnessadvisor.Database.Workout;

import java.util.List;

public class MyAdapterHydration extends BaseAdapter {
    Context context;
    List<Hydration> hydration;
    LayoutInflater inflater;

    public MyAdapterHydration(Context applicationContext, List<Hydration> hydration) {
        this.hydration = hydration;
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    public void setFoods(List<Hydration> hydration) {
        this.hydration = hydration;
    }

    @Override
    public int getCount() {
        return hydration.size();
    }

    @Override
    public Object getItem(int i) {
        return hydration.get(i);
    }

    @Override
    public long getItemId(int i) {

        return hydration.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Hydration h = hydration.get(i);

        view = inflater.inflate(R.layout.list_item_hydration, null);

        //ImageView img = view.findViewById(R.id.image);
        //img.setFocusable(false);

        TextView cal = (TextView) view.findViewById(R.id.quantity);
        cal.setText(String.format("%.2f", h.quantity) + "ml");

        return view;
    }

    public float getTotalHydration(){
        float result =0;
        for(int i=0; i<hydration.size(); i++){
            result += hydration.get(i).quantity;
        }
        return result/1000;
    }
}

