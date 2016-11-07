package group_0778.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import bookingapp.Client;
import infomanager.NoSuchUserException;

public class ClientMain extends AppCompatActivity {
    protected static Client client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);
        Intent intent = getIntent();
        String email = (String) intent.getSerializableExtra
                (LoginActivity.USER_EMAIL_KEY);
        try {
            client = (Client) LoginActivity.userManager.getUser(email);
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }
        TextView textView = (TextView) findViewById(R.id.userInfo);
        String clientInfo;
        clientInfo = "Email: " + client.getEmail() + "\n";
        clientInfo += "Name: " + client.getFirstNames() + " " +
                client.getLastName() + "\n";
        clientInfo += "Address: " + client.getAddress() + "\n";
        clientInfo += "Credit Card Number: " + client.getCreditCardNumber() +
                "\n";
        clientInfo += "Expiry Date: " + client.getExpiryDate();
        textView.setText(clientInfo);
    }

    /**
     * Go to ViewBookedItineraries activity
     * Bundle contains current user as both edited user and editor user,
     * and user manager.
     *
     */
    public void viewBooked(View view) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, DisplayBookedItineraries.class);
        bundle.putSerializable(editClientInfoActivity.EDITED_USER_EMAIL_KEY,
                client.getEmail());
        bundle.putSerializable(editClientInfoActivity.EDITOR_USER_EMAIL_KEY,
                client.getEmail());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Go to editClientInfo activity for editing client info
     * Goes to editClientInfoActivity
     *
     */
    public void editClientInfo(View view) {
        Intent intent = new Intent(this, editClientInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(editClientInfoActivity.EDITOR_USER_EMAIL_KEY,
                client.getEmail());
        bundle.putSerializable(editClientInfoActivity.EDITED_USER_EMAIL_KEY,
                client.getEmail());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Onclick method for booking itinerary, goes to SearchActivity

     */
    public void bookItineraryByClient(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        LoginActivity.clientEmail = client.getEmail();
        startActivity(intent);
    }
}
