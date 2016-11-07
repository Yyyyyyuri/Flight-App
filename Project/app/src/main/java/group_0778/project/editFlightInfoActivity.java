package group_0778.project;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import bookingapp.Flight;
import infomanager.NoSuchFlightException;

import android.content.Intent;
import android.widget.EditText;

public class editFlightInfoActivity extends Activity {

    private Flight editFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flight_info);
        Intent intent = getIntent();
        String flightNum = intent.getStringExtra(AdminActivity
                .EDITED_FLIGHTNUM_KEY);
        try{
            editFlight = LoginActivity.flightManager.getFlight(flightNum);
        }catch(NoSuchFlightException e){
            e.printStackTrace();
        }

        //Set up the existing information, and ready for the admin to edit it.
        EditText departureDateTime = (EditText) findViewById(R.id
                .editingDepartureDateTime);
        departureDateTime.setText(editFlight.getDepartureDateTime());

        EditText arrivalDateTime = (EditText) findViewById(R.id
                .editingArrivalDateTime);
        arrivalDateTime.setText(editFlight.getArrivalDateTime());

        EditText airline = (EditText) findViewById(R.id.editingAirline);
        airline.setText(editFlight.getAirline());

        EditText origin = (EditText) findViewById(R.id.editingOrigin);
        origin.setText(editFlight.getOrigin());

        EditText destination = (EditText) findViewById(R.id.editingDestination);
        destination.setText(editFlight.getDestination());

        EditText price = (EditText) findViewById(R.id.editingPrice);
        price.setText(editFlight.getStringCost());

        EditText numSeats = (EditText) findViewById(R.id.editNumSeats);
        numSeats.setText(editFlight.getNumSeats());
    }

    /**
     * Update the information about a flight.
     */
    public void submitFlightInfo(View view){

        // Update the information according to the input.
        editFlight.setDepartureDateTime(((EditText) findViewById(R.id
                .editingDepartureDateTime)).getText().toString());
        editFlight.setArrivalDateTime(((EditText) findViewById(R.id
                .editingArrivalDateTime)).getText().toString());
        editFlight.setAirline(((EditText) findViewById(R.id.editingAirline))
                .getText().toString());
        editFlight.setOrigin(((EditText) findViewById(R.id.editingOrigin))
                .getText().toString());
        editFlight.setDestination(((EditText) findViewById(R.id
                .editingDestination)).getText().toString());
        editFlight.setCost(((EditText) findViewById(R.id.editingPrice))
                .getText().toString());
        editFlight.setNumSeats(((EditText) findViewById(R.id.editNumSeats))
                .getText().toString());

        // Save the new data into the internal storage.
        LoginActivity.flightManager.saveToFile();
        // Move back to the admin homepage
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra(LoginActivity.USER_EMAIL_KEY, AdminActivity.adminEmail);
        startActivity(intent);


    }
}
