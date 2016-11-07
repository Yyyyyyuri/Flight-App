package group_0778.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bookingapp.Admin;
import bookingapp.Client;
import bookingapp.Flight;
import bookingapp.User;
import infomanager.NoSuchFlightException;
import infomanager.NoSuchUserException;


import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileNotFoundException;


public class AdminActivity extends AppCompatActivity {

    protected static final String EDITED_FLIGHTNUM_KEY = "editedFlightNumKey";
    protected static String adminEmail;
    private Admin admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Gets the user email from the intent passed from LoginActivity.
        Intent intent = getIntent();
        adminEmail = intent.getStringExtra(LoginActivity.USER_EMAIL_KEY);

        // Gets the Admin from the user manager
        try {
            admin = (Admin) LoginActivity.userManager.getUser(adminEmail);
        } catch (NoSuchUserException e) {

            // Shows a pop up message if the Admin is not in user manager
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Admin doesn't exist.", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }

    }

    /**
     * Goes into editClientInfoActivity to edit the information of the client
     * whose username is entered in the userEmailField EditText.
     *
     */
    public void editClientInfos(View view) {
        Intent intent = new Intent(this, editClientInfoActivity.class);

        // Store the client's email address to edit the info of it later.
        EditText userEmailField = (EditText) findViewById(R.id.user_email);
        String userEmail = userEmailField.getText().toString();

        try {
            // If the client email belongs to a valid client, go to
            // editClientActivity
            User curUser = LoginActivity.userManager.getUser(userEmail);
            Bundle bundle = new Bundle();

            // Put the current admin's email in the intent
            bundle.putSerializable(editClientInfoActivity.EDITOR_USER_EMAIL_KEY,
                    admin.getEmail());

            // Put the email of the client to be edited in the intent
            bundle.putSerializable(editClientInfoActivity.EDITED_USER_EMAIL_KEY,
                    curUser.getEmail());

            // Go to activity with intent
            intent.putExtras(bundle);
            startActivity(intent);

        } catch (NoSuchUserException e) {

            // If client username entered is not a valid user, show pop up with
            // an error message
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Client doesn't exist.", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
    }


    /**
     * Upload a new client information file to internal storage.
     *
     */
    public void uploadClientInfoFile(View view) {
        EditText clientPathField = (EditText) findViewById
                (R.id.client_file_path_field);
        String clientPath = clientPathField.getText().toString();

        try {
            // add new client information into userManager
            LoginActivity.userManager.add(clientPath);
            // save new information into internal storage
            LoginActivity.userManager.saveToFile();
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Successfully uploaded!", Toast.LENGTH_SHORT);
            toast.show();
        } catch (FileNotFoundException e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "File doesn't exist.", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
    }

    /**
     * Goes to editFlightInfoActivity to edit the information of a flight based
     * on the Flight number entered in the flightNumberField EditText.
     *
     */
    public void editFlightInfos(View view) {

        //Create new intent
        Intent intent = new Intent(this, editFlightInfoActivity.class);

        // Get flight number entered in EditText
        EditText flightNumberField = (EditText) findViewById(R.id
                .flight_number_field);
        String flightNum = flightNumberField.getText().toString();
        try{
            // If flight number belongs to valid flight, start new Activity
            Flight editFlight = LoginActivity.flightManager
                    .getFlight(flightNum);
            intent.putExtra(EDITED_FLIGHTNUM_KEY,flightNum);
            startActivity(intent);
        }catch(NoSuchFlightException e){
            // If flight number does not belong to valid flight, show pop up
            // with error message
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Flight doesn't exist.", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }

        }

    /**
     * Upload a new flight information file to internal storage
     *
     */
    public void uploadFlightInfoFile(View view) {
        EditText flightPathField = (EditText)
                findViewById(R.id.flight_file_path_field);
        String flightPath = flightPathField.getText().toString();

        try {
            // read the csv file and add the information into internal storage
            LoginActivity.flightManager.add(flightPath);
            LoginActivity.flightManager.saveToFile();
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Successfully uploaded!", Toast.LENGTH_SHORT);
            toast.show();
        } catch (FileNotFoundException e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "File doesn't exist.", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }

    }

    /**
     * View booked itineraries by admin for a specific client based on the email
     * entered in the clientEmailField EditText.
     *
     */
    public void viewBookedItinerariesByAdmin(View view) {

        // Get the client email from the EditText
        EditText clientEmailField = (EditText)
                findViewById(R.id.user_email);
        String clientEmail = clientEmailField.getText().toString();

        // Try to go to viewBookedItineraries Activity of client
        try {

            // If clientEmail belongs to valid client
            Client client = (Client)LoginActivity.userManager
                    .getUser(clientEmail);
            // Set up the intent that will be pass onto.
            Intent intent = new Intent(this, DisplayBookedItineraries.class);
            // pass the client email into next activity
            intent.putExtra
                    (editClientInfoActivity.EDITED_USER_EMAIL_KEY, clientEmail);
            startActivity(intent);
        } catch (NoSuchUserException e) {
            // If clientEmail does not belong to valid client, show errMessage
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Client doesn't exist",
                    Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }

    }

    /**
     * Book itinerary for client by admin, this onclick method will
     * goes into SearchActivity
     *
     */
    public void bookItineraryByAdmin(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        // Get client email from EditText
        EditText clientEmailField = (EditText) findViewById
                (R.id.user_email);
        // try to see if the email address exists.
        try{
            LoginActivity.userManager.getUser(clientEmailField.getText()
                    .toString());
            LoginActivity.clientEmail = clientEmailField.getText().toString();
            startActivity(intent);
        }catch(NoSuchUserException e){
            // If user with email does not exist, show error message
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Client doesn't exist.", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }

    }
}
