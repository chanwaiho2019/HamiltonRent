package com.example.hamiltonrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

public class HarcourtsByBedroom extends AppCompatActivity {

    private HarcourtsWebScraper harcourtsWebScraper;
    private List<Property> allResidentialProperties;
    private List<Property> propertiesByBedroomNum;
    private List<Property> sortedList;
    private ListView listViewProperty;
    private String numBedroom;
    private AlphaAnimation animation;
    private FrameLayout progressBarHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harcourts_by_bedroom);

        //Initialize a progressBarHolder
        progressBarHolder = findViewById(R.id.progressBarHolderHarcourts);

        //Get the string from previous intent
        Intent intent = getIntent();
        numBedroom = intent.getStringExtra("numBedroom");

        //Set title of activity
        setTitle("Harcourts " + "(" + numBedroom + ")");

        //Initialize the textView object
        listViewProperty = findViewById(R.id.listViewPropertyHarcourts);

        //Execute the scraping process
        new doTheScraping().execute();

        //Set the spinner
        Spinner spinnerSort = findViewById(R.id.spinnerSortHarcourts);
        String[] items = new String[]{getResources().getString(R.string.lowToHigh),
                getResources().getString(R.string.highToLow),
                getResources().getString(R.string.sortByRent)};
        HintAdapter adapter = new HintAdapter(this, R.layout.spinner_item, items);
        spinnerSort.setAdapter(adapter);
        //Set the last string of items as the initial text of spinner (as hint)
        spinnerSort.setSelection(adapter.getCount());
        //Set listener on spinner items
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals(getResources().getString(R.string.lowToHigh)))
                {
                    sortedList = harcourtsWebScraper.sortByRentLowToHigh(propertiesByBedroomNum);
                    PropertyListViewAdapter adapter = new PropertyListViewAdapter(HarcourtsByBedroom.this, sortedList);
                    listViewProperty.setAdapter(adapter);
                }
                else if (selectedItem.equals(getResources().getString(R.string.highToLow))){
                    sortedList = harcourtsWebScraper.sortByRentHighToLow(propertiesByBedroomNum);
                    PropertyListViewAdapter adapter = new PropertyListViewAdapter(HarcourtsByBedroom.this, sortedList);
                    listViewProperty.setAdapter(adapter);
                }
            } // To close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**
     * A class for doing the web scraping in the background
     */
    public class doTheScraping extends AsyncTask<Void, Void, List<Property>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Set the loading animation
            animation = new AlphaAnimation(0f, 1f);
            animation.setDuration(200);
            progressBarHolder.setAnimation(animation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Property> doInBackground(Void... voids) {
            try {
                //Initialize a HarcourtsWebScraperUsingJsoup object
                harcourtsWebScraper = new HarcourtsWebScraper();
                //Get the list of all properties
                allResidentialProperties = harcourtsWebScraper.getHamiltonRentResidentialData();

                //Get the corresponding data according to the numBedroom category selected by user
                if (numBedroom.equals(getResources().getString(R.string.oneBedroom))){
                    propertiesByBedroomNum = harcourtsWebScraper.getByBedroomNum(allResidentialProperties, 1);
                }
                else if (numBedroom.equals(getResources().getString(R.string.twoBedroom))){
                    propertiesByBedroomNum = harcourtsWebScraper.getByBedroomNum(allResidentialProperties, 2);
                }
                else if (numBedroom.equals(getResources().getString(R.string.threeBedroom))){
                    propertiesByBedroomNum = harcourtsWebScraper.getByBedroomNum(allResidentialProperties, 3);
                }
                else if (numBedroom.equals(getResources().getString(R.string.fourBedroom))){
                    propertiesByBedroomNum = harcourtsWebScraper.getByBedroomNum(allResidentialProperties, 4);
                }
                else if (numBedroom.equals(getResources().getString(R.string.fiveBedroomOrMore))){
                    propertiesByBedroomNum = harcourtsWebScraper.getByBedroomNum(allResidentialProperties, 5);
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

            //End the loading animation
            progressBarHolder.setVisibility(View.GONE);

            //Use adapter to connect the listView and my list
            PropertyListViewAdapter adapter = new PropertyListViewAdapter(HarcourtsByBedroom.this, propertiesByBedroomNum);
            listViewProperty.setAdapter(adapter);
        }
    }
}
