package group_0778.project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import bookingapp.Admin;
import bookingapp.Client;
import bookingapp.User;
import infomanager.FlightManager;
import infomanager.NoSuchUserException;
import infomanager.UserManager;

public class LoginActivity extends AppCompatActivity {

    // If "userdata" or "user.ser" (same for flights) are used in several places
    // in the code then if we need to change one later on, then we need to make
    // a single change here, in order for all of them to be consistently
    // updated.
    public static final String CLIENTFILENAME = "serClient.txt";
    public static final String FLIGHTFILENAME = "serFlight.txt";
    public static final String PWDFILENAME = "password.txt";
    public static final String USER_EMAIL_KEY = "userKey";
    public static final String DATADIR = "Database";
    public static final String CLIENTFILE = "clients.txt";
    public static final String FLIGHTFILE1 = "flights1.txt";
    public static final String FLIGHTFILE2 = "flights2.txt";
    public static final String PASSWORD_FILE = "passwords.txt";


    protected static FlightManager flightManager;
    protected static UserManager userManager;
    protected User user;
    protected static String email;
    protected static String clientEmail = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Find out where this application stores files, and get
        // the file Directory
        File userdata = this.getApplicationContext().getDir(DATADIR,
                MODE_APPEND);
        File flightdata = this.getApplicationContext().getDir(LoginActivity
                .DATADIR, MODE_APPEND);

        // Make a File object that stores this path to a file called
        // CLIENTFILENAME.
        File usersFile = new File(userdata, CLIENTFILENAME);

        // Make a File object that stores this path to a file called
        // FLIGHTFILENAME.
        File flightsFile = new File(flightdata, FLIGHTFILENAME);


        // Initialize the *Manager.  It will load data from *File
        try {

            userManager = new UserManager(usersFile);
//            if (userManager.isFlag()) {
            userManager.add(this.getApplicationContext().getAssets()
                    .open(CLIENTFILE));
//            }
            userManager.saveToFile();
            flightManager = new FlightManager(flightsFile);
//            if (flightManager.isFlag()) {
            flightManager.add(this.getApplicationContext().getAssets()
                    .open(FLIGHTFILE1));
            flightManager.add(this.getApplicationContext().getAssets()
                    .open(FLIGHTFILE2));
//            }
            flightManager.saveToFile();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Store passwords from password.txt in internal storage

        ArrayList<String> passwordsTxt = new ArrayList<>();

        File pwddata = this.getApplicationContext().getDir(DATADIR,
                MODE_APPEND);
        File pwdFile = new File(pwddata, PWDFILENAME);

        try {
            // Read from passwords.txt in Assets
            InputStream externalPasswords = this.getApplicationContext()
                    .getAssets().open(PASSWORD_FILE);
            if (pwdFile.exists()) {
                userManager.readFromPasswordFile(pwdFile.getPath());
            } else {
                pwdFile.createNewFile();
            }
            userManager.setPwdFilePath(pwdFile.getPath());

            userManager.readPassword(externalPasswords);
            // write the password to the internal storage.
            userManager.savePasswordToFile();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Onclick method that check the usertype and pass onto the corresponding
     * activity, admin or client
     * @param view the sign in button
     */
    public void login(View view) {

        //gets email and save it as string
        TextView errormsg = (TextView) findViewById(R.id.errorMsg);
        EditText emailField = (EditText) findViewById(R.id.email_field);
        String email = emailField.getText().toString();

        //save password as string
        EditText passwordField = (EditText) findViewById(R.id.password_field);
        String password = passwordField.getText().toString();

        try{
            String RealPassword = userManager.getPassword(email);

            // If Password is correct
            if (password.equals(RealPassword)) {
                user = userManager.getUser(email);

                //if user is a client
                if (user instanceof Client) {
                    Intent intent = new Intent(this, ClientMain.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(USER_EMAIL_KEY, email);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                // If email is an admin
                else if (user instanceof Admin) {
                    Intent intent = new Intent(this, AdminActivity.class);
                    intent.putExtra(USER_EMAIL_KEY, email);
                    startActivity(intent);
                }
            }
            //if Password is not correct
            else {
                Context context = getApplicationContext();
                CharSequence text = "Password is not correct!";
                int duration = Toast.LENGTH_SHORT;
                // display booking success message.
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }


        }
        //If email is not in password Map in User Manager
        catch(NoSuchUserException e) {
            Context context = getApplicationContext();
            CharSequence text = "User does not exist!";
            int duration = Toast.LENGTH_SHORT;
            // display booking success message.
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

}
