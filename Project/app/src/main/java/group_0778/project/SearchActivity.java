package group_0778.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class SearchActivity extends AppCompatActivity {

    public final static String SEARCH_INFO_KEY = "searchInfoKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    }

    /**
     * The onClick method for search_flight_field button that searches flights
     * with given inputs, and starts DisplaySearchedFlightsActivity.
     *
     */
    public void startSearchFlightsActivity(View view) {
        Intent intent = new Intent(this, DisplaySearchedFlightsActivity.class);

        String[] searchInfo = getSearchInfo(view);

        intent.putExtra(SEARCH_INFO_KEY, searchInfo);

        startActivity(intent);
    }


    /**
     * The onClick method for search_itineraries_field that searches itinerary
     * by the given input, and starts DisplaySearchedItinerariesActivity.
     *
     */
    public void startSearchItinerariesActivity(View view) {
        Intent intent = new Intent(this,
                DisplaySearchedItinerariesActivity.class);

        String[] searchInfo = getSearchInfo(view);

        intent.putExtra(SEARCH_INFO_KEY, searchInfo);

        startActivity(intent);
    }

    /**
     * Get all the input info into a String[] and returns it.
     *
     * @param view the current view
     * @return a String[] with each String inside:
     *                  departureDate, travelOrigin, destination.
     */
    public String[] getSearchInfo(View view) {
        // Gets all the infos we need for searching flights/itineraries
        EditText departureDateField
                = (EditText) findViewById(R.id.departureDateField);
        EditText travelOriginField
                = (EditText) findViewById(R.id.travelOriginField);
        EditText destinationField
                = (EditText) findViewById(R.id.destinationField);

        // Put all the infos to String form
        String departureDate = departureDateField.getText().toString();
        String travelOrigin = travelOriginField.getText().toString();
        String destination = destinationField.getText().toString();

        // Put into String[] form
        String[] result = {departureDate, travelOrigin, destination};

        // Return result
        return result;
    }
}
