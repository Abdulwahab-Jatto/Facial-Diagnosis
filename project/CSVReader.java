/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author User 2
 */
public class CSVReader {

    public static void main(String[] args) throws IOException {

        String csvFile = "C:\\Users\\User 2\\Desktop\\nkem.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] attribute = null;
         //FileWriter fileWriter = null;
         String csvFile1 = "C:\\Users\\User 2\\Desktop\\test.txt";
        FileWriter writer = new FileWriter(csvFile1);
         String [] a = new String [56];
        try {
           // fileWriter = new FileWriter("C:\\Users\\User 2\\Desktop\\nkemOut.csv");

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                 attribute = line.split(cvsSplitBy);
                 System.out.println(attribute[0]+"  "+attribute[1]);
                 for(int i=0; i<attribute.length;i++){
                     
                     switch(attribute[i]){
                         case "A":
                             System.out.println(attribute[0]);
                             a[i]="1";
                             System.out.println(a[i]);
                         break;
                         case "B":
                             a[i]="0.8";
                             break;
                         case "C":
                             a[i]="0.6";
                             break;
                         case "D":
                             a[i]="0.4";
                             break;
                         case "E":
                             a[i]="0.2";
                             break;
                         case "F":
                             a[i]="0";
                             break;
                         case "0":
                             a[i]="0";
                             break;
                         case "1":
                             a[i]="1";
                             break; 
                                               
                                                     
                 }
                 
                 }
                 CSVUtils.writeLine(writer, Arrays.asList(a));
                 
                 }
            
    
            
            
        }
        
        catch (FileNotFoundException e) {}
        
        //for(int i = 0; i<)
    
    
             writer.flush();
        writer.close();
    }
}