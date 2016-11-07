package bookingapp;

import java.util.ArrayList;
import java.util.List;

public class Client extends User {

    /**
     * The unique serialversionUID of Client class.
     */
    private static final long serialVersionUID = 6998984623812693580L;

    // fields of this Client.
    private String lastName;
    private String firstNames;
    private String address;
    private String creditCardNumber;
    private String expiryDate;
    private List<Itinerary> bookedItineraries;


    /**
     * Creates a new Client with email email, last name
     * lastName, first names firstNames, address address, credit card number
     * creditCardNumber, expire date expiryDate and booked Itineraries
     * bookedItineraries, and null selectedItinerary and selectedFlight.
     *
     * @param lastName         the last name of this CLient.
     * @param firstNames       the first names of this Client.
     * @param email            the email of this Client.
     * @param address          the address of this Client.
     * @param creditCardNumber the credit card number of this Client.
     * @param expiryDate       the expire date of this Client credit card.
     */
    public Client(String lastName, String firstNames, String email, String
            address, String creditCardNumber, String expiryDate) {
        super(email);
        this.lastName = lastName;
        this.firstNames = firstNames;
        this.address = address;
        this.creditCardNumber = creditCardNumber;
        this.expiryDate = expiryDate;
        this.bookedItineraries = new ArrayList<>();
    }

    /**
     * Return the last name of the client
     *
     * @return the last name of the client
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the client
     *
     * @param lastName the last name of the client
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Return the first name of the client
     *
     * @return the first name of the client
     */
    public String getFirstNames() {
        return firstNames;
    }

    /**
     * Set the first name of the client
     *
     * @param firstNames the first name of the client
     */
    public void setFirstNames(String firstNames) {

        this.firstNames = firstNames;
    }

    /**
     * Return the address of the client
     *
     * @return address of the client
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address of the client
     *
     * @param address address of the client
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Return the credit card number of the client
     *
     * @return credit card number of the client
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * Set the credit card number of the client
     *
     * @param creditCardNumber credit card number of the client
     */
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    /**
     * Return the expire date of the credit card.
     *
     * @return expire date of the credit card
     */
    public String getExpiryDate() {

        return expiryDate;
    }

    /**
     * Set the expire date of the credit card.
     *
     * @param expiryDate expire date of the credit card
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Return the booked itineraries of this client.
     *
     * @return all the booked itineraries by the client
     */
    public List<Itinerary> getBookedItineraries() {
        return this.bookedItineraries;
    }

    /**
     * Book the selectedItinerary itinerary.
     */
    public void add() {
        this.bookedItineraries.add(this.getSelectedItinerary());
        this.selectItinerary(null);
    }

    /**
     * Remove the cancel this selected Itinerary itinerary from booked
     * Itineraries for this Client.
     */
    public void remove() {
        this.bookedItineraries.remove(this.getSelectedItinerary());
        this.selectItinerary(null);
    }

    /**
     * Return a string represent the information of this client
     *
     * @return a string represent the information of this client
     */
    @Override
    public String toString() {
        return this.lastName + "," + this.firstNames + "," + super.toString()
                + "," + this.address + "," + this.creditCardNumber + ","
                + this.expiryDate;
    }

}
