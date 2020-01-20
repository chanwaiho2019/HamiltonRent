package com.example.hamiltonrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class HarcourtsByBedroom extends AppCompatActivity {

    private HarcourtsWebscraperUsingJsoup harcourtsWebscraperUsingJsoup;
    private List<Property> allResidentialProperties;
    private List<Property> propertiesByBedroomNum;
    private ListView listViewProperty;
    private String numBedroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harcourts_one_bedroom);

        //Get the string from previous intent
        Intent intent = getIntent();
        numBedroom = intent.getStringExtra("numBedroom");

        //Set title of activity
        setTitle("Harcourts " + "(" + numBedroom + ")");

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

                //Get the corresponding data according to the numBedroom category selected by user
                if (numBedroom.equals(getResources().getString(R.string.oneBedroom))){
                    propertiesByBedroomNum = harcourtsWebscraperUsingJsoup.getByBedroomNum(allResidentialProperties, 1);
                }
                else if (numBedroom.equals(getResources().getString(R.string.twoBedroom))){
                    propertiesByBedroomNum = harcourtsWebscraperUsingJsoup.getByBedroomNum(allResidentialProperties, 2);
                }
                else if (numBedroom.equals(getResources().getString(R.string.threeBedroom))){
                    propertiesByBedroomNum = harcourtsWebscraperUsingJsoup.getByBedroomNum(allResidentialProperties, 3);
                }
                else if (numBedroom.equals(getResources().getString(R.string.fourBedroom))){
                    propertiesByBedroomNum = harcourtsWebscraperUsingJsoup.getByBedroomNum(allResidentialProperties, 4);
                }
                else if (numBedroom.equals(getResources().getString(R.string.fiveBedroom))){
                    propertiesByBedroomNum = harcourtsWebscraperUsingJsoup.getByBedroomNum(allResidentialProperties, 5);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return propertiesByBedroomNum;
        }

        @Override
        protected void onPostExecute(List<Property> properties) {
            super.onPostExecute(properties);
            //Use adapter to connect the listView and my list
            PropertyListViewAdapter adapter = new PropertyListViewAdapter(HarcourtsByBedroom.this, propertiesByBedroomNum);
            listViewProperty.setAdapter(adapter);
        }
    }
}
