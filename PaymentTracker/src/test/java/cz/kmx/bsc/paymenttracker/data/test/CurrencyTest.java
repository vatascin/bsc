/**
 * 
 */
package cz.kmx.bsc.paymenttracker.data.test;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.kmx.bsc.paymenttracker.data.Currency;
import cz.kmx.bsc.paymenttracker.exception.PaymentTrackerException;

/**
 * @author vatascin
 *
 */
public class CurrencyTest {

	/**
	 * Test method for {@link cz.kmx.bsc.paymenttracker.data.Currency#updateRate(java.lang.String)}.
	 */
	@Test
	public void testUpdateRate() {
		Currency curr = new Currency("USD");
		try{
			assertTrue(curr.updateRate("USD"));
		}catch(PaymentTrackerException ex){
			fail(ex.toString());
		}
	}

}
