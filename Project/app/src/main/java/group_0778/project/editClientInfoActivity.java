package group_0778.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import bookingapp.Client;
import bookingapp.User;
import infomanager.NoSuchUserException;

public class editClientInfoActivity extends Activity {
    public static final String EDITED_USER_EMAIL_KEY = "editedUserKey";
    public static final String EDITOR_USER_EMAIL_KEY = "editorUserKey";
    private User editor;
    private Client edited;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client_info);
        Intent intent = getIntent();

        // Client to be edited
        String editedEmail = (String)intent.getSerializableExtra
                (EDITED_USER_EMAIL_KEY);
        String editorEmail = (String)intent.getSerializableExtra
                (EDITOR_USER_EMAIL_KEY);
        try {
            edited = (Client)LoginActivity.userManager.getUser(editedEmail);
            editor =  LoginActivity.userManager.getUser(editorEmail);
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }
        // User doing the editing


        try {
            edited = (Client)LoginActivity.userManager.
                    getUser(edited.getEmail());
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }

        //Set default edit text values to current client info
        EditText firstName = (EditText) findViewById(R.id.firstNameEditText);
        firstName.setText(edited.getFirstNames());

        EditText lastName = (EditText) findViewById(R.id.lastNameEditText);
        lastName.setText(edited.getLastName());

        EditText address = (EditText) findViewById(R.id.addressEditText);
        address.setText(edited.getAddress());

        EditText creditCard = (EditText) findViewById(R.id.creditCardEditText);
        creditCard.setText(edited.getCreditCardNumber());

        EditText expiryDate = (EditText) findViewById(R.id.expiryDateEditText);
        expiryDate.setText(edited.getExpiryDate());
    }

    /**
     * Change the client info based on the values submitted in the text fields
     * and go back to the
     * main activity for either client or admin, based on editing user.
     */
    public void submitClientInfo(View view){

        // Change Client info to new input
        edited.setFirstNames(((EditText)
                findViewById(R.id.firstNameEditText)).getText().toString());
        edited.setLastName(((EditText)
                findViewById(R.id.lastNameEditText)).getText().toString());
        edited.setAddress(((EditText)
                findViewById(R.id.addressEditText)).getText().toString());
        edited.setCreditCardNumber(((EditText)
                findViewById(R.id.creditCardEditText)).getText().toString());
        edited.setExpiryDate(((EditText)
                findViewById(R.id.expiryDateEditText)).getText().toString());

        //Change Password in User Manager
        String password = ((EditText)
                findViewById(R.id.passwordTextField)).getText().toString();
        LoginActivity.userManager.changePassword(edited, password);

        //Save user manager to internal storage
        LoginActivity.userManager.saveToFile();
        back(view);
    }

    /**
     * Go back to main activity for either admin or client,
     * based on editing user, without changing
     * any user info.
     */
    public void back(View view){

        // If editing user is a client
        if (editor instanceof Client){

            // Create new Intent to go to ClientMain and put in and put
            // in UserManager and client as User
            Intent intent = new Intent(this, ClientMain.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(LoginActivity.USER_EMAIL_KEY,
                    edited.getEmail());
            intent.putExtras(bundle);
            startActivity(intent);
        }

        // If editing user is an admin editing a client
        else{

            // Create new Intent to go to AdminActivity and put UserManager
            // and editor as User
            Intent intent = new Intent(this, AdminActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(LoginActivity.USER_EMAIL_KEY,
                    editor.getEmail());
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }
}
