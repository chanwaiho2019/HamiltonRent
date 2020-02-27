package com.example.hamiltonrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

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
        listDataHeader.add(getResources().getString(R.string.beerescourt));
        listDataHeader.add(getResources().getString(R.string.cambridge));
        listDataHeader.add(getResources().getString(R.string.chartwell));
        listDataHeader.add(getResources().getString(R.string.claudelands));
        listDataHeader.add(getResources().getString(R.string.deanwell));
        listDataHeader.add(getResources().getString(R.string.dinsdale));
        listDataHeader.add(getResources().getString(R.string.enderley));
        listDataHeader.add(getResources().getString(R.string.eureka));
        listDataHeader.add(getResources().getString(R.string.fairfield));
        listDataHeader.add(getResources().getString(R.string.fairviewDowns));
        listDataHeader.add(getResources().getString(R.string.flagstaff));
        listDataHeader.add(getResources().getString(R.string.frankton));
        listDataHeader.add(getResources().getString(R.string.glenView));
        listDataHeader.add(getResources().getString(R.string.grandviewHeights));
        listDataHeader.add(getResources().getString(R.string.hamiltonCityCentral));
        listDataHeader.add(getResources().getString(R.string.hamiltonEast));
        listDataHeader.add(getResources().getString(R.string.hamiltonLake));
        listDataHeader.add(getResources().getString(R.string.harrowfield));
        listDataHeader.add(getResources().getString(R.string.hillcrest));
        listDataHeader.add(getResources().getString(R.string.maeroa));
        listDataHeader.add(getResources().getString(R.string.melville));
        listDataHeader.add(getResources().getString(R.string.nawton));
        listDataHeader.add(getResources().getString(R.string.ohaupo));
        listDataHeader.add(getResources().getString(R.string.pukete));
        listDataHeader.add(getResources().getString(R.string.rotokauri));
        listDataHeader.add(getResources().getString(R.string.rototuna));
        listDataHeader.add(getResources().getString(R.string.silverdale));
        listDataHeader.add(getResources().getString(R.string.teAwamutu));
        listDataHeader.add(getResources().getString(R.string.teKowhai));
        listDataHeader.add(getResources().getString(R.string.whitiora));


//        //List of items in Harcourts category
//        List<String> beerescourt = new ArrayList<>();
//        beerescourt.add(getResources().getString(R.string.oneBedroom));
//        beerescourt.add(getResources().getString(R.string.twoBedroom));
//        beerescourt.add(getResources().getString(R.string.threeBedroom));
//        beerescourt.add(getResources().getString(R.string.fourBedroom));
//        beerescourt.add(getResources().getString(R.string.fiveBedroomOrMore));
//
//        //List of items in Waikato Real Estate category
//        List<String> waikatoREList = new ArrayList<>();
//        waikatoREList.add(getResources().getString(R.string.oneBedroom));
//        waikatoREList.add(getResources().getString(R.string.twoBedroom));
//        waikatoREList.add(getResources().getString(R.string.threeBedroom));
//        waikatoREList.add(getResources().getString(R.string.fourBedroom));
//        waikatoREList.add(getResources().getString(R.string.fiveBedroomOrMore));
//
//        //List of items in Lodge category
//        List<String> lodgeList = new ArrayList<>();
//        lodgeList.add(getResources().getString(R.string.oneBedroom));
//        lodgeList.add(getResources().getString(R.string.twoBedroom));
//        lodgeList.add(getResources().getString(R.string.threeBedroom));
//        lodgeList.add(getResources().getString(R.string.fourBedroom));
//        lodgeList.add(getResources().getString(R.string.fiveBedroomOrMore));
//
//        //List of items in RayWhite category
//        List<String> rayWhiteList = new ArrayList<>();
//        rayWhiteList.add(getResources().getString(R.string.oneBedroom));
//        rayWhiteList.add(getResources().getString(R.string.twoBedroom));
//        rayWhiteList.add(getResources().getString(R.string.threeBedroom));
//        rayWhiteList.add(getResources().getString(R.string.fourBedroom));
//        rayWhiteList.add(getResources().getString(R.string.fiveBedroomOrMore));
//
//        //List of items in EVES category
//        List<String> evesList = new ArrayList<>();
//        evesList.add(getResources().getString(R.string.oneBedroom));
//        evesList.add(getResources().getString(R.string.twoBedroom));
//        evesList.add(getResources().getString(R.string.threeBedroom));
//        evesList.add(getResources().getString(R.string.fourBedroom));
//        evesList.add(getResources().getString(R.string.fiveBedroomOrMore));
//
//        //List of items in Glasshouse category
//        List<String> glasshouseList = new ArrayList<>();
//        glasshouseList.add(getResources().getString(R.string.oneBedroom));
//        glasshouseList.add(getResources().getString(R.string.twoBedroom));
//        glasshouseList.add(getResources().getString(R.string.threeBedroom));
//        glasshouseList.add(getResources().getString(R.string.fourBedroom));
//        glasshouseList.add(getResources().getString(R.string.fiveBedroomOrMore));

//        //Map the category(key) and corresponding sublist(value) into the hash map
//        listHashMap.put(listDataHeader.get(0), beerescourt);
//        listHashMap.put(listDataHeader.get(1), waikatoREList);
//        listHashMap.put(listDataHeader.get(2), lodgeList);
//        listHashMap.put(listDataHeader.get(3), rayWhiteList);
//        listHashMap.put(listDataHeader.get(4), evesList);
//        listHashMap.put(listDataHeader.get(5), glasshouseList);
    }
}
