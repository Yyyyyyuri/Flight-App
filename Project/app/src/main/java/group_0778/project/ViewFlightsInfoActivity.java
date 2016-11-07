package group_0778.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import bookingapp.Client;
import bookingapp.Itinerary;
import infomanager.NoSuchUserException;

/**
 * Show the flights info for the corresponding Itinerary
 */
public class ViewFlightsInfoActivity extends Activity {

    private Itinerary itinerary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flights_info);

        Intent intent = getIntent();

        // get the itinerary client selected
        itinerary = (Itinerary) intent
                .getSerializableExtra(DisplaySearchedItinerariesActivity
                        .ITINERARY_KEY);
        String flights = itinerary.toString();
        TextView textView = new TextView(this);
        textView.setText(flights);
        LinearLayout row = new LinearLayout(this);
        row.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                        .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout lm = (LinearLayout) findViewById(R.id.flights_info_layout);
        row.addView(textView);
        lm.addView(row);
    }

    /**
     * onClick method for booking itinerary
     */
    public void bookItinerary(View view) {
        try{
            // find out which client is booking itinerary
            Client curClient = (Client) LoginActivity.userManager.getUser
                    (LoginActivity.clientEmail);
            // select itinerary
            curClient.selectItinerary(itinerary);
            // book the selected itinerary if it isn't in client's
            // bookedItineraries
            if (!curClient.getBookedItineraries().contains(itinerary)) {
                curClient.add();
                LoginActivity.userManager.saveToFile();
                Context context = getApplicationContext();
                CharSequence text = "You have successfully booked the current"
                        + " Itinerary!";
                int duration = Toast.LENGTH_SHORT;
                // display booking success message.
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                // prompt out message if itinerary was already booked
                Context context = getApplicationContext();
                CharSequence text = "The selected Itinerary was already " +
                        "booked.";
                int duration = Toast.LENGTH_SHORT;
                // display booking success message.
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }catch(NoSuchUserException e){
            e.printStackTrace();
        }



    }
}
