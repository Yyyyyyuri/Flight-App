package infomanager;

public class NoSuchUserException extends Exception {
	
	public NoSuchUserException(){
	// empty Exception		
	}
	public NoSuchUserException(String message){
	// constructor with a message
			super(message);
	}
	
}
