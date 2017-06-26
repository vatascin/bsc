package cz.kmx.bsc.paymenttracker.data;

/**
 * Payment data type
 * @author vatascin
 *
 */
public class Payment {
	
	/**
	 * payment amount
	 */
	private double amount;
	/** 
	 * currency of the payment
	 */
	private String currency;
	
	public Payment(double amount,  String currency){
		this.amount = amount;
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
