package group_0778.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;

import bookingapp.Client;
import bookingapp.Itinerary;
import infomanager.NoSuchUserException;

public class DisplayBookedItineraries extends Activity {
    protected static Client client;
    private LinearLayout.LayoutParams params;
    private LinearLayout lm;
    private HashMap<CheckBox, Itinerary> checkBoxHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_booked_itineraries);

        Intent intent = getIntent();

        String email = (String) intent.getSerializableExtra(
                editClientInfoActivity.EDITED_USER_EMAIL_KEY);
        client = null;
        try {
            client = (Client) LoginActivity.userManager.getUser(email);
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }
        List<Itinerary> bookedItineraries = client.getBookedItineraries();


        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        lm = (LinearLayout) findViewById(R.id.linearLayout);

        //for all booked itinerary we are gonna show them as a button
        int i = 0;
        for (final Itinerary itinerary : bookedItineraries) {
            String flights = itinerary.toString();
            makeButton(i, itinerary);
            i++;
        }
    }


    /**
     * make a button out of each booked Itinerary we have for the client
     *
     * @param j
     * @param itin
     */
    protected void makeButton(int j, Itinerary itin) {
        LinearLayout row = new LinearLayout(this);
        row.setLayoutParams(params);

        // Create Checkbox
        CheckBox btn = new CheckBox(this);
        checkBoxHashMap.put(btn, itin);

        btn.setLayoutParams(params);

        // set id
        btn.setId(j + 1);


        // set text to its cost and travel time
        btn.setText(itin.toString());
        //Add button to LinearLayout defined in XML
        row.addView(btn);
        lm.addView(row);
    }

    /**
     * when the box is checked and remove button is pressed
     * the booked itinerary is removed for that
     * client
     *
     */
    public void remove(View view) {

        for (CheckBox box : checkBoxHashMap.keySet()) {
            if (box.isChecked()) {
                box.setText(checkBoxHashMap.get(box).getOrigin());
                client.selectItinerary(checkBoxHashMap.get(box));
                client.remove();
                box.setVisibility(View.GONE);
            }
        }

        //save the changes to the file
        LoginActivity.userManager.saveToFile();
        LoginActivity.flightManager.saveToFile();
    }


}


