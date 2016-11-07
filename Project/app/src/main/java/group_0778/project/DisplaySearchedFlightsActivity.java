package group_0778.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import bookingapp.Flight;
import infomanager.FlightManager;

public class DisplaySearchedFlightsActivity extends AppCompatActivity {

    private FlightManager manager;
    private String[] searchInfos;
    private LinearLayout.LayoutParams params;
    private LinearLayout lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_searched_flights);

        //getting the intent
        Intent intent = getIntent();
        manager = LoginActivity.flightManager;
        searchInfos = intent.getStringArrayExtra(SearchActivity.
                SEARCH_INFO_KEY);
        Map<String, Flight> possibleFlights = manager.getFlights(
                searchInfos[0], searchInfos[1], searchInfos[2]);

        // define the layout params that will be used to define how the
        // button will be displayed
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        lm = (LinearLayout) findViewById(R.id.linearLayout);

        //show all the possible flights in the txtviews
        int j = 0;
        for (String fN : possibleFlights.keySet()) {
            Flight flight = possibleFlights.get(fN);
            makeTxtview(j, flight);
            j++;
        }
    }

    /**
     * making textview of all possible flights
     */
    protected void makeTxtview(int j, Flight flight) {
        LinearLayout row = new LinearLayout(this);
        row.setLayoutParams(params);

        // Create txtview
        TextView txt = new TextView(this);

        txt.setLayoutParams(params);

        // set id
        txt.setId(j + 1);


        // set text of the textview to the information of the flight
        txt.setText(flight.toString()+" Total Travel Time: " +
                flight.getTravelTime());


        final Intent newIntent = new Intent(this,
                ViewFlightsInfoActivity.class);



        //Add txtview to LinearLayout defined in XML
        row.addView(txt);
        lm.addView(row);
    }


    /**
     * sorts the flights by time and make txtviews for each
     */
    public void SortByTime(View view) {

        Map<String, Flight> possibleFlights = manager.getFlights(
                searchInfos[0], searchInfos[1], searchInfos[2]);

        ArrayList<Flight> flight = new ArrayList<>();
        for (String fN : possibleFlights.keySet()) {
             flight.add(possibleFlights.get(fN));}

        ArrayList<Flight> sort = manager
                .sortFlightsByTime(flight);
        lm.removeAllViews();

        int j = 0;
        for (Flight fN : sort) {
            makeTxtview(j, fN);
            j++;
        }
    }

    /**
     * sorts the flights by Cost and make txtviews for each
     */
    public void SortByCost(View view) {
        Map<String, Flight> possibleFlights = manager.getFlights(
                searchInfos[0], searchInfos[1], searchInfos[2]);

        ArrayList<Flight> flight = new ArrayList<>();
        for (String fN : possibleFlights.keySet()) {
            flight.add(possibleFlights.get(fN));}

        ArrayList<Flight> sort = manager
                .sortFlightsByCost(flight);
        lm.removeAllViews();

        int j = 0;
        for (Flight fN : sort) {
            makeTxtview(j, fN);
            j++;
        }
    }
}
