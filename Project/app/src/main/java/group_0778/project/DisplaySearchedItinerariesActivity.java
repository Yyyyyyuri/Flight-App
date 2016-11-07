package group_0778.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import bookingapp.Itinerary;
import infomanager.SearchAlgorithm;

public class DisplaySearchedItinerariesActivity extends AppCompatActivity {

    public static final String ITINERARY_KEY = "itineraryKey";
    static SearchAlgorithm searchAlgorithm = new SearchAlgorithm();
    private String[] searchInfos;
    private LinearLayout.LayoutParams params;
    private LinearLayout lm;

    // Maps each Button's id to its corresponding Itinerary

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_searched_itineraries);

        Intent intent = getIntent();

        // gets the searchInfos from intent
        searchInfos = intent.getStringArrayExtra(SearchActivity
                .SEARCH_INFO_KEY);

        // search for possible itineraries
        ArrayList<Itinerary> possibleItineraries = searchAlgorithm
                .searchItineraries(LoginActivity.flightManager, searchInfos[0],
                        searchInfos[1], searchInfos[2]);

        // define the layout params that will be used to define how the
        // button will be displayed
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Create buttons dynamically
        int j = 0;

        // gets the linearlayout that's gonna contain all the buttons
        lm = (LinearLayout) findViewById(R.id.linearLayout);

        // generates buttons
        for(Itinerary itinerary : possibleItineraries) {
            makeButton(j, itinerary);
            j++;
        }

    }


    /**
     * Make a button and add view according to itinerary with a onClickListener
     * that starts ViewFlightsInfoActivity and brings the itinerary over.
     *
     */
    protected void makeButton(int j, Itinerary itinerary) {
        LinearLayout row = new LinearLayout(this);
        row.setLayoutParams(params);

        // Create Button
        Button btn = new Button(this);

        // set its layout params
        btn.setLayoutParams(params);

        // set id
        btn.setId(j+1);

        final Itinerary onClickItinerary = itinerary;


        // set text to its cost and travel time
        btn.setText("Total Cost: " + onClickItinerary.getTotalCost()
                + ", Total Travel Time: " + onClickItinerary.getTotalTime()
                + ".\nClick for flights information or Booking.");


        // declare index and initiate intent
        final int index = j;
        final Intent newIntent = new Intent(this,
                ViewFlightsInfoActivity.class);

        // Set click listener for button
        btn.setOnClickListener(new View.OnClickListener() {
            // the onClick method for the current button
            public void onClick(View v) {

                // for testing
                Log.i("TAG", "index :" + index);

                // put the corresponding Itinerary in Intent
                newIntent.putExtra(ITINERARY_KEY, onClickItinerary);


                // start new activity
                startActivity(newIntent);
            }
        });
        //Add button to LinearLayout defined in XML
        row.addView(btn);
        lm.addView(row);
    }

    /**
     * Update view with new buttons displaying itinerary sorted by time.
     *
     */
    public void displayItinerariesByTime(View view) {
        ArrayList<Itinerary> possibleItineraries = searchAlgorithm
                .searchItinerariesSortedByTime(LoginActivity.flightManager,
                        searchInfos[0], searchInfos[1], searchInfos[2]);
        lm.removeAllViews();

        int i = 0;
        for (Itinerary itinerary : possibleItineraries) {
            makeButton(i, itinerary);
            i++;
        }
    }

    /**
     * Update view with new buttons displaying itinerary sorted by cost.
     */
    public void displayItinerariesByCost(View view) {
        ArrayList<Itinerary> possibleItineraries = searchAlgorithm
                .searchItinerariesSortedByCost(LoginActivity.flightManager,
                        searchInfos[0], searchInfos[1], searchInfos[2]);
        lm.removeAllViews();

        int i = 0;
        for (Itinerary itinerary : possibleItineraries) {
            makeButton(i, itinerary);
            i++;
        }
    }
}
