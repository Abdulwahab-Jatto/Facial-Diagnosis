/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;


import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

/**
 *
 * @author User 2
 */
public class SVD {
    static void writeName(String s) throws IOException{
        BufferedWriter writeval = new BufferedWriter(new FileWriter("svd.txt"));//"C:\\Users\\User 2\\Desktop\\clinicdata.txt"));
            writeval.write(s);
            writeval.close();

    }
 static double sum(double [] singularValues){
     double sum = 0;
for (int k = 0; k<singularValues.length;k++){
    sum += singularValues[k];

}
return sum;
 
 } 
 
 static double max(double[] singularValues){
     
double max = singularValues[0];
for(int i = 1;i<singularValues.length;i++) { // legal because rest is actually an array
if (singularValues[i] > max) max = singularValues[i];
}
return max;
}
 
 static double min(double[] singularValues){
     
double min = singularValues[0];
for(int i = 1;i<singularValues.length;i++) { // legal because rest is actually an array
if ( singularValues[i]< min) min = singularValues[i];
}
return min;
}
 
    
   public static String algebraicFeature(String file) throws IOException{
       BufferedImage bi = ImageIO.read(new File(file));
       double[][] C = new double[bi.getWidth()][bi.getHeight()];
       for(int i = 0; i < bi.getWidth(); i++){
           for(int j = 0; j < bi.getHeight(); j++){
               C[i][j] = bi.getRGB(i, j);
           }
       }
       Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(C);
       SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
//        RealMatrix u = svd.getU(); // m x p
//        RealMatrix s = svd.getS(); // p x p
//        RealMatrix v = svd.getV(); // p x n
/* retrieve values, in decreasing order, from the diagonal of S */
        double[] singularValues = svd.getSingularValues();
/* can also get covariance of input matrix */
//    double minSingularValue = 0;// 0 or neg value means all sv are used
//RealMatrix cov = svd.getCovariance(minSingularValue);
//System.out.println(u);
//System.out.println(s);
//System.out.println(v);
//System.out.println(singularValues.length);
//System.out.println(Arrays.toString(singularValues));
//double sum = sum(singularValues);
//double max = max(singularValues);
//String features = sum(singularValues)+","+max(singularValues)+","+min(singularValues);
//System.out.println(sum);
//System.out.println();
//System.out.println(min(singularValues));
//System.out.println(sum);
//writeName(matrix.toString());
//System.out.println(C.length);
   return sum(singularValues)+","+max(singularValues)+","+min(singularValues);
   } 
   
}
