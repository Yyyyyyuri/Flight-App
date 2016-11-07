package infomanager;

public class NoSuchFlightException extends Exception {
		
	public NoSuchFlightException(){
	// empty Exception		
	}
	public NoSuchFlightException(String message){
	// constructor with a message
			super(message);
	}
		
}
