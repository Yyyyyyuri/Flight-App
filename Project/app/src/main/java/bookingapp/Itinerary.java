package bookingapp;

import java.io.Serializable;
import java.util.ArrayList;

import infomanager.SearchAlgorithm;

public class Itinerary implements Serializable {

	protected String departureDate;
	protected String origin;
	protected String destination;
	private ArrayList<Flight> flights;
	private double totalCost;

	/**
	 * Constructs a new instance of Itinerary.
	 * 
	 * @param departureDate
	 *            the first date of the itinerary
	 * @param origin
	 *            the city the itinerary starts in
	 * @param destination
	 *            the city the itinerary ends in
	 */
	public Itinerary(String departureDate, String origin, String destination) {
		this.departureDate = departureDate;
		this.origin = origin;
		this.destination = destination;
		this.flights = new ArrayList<Flight>();
		totalCost = 0;
	}

	/**
	 * Returns the total time of the flight as a string in the form HH:MM.
	 * 
	 * @return totalTime
	 */
	public String getTotalTime() {
		int minutes = totalMinutes();
		int h=(minutes / 60);
		int m=(minutes % 60);
		return String.format("%02d:%02d", h,m);
	}

    /**
     * Returns the total time of the itinerary as minutes.
     *
     * @return the number of minutes between the beginning and end of the itinerary
     */
	public int totalMinutes(){
		Flight firstFlight = flights.get(0);
		Flight lastFlight = flights.get(flights.size() - 1);
		int minutes = SearchAlgorithm.minutesInbetween(
				firstFlight.getDepartureDate(), firstFlight.getDepartureTime(),
				lastFlight.getArrivalDate(), lastFlight.getArrivalTime());
		return minutes;

	}


	/**
	 * Returns the total cost of the itinerary.
	 * 
	 * @return total cost of itinerary
	 */
	public double getTotalCost() {
		return totalCost;
	}

	/**
	 * Returns the departure date of the itinerary as a string in the form YYYY-MM-DD.
	 * 
	 * @return the departure date
	 */
	public String getDepartureDate() {
		return departureDate;
	}

	/**
	 * Returns the city that the itinerary starts in.
	 * 
	 * @return the origin city
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Returns the city that the itinerary ends in
	 * 
	 * @return the destination city
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Returns all the flights travelled in itinerary as an ArrayList.
	 * 
	 * @return the flights in Itinerary
	 */
	public ArrayList<Flight> getFlights() {
		return flights;
	}

	/**
	 * Adds all the flights in flights to the flights of this itinerary.
	 * 
	 * @param flights
	 *            to be added
	 */
	public void addFlights(ArrayList<Flight> flights) {
		for (Flight flight : flights) {
			this.addFlight(flight);
		}
	}

	/**
	 * Adds flight to itinerary's flights and updates the total cost.
	 * 
	 * @param flight
	 *            the flight to be added
	 */
	public void addFlight(Flight flight) {
		this.flights.add(flight);
		totalCost += flight.getCost();
	}
	
	/**
	 * Return a string representing the information of the itinerary
	 * 
	 * @return a string representing the information of the itinerary.
	 */
	@Override
	public String toString() {
		String r = new String();
		for (Flight i : flights) {
			r += i.getFlightNum() + "," + i.getDepartureDate() + " "
					+ i.getDepartureTime() + "," + i.getArrivalDate() + " "
					+ i.getArrivalTime() + "," + i.getAirline() + ","
					+ i.getOrigin() + "," + i.getDestination()+"\n";
		}
		
		return r + String.format("%.2f", this.totalCost) + "\n" 
					+ getTotalTime();

	}

}
