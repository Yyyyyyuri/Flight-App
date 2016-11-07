package infomanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import bookingapp.Flight;

/**
 * An UserManager, child class of InfoManager<Flight>, 
 * managing Flight objects with CSV files.
 */
public class FlightManager extends InfoManager<Flight> {

	/**
	 * The unique serial version UID for FlightManager.
	 */
	private static final long serialVersionUID = -2306309626293892752L;


	/* A mapping of flight numbers to flights. */
	private Map<String, Flight> flights;

	/**
	 * Creates a new empty FlightManager.
	 */
	public FlightManager(File file) throws ClassNotFoundException, IOException {
		super(file);
		this.flights = new HashMap<>();
	}

	/**
	 * empty constructor
	 */
	public FlightManager() {
		super();
		this.flights = new HashMap<>();
	}

	/**
	 * Returns the Flight from this FlightManager with the given flightNum.
	 * 
	 * @param flightNum the flightNum of the Flight to return
	 * @return the Flight from this FlightManager with the given flight number
	 * @throws NoSuchFlightException if there is no Flight with flightNum 
	 * 		flightNum in this FlightManager
	 */
	public Flight getFlight(String flightNum) throws NoSuchFlightException {
		for (String str : flights.keySet()) {
			if (str.equals(flightNum)) {		
				return flights.get(str);
			}
		} // no flight with such Flight were found, got out of the for loop	
			throw new NoSuchFlightException("No flight in file with "		
	 		+ "such flight number: " + flightNum + ".");
	}

	/**
	 * Returns a map of flights that satisfies the requirements
	 * 
	 * @param date
	 *            the departure date
	 * @param origin
	 *            the departure place
	 * @param destination
	 *            the arrival place
	 * @return a map of flights that satisfies the requirements
	 */
	public Map<String, Flight> getFlights(String date, String origin,
			String destination) {
		Set<String> flightNumSet = flights.keySet();
		Map<String, Flight> result = new HashMap<>();
		// Iterates over every flight and add the flight that meet the
		// requirement into the HashMap
		for (String flightNum : flightNumSet) {
			Flight curFlight = flights.get(flightNum);
			if (curFlight.getDepartureDate().equals(date)
					&& curFlight.getOrigin().equals(origin)
					&& curFlight.getDestination().equals(destination)) {
				result.put(curFlight.getFlightNum(), curFlight);
			}
		}
		return result;
	}

	/**
	 * Returns all flights that depart from origin and arrive at destination on
	 * the given date.
	 * 
	 * @param origin
	 *            a flight origin
	 * @return the set of all the flights that depart from origin
	 */
	public Set<Flight> searchFlights(String origin) {
		Set<String> flightNumSet = flights.keySet();
		Set<Flight> result = new HashSet<>();
		// Iterates over every flight and add the flight that meet the
		// requirement into the HashSet
		for (String flightNum : flightNumSet) {
			Flight curFlight = flights.get(flightNum);
			if (curFlight.getOrigin().equals(origin)) {
				result.add(curFlight);
			}
		}
		return result;
	}

	/**
	 * Returns a set of Flights that has the required departureDate and origin
	 * 
	 * @param date
	 *            the departure date of the flight (in the format YYYY-MM-DD)
	 * @param origin
	 *            the origin of the flight
	 * @return a set of Flights that has the required departureDate and origin
	 */
	public Set<Flight> searchFlights(String date, String origin) {
		Set<String> flightNumSet = flights.keySet();
		Set<Flight> result = new HashSet<>();
		// Iterates over every flight and add the flight that meet the
		// requirement into the HashSet
		for (String flightNum : flightNumSet) {
			Flight curFlight = flights.get(flightNum);
			if (curFlight.getDepartureDate().equals(date)
					&& curFlight.getOrigin().equals(origin)) {
				result.add(curFlight);
			}
		}
		return result;
	}

