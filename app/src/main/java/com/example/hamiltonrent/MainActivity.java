package com.example.hamiltonrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //private static int SPLASH_TIME_OUT = 65000;
    private static int SPLASH_TIME_OUT = 8000;
    private HarcourtsWebScraper harcourtsWebScraper;
    private WaikatoREWebScraper waikatoREWebScraper;
    private LodgeWebScraper lodgeWebScraper;
    private RayWhiteWebScraper rayWhiteWebScraper;
    private EvesWebScraper evesWebScraper;
    private GlasshouseWebScraper glasshouseWebScraper;
    private List<Property> allProperties;
    private SharedPreferences sharedPreferences;
    private AlphaAnimation animation;
    private FrameLayout progressBarHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize a progressBarHolder
        progressBarHolder = findViewById(R.id.progressBarHolder);

        //Clear the previous shared preferences
        this.getSharedPreferences("shared preferences", 0).edit().clear().commit();

        //Execute the scraping process
        new doTheScraping().execute();

        //Splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, HomePage.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    /**
     * A class for doing the web scraping in the background
     */
    public class doTheScraping extends AsyncTask<Void, Void, List<Property>> {

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
                //Initialize an empty list to store all the properties
                allProperties = new ArrayList<>();

                harcourtsWebScraper = new HarcourtsWebScraper();
                List<Property> listHarcourts = harcourtsWebScraper.getHamiltonRentResidentialData();

//                waikatoREWebScraper = new WaikatoREWebScraper();
//                List<Property> listWaikatoRE = waikatoREWebScraper.getHamiltonRentResidentialData();

                lodgeWebScraper = new LodgeWebScraper();
                List<Property> listLodge = lodgeWebScraper.getHamiltonRentResidentialData();

                rayWhiteWebScraper = new RayWhiteWebScraper();
                List<Property> listRayWhite = rayWhiteWebScraper.getHamiltonRentResidentialData();

                evesWebScraper = new EvesWebScraper();
                List<Property> listEves = evesWebScraper.getHamiltonRentResidentialData();

                glasshouseWebScraper = new GlasshouseWebScraper();
                List<Property> listGlasshouse = glasshouseWebScraper.getHamiltonRentResidentialData();

                //Combine all the lists into one list
                allProperties.addAll(listHarcourts);
//                allProperties.addAll(listWaikatoRE);
                allProperties.addAll(listLodge);
                allProperties.addAll(listRayWhite);
                allProperties.addAll(listEves);
                allProperties.addAll(listGlasshouse);

                //Save data in shared preferences
                sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(allProperties);
                editor.putString("task list", json);
                editor.apply();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return allProperties;
        }

        @Override
        protected void onPostExecute(List<Property> properties) {
            super.onPostExecute(properties);

            //End the loading animation
            progressBarHolder.setVisibility(View.GONE);
        }
    }
}
