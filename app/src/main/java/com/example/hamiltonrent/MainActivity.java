package com.example.hamiltonrent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = findViewById(R.id.expandableLVCategory);
        initData();
        expandableListAdapter = new ExpandableListAdapter(this, listDataHeader, listHashMap);
        expandableListView.setAdapter(expandableListAdapter);
    }

    /**
     * A method to initialize the data for the expandable list view
     */
    private void initData() {
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        //List of Categories
        listDataHeader.add(getResources().getString(R.string.harcourts));
        listDataHeader.add(getResources().getString(R.string.waikatoRealEstate));
        listDataHeader.add(getResources().getString(R.string.lodge));
        listDataHeader.add(getResources().getString(R.string.rayWhite));

        //List of items in Harcourts category
        List<String> harcourtsList = new ArrayList<>();
        harcourtsList.add(getResources().getString(R.string.oneBedroom));
        harcourtsList.add(getResources().getString(R.string.twoBedroom));
        harcourtsList.add(getResources().getString(R.string.threeBedroom));
        harcourtsList.add(getResources().getString(R.string.fourBedroom));
        harcourtsList.add(getResources().getString(R.string.fiveBedroomOrMore));

        //List of items in Waikato Real Estate category
        List<String> waikatoREList = new ArrayList<>();
        waikatoREList.add(getResources().getString(R.string.oneBedroom));
        waikatoREList.add(getResources().getString(R.string.twoBedroom));
        waikatoREList.add(getResources().getString(R.string.threeBedroom));
        waikatoREList.add(getResources().getString(R.string.fourBedroom));
        waikatoREList.add(getResources().getString(R.string.fiveBedroomOrMore));

        //List of items in Lodge category
        List<String> lodgeList = new ArrayList<>();
        lodgeList.add(getResources().getString(R.string.oneBedroom));
        lodgeList.add(getResources().getString(R.string.twoBedroom));
        lodgeList.add(getResources().getString(R.string.threeBedroom));
        lodgeList.add(getResources().getString(R.string.fourBedroom));
        lodgeList.add(getResources().getString(R.string.fiveBedroomOrMore));

        //List of items in RayWhite category
        List<String> rayWhiteList = new ArrayList<>();
        rayWhiteList.add(getResources().getString(R.string.oneBedroom));
        rayWhiteList.add(getResources().getString(R.string.twoBedroom));
        rayWhiteList.add(getResources().getString(R.string.threeBedroom));
        rayWhiteList.add(getResources().getString(R.string.fourBedroom));
        rayWhiteList.add(getResources().getString(R.string.fiveBedroomOrMore));

        //Map the category(key) and corresponding sublist(value) into the hash map
        listHashMap.put(listDataHeader.get(0), harcourtsList);
        listHashMap.put(listDataHeader.get(1), waikatoREList);
        listHashMap.put(listDataHeader.get(2), lodgeList);
        listHashMap.put(listDataHeader.get(3), rayWhiteList);
    }
}
