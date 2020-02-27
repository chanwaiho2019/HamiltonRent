package com.example.hamiltonrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PropertiesBySuburb extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private List<Property> allProperties;
    private List<Property> propertiesBySuburb;
    private List<Property> propertiesByBedroomNum;
    private List<Property> sortedListRent;
    private ListView listViewProperty;
//    private int numBedroom = -1;
//    private int numBathroom = -1;
//    private int numCarSpace = -1;
    private String suburb;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties_by_suburb);

        //Get the string from previous intent
        Intent intent = getIntent();
//        numBedroom = intent.getStringExtra("numBedroom");
        suburb = intent.getStringExtra("suburb");

        //Set title of activity
        setTitle(suburb);

        //Initialize the listView object
        listViewProperty = findViewById(R.id.listViewProperty);

        //Load data from sharedPreferences
        loadData();

        //Get the properties by suburb
        propertiesBySuburb = WebSraperUsingJsoup.getBySuburb(allProperties, suburb);

//        //Get the corresponding data according to the numBedroom category selected by user
//        if (numBedroom.equals(getResources().getString(R.string.oneBedroom))){
//            propertiesByBedroomNum = WebSraperUsingJsoup.getByBedroomNum(propertiesBySuburb, 1);
//        }
//        else if (numBedroom.equals(getResources().getString(R.string.twoBedroom))){
//            propertiesByBedroomNum = WebSraperUsingJsoup.getByBedroomNum(propertiesBySuburb, 2);
//        }
//        else if (numBedroom.equals(getResources().getString(R.string.threeBedroom))){
//            propertiesByBedroomNum = WebSraperUsingJsoup.getByBedroomNum(propertiesBySuburb, 3);
//        }
//        else if (numBedroom.equals(getResources().getString(R.string.fourBedroom))){
//            propertiesByBedroomNum = WebSraperUsingJsoup.getByBedroomNum(propertiesBySuburb, 4);
//        }
//        else if (numBedroom.equals(getResources().getString(R.string.fiveBedroomOrMore))){
//            propertiesByBedroomNum = WebSraperUsingJsoup.getByBedroomNum(propertiesBySuburb, 5);
//        }

        //Use adapter to connect the listView and my list
        PropertyListViewAdapter propertyListViewAdapter = new PropertyListViewAdapter(PropertiesBySuburb.this, propertiesBySuburb);
        listViewProperty.setAdapter(propertyListViewAdapter);

        //Display the number of results found
        textViewResult = findViewById(R.id.textViewResult);
        textViewResult.setText(propertiesBySuburb.size() + " results found");

