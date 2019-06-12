/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author User 2
 */
public class Buff {
    public static String bufImg(String str) throws IOException{
        String[] result = str.split(",");
        int r = Integer.parseInt(result[0]);
        int g = Integer.parseInt(result[1]);
        int b = Integer.parseInt(result[2]);
        //BufferedImage image = new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
        
        BufferedImage image = new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    image.setRGB(i, j, new Color(r,g,b).getRGB());
                }
            }
        File file = new File("C:\\ProjectImages\\test.png");
        ImageIO.write(image,"png",file);
        return file.getAbsolutePath();
            
    }
    
}
