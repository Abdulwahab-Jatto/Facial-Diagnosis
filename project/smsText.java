/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author User 2
 */
public class smsText {
     static void smsSender(String phoneNo,String name,String key) throws IllegalArgumentException, NoSuchAlgorithmException, Exception{
            String[] recipients = {phoneNo};
            String xmlrecipients = "";
            String username = "abdulwahabjatto573@gmail.com";
            String apikey = "f196928d8fe2947f95e4f3988b4afa381a126d9b";
            String sendername = "Nurse "+name;
            String message = "The secret key for the received data is "+ key;
            String flash = "0";
            String theoutput = "";
            String randmsgid = Double.toString(Math.random());
            for( int i =0; i < recipients.length; i++ ){
                xmlrecipients += "<gsm><msidn>"+ recipients[i] + "</msidn><msgid>" + randmsgid + "_" + i + "</msgid>" + "</gsm>";
            }
            String xmlrequest =
                    "<SMS>\n"
                    + "<auth>"
                    + "<username>" + username + "</username>\n"
                    + "<apikey>" + apikey + "</apikey>\n"
                    + "</auth>\n"
                    + "<message>"
                    + "<sender>" + sendername + "</sender>\n"
                    + "<messagetext>" + message + "</messagetext>\n"
                    + "<flash>" + flash + "</flash>\n"
                    + "</message>\n"
                    + "<recipients>\n"
                    + xmlrecipients
                    + "</recipients>\n"
                    + "</SMS>";
            
            String theurl = "http://api.ebulksms.com:8080/sendsms.xml";
             //requester = new Sender();
            theoutput = postXMLData(xmlrequest, theurl);
            if(theoutput.contains("100")){
                System.out.println("100");
            }
            else{
                System.out.println(theoutput);
            }
        }
        
        public static String postXMLData(String xmldata, String urlpath){
            String result = "";
            try {
                URL myurl = new URL(urlpath);
                URLConnection urlconn = myurl.openConnection();
     
                urlconn.setRequestProperty("Content-Type", "text/xml");
                urlconn.setDoOutput(true);
                urlconn.setDoInput(true);
                urlconn.connect();
     
                //Create a writer to the url
                PrintWriter pw = new PrintWriter(urlconn.getOutputStream());
                pw.write(xmldata, 0, xmldata.length());
                pw.close();
     
                //Create a reader for the output of the connection
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
                String line = reader.readLine();
                while (line != null) {
                    result = result + line + "\n";
                    line = reader.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
     
    
    
    
}