	/**
	 * Adds single Flight object record to this FlightManager and maps the info
	 * required for searching flights, or change Flight information by mapping 
	 * the flightNum to a new Flight.
	 * 
	 * @param record
	 *            a record to be added.
	 */
	public void add(Flight record) {	
		try {
			Flight flight = getFlight(record.getFlightNum());
			flights.remove(flight.getFlightNum());
			flights.put(record.getFlightNum(), record);
		} catch (NoSuchFlightException e) {
			flights.put(record.getFlightNum(), record);
		}
		// Log the addition of a student.
		getLogger().log(Level.FINE, "Added a new user " + record.toString());
	
	}
	
	/**
	 * Return a ArrayList of sorted results of Flight objects by cost in 
	 * non-decreasing order.
	 * 
	 * @param results 
	 * 			  Flight objects.
	 * @return flights, results sorted in non-decreasing order of cost.
	 */
	public ArrayList<Flight> sortFlightsByCost(ArrayList<Flight> results) {
		// list of cost of all flight in results
		ArrayList<Double> cost = new ArrayList<>();
		// the sorted flights by cost
		ArrayList<Flight> sortedFlights = new ArrayList<>(results.size());
		int k = 0;
		for (Flight flight : results) {
			cost.add(k, flight.getCost());
			k++;
		}
		// sort the cost of flights
		Collections.sort(cost);
		List<Flight> result = new ArrayList<>();
		for (int l = 0; l < cost.size(); l++) {
			for (Flight i : results) {
				if (i.getCost() == cost.get(l)) {
					// add flight to sortedFlights in order
					if (!sortedFlights.contains(i)){
						sortedFlights.add(i);
					}
				}
			}
		}
		return sortedFlights;
	}
	
	/**
	 * Return a ArrayList of sorted results of Flight objects by travel time in 
	 * non-decreasing order.
	 * 
	 * @param results 
	 * 			  Flight objects.
	 * @return flights, results sorted in non-decreasing order of travel time.
	 */
	public ArrayList<Flight> sortFlightsByTime(ArrayList<Flight> results) {
		// list of traveltime of all flight in results
		ArrayList<Integer> travelTime = new ArrayList<>();
		// the sorted flights by time
		ArrayList<Flight> sortedFlights = new ArrayList<>(results.size());
		int k = 0;
		for (Flight flight : results) {
			// the flight's travel time in minutes
			int minutes = SearchAlgorithm.minutesInbetween(flight
					.getDepartureDate(), flight.getDepartureTime(),
					flight.getArrivalDate(), flight.getArrivalTime());
			travelTime.add(k, minutes);
			k++;
		}
		// sort the travel time of flights by mins
		Collections.sort(travelTime);
		List<Flight> result = new ArrayList<>();
		for (int l = 0; l < travelTime.size(); l++) {
			for (Flight i : results) {
				int time = SearchAlgorithm.minutesInbetween(i
						.getDepartureDate(), i.getDepartureTime(),
						i.getArrivalDate(), i.getArrivalTime());
				if (time == travelTime.get(l)) {
					// add flight to sortedFlights in order
					if (!sortedFlights.contains(i)){
						sortedFlights.add(i);
					}
				}
			}
		}
		return sortedFlights;
	}
	
	
	/**
	 * Return a map of all flights.
	 * 
	 * @return a map of all flights.
	 */
	@Override
	public Map<String, Flight> getMap() {
		return this.flights;
	}


	/**
	 * Set the flights map for FlightManager.
	 */
	public void setMap(Map<String, Flight> flights) {
		this.flights = flights;
	}

	/**
	 * Remove the corresponding Flight with that flightNum.
	 * 
	 * @param flightNum
	 * 			  the Flight with that flightNum to be removed.
	 * @throws NoSuchFlightException 
	 */
	public void removeFlight(String flightNum) throws NoSuchFlightException {
		flights.remove(getFlight(flightNum).getFlightNum());
	}
	
	/**
	 * Return created new Flight object according to record.
	 * 
	 * @return create a new Flight object according to given record.
	 */
	@Override
	public Flight createObject(String[] record) {
		return new Flight(record[0], record[1].split(" "),
				record[2].split(" "), record[3], record[4], record[5],
				Double.valueOf(record[6]), Integer.valueOf(record[7]));
	}

	
}
