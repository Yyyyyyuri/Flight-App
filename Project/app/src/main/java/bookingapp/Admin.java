package bookingapp;

public class Admin extends User {

    /**
     * The unique serialversionUID of Admin class.
     */
    private static final long serialVersionUID = -218126004148321678L;


    /* the Flight that is currently selected by this Admin,
     * null is none were selected */
    private Flight selectedFlight;

    /**
     * Creates a new Admin with email email.
     *
     * @param email the email of this Admin.
     */
    public Admin(String email) {
        super(email);
        this.selectedFlight = null;
    }

    /**
     * Selects the parameter flight
     *
     * @param flight the flight has been selected by the admin.
     */
    public void selectFlight(Flight flight) {
        this.selectedFlight = flight;
    }


    /**
     * Return the last Flight that have been selected by the admin, null if none
     * were selected.
     *
     * @return the selectedFlight
     */
    public Flight getSelectedFlight() {
        return selectedFlight;
    }
}
