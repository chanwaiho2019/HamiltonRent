package com.example.hamiltonrent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.PrimitiveIterator;

public class HarcourtsOneBedroom extends AppCompatActivity {

    private HarcourtsWebscraperUsingJsoup harcourtsWebscraperUsingJsoup;
    private List<Property> allResidentialProperties;
    private List<Property> oneBedroomProperties;
    private ListView listViewProperty;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_harcourts_one_bedroom);
//
//        //Initialize a HarcourtsWebScraperUsingJsoup object
//        harcourtsWebscraperUsingJsoup = new HarcourtsWebscraperUsingJsoup();
//        //Get the list of all properties
//        allResidentialProperties = harcourtsWebscraperUsingJsoup.getHamiltonRentResidentialData();
//        //Get the list of one bedroom properties
//        oneBedroomProperties = harcourtsWebscraperUsingJsoup.getByBedroomNum(allResidentialProperties, 1);
//
//        //Initialize the listViewProperty
//        listViewProperty = findViewById(R.id.listViewProperty);
//        //Use adapter to connect the listView and my list
//        PropertyListViewAdapter adapter = new PropertyListViewAdapter(this, oneBedroomProperties);
//        listViewProperty.setAdapter(adapter);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harcourts_one_bedroom);
        //Initialize the textView object
        listViewProperty = findViewById(R.id.listViewProperty);
        //Execute the scraping process
        new doTheScraping().execute();

    }

    /**
     * A class for doing the web scraping in the background
     */
    public class doTheScraping extends AsyncTask<Void, Void, List<Property>>{
        @Override
        protected List<Property> doInBackground(Void... voids) {
            try {
                //Initialize a HarcourtsWebScraperUsingJsoup object
                harcourtsWebscraperUsingJsoup = new HarcourtsWebscraperUsingJsoup();
                //Get the list of all properties
                allResidentialProperties = harcourtsWebscraperUsingJsoup.getHamiltonRentResidentialData();
                //Get the list of one bedroom properties
                oneBedroomProperties = harcourtsWebscraperUsingJsoup.getByBedroomNum(allResidentialProperties, 1);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return oneBedroomProperties;
        }

        @Override
        protected void onPostExecute(List<Property> properties) {
            super.onPostExecute(properties);
            //Use adapter to connect the listView and my list
            PropertyListViewAdapter adapter = new PropertyListViewAdapter(HarcourtsOneBedroom.this, oneBedroomProperties);
            listViewProperty.setAdapter(adapter);
        }
    }
}
