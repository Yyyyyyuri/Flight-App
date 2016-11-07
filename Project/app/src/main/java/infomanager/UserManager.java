package infomanager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookingapp.Admin;
import bookingapp.Client;
import bookingapp.User;

/**
 * An UserManager, child class of InfoManager<User>, 
 * managing User objects with CSV files.
 */
public class UserManager extends InfoManager<User> {

	/**
	 * The unique serial version UID for UserManager
	 */
	private static final long serialVersionUID = 7244180093319251957L;


	/* A mapping of user email to users. */
	private Map<String, User> users;
	private Map<User, String> pwdMap;
	private String pwdFilePath;

	/**
	 * Constructs a new empty UserManager.
	 *
	 * @param file
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public UserManager(File file) throws ClassNotFoundException, IOException {
		super(file);
		this.users = new HashMap<>();
		this.pwdMap = new HashMap<>();
	}
	
	/**
	 * Populates the password map from the file at InputStream inputStream.
	 * 
	 * @param inputStream
	 *            the inputStream of the file containing the passwords
	 * @throws NoSuchUserException 
	 */		
	public void readPassword(InputStream inputStream) throws NoSuchUserException
	{
		String email = "";
		String pwd = "";
			// FileInputStream can be used for reading raw bytes, like an image.
			Scanner scanner = new Scanner(inputStream);
			// a flag for whether current line is a email for password.
			int i = 0;
			while (scanner.hasNextLine()) {
				if (i == 0) {
					email = scanner.nextLine();
					i = 1;
				} else {
					pwd = scanner.nextLine();
					i = 0;
					try {
						// if it is a client then it already exists
						pwdMap.put(getUser(email), pwd);
					} 
					catch (NoSuchUserException e) {
						// create new admin object and maps it
						Admin adm = new Admin(email);
						pwdMap.put(adm, pwd);
						users.put(email, adm);
						
					} 
				}
			}
			scanner.close();
	}

    /**
     * empty constructor
     */
	public UserManager() {
		super();
        this.users = new HashMap<>();
        this.pwdMap = new HashMap<>();
	}
	
	/**
	 * Populates the pwdMap from the serfile at path filePath.
	 * 
	 * @param filePath
	 * 			the file path of the password map.
	 */
	public void readFromPasswordFile(String filePath) {
		Logger logger = getLogger();
		try {
			InputStream file = new FileInputStream(filePath);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);

			// Deserialize the Map.
			this.setPwdMap((Map<User, String>) input.readObject());
			input.close();
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "Cannot read from input.", ex);
		}  catch (ClassNotFoundException ex) {
			logger.log(Level.SEVERE, "Cannot find class.", ex);
		}
	}
	
	/**
	 * Write the pwdMap to file at pwdFilePath.
	 */
	public void savePasswordToFile() {
		Logger logger = getLogger();
		try {
			OutputStream file = new FileOutputStream(pwdFilePath);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);

			// Serialize the corresponding Map.
			output.writeObject(this.getPwdMap());
			logger.log(Level.FINE, "Serialized password map.");
			output.close();
		} catch(IOException ex) {
			logger.log(Level.SEVERE,
					"Cannot perform output. File I/O failed.", ex);
		}
	}

    /**
     * Change the password of user in the password map.
     * @param user
     *          the user whose password is to be changed
     * @param password
     *          the user's new password
     */
	public void changePassword(User user, String password){
        pwdMap.put(user, password);
    }

    /**
     * Returns the password for the user with the username email.
     * @param email
     */
    public String getPassword(String email) throws NoSuchUserException{
        return pwdMap.get(getUser(email));
    }

	/**
	 * Returns a user from this UserManager with the given email.
	 * 
	 * @param email the email of the User to return
	 * @return a user from this UserManager with the given email
	 * @throws NoSuchUserException if there is no User with email 
	 * 		email in this UserManager
	 */
	public User getUser(String email) throws NoSuchUserException {
		for (String str : users.keySet()) {
			if (str.equals(email)) {		
				return users.get(str);
			}
		}
		 // no user with such User were found got out of the for loop	  
		  throw new NoSuchUserException("No user in file with "
	 		+ "such email: " + email + ".");		
	}
	
	/**
	 * Display all the users in this UserManager.
	 * 
	 * @return all the users
	 */
	public String displayUsers(){
		return this.toString();
	}

	/**
	 * Adds single User object record to this UserManager, if user already 
	 * exists and have different field with record, replace the old data with  
	 * the new ones.
	 * 
	 * @param record
	 *            a User to be added.
	 */
	public void add(User record) {
		try {
			User user = getUser(record.getEmail());
			// if they have the same fields we don't overwrites.
			if (user.compare(record)) {
			} else {
				// replace new data with old data.
				record.setEmail(user.getEmail());
				users.put(record.getEmail(), record);
				// Log the change of a user.
				getLogger().log(Level.FINE, "Changed the user information" + record.toString());
			}
		} catch (NoSuchUserException e) {
			users.put(record.getEmail(), record);
			// Log the addition of a user.
			getLogger().log(Level.FINE, "Added a new user " + record.toString());
		}
	}
	
	/**
	 * Return the map of all users.
	 * 
	 * @return a map of all users.
	 */
	@Override
	public Map<String, User> getMap() {
		return this.users;
	}

	/**
	 * Set the users map to param users.
	 * 
	 * @param users
	 * 			a map from email to users.
	 */
	public void setMap(Map<String, User> users) {
		this.users = users;
	}
	
	/**
	 * Return the password map.
	 * 
	 * @return a password map.
	 */
	public Map<User, String> getPwdMap() {
		return pwdMap;
	}

	/**
	 * Set the pwdMap to param pwdMap
	 * 
	 * @param pwdMap
	 * 			a map from User to password.
	 */
	public void setPwdMap(Map<User, String> pwdMap) {
		this.pwdMap = pwdMap;
	}
	
	/**
	 * Set the pwdFilePath to pwdFilePath.
	 * 
	 * @param pwdFilePath
	 * 			the pwdFilePath of this UserManager.
	 */
	public void setPwdFilePath(String pwdFilePath) {
		this.pwdFilePath = pwdFilePath;
	}

	/**
	 * Return created new Client object according to record.
	 * 
	 * @return create a new Client object according to given record.
	 */
	@Override
	public Client createObject(String[] record) {
		return new Client(record[0], record[1], record[2], record[3],
				record[4], record[5]);
	}

}
