package org.aiti.sms;

import org.smslib.InboundMessage;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.Message.MessageTypes;
import java.sql.*;


public class Receive implements IAITIInboundMessageNotification{

	String reply;
	OutboundMessage out;
public void process(Service service, String gatewayId, MessageTypes msgType,
			InboundMessage inbox) {
		String message = inbox.getText();
		String originator = inbox.getOriginator();
		try {
			
			//The response is simply the message we received
			//String response = inbox.getText();
			Connection connect = null;
	        String url = "jdbc:mysql://localhost/";
	        String db = "winsor";
	        String user = "root";
	        String pass = "";
	        
	        Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(url + db, user, pass);
            System.out.println("Connection Successfully Established!!");
     
            if(originator.equals("MPESA"))
            {
            	String[] mpesa_sms  = message.split(" ");
            	
				//Split string to get the requires string elements;
            	
				String code = "";
				String mpesa_amount="";
				//String amount="";
				float value=0;				
				
				PreparedStatement mpesa_state = null;				
				
				for(int i = 0; i < mpesa_sms.length; i++){
					code = mpesa_sms[0];
					mpesa_amount = mpesa_sms[5];

					//amount = mpesa_sms[6];
				}
				
				
				//Enter Resident PIN Application
            	String mpesa_payment = "INSERT INTO mpesapayment(MpesaCode,PhoneNo,Value)VALUES(?,'"+originator+"',?)";
            	//value=Integer.parseInt(amount);
            	mpesa_state = connect.prepareStatement(mpesa_payment);
            	
            	String mpesa_value = mpesa_amount.substring( 3, mpesa_amount.length() );
            	value=Integer.parseInt(mpesa_value);


            	
            	mpesa_state.setString(1, code);
            	mpesa_state.setFloat(2, value);
       
             
            	mpesa_state.executeUpdate();
            	mpesa_state.close();
                connect.close();
                
              //Send successful registration message
                reply = "Transaction successful!!";
    			OutboundMessage out = new OutboundMessage(originator, reply);
    			service.sendMessage(out);
    			System.out.println(out);
    			
           }
           else
           {
            	reply = "Invalid transaction!!";
    		    out = new OutboundMessage(originator, reply);
    			service.sendMessage(out);
    			System.out.println(out);
            }
//=================================
		} 
		catch (Exception e) {
			System.err.println("Error getting message");
			e.printStackTrace();
			
		}
		
}
}
	


