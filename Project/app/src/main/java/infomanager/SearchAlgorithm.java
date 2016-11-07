package infomanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Set;

import bookingapp.Flight;
import bookingapp.Itinerary;

public class SearchAlgorithm {

    public SearchAlgorithm() {

    }

    /**
     * Returns an ArrayList of all the possible itineraries from origin to
     * destination
     *
     * @param departureDate the departure date of the itinerary
     * @param origin        the origin of the itinerary
     * @param destination   the destination of the itinerary
     * @return an ArrayList of all the possible itineraries from origin to
     * destination
     */
    public static ArrayList<Itinerary> searchItineraries(FlightManager fm,
                                                         String departureDate,
                                                         String origin,
                                                         String destination) {
        ArrayList<Itinerary> itinList = new ArrayList<Itinerary>();
        // Find a set of flights that has the required departureDate and origin.
        Set<Flight> startingFlights = fm.searchFlights(departureDate, origin);
        for (Flight flight : startingFlights) {
            if (flight.bookable()) {
                ArrayList<Flight> startFlightList = new ArrayList<Flight>();
                startFlightList.add(flight);
                itinList.addAll(findDestination(fm, startFlightList,
                        destination));
            }
        }
        return itinList;

    }

    /**
     * If not a valid itinerary (contains cycle, no more connecting flights)
     * returns null, or creates Itinerary if last flight arrived at destination
     * Otherwise recurses on itself after after adding each possible new flight
     * connecting flight to currentFlights.
     *
     * @param currentFlights
     * @return an ArrayList of itineraries whose last flight reaches the
     * destination
     */
    public static ArrayList<Itinerary> findDestination(FlightManager fm,
                       ArrayList<Flight> currentFlights, String destination) {
        Flight newestFlight = currentFlights.get(currentFlights.size() - 1);
        ArrayList<Itinerary> itinList = new ArrayList<Itinerary>();

        // Return empty if cycle occurs
        if (isCycle(currentFlights)) {
            return itinList;
        }

        // Create new itinerary if destination is reached
        if (newestFlight.getDestination().equals(destination)) {

            // Get origin and departure date
            Flight firstFlight = currentFlights.get(0);
            String departureDate = firstFlight.getDepartureDate();
            String origin = firstFlight.getOrigin();

            // Create new itinerary and add the flights
            Itinerary itin = new Itinerary(departureDate, origin, destination);
            itin.addFlights(currentFlights);

            // Create new arraylist of itineraries containing only the new
            // itinerary and return it
            itinList.add(itin);
            return itinList;
        }

        // Recurse on all connecting flights
        // Get all connecting flights in map in arrayList
        // connectingFlights(IMPLEMENT)
        Set<Flight> originFlights =
                fm.searchFlights(newestFlight.getDestination());
        ArrayList<Flight> connectingFlights = new ArrayList<Flight>();
        for (Flight flight : originFlights) {
            if (areConnected(newestFlight, flight) && flight.bookable()) {
                connectingFlights.add(flight);
            }
        }

        // Return no itineraries if there are no more connecting flights
        if (connectingFlights.isEmpty()) {
            return itinList;
        }

        // Recurse and return new itinerary list for all connecting flights
        for (Flight flight : connectingFlights) {
            // Add new flight to current flights
            ArrayList<Flight> newConnectingFlights = new ArrayList<Flight>();
            newConnectingFlights.addAll(currentFlights);
            newConnectingFlights.add(flight);

            // Merge itinerary list for new path and current itinerary list
            itinList.addAll(findDestination
                    (fm, newConnectingFlights, destination));
        }
        return itinList;

    }

