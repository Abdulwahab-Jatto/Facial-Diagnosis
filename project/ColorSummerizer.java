/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author User 2
 */
public class ColorSummerizer {
    static String s;
    static String write(String str) throws IOException{
        String[] result = str.split("\\s");
        String st;
//     for (int x=0; x<result.length; x++)
//         System.out.println(result[x]);
     s = result[8]+","+result[9]+","+result[10]+","+"0"+","+result[14]+","+result[15]+","+result[16]+","+
             result[18]+","+result[19]+","+result[20]+","+result[22]+","+result[23]+","+result[24]+","+result[26]+","+
             result[27]+","+result[28]+","+result[30]+","+result[31]+","+result[32]+","+result[33];
     st = result[8]+","+result[9]+","+result[10];
     writeData(st);
     return s;
    }
    public static void writeData(String s) throws IOException {
            BufferedWriter writeval = new BufferedWriter(new FileWriter("rgb.txt"));//"C:\\Users\\User 2\\Desktop\\clinicdata.txt"));
            writeval.write(s);
            writeval.close();

        }
    
    public static String colourFeatures(String file){
       String features = null;
       //String exe =
        try 
    { 
        Process p=Runtime.getRuntime().exec("C:\\colorsummarizer-0.77-win\\bin\\colorsummarizer.exe -image "+file+" "+"-text");//\C:\\myframe6.png -text"); 
        p.waitFor(); 
        //DataInputStream dis = new DataInputStream(reader);
        //String line=reader.readLine();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            //DataInputStream dis = new DataInputStream(reader);
            //String line=reader.readLine();
            boolean eof = false;
            int i = 0;
            while(!eof)
            {   String line=reader.readLine();
            //String line = null;
            if (line==null)
                eof=true;
            else
                if(i == 3){
                    System.out.println(line);
                    features = write(line);
                }
            i++;
                //System.out.println(line);
                
            }
        }
    }
    catch(IOException | InterruptedException e1) {} 

    System.out.println("Done");
      
   return features ;
    }
    
}
