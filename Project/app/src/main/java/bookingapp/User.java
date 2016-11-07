package bookingapp;

import java.io.Serializable;


public abstract class User implements Serializable {

	// fields of the abstract class
	
	/* the email of each user must be unique */
	private String email;

	/* the Itinerary that is currently selected by this User, 
	 * null is none were selected */
	private Itinerary selectedItinerary;


	
	/**
	 * Creates a new User with email email and null selectedItinerary.
	 * 
	 * @param email
	 *            the email of this User.
	 */
	public User(String email) {
		this.email = email;
		this.selectedItinerary = null;
	}

	/**
	 * Return the email of the user.
	 * 
	 * @return email of the user
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Set this User email to email.
	 * 
	 * @param email
	 * 			the email user will have.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Return the last Itinerary that have been selected by the user, null if 
	 * none were selected.
	 * 
	 * @return the selectedItinerary
	 */
	public Itinerary getSelectedItinerary() {
		return selectedItinerary;
	}

	/**
	 * Set itinerary as the client's selected itinerary.
	 * 
	 * @param itinerary
	 *            the itinerary has been selected by the user.
	 */
	public void selectItinerary(Itinerary itinerary) {
		this.selectedItinerary = itinerary;
	}

	/**
	 * Return a string representing the email of this user.
	 * 
	 * @return a string represent the information of this user
	 */
	@Override
	public String toString() {
		return this.email;
	}
	
	/**
	 * Compare whether this and user have the same email.
	 * 
	 * @param user the user we are comparing with.
	 * 
	 * @return true if the user have the same fields with this User, false else.
	 */
	public boolean compare(User user) {
		return this.toString().equals(user.toString());
	}
}
