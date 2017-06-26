# bsc
BSC Java test
Payment Tracker

<b>Instructions for use</b>

Application runs as runnable jar
<p>Run command: java -jar paymentTracker.jar [fileName]
<p>fileName - file name with path of file with payments, can be omitted
<p>Format of the payment file: 
<p>			USD 23
<p>			EUR 77
<p>			CZK 100
<p>Application reads the payments from the file (if entered).
<p>Ones per minute it prints the payment status in the console. If amount of a currency is 0, no output for this 
currency will be generated.
<p>Ones per hour it updates the present currencies rates to the default rate (USD).

<p><b>User commands:</b>
<p>quit - quits the application
<p>print - prints the payment status to console
<p>USD 25 - adds new payment of amount 25 USD, format <XXX> <amount>, where XXX is currency code and amount is integer
<p>other text causes "Invalid input" output


<p><b>Build instructions:</b>
<p>run maven install on project PaymentTracker
