package org.aiti.sms;



//import org.smslib.OutboundMessage;

/**
 * The main driver for an SMS application in the AITI SMSlib framework.  In 
 * the main method you should register your inbound handler, set the proxy (if
 * necessary) and set the comm port of the modem.  The service will then start, 
 * waiting for incoming messages.
 * 
 * A new thread will be spawned to service each incoming message.  The thread
 * will create a new object of the class type of the registered handler 
 * (see SMSHandlerThread.setAITIInboundMessageNotification).  This new object, 
 * in its own thread, can then service the inbound message, sending a response
 * if necessary.
 * 
 * @author AITI
 *
 */
public class Main {
	/** set to true if you want debugging information */
	public static final boolean debug = true;
	
	/**
	 * The entry point for our SMS application.  You should register your inbound 
	 * handler, set the proxy (if necessary) and set the comm port of the modem.  T
	 * he service will then start, waiting for incoming messages.
	 * 
	 * @param args Args passed from the command line
	 */
	public static void main(String args[]) {
		
		AITISMSServer app = new AITISMSServer(true);
		try {
			SMSHandlerThread.setAITIInboundMessageNotification(new Receive());
			
			//set proxy if necessary, at Strathmore this is necessary
			//app.setProxyServer("192.168.170.25", 3135);
			
			//set modem port and speed for the gsm modem
			//app.setComPort((short)25, 460800);
			//start the service so that we can send and receive messages
			app.startService();
				
			//send a test message
			//app.getService().sendMessage(new OutboundMessage("0728306203", "Test"));
			
			//wait for incoming messages
			app.waitForInput();
			
			//stop all services and threads
			app.stopService();
			
			//wait for incoming messages
			app.doIt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A helpful printing method to use instead of System.out.println().  
	 * Use Main.debug to toggle printing the the screen.
	 * 
	 * @param s The object to print
	 */
	public static final void debugPrintln(Object s) {
		if (debug)
			System.out.println(s);
	}
}
