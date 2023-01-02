package com.example.fitnessadvisor;
import com.example.fitnessadvisor.Database.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MealListDataPump{
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> breakfast = new ArrayList<String>();
        breakfast.add("Ovo");
        breakfast.add("Bacon");
        breakfast.add("Torrada");

        List<String> lunch = new ArrayList<String>();
        lunch.add("Carne de vaca");
        lunch.add("Arroz");
        lunch.add("Salada");
        lunch.add("Cenoura");
        lunch.add("Sopa de legumes");

        List<String> dinner = new ArrayList<String>();
        dinner.add("Salmão");
        dinner.add("Batatas cozidas");
        dinner.add("Couve");
        dinner.add("Azeite");

        expandableListDetail.put("Pequeno Almoço", breakfast);
        expandableListDetail.put("Almoço", lunch);
        expandableListDetail.put("Jantar", dinner);
        return expandableListDetail;
    }


}