package com.example.fitnessadvisor;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessadvisor.Database.Food;
import com.example.fitnessadvisor.Database.Hydration;
import com.example.fitnessadvisor.Database.Meal;
import com.example.fitnessadvisor.Database.Meal_Food;
import com.example.fitnessadvisor.Database.Profile;
import com.example.fitnessadvisor.Database.Workout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class HydrationFragment extends Fragment implements NutritionTaskManager.Callback{

    protected SharedViewModel viewmodel;
    protected NutritionTaskManager taskManager = new NutritionTaskManager(this);
    TextView waterGoal;
    TextView waterDrank;
    TextView hydrationDate;
    final Calendar myCalendar= Calendar.getInstance();
    FloatingActionButton addButton;
    private ProgressBar progress;
    protected ListView list;
    protected Parcelable state;
    protected long selected_id;
    protected float waterQuantityGoal = 2.5f;

    public HydrationFragment() {
        // Required empty public constructor
    }

    public static HydrationFragment newInstance() {
        HydrationFragment fragment = new HydrationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hydration, container, false);
        MainActivity act = (MainActivity)getActivity();
        viewmodel = act.getViewModel();

        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        if(viewmodel.getSetDate().equals("")){
            viewmodel.setSetDate(today);
        }

        waterGoal = v.findViewById(R.id.waterGoal);
        waterDrank = v.findViewById(R.id.waterDrank);
        addButton = v.findViewById(R.id.add_button);
        list = v.findViewById(R.id.hydration_list);
        progress = v.findViewById(R.id.progress);

        taskManager.executeLoadHydrationAsync(viewmodel.getDB(), viewmodel.getSetDate());

        hydrationDate = v.findViewById(R.id.date);
        hydrationDate.setText(today);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        hydrationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(act,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        setListener();

        return v;
    }

    public void setListener(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertDialog mydialog;

                builder.setTitle("Quantity: ");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(input);

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Hydration hydration = new Hydration();
                                hydration.day = viewmodel.getSetDate();
                                hydration.quantity = Float.parseFloat(input.getText().toString());
                                taskManager.executeInsertHydration(viewmodel.getDB(), hydration);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                mydialog = builder.create();
                mydialog.show();

                ((AlertDialog) mydialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                input.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (TextUtils.isEmpty(s)) {
                            ((AlertDialog) mydialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                        } else {
                            ((AlertDialog) mydialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                        }

                    }
                });
            }
        });
    }

    public void setListListener() {
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View v, int position, long id) {

                selected_id = id;
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.popup_menu_hydration, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.option1:
                taskManager.executeDeleteHydration(viewmodel.getDB(),selected_id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void updateLabel(){
        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        String myFormat="dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
        hydrationDate.setText(dateFormat.format(myCalendar.getTime()));
        viewmodel.setSetDate(dateFormat.format(myCalendar.getTime()));

        taskManager.executeLoadHydrationAsync(viewmodel.getDB(), viewmodel.getSetDate());

    }

    @Override
    public void onLoadBMR(float BMR) {

    }

    @Override
    public void onLoadWaterGoal(float waterGoal) {
        waterQuantityGoal = waterGoal/1000;
        this.waterGoal.setText("/" + String.format("%.2f", waterQuantityGoal)+"L");
    }

    @Override
    public void onLoadProfileComplete(Profile profile, boolean empty) {

    }

    @Override
    public void onLoadMealComplete(HashMap<Long, List<Meal_Food>> mealList, List<Meal> meals, HashMap<Long, Food> foods) {

    }

    @Override
    public void onLoadFoodComplete(List<Food> food) {

    }

    @Override
    public void onInsertMealComplete(long mealId) {

    }

    @Override
    public void onDeleteMealComplete() {

    }

    @Override
    public void onLoadHydrationComplete(List<Hydration> hydration, float waterGoal) {
        try {
            waterQuantityGoal = waterGoal/1000;
            this.waterGoal.setText("/" + String.format("%.2f", waterQuantityGoal)+"L");

            MyAdapterHydration myAdapterHydration = new MyAdapterHydration(getActivity().getApplicationContext(), hydration);
            state = list.onSaveInstanceState();
            list.setAdapter(myAdapterHydration);
            setListListener();
            list.onRestoreInstanceState(state);
            float total_quantity = myAdapterHydration.getTotalHydration();
            waterDrank.setText(String.format("%.2f", total_quantity));
            progress.setProgress((int)(100 * total_quantity/waterQuantityGoal));
        }catch(Exception e){
            System.out.println("erro");
        }
    }

    @Override
    public void onUpdateHydrationComplete() {

        taskManager.executeLoadHydrationAsync(viewmodel.getDB(), viewmodel.getSetDate());
    }

    @Override
    public void onLoadFoodFromMeal(Meal meal, List<Food> foodList) {

    }


}
