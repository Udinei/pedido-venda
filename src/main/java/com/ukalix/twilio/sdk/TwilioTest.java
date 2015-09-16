package com.ukalix.twilio.sdk;
// You may want to be more specific in your imports 
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
 
public class TwilioTest { 
 // Find your Account Sid and Token at twilio.com/user/account 
 public static final String ACCOUNT_SID = "ACbac28501910cfd7e6562b46d6cb5343f"; 
 public static final String AUTH_TOKEN = "8f988cdc5951cc1faa1b0d5057f4b6b4"; 
 
 public static void main(String[]args) throws TwilioRestException { 
	TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 
 
	 // Build the parameters 
	 List<NameValuePair> params = new ArrayList<NameValuePair>();  
	  
	 params.add(new BasicNameValuePair("To", "+55930998947"));
	 params.add(new BasicNameValuePair("From", "+18559730067"));  
	 params.add(new BasicNameValuePair("Body", "Jenny please?! I love you <3"));
	

	    
	    MessageFactory messageFactory = client.getAccount().getMessageFactory(); 
	 Message message = messageFactory.create(params); 
	 System.out.println(message.getSid()); 
 } 
}