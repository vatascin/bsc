package cz.kmx.bsc.paymenttracker.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.kmx.bsc.paymenttracker.exception.PaymentTrackerException;

/**
 * Data type for keeping all currencies
 * @author vatascin
 *
 */
public class Currencies {
	
	private static Logger logger = Logger.getLogger("PaymentTracker");
	/**
	 * Map of currencies
	 */
	private Map<String, Currency> currencyList;
	/**
	 * Currently used default currency
	 */
	public static final String DEFAULT_CURRENCY_CODE = "USD";
	
	public Currencies(){
		this.currencyList = new HashMap<>();
	}

	public Currency getCurrency(String code){
		return currencyList.get(code.toUpperCase());
	}
	
	public boolean insertCurrency(String code){
		String upperCode = code.toUpperCase();
		if(!currencyList.containsKey(upperCode)){
			Currency currency = new Currency(upperCode);
			try{
				currency.updateRate(DEFAULT_CURRENCY_CODE);
			}catch(PaymentTrackerException ex){
				currencyList.put(upperCode, currency);
				logger.log(Level.WARNING, ex.toString());
				return false;
			}
			currencyList.put(upperCode, currency);
		}
		return true;
	}
	
	/**
	 * Updates rates of all currencies
	 * @return
	 */
	public boolean updateRates(){
		boolean updated = true;
		for(Entry<String,Currency> currency : currencyList.entrySet()){
			try{
				currency.getValue().updateRate(DEFAULT_CURRENCY_CODE);
			}catch(PaymentTrackerException ex){
				System.out.println(ex.toString());
				updated = false;
			}
		}
		return updated;
	}
	
	public Map<String, Currency> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(Map<String, Currency> currencyList) {
		this.currencyList = currencyList;
	}
}
