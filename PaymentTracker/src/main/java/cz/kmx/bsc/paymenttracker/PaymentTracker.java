package cz.kmx.bsc.paymenttracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

import cz.kmx.bsc.paymenttracker.exception.PaymentTrackerException;
import cz.kmx.bsc.paymenttracker.thread.PaymentTrackerPrinter;

/**
 * Payment Tracker keeps a record of payments. Each payment includes a currency and an amount. Data are kept in memory.
 * The application outputs a list of all the currency and amounts to the console once per minute. 
 * The input can be typed into the command line with possibility to be automated in the future and optionally also be loaded from a file when starting up.
 * It stores the currencies rates to default currency (USD) and updates the rates every hour.
 * The output list can be also printed by the "print" command
 * @author vatascin
 *
 */
public class PaymentTracker {
	
	/**
	 * Quit command
	 */
	public final String strQuit = "quit";
	/**
	 * Print command
	 */
	public final String strPrint = "print";
	/**
	 * Output printer object with payments and currencies
	 */
	private PaymentTrackerPrinter printer = new PaymentTrackerPrinter();
	/**
	 * Thread for printing
	 */
	private Thread printTread;

	/**
	 * Starts the Payment tracker application, prints hints at the beginning
	 */
	public void startApp(){
		System.out.println("Welcome to  Payment Tracker");
		System.out.println("Use following commands:");
		System.out.println("	quit - close application");
		System.out.println("	print - show payments amounts");
		System.out.println("    XXX <amount> - new payment, e.g. USD 1000");
		printTread = new Thread(printer);
		printTread.start();
		readInput();
	}
	
	/**
	 * Reads input from console, validates it and processes entered command
	 */
	public void readInput(){
		Scanner scanner = new Scanner(System.in);
		String line = "";
		Pattern pattern = Pattern.compile("([a-zA-Z]{3} (\\-)?(0|([1-9][0-9]*))|" + strQuit + "|" + strPrint + ")");
		do {
			line = scanner.nextLine();
			if ((pattern.matcher(line).matches())){
				if(line.equals(strPrint)){
					printer.printStatus();
				}else if(!line.equals(strQuit)) {
					insertPayment(line);
				}
			} else {
				System.out.println("Invalid input.");
			}
		} while(!line.equals(strQuit));
		printTread.interrupt();
		scanner.close();
		System.out.println("End.");
	}
	
	/**
	 * Reads payments from file and inserts them to payment list
	 * @param fileName
	 */
	public void readFromFile(String fileName){
		File file = new File(fileName);
		try(Scanner scanner = new Scanner(file)){
			Pattern pattern = Pattern.compile("([a-zA-Z]{3} (\\-)?(0|([1-9][0-9]*)))");
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				if((pattern.matcher(line).matches())) {
					insertPayment(line);
				}else{
					System.out.println("Invalid payment data: " + line);
				}
			}
			scanner.close();
		}catch(FileNotFoundException ex){
			System.out.println(new PaymentTrackerException(ex.getMessage()).toString());
		}
	}
	
	/**
	 * Inserts payment from input line to payment list
	 * @param line
	 */
	private void insertPayment(String line){
		String[] values = line.split(" ");
		if(values.length == 2){
			try{
				printer.addPayment(Double.valueOf(values[1]), values[0].toUpperCase());
				System.out.println("Payment added");
			}catch(NumberFormatException ex){
				System.out.println("Invalid payment data");
			}
		}else{
			System.out.println("Invalid payment data");
		}
	}

	public static void main(String[] args) {
		PaymentTracker tracker = new PaymentTracker();
		if(args.length > 0){
			tracker.readFromFile(args[0]);
		}
		tracker.startApp();
	}

}
