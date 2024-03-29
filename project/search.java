/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

/**
 *
 * @author User 2
 */
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Vector;
 
public class search {
    public static void main(String args[]) throws UnknownHostException{
 
        Vector<String> Available_Devices=new Vector<>();
        String myip=InetAddress.getLocalHost().getHostAddress();
        //String myip = "192.168.173.1";
        String mynetworkips=new String();
 
        for(int i=myip.length();i>0;--i){
            if(myip.charAt(i-1)=='.'){
		 mynetworkips=myip.substring(0,i);
		 break; 
	    }
        }
 
        System.out.println("My Device IP: " + myip+"\n");
 
        System.out.println("Search log:");
        for(int i=1;i<=254;++i){
          try {
                InetAddress addr=InetAddress.getByName(mynetworkips + new Integer(i).toString());
                if (addr.isReachable(50)){
                    System.out.println("Available: " + addr.getHostAddress());
                    Available_Devices.add(addr.getHostAddress());
                }
                else System.out.println("Not available: "+ addr.getHostAddress());
 
          }catch (IOException ioex){}
        }
 
        System.out.println("\nAll Connected devices(" + Available_Devices.size() +"):");
        for(int i=0;i<Available_Devices.size();++i) System.out.println(Available_Devices.get(i));
    }
}