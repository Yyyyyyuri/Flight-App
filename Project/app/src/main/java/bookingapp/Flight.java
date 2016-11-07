package bookingapp;

import java.io.Serializable;

import infomanager.SearchAlgorithm;

public class Flight implements Serializable {

    /**
     * The unique serialversionUID of Flight class
     */
    private static final long serialVersionUID = 1516050951154183247L;

    private String flightNum;
    private String[] departureDateTime; // in format {"YYYY-MM-DD","HH:MM"}
    private String[] arrivalDateTime; // in format {"YYYY-MM-DD","HH:MM"}
    private String airline;
    private String origin;
    private String destination;
    private double cost;
    private String travelTime = "00:00"; // in format "HH:MM"
    private int numSeats;

    private int availableSeats;


    /**
     * Creates a new Flight with flight number flightNum, departure date and
     * time departureDateTime, arrival date and time arrivalDateTime, airline
     * airline, departure origin origin, arrival destination, price cost, and
     * number of seats numSeats and initiate available seats to numberseats at
     * first.
     *
     * @param flightNum         the flight number of this Flight.
     * @param departureDateTime the departure date and time of this Flight.
     * @param arrivalDateTime   the arrival date and time of this Flight.
     * @param airline           the airline of this Flight.
     * @param origin            the departure origin of this Flight.
     * @param destination       the arrival destination of this Flight.
     * @param cost              the cost of this Flight.
     * @param numSeats          the total number of seats bookable of thisFlight
     */
    public Flight(String flightNum, String[] departureDateTime,
                  String[] arrivalDateTime, String airline, String origin,
                  String destination, double cost, int numSeats) {

        this.flightNum = flightNum;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
        this.numSeats = numSeats;
        // calculate the travelTime from the informations we have
        this.travelTime = this.getTravelTime();
        // number of seats bookable is the total number of seats at first.
        this.availableSeats = numSeats;

    }

    /**
     * set departure Date and time to DDateTime
     *
     * @param DDateTime
     */
    public void setDepartureDateTime(String DDateTime) {
        this.departureDateTime = DDateTime.split(" ");
    }

    /**
     * set arrival date and time to ASateTime
     *
     * @param ADateTime
     */
    public void setArrivalDateTime(String ADateTime) {
        this.arrivalDateTime = ADateTime.split(" ");
    }

    /**
     * sets the Airline to airline
     *
     * @param airline
     */
    public void setAirline(String airline) {
        this.airline = airline;
    }

    /**
     * set the origin of the flight to origin
     *
     * @param origin
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * set the Destination of the flight to Destination
     *
     * @param destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * sets the cost of the flight to Cost
     *
     * @param cost
     */
    public void setCost(String cost) {
        this.cost = Double.parseDouble(cost);
    }

    /**
     * sets the available seats of the flight to availableSeats
     *
     * @param availableSeats
     */
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    /**
     * Return available seats of the Flight for book.
     *
     * @return the availableSeats for book.
     */
    public int getAvailableSeats() {
        return availableSeats;
    }

    /**
     * Returns the flight number
     *
     * @return flight number
     */
    public String getFlightNum() {
        return flightNum;
    }

    /**
     * Returns the airline running the flight
     *
     * @return airline
     */
    public String getAirline() {
        return airline;
    }

    /**
     * Returns the date the flight departs its origin city in the format
     * YYYY-MM-DD
     *
     * @return the departure date
     */
    public String getDepartureDate() {
        return departureDateTime[0];
    }

    /**
     * Returns the time the flight departs its origin city in the format HH:MM
     *
     * @return the departure time
     */
    public String getDepartureTime() {
        return departureDateTime[1];
    }

    /**
     * Returns the date and time the flight departs its origin city in
     * the format YYYY-MM-DD HH:MM
     *
     * @return the departure date and time
     */
    public String getDepartureDateTime() {
        return getDepartureDate() + " " + getDepartureTime();
    }

    /**
     * Returns the date the flight arrives at its destination in the format
     * YYYY-MM-DD
     *
     * @return the departure date
     */
    public String getArrivalDate() {
        return arrivalDateTime[0];
    }

    /**
     * Returns the time the flight arrives at its destination in the format
     * HH:MM
     *
     * @return the arrival time
     */
    public String getArrivalTime() {
        return arrivalDateTime[1];
    }

    /**
     * Returns the date and time the flight arrives its destination city in
     * the format YYYY-MM-DD HH:MM
     *
     * @return the arrival date and time
     */
    public String getArrivalDateTime() {
        return getArrivalDate() + " " + getArrivalTime();
    }

    /**
     * Returns the city the flight starts in
     *
     * @return the origin city
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Returns the city the flight ends in
     *
     * @return the destination city
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Returns the cost of the flight
     *
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Return the cost in type string
     *
     * @return the cost as a string
     */
    public String getStringCost() {
        return String.valueOf(cost);
    }

    /**
     * Returns a string containing the travel time of the flight in the format
     * "HH:MM" by calculating it from the arrival and departure date and time.
     *
     * @return a string representing the travel time of the flight
     */
    public String getTravelTime() {
        if (travelTime.equals("00:00")) {

            int travelMinutes = SearchAlgorithm.minutesInbetween(
                    this.getDepartureDate(), this.getDepartureTime(),
                    this.getArrivalDate(), this.getArrivalTime());
            int h = (travelMinutes / 60);
            int m = (travelMinutes % 60);
            travelTime = String.format("%02d:%02d", h, m);
        }

        return this.travelTime;
    }

    /**
     * Returns the number of seats on the Flight.
     *
     * @return the number of seats available on the flight;
     */
    public String getNumSeats() {
        return String.valueOf(numSeats);
    }

    /**
     * Set the total number of seats of the Flight to numSeats.
     *
     * @param numSeats the new number of seats of the Flight.
     */
    public void setNumSeats(String numSeats) {
        this.numSeats = Integer.parseInt(numSeats);
        this.availableSeats = this.numSeats;
    }

    /**
     * Add one passenger to the flight if bookable.
     */
    public void addPassenger() {
        if (bookable()) {
            this.availableSeats -= 1;
        }
    }

    /**
     * Check whether this Flight is bookable or not.
     *
     * @return true if the flight is bookable, false is not.
     */
    public boolean bookable() {
        return availableSeats > 0;
    }

    /**
     * Return a string represent the information of this flight
     *
     * @return a string represent the information of this flight.
     */
    public String toString() {
        return this.flightNum + "," + this.departureDateTime[0] + " "
                + this.departureDateTime[1] + "," + this.arrivalDateTime[0]
                + " " + this.arrivalDateTime[1] + "," + this.airline + ","
                + this.origin + "," + this.destination + ","
                + String.format("%.2f", this.cost);

    }
}
