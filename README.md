# bsc
BSC Java test
Payment Tracker

Instructions for use

Application runs as runnable jar
Run command: java -jar paymentTracker.jar [fileName]
fileName - file name with path of file with payments, can be omitted
Format of the payment file: USD 23
			EUR 77
			CZK 100
Application reads the payments from the file (if entered).
Ones per minute it prints the payment status in the console. If amount of a currency is 0, no output for this 
currency will be generated.
Ones per hour it updates the present currencies rates to the default rate (USD).

User commands:
quit - quits the application
print - prints the payment status to console
USD 25 - adds new payment of amount 25 USD, format <XXX> <amount>, where XXX is currency code and amount is integer
other text causes "Invalid input" output


Build instructions:
run maven install on project PaymentTracker
