package cz.kmx.bsc.paymenttracker.data;

import cz.kmx.bsc.paymenttracker.exception.PaymentTrackerException;

/**
 * Currency data type
 * @author vatascin
 *
 */
public class Currency {

	/**
	 * currency code 3 upper-case alphabet chars
	 */
	private String code;
	/** 
	 * currency rate to default currency
	 */
	private Double rate;
	
	public Currency(String code, Double rate){
		this.code = code.toUpperCase();
		this.rate = rate;
	}
	
	public Currency(String code){
		this.code = code;
		this.rate = null;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	/**
	 * Updates the currency rate with current value
	 * @param defaultCurrencyCode
	 * @return
	 * @throws PaymentTrackerException
	 */
	public boolean updateRate(String defaultCurrencyCode) throws PaymentTrackerException{
		if(defaultCurrencyCode.equals(code)){
			rate = Double.valueOf(1);
		}else{
			rate = getRateFromWeb(defaultCurrencyCode);
		}
		return rate != null;
	}

	/**
	 * In the future this will download the current rate of the currency to default currency from the Web
	 * @param defaultCurrencyCode
	 * @return
	 * @throws PaymentTrackerException
	 */
	private Double getRateFromWeb(String defaultCurrencyCode) throws PaymentTrackerException{
		//TODO download currency rate from web
		if("CZK".equalsIgnoreCase(code)){
			return Double.valueOf(0.0426086);
		}
		if("EUR".equalsIgnoreCase(code)){
			return Double.valueOf(1.11915);
		}
		if("GBP".equalsIgnoreCase(code)){
			return Double.valueOf(1.27145);
		}
		throw new PaymentTrackerException("Rate for " + code + " to " + defaultCurrencyCode + " couldn't be downloaded");
	}
	
	public String getCode() {
		return code;
	}
	
}
