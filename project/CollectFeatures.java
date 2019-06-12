/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui;
import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import org.bytedeco.javacpp.opencv_imgproc;

/**
 *
 * @author User 2
 */
public class CollectFeatures {
    static String[] attribute;
    static void writeName(String s,String t) throws IOException{
        BufferedWriter writeval = new BufferedWriter(new FileWriter("FacialTrain.csv"));//"C:\\Users\\User 2\\Desktop\\clinicdata.txt"));
            writeval.write(s);
            writeval.newLine();
            writeval.write(t);
            writeval.close();

    }
    
    static void writeName1(String s,String t) throws IOException{
        BufferedWriter writeval = new BufferedWriter(new FileWriter("FacialTest.csv"));//"C:\\Users\\User 2\\Desktop\\clinicdata.txt"));
            writeval.write(s);
            writeval.newLine();
            writeval.write(t);
            writeval.close();

    }
    
    
    static void writeTrain(String s) throws IOException{
        BufferedWriter writeval = new BufferedWriter(new FileWriter("C:\\libsvm-3.22\\windows\\FacialTrain.train"));//"C:\\Users\\User 2\\Desktop\\clinicdata.txt"));
            writeval.write(s);
            writeval.close();

    }

static void writeTest(String s) throws IOException{
        BufferedWriter writeval = new BufferedWriter(new FileWriter("C:\\libsvm-3.22\\windows\\FacialTest.test"));//"C:\\Users\\User 2\\Desktop\\clinicdata.txt"));
            writeval.write(s);
            writeval.close();

    }
        
    
    public static String readData() throws IOException {
            String val = null;
            try {
                BufferedReader checkVal = new BufferedReader(new FileReader("rgb.txt"));
                val = checkVal.readLine();
                System.out.println(val);
                checkVal.close();
            } catch (FileNotFoundException ex) {
            }
            return val;
        }
    static void LibSVMFile() throws FileNotFoundException, IOException{
        StringBuilder sb = new StringBuilder(); 
        //StringBuilder sb1 = new StringBuilder();
        String csvFile = "FacialTrain.csv";
        String line = "";
        String cvsSplitBy = ",";
        String[] attribute = null;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int i = 0;  
                    while ((line = br.readLine()) != null) {

                // use comma as separator
                attribute = line.split(cvsSplitBy);
               System.out.println(attribute.length);
                if(i>0){
                    for (int j = 0; j<=19;j++){
                    sb.append(i).append(" ").append("1").append(":").append(attribute[0]).append(" ").append("2").append(":").append(attribute[1])
                            .append(" ").append("3").append(":").append(attribute[2]).append(" ").append("4").append(":").append(attribute[3])
                            .append(" ").append("5").append(":").append(attribute[4]).append(" ").append("6").append(":").append(attribute[5])
                            .append(" ").append("7").append(":").append(attribute[6]).append(" ").append("8").append(":").append(attribute[7])
                            .append(" ").append("9").append(":").append(attribute[8]).append(" ").append("10").append(":").append(attribute[9])
                            .append(" ").append("11").append(":").append(attribute[10]).append(" ").append("12").append(":").append(attribute[11])
                            .append(" ").append("13").append(":").append(attribute[12]).append(" ").append("14").append(":").append(attribute[13])
                            .append(" ").append("15").append(":").append(attribute[14]).append(" ").append("16").append(":").append(attribute[15])
                            .append(" ").append("17").append(":").append(attribute[16]).append(" ").append("18").append(":").append(attribute[17])
                            .append(" ").append("19").append(":").append(attribute[18]).append(" ").append("20").append(":").append(attribute[19])
                            .append(" ").append("21").append(":").append(attribute[20]).append(" ").append("22").append(":").append(attribute[21])
                            .append(" ").append("23").append(":").append(attribute[22]).append(" ").append("24").append(":").append(attribute[23])
                            .append(" ").append("25").append(":").append(attribute[24]).append(" ").append("26").append(":").append(attribute[25])
                            .append(" ").append("27").append(":").append(attribute[26]).append(" ").append("28").append(":").append(attribute[27])
                            .append(" ").append("29").append(":").append(attribute[28]).append(" ").append("30").append(":").append(attribute[29])
                            .append(" ").append("31").append(":").append(attribute[30]).append(" ").append("32").append(":").append(attribute[31])
                            .append(" ").append("33").append(":").append(attribute[32]).append(" ").append("34").append(":").append(attribute[33])
                            .append(" ").append("35").append(":").append(attribute[34]).append(" ").append("36").append(":").append(attribute[35])
                            .append(" ").append("37").append(":").append(attribute[36]).append("\n");
                    
                } }  i++;    }
   
    writeTrain(sb.toString());
    }}
    
    static void LibSVMFile1() throws FileNotFoundException, IOException{
        StringBuilder sb = new StringBuilder(); 
        //StringBuilder sb1 = new StringBuilder();
        String csvFile = "FacialTest.csv";
        String line = "";
        String cvsSplitBy = ",";
        attribute = null;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int i = 0,k=1;  
                    while ((line = br.readLine()) != null) {

                // use comma as separator
                attribute = line.split(cvsSplitBy);
               System.out.println(attribute.length);
                if(i>0){
                    while(k<=6){
                       test(k); 
                    k++;
                    } }  i++;    }
   
    //writeTrain1(sb.toString());
    }}
   static void test(int k) throws IOException{
       StringBuilder sb = new StringBuilder();
       sb.append(k).append(" ").append("1").append(":").append(attribute[0]).append(" ").append("2").append(":").append(attribute[1])
                            .append(" ").append("3").append(":").append(attribute[2]).append(" ").append("4").append(":").append(attribute[3])
                            .append(" ").append("5").append(":").append(attribute[4]).append(" ").append("6").append(":").append(attribute[5])
                            .append(" ").append("7").append(":").append(attribute[6]).append(" ").append("8").append(":").append(attribute[7])
                            .append(" ").append("9").append(":").append(attribute[8]).append(" ").append("10").append(":").append(attribute[9])
                            .append(" ").append("11").append(":").append(attribute[10]).append(" ").append("12").append(":").append(attribute[11])
                            .append(" ").append("13").append(":").append(attribute[12]).append(" ").append("14").append(":").append(attribute[13])
                            .append(" ").append("15").append(":").append(attribute[14]).append(" ").append("16").append(":").append(attribute[15])
                            .append(" ").append("17").append(":").append(attribute[16]).append(" ").append("18").append(":").append(attribute[17])
                            .append(" ").append("19").append(":").append(attribute[18]).append(" ").append("20").append(":").append(attribute[19])
                            .append(" ").append("21").append(":").append(attribute[20]).append(" ").append("22").append(":").append(attribute[21])
                            .append(" ").append("23").append(":").append(attribute[22]).append(" ").append("24").append(":").append(attribute[23])
                            .append(" ").append("25").append(":").append(attribute[24]).append(" ").append("26").append(":").append(attribute[25])
                            .append(" ").append("27").append(":").append(attribute[26]).append(" ").append("28").append(":").append(attribute[27])
                            .append(" ").append("29").append(":").append(attribute[28]).append(" ").append("30").append(":").append(attribute[29])
                            .append(" ").append("31").append(":").append(attribute[30]).append(" ").append("32").append(":").append(attribute[31])
                            .append(" ").append("33").append(":").append(attribute[32]).append(" ").append("34").append(":").append(attribute[33])
                            .append(" ").append("35").append(":").append(attribute[34]).append(" ").append("36").append(":").append(attribute[35])
                            .append(" ").append("37").append(":").append(attribute[36]);
                    
                    writeTest(sb.toString());
                    LIBSVM.predict(k);
                    System.out.println(k);
                    
   
   } 
    public static void diagnose() throws IOException{
        IplImage img, testImg;// = null;
        String attribute = "R"+","+"G"+","+"B"+","+"HEX"+","+"H"+","+"S"+","+"V"+","+"L"+","+"A"+","+"B"+","+"L"+","+"C"+","+"H"+","+"X"+","+"Y"+","+"Z"+","+"C"+","+"M"+","+"Y"+","+"K"+","+"SUM"+","+"MAX"+","+"MIN"+","+
        "Angular 2nd moment"+","+"Contrast"+","+"Correlation"+","+"variance"+","+"Inverse Difference Moment"+","+"Sum Average"+","+"Sum Variance"+","+"Sum Entropy"+","+
        "Entropy"+","+"Difference Variance"+","+"Difference Entropy"+","+"Information Measures of Correlation"+","+"Information Measures of Correlation"+","+
        "Maximum Correlation COefficient";
        StringBuilder sb =new StringBuilder();
        StringBuilder sb1 =new StringBuilder();
        
        String file,file1;
        for(int i =0;i<=5;i++){
        
        img = cvLoadImage("C:\\ProjectImages\\image"+i+".png",opencv_highgui.CV_LOAD_IMAGE_ANYCOLOR);
        opencv_imgproc.cvCvtColor(img, img,opencv_imgproc.CV_RGB2Lab );
        cvSaveImage("C:\\ProjectImages\\image"+i+".png",img);
        file = "C:\\ProjectImages\\image"+i+".png";
        sb.append(ColorSummerizer.colourFeatures(file)).append(",").append(SVD.algebraicFeature(Buff.bufImg(readData()))).append(",").append(new Haralick().haralickFeature(file)).append("\n");
         //System.out.println(data);}
        }
        writeName(attribute,sb.toString());
        LibSVMFile();
        
        testImg = cvLoadImage("C:\\ProjectImages\\testimage.png",opencv_highgui.CV_LOAD_IMAGE_ANYCOLOR);
        opencv_imgproc.cvCvtColor(testImg, testImg,opencv_imgproc.CV_RGB2Lab );
        cvSaveImage("C:\\ProjectImages\\testimage.png",testImg);
        file1 = "C:\\ProjectImages\\testimage.png";
        sb1.append(ColorSummerizer.colourFeatures(file1)).append(",").append(SVD.algebraicFeature(Buff.bufImg(readData()))).append(",").append(new Haralick().haralickFeature(file1)).append("\n");
        writeName1(attribute,sb1.toString());
        LibSVMFile1();
        
                 
    }
    
}