    /**
     * Return true if the ArrayList of flights go back to the place that has
     * visited before
     *
     * @param flights an ArrayList of flights
     * @return true if the ArrayList of flights go back to the place that has
     * visited before, false otherwise
     */
    public static boolean isCycle(ArrayList<Flight> flights) {

        Flight newestFlight = flights.get(flights.size() - 1);
        for (int i = 0; i < flights.size() - 1; i++) {
            if (flights.get(i).getOrigin().equals
                    (newestFlight.getDestination())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of minutes between date1, time1 and date2, time 2 if
     * date2, time2 is the same as or after date 1, time1.
     *
     * @param date1 the date of first date
     * @param time1 the time of first date
     * @param date2 the date of second date
     * @param time2 the time of second date
     * @return an integer which is the number of minutes between date1 and date2
     */
    public static int minutesInbetween(String date1, String time1,
                                       String date2, String time2) {

        int year1 = Integer.parseInt(date1.substring(0, 4));
        int year2 = Integer.parseInt(date2.substring(0, 4));

        int month1 = Integer.parseInt(date1.substring(5, 7)) - 1;
        int month2 = Integer.parseInt(date2.substring(5, 7)) - 1;

        int day1 = Integer.parseInt(date1.substring(8));
        int day2 = Integer.parseInt(date2.substring(8));

        int hour1 = Integer.parseInt(time1.substring(0, 2));
        int hour2 = Integer.parseInt(time2.substring(0, 2));

        int min1 = Integer.parseInt(time1.substring(3));
        int min2 = Integer.parseInt(time2.substring(3));

        GregorianCalendar calen1 =
                new GregorianCalendar(year1, month1, day1, hour1, min1);
        GregorianCalendar calen2 =
                new GregorianCalendar(year2, month2, day2, hour2, min2);

        return (int) ((calen2.getTimeInMillis() - calen1.getTimeInMillis())
                / (1000 * 60));

    }

    /**
     * Returns true if flight1's destination is flight2's origin and there is
     * less than 6 hours between flight1's arrival and flight2's departure.
     *
     * @param flight1 the first flight
     * @param flight2 the second flight
     * @return true if the 2 flights are connecting, and have stopover less than
     * 6 hours
     */
    protected static boolean areConnected(Flight flight1, Flight flight2) {
        int timeDifference = minutesInbetween(flight1.getArrivalDate(),
                flight1.getArrivalTime(), flight2.getDepartureDate(),
                flight2.getDepartureTime());

        return timeDifference >= 0 && timeDifference < (6 * 60);
    }

    /**
     * Returns the same itineraries as getItineraries produces, but sorted
     * according to total itinerary travel time, in non-decreasing order.
     *
     * @param date        a departure date (in the format YYYY-MM-DD)
     * @param origin      a flight original
     * @param destination a flight destination
     * @return itineraries (sorted in non-decreasing order of travel itinerary
     * travel time) that depart from origin and arrive at destination on
     * the given date with stopovers at or under 6 hours.
     */
    public static ArrayList<Itinerary> searchItinerariesSortedByTime(
            FlightManager fm, String date, String origin, String destination) {
        // saves searched Itineraries in an ArrayList in order to sort them
        ArrayList<Itinerary> itinerary = searchItineraries
                (fm, date, origin, destination);
        ArrayList<Integer> travelTime = new ArrayList<Integer>();
        ArrayList<Itinerary> sortedItineraries = new ArrayList<Itinerary>
                (itinerary.size());
        // adds total time (in minute) of all itineraries to an array called
        // travelTimeM

        int k = 0;
        for (Itinerary i : itinerary) {
            ArrayList<Flight> flights = i.getFlights();
            Flight firstFlight = flights.get(0);
            Flight lastFlight = flights.get(flights.size() - 1);
            int minutes = minutesInbetween(firstFlight.getDepartureDate(),
                    firstFlight.getDepartureTime(),
                    lastFlight.getArrivalDate(), lastFlight.getArrivalTime());
            travelTime.add(k, minutes);
            k += 1;
        }

        // sorts the travel time of itineraries by minute
        Collections.sort(travelTime);
        // turns minute to String and add them to Array totalTimeS


        // sorts the itineraries as same as the order in totalTimeS
        // sort them in an arraylist called sortedItineraries
        for (int l = 0; l < travelTime.size(); l++) {
            for (Itinerary i : itinerary) {
                if (i.totalMinutes() == travelTime.get(l)) {
                    if (!sortedItineraries.contains(i))
                        sortedItineraries.add(i);
                }
            }
        }
        return sortedItineraries;
    }

    /**
     * Returns the same itineraries as getItineraries produces, but sorted
     * according to total itinerary cost, in non-decreasing order.
     *
     * @param date        a departure date (in the format YYYY-MM-DD)
     * @param origin      a flight original
     * @param destination a flight destination
     * @return itineraries (sorted in non-decreasing order of total itinerary
     * cost) that depart from origin and arrive at destination on the
     * given date with stopovers at or under 6 hours.
     */
    public static ArrayList<Itinerary> searchItinerariesSortedByCost(
            FlightManager fm, String date, String origin, String destination) {
        // saves searched Itineraries in an ArrayList in order to sort them
        ArrayList<Itinerary> itinerary = searchItineraries
                (fm, date, origin, destination);
        ArrayList<Double> totalCosts = new ArrayList<Double>();
        ArrayList<Itinerary> sortedItineraries = new ArrayList<Itinerary>
                (itinerary.size());
        // adds total cost of all itineraries to an array called totalCosts
        int k = 0;
        for (Itinerary i : itinerary) {
            totalCosts.add(k, i.getTotalCost());
            k += 1;
        }
        // sorts the travel cost of itineraries
        Collections.sort(totalCosts);

        // sorts the itineraries as same as the order in totalCosts
        // sort them in an arraylist called sortedItineraries

        for (int l = 0; l < totalCosts.size(); l++) {
            for (Itinerary i : itinerary) {
                if (i.getTotalCost() == totalCosts.get(l)) {
                    if (!sortedItineraries.contains(i))
                        sortedItineraries.add(i);
                }
            }
        }
        return sortedItineraries;
    }

		/*for (int n = 0; n < itinerary.size(); n++) {
            for (Itinerary i : itinerary) {

				if (i.getTotalCost() == totalCosts.get(n)) {
					int d=n;
					while (!sortedItineraries.get(d).equals(null)){
						d++;
					}
					sortedItineraries.add(d, i);}

						break;
				}

			}
		return sortedItineraries;

	}*/

    /**
     * Return a String represents the results that sorted by travel time.
     *
     * @param fm          the flight manager
     * @param date        the departure date
     * @param origin      the origin
     * @param destination the destination
     * @return a String represents the results that sorted by travel time.
     */
    public static String DisplayResultsSortedByTravelTime(FlightManager fm,
                          String date, String origin, String destination) {
        String result = "";
        ArrayList<Itinerary> itineraryByTime = searchItinerariesSortedByTime
                (fm, date, origin, destination);
        for (Itinerary itinerary : itineraryByTime) {
            result += itinerary.toString() + "\n";
        }
        return result;

    }

    /**
     * Return a String represents the results that sorted by total cost.
     *
     * @param fm          the flight manager
     * @param date        the departure date
     * @param origin      the origin
     * @param destination the destination
     * @return Return a String represents the results that sorted by total cost.
     */
    public static String DisplayResultsSortedByCost(FlightManager fm,
                         String date, String origin, String destination) {
        String result = "";
        ArrayList<Itinerary> itineraryByCost = searchItinerariesSortedByCost
                (fm, date, origin, destination);
        for (Itinerary itinerary : itineraryByCost) {
            result += itinerary.toString() + "\n";
        }
        return result;
    }

}
