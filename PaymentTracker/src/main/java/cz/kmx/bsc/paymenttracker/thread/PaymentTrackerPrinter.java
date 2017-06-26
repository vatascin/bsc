/**
 * 
 */
package cz.kmx.bsc.paymenttracker.thread;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.kmx.bsc.paymenttracker.data.Currencies;
import cz.kmx.bsc.paymenttracker.data.Payment;

/**
 * Class for printing payments status in own thread
 * @author vatascin
 *
 */
public class PaymentTrackerPrinter implements Runnable {

	/**
	 * Currency and default currency amounts for printing
	 * @author vatascin
	 *
	 */
	private class CurrenciesAmount {
		/**
		 * payments amount in currency
		 */
		private double currencyAmount;
		/**
		 * payments amount in default currency
		 */
		private double defaultCurrencyAmount;
		
		public CurrenciesAmount(){
			this.currencyAmount = 0;
			this.defaultCurrencyAmount = 0;
		}

		/**
		 * Adds amount from payment to the currency sum
		 * @param amount
		 * @param rate to default currency
		 */
		public void updateAmount(double amount, Double rate){
			currencyAmount = currencyAmount + amount;
			defaultCurrencyAmount = rate == null ? 0 : defaultCurrencyAmount + amount * rate.doubleValue();
		}
		
		/**
		 * Prints the currency status
		 * @param code
		 */
		public void print(String code){
			long amount = Math.round(currencyAmount);
			NumberFormat formatter = new DecimalFormat("#0.00");     
			BigDecimal defAmount = BigDecimal.valueOf(defaultCurrencyAmount);
			if( amount != 0){
				System.out.println(code + " " + amount + (defAmount.compareTo(new BigDecimal(0)) == 0 ? " (" + Currencies.DEFAULT_CURRENCY_CODE + " not defined )" : " (" + Currencies.DEFAULT_CURRENCY_CODE + " " + formatter.format(defAmount) + " )"));
			}
		}
		
	}

	/**
	 * List of all payments
	 */
	private List<Payment> payments = new ArrayList<Payment>();
	/**
	 * Map of payments currencies
	 */
	private Currencies currencies = new Currencies();

	/**
	 * Counts payments sum for the currencies
	 * @return currencies status map
	 */
	public Map<String, CurrenciesAmount> prepareStatus(){
		Map<String, CurrenciesAmount> paymentsStatus = new HashMap<String, CurrenciesAmount>();
		for(Payment payment : payments){
			double amount = payment.getAmount();
			String code = payment.getCurrency();
			if(!paymentsStatus.containsKey(code)){
				paymentsStatus.put(code, new CurrenciesAmount());
			}
			paymentsStatus.get(code).updateAmount(amount, currencies.getCurrency(code).getRate());
		}
		return paymentsStatus;
	}
	
	/**
	 * Prints currencies status
	 */
	public void printStatus(){
		Map<String, CurrenciesAmount> paymentsStatus = prepareStatus();
		System.out.println("==============================");
		for(String code : paymentsStatus.keySet()){
			CurrenciesAmount amounts = paymentsStatus.get(code);
			amounts.print(code);
		}
		System.out.println("==============================");
		System.out.println(new Date() + "\n");
	}
	
	/**
	 * Adds payment to payment list
	 * @param amount
	 * @param currency
	 */
	public void addPayment(double amount, String currency){
		payments.add(new Payment(amount, currency));
		currencies.insertCurrency(currency);
	}
	
	/**
	 * Prints status every minute and updates currency rates every hour
	 */
	public void run() {
		boolean repeat = true;
		int count = 0;
		while(repeat){
			count ++;
			//updates currency rates every hour (count>60)
			if(count > 60){
				currencies.updateRates();
				count = 1;
			}
			printStatus();
			try{
				Thread.sleep(60000);
			}catch(InterruptedException ex){
				repeat = false;
			}
		}
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public Currencies getCurrencies() {
		return currencies;
	}

}
