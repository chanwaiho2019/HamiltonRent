package com.example.hamiltonrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class trial extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    List<Property> allProperties;
    ListView listViewProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);

        loadData();

        //Use adapter to connect the listView and my list
        PropertyListViewAdapter adapter = new PropertyListViewAdapter(trial.this, allProperties);
        listViewProperty = findViewById(R.id.listViewProperty);
        listViewProperty.setAdapter(adapter);
    }

    /**
     * A method to load the data stored in sharedPreferences
     */
    private void loadData(){
        sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Property>>(){}.getType();
        allProperties = gson.fromJson(json, type);

        if (allProperties == null){
            allProperties = new ArrayList<>();
        }
    }
}