//        //Set the spinnerSortRent
//        Spinner spinnerSortRent = findViewById(R.id.spinnerSortRent);
//        String[] items = new String[]{getResources().getString(R.string.lowToHigh),
//                getResources().getString(R.string.highToLow),
//                getResources().getString(R.string.sortByRent)};
//        HintAdapter adapter = new HintAdapter(this, R.layout.spinner_item, items);
//        spinnerSortRent.setAdapter(adapter);
//        //Set the last string of items as the initial text of spinner (as hint)
//        spinnerSortRent.setSelection(adapter.getCount());
//        //Set listener on spinner items
//        spinnerSortRent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//                String selectedItem = parent.getItemAtPosition(position).toString();
//                if(selectedItem.equals(getResources().getString(R.string.lowToHigh)))
//                {
//                    sortedListRent = WebSraperUsingJsoup.sortByRentLowToHigh(propertiesBySuburb);
//                    PropertyListViewAdapter adapter = new PropertyListViewAdapter(PropertiesBySuburb.this, sortedListRent);
//                    listViewProperty.setAdapter(adapter);
//                }
//                else if (selectedItem.equals(getResources().getString(R.string.highToLow))){
//                    sortedListRent = WebSraperUsingJsoup.sortByRentHighToLow(propertiesBySuburb);
//                    PropertyListViewAdapter adapter = new PropertyListViewAdapter(PropertiesBySuburb.this, sortedListRent);
//                    listViewProperty.setAdapter(adapter);
//                }
//            } // To close the onItemSelected
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });

        //Set the spinnerSortBedroom
        Spinner spinnerSortBedroom = findViewById(R.id.spinnerSortBedroom);
        String[] itemsBedroom = new String[]{getResources().getString(R.string.oneBedroom),
                getResources().getString(R.string.twoBedroom),
                getResources().getString(R.string.threeBedroom),
                getResources().getString(R.string.fourBedroom),
                getResources().getString(R.string.fiveBedroomOrMore),
                getResources().getString(R.string.sortByBedroom)};
        HintAdapter adapterBedroom = new HintAdapter(this, R.layout.spinner_item, itemsBedroom);
        spinnerSortBedroom.setAdapter(adapterBedroom);
        //Set the last string of items as the initial text of spinner (as hint)
        spinnerSortBedroom.setSelection(adapterBedroom.getCount());
        //Set listener on spinner items
        spinnerSortBedroom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals(getResources().getString(R.string.oneBedroom)))
                {
                    propertiesByBedroomNum = WebSraperUsingJsoup.getByBedroomNum(propertiesBySuburb, 1);
                    sortedListRent = WebSraperUsingJsoup.sortByRentLowToHigh(propertiesByBedroomNum);
                    PropertyListViewAdapter adapter = new PropertyListViewAdapter(PropertiesBySuburb.this, propertiesByBedroomNum);
                    listViewProperty.setAdapter(adapter);
                    int result = propertiesByBedroomNum.size();
                    if (result == 0){
                        textViewResult.setText(result + " results found.");
                    }
                    else{
                        textViewResult.setText(result + " results found.   Rent is automatically sorted from low to high.");
                    }
//                    numBedroom = 1;
                }
                else if (selectedItem.equals(getResources().getString(R.string.twoBedroom))){
                    propertiesByBedroomNum = WebSraperUsingJsoup.getByBedroomNum(propertiesBySuburb, 2);
                    sortedListRent = WebSraperUsingJsoup.sortByRentLowToHigh(propertiesByBedroomNum);
                    PropertyListViewAdapter adapter = new PropertyListViewAdapter(PropertiesBySuburb.this, propertiesByBedroomNum);
                    listViewProperty.setAdapter(adapter);
                    int result = propertiesByBedroomNum.size();
                    if (result == 0){
                        textViewResult.setText(result + " results found.");
                    }
                    else{
                        textViewResult.setText(result + " results found.   Rent is automatically sorted from low to high.");
                    }
//                    numBedroom = 2;
                }
                else if (selectedItem.equals(getResources().getString(R.string.threeBedroom))){
                    propertiesByBedroomNum = WebSraperUsingJsoup.getByBedroomNum(propertiesBySuburb, 3);
                    sortedListRent = WebSraperUsingJsoup.sortByRentLowToHigh(propertiesByBedroomNum);
                    PropertyListViewAdapter adapter = new PropertyListViewAdapter(PropertiesBySuburb.this, propertiesByBedroomNum);
                    listViewProperty.setAdapter(adapter);
                    int result = propertiesByBedroomNum.size();
                    if (result == 0){
                        textViewResult.setText(result + " results found.");
                    }
                    else{
                        textViewResult.setText(result + " results found.   Rent is automatically sorted from low to high.");
                    }
//                    numBedroom = 3;
                }
                else if (selectedItem.equals(getResources().getString(R.string.fourBedroom))){
                    propertiesByBedroomNum = WebSraperUsingJsoup.getByBedroomNum(propertiesBySuburb, 4);
                    sortedListRent = WebSraperUsingJsoup.sortByRentLowToHigh(propertiesByBedroomNum);
                    PropertyListViewAdapter adapter = new PropertyListViewAdapter(PropertiesBySuburb.this, propertiesByBedroomNum);
                    listViewProperty.setAdapter(adapter);
                    int result = propertiesByBedroomNum.size();
                    if (result == 0){
                        textViewResult.setText(result + " results found.");
                    }
                    else{
                        textViewResult.setText(result + " results found.   Rent is automatically sorted from low to high.");
                    }
//                    numBedroom = 4;
                }
                else if (selectedItem.equals(getResources().getString(R.string.fiveBedroomOrMore))){
                    propertiesByBedroomNum = WebSraperUsingJsoup.getByBedroomNum(propertiesBySuburb, 5);
                    sortedListRent = WebSraperUsingJsoup.sortByRentLowToHigh(propertiesByBedroomNum);
                    PropertyListViewAdapter adapter = new PropertyListViewAdapter(PropertiesBySuburb.this, propertiesByBedroomNum);
                    listViewProperty.setAdapter(adapter);
                    int result = propertiesByBedroomNum.size();
                    if (result == 0){
                        textViewResult.setText(result + " results found.");
                    }
                    else{
                        textViewResult.setText(result + " results found.   Rent is automatically sorted from low to high.");
                    }
//                    numBedroom = 5;
                }
            } // To close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {}
        });

//        //Use adapter to connect the listView and my list
//        PropertyListViewAdapter propertyListViewAdapter = new PropertyListViewAdapter(PropertiesBySuburb.this, allProperties);
//        listViewProperty.setAdapter(propertyListViewAdapter);
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
