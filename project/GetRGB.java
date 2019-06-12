/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_highgui;
import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_highgui.imread;
import org.bytedeco.javacpp.opencv_imgproc;

/**
 *
 * @author User 2
 */
public class GetRGB {
    static int [] colour = new int[3];
    
    public static Color averageColor(BufferedImage bi, int x0, int y0, int w,
        int h) {
    int x1 = x0 + w;
    int y1 = y0 + h;
    long sumr = 0, sumg = 0, sumb = 0;
    for (int x = x0; x < x1; x++) {
        for (int y = y0; y < y1; y++) {
            Color pixel = new Color(bi.getRGB(x, y));
            sumr += pixel.getRed();
            sumg += pixel.getGreen();
            sumb += pixel.getBlue();
        }
    }
    int num = w * h;
    colour [0] = (int)sumr / num;
    colour [1] = (int)sumg / num;
    colour [2] = (int)sumb / num;
    return new Color((int)sumr / num, (int)sumg / num, (int)sumb / num);
}
    
    
public static double[] D65 = {95.0429, 100.0, 108.8900};
public static  double[] whitePoint = D65;


public static double[][] M   = {{0.4124, 0.3576,  0.1805},
                         {0.2126, 0.7152,  0.0722},
                         {0.0193, 0.1192,  0.9505}};

   
 
    /**
     * @param RGB
     * @return Lab values
     */
    public static double[] RGBtoLAB(int[] RGB) {
      return XYZtoLAB(RGBtoXYZ(RGB));
    }

 public static double[] RGBtoLAB(int R, int G, int B) {
      return (RGBtoXYZ(R, G, B));
    }


 /**
     * Convert RGB to XYZ
     * @param R
     * @param G
     * @param B
     * @return XYZ in double array.
     */
    public static double[] RGBtoXYZ(int R, int G, int B) {
      double[] result = new double[3];

      // convert 0..255 into 0..1
      double r = R / 255.0;
      double g = G / 255.0;
      double b = B / 255.0;

      // assume sRGB
      if (r <= 0.04045) {
        r = r / 12.92;
      }
      else {
        r = Math.pow(((r + 0.055) / 1.055), 2.4);
      }
      if (g <= 0.04045) {
        g = g / 12.92;
      }
      else {
        g = Math.pow(((g + 0.055) / 1.055), 2.4);
      }
      if (b <= 0.04045) {
        b = b / 12.92;
      }
      else {
        b = Math.pow(((b + 0.055) / 1.055), 2.4);
      }

      r *= 100.0;
      g *= 100.0;
      b *= 100.0;

      // [X Y Z] = [r g b][M]
      result[0] = (r * M[0][0]) + (g * M[0][1]) + (b * M[0][2]);
      result[1] = (r * M[1][0]) + (g * M[1][1]) + (b * M[1][2]);
      result[2] = (r * M[2][0]) + (g * M[2][1]) + (b * M[2][2]);

      return result;
    }

public static double[] RGBtoXYZ(int[] RGB) {
      return RGBtoXYZ(RGB[0], RGB[1], RGB[2]);
    }

   
public static double[] XYZtoLAB(double X, double Y, double Z) {

      double x = X / whitePoint[0];
      double y = Y / whitePoint[1];
      double z = Z / whitePoint[2];

      if (x > 0.008856) {
        x = Math.pow(x, 1.0 / 3.0);
      }
      else {
        x = (7.787 * x) + (16.0 / 116.0);
      }
      if (y > 0.008856) {
        y = Math.pow(y, 1.0 / 3.0);
      }
      else {
        y = (7.787 * y) + (16.0 / 116.0);
      }
      if (z > 0.008856) {
        z = Math.pow(z, 1.0 / 3.0);
      }
      else {
        z = (7.787 * z) + (16.0 / 116.0);
      }

      double[] result = new double[3];

      result[0] = (116.0 * y) - 16.0;
      result[1] = 500.0 * (x - y);
      result[2] = 200.0 * (y - z);

      return result;
    }

    /**
     * Convert XYZ to LAB.
     * @param XYZ
     * @return Lab values
     */
    public static double[] XYZtoLAB(double[] XYZ) {
      return XYZtoLAB(XYZ[0], XYZ[1], XYZ[2]);
    }

public GetRGB() {
      whitePoint = D65;
      }
    
    
 public static void main(String [] args){
     opencv_core.Mat testImage = imread("C:\\myframe6.png",opencv_highgui.CV_LOAD_IMAGE_ANYCOLOR);
     BufferedImage buf = testImage.getBufferedImage();
     Color col = averageColor(buf,0,0,buf.getWidth(),buf.getHeight());
     String co = col.toString();
     int [] c = {144,110,119};
     System.out.println(co);
    double[] mycol = RGBtoLAB(c);
     System.out.println(Arrays.toString(mycol));
     
//     opencv_core.IplImage img = testImage.asIplImage();
//     
//     opencv_core.IplImage labImage = opencv_core.IplImage.create(img.width(),img.height(),img.depth(),3);
//     opencv_imgproc.cvCvtColor(testImage.asIplImage(), labImage, opencv_imgproc.COLOR_RGB2Lab);
//     opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\myframe6.png",labImage );
//     
        
        
    
    }
    
}
