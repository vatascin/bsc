package cz.kmx.bsc.paymenttracker.thread.test;

import static org.junit.Assert.*;

import org.junit.Test;
import cz.kmx.bsc.paymenttracker.thread.PaymentTrackerPrinter;

public class PaymentTrackerPrinterTest {

	/**
	 * Test method for {@link cz.kmx.bsc.paymenttracker.thread.PaymentTrackerPrinter#addPayment()}.
	 */
	@Test
	public void testAddPayment() {
		PaymentTrackerPrinter printer = new PaymentTrackerPrinter();
		printer.addPayment(222, "CZK");
		assertTrue(printer.getCurrencies().getCurrencyList().containsKey("CZK"));
		assertEquals(222, printer.getPayments().get(0).getAmount(), 0);
		assertEquals("CZK", printer.getPayments().get(0).getCurrency());
	}

}
