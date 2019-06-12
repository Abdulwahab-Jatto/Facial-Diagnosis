/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

import static com.project.ColorSummerizer.write;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author User 2
 */
public class LIBSVM {
    static Map<String,Integer> map = new HashMap<>();
    public static void predict(int i){
        String [] result;
        
      try{ 
        String file,file1,file2,file3;
        file = "C:\\libsvm-3.22\\windows\\FacialTrain.train";
        file1 = "C:\\libsvm-3.22\\windows\\FacialTest.test";
        file2 = "C:\\libsvm-3.22\\windows\\FacialTrain.train.model";
        file3 = "C:\\libsvm-3.22\\windows\\result.out";
        Process p=Runtime.getRuntime().exec("C:\\libsvm-3.22\\windows\\svm-train.exe "+file);//\C:\\myframe6.png -text"); 
        p.waitFor(); 
        
       Process p1=Runtime.getRuntime().exec("C:\\libsvm-3.22\\windows\\svm-predict.exe "+file1+" "+file2+" "+file3);//\C:\\myframe6.png -text"); 
        p1.waitFor(); 
        
        //DataInputStream dis = new DataInputStream(reader);
        //String line=reader.readLine();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p1.getInputStream()))) {
            //DataInputStream dis = new DataInputStream(reader);
            //String line=reader.readLine();
            boolean eof = false;
            
            while(!eof)
            {   String line=reader.readLine();
            //String line = null;
            if (line==null)
                eof=true;
            else{
                    System.out.println(line);
                    result = line.split("\\s");
                    if(i == 1){
                      map.put("Red",Integer.parseInt(result[2].split("%")[0]));
                   }
                    if(i == 2){
                      map.put("Green",Integer.parseInt(result[2].split("%")[0]));
                   }
                    if(i == 3){
                      map.put("Blue",Integer.parseInt(result[2].split("%")[0]));
                   }
                    if(i == 4){
                      map.put("Yellow",Integer.parseInt(result[2].split("%")[0]));
                   }
                    if(i == 5){
                      map.put("Black",Integer.parseInt(result[2].split("%")[0]));
                   }
                    if(i == 6){
                      map.put("Normal",Integer.parseInt(result[2].split("%")[0]));
                      BarChart.displayChart(map);
                   }
            }
            
                
            }
        }
        
    }
    catch(IOException | InterruptedException e1) {} 
    
    
    }
    
}
