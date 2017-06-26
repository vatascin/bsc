package cz.kmx.bsc.paymenttracker.exception;

/**
 * 
 */
public class PaymentTrackerException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String ERROR_HEAD = ">>>Error: ";

	public PaymentTrackerException(String message){
		super(message);
	}

	@Override
	public String toString() {
		return ERROR_HEAD + getMessage() + "\n";
	}
}
