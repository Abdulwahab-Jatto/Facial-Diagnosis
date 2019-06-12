/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.project;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.CV_AA;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import static org.bytedeco.javacpp.opencv_core.cvSize;
import org.bytedeco.javacpp.opencv_highgui;
import static org.bytedeco.javacpp.opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT;
import static org.bytedeco.javacpp.opencv_highgui.CV_CAP_PROP_FRAME_WIDTH;
import static org.bytedeco.javacpp.opencv_highgui.cvCreateCameraCapture;
import static org.bytedeco.javacpp.opencv_highgui.cvQueryFrame;
import static org.bytedeco.javacpp.opencv_highgui.cvReleaseCapture;
import static org.bytedeco.javacpp.opencv_highgui.cvWaitKey;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_objdetect;

/**
 *
 * @author User 2
 */
public class Diagnosis extends javax.swing.JFrame {

    /** Creates new form Diagnosis */
    public Diagnosis() {
        initComponents();
    }
    int count = 0;
    private DaemonThread mythread = new Diagnosis.DaemonThread();  
  opencv_highgui.CvCapture capture;
  opencv_core.IplImage frame;
    BufferedImage buf;
    class DaemonThread implements Runnable{
protected volatile boolean runnable = false;
@Override
@SuppressWarnings({"empty-statement", "UnusedAssignment", "ResultOfObjectAllocationIgnored"})
public void run(){
synchronized (this){
while(runnable){
  
        System.out.println("Working!!!");
	    capture = cvCreateCameraCapture(0);
           opencv_highgui.cvSetCaptureProperty(capture,CV_CAP_PROP_FRAME_WIDTH,640);
            opencv_highgui.cvSetCaptureProperty(capture,CV_CAP_PROP_FRAME_HEIGHT,480);
            
           // opencv_core.Mat frame = new opencv_core.Mat();
            Graphics g;
                g = dislbl.getGraphics();
       // String fname =  "haarcascade_frontalface_alt2.xml";
        
        //pencv_objdetect.CvHaarClassifierCascade fnames = new opencv_objdetect.CvHaarClassifierCascade(opencv_core.cvLoad(fname));
        opencv_core.IplImage image;
        opencv_core.CvMat face_img = new opencv_core.CvMat() ;
            while(true){
        //          opencv_core.CvMemStorage faceStorage= opencv_core.CvMemStorage.create();
                  //image = cvQueryFrame(capture1);
                  
                  if(!capture.isNull()){
                      image  = cvQueryFrame(capture);
                    //image = frame.asIplImage();
                  opencv_core.cvFlip(image,image,1);
                    frame = image;
                    //opencv_core.cvClearMemStorage(faceStorage);
                    //IplImage image1=IplImage.create(frame1.width(),frame1.height(), IPL_DEPTH_8U, 1);
                    //opencv_core.IplImage frame_gray = opencv_core.IplImage.create(frame1.width(),frame1.height(), IPL_DEPTH_8U, 1);
                      //  opencv_imgproc.cvCvtColor(frame1, frame_gray,opencv_imgproc.CV_BGR2GRAY );
                        //opencv_imgproc.cvEqualizeHist(frame_gray, frame_gray);
                       
                        //opencv_core.CvSeq faces=opencv_objdetect.cvHaarDetectObjects(frame_gray, fnames, faceStorage, 1.1, 3,opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING,cvSize(80,80),cvSize(250,250));
                        //System.out.println(faces.total());
                        
//                        for(int i=0;i<faces.total();i++){
//                            opencv_core.CvRect r = new opencv_core.CvRect(cvGetSeqElem(faces,i));
//                            int x=r.x(),y=r.y(),w=r.width(),h=r.height();
//                            opencv_core.cvRectangle(frame1, cvPoint(x,y),cvPoint(x+w,y+h), opencv_core.CvScalar.GREEN, 2, CV_AA, 0);
//                            opencv_core.cvGetSubRect(frame1, face_img, r); 
//                          try {
//                              new Recognition.ImageProcessing(face_img);
//                          } catch (IOException ex) {
//                             
//                          } catch (ClassNotFoundException ex) {
//                              
//                          }
//                        }
            
               
                    
                //}
                BufferedImage dis = image.getBufferedImage();
                if(g.drawImage(dis,0,0,dislbl.getWidth(),dislbl.getHeight(),0,0,dis.getWidth(),dis.getHeight(),null)){}

                char c = (char)cvWaitKey(15);
                if(c == 'q')break;
                
            }
            if(runnable == false){
	System.out.println("going to wait");
    try {
        this.wait();
    } catch (InterruptedException ex) {
       
    }}
}
            
        }
} } }

void displayImg(BufferedImage img, int counter){
    switch (counter) {
            case 0:
                //Red
                ImageIcon image1 = new ImageIcon(img);
                Rectangle rec = redlbl.getBounds();
                Image scaledimage = image1.getImage().getScaledInstance(rec.width,rec.height,Image.SCALE_DEFAULT);
                image1 = new ImageIcon(scaledimage);
                redlbl.setIcon(image1);
                break;
            case 1:
                //Green
                ImageIcon image2 = new ImageIcon(img);
                Rectangle rec2 = greenlbl.getBounds();
                Image scaledimage2 = image2.getImage().getScaledInstance(rec2.width,rec2.height,Image.SCALE_DEFAULT);
                image2 = new ImageIcon(scaledimage2);
                greenlbl.setIcon(image2);
                
                break;
            case 2:
                //Blue
                ImageIcon image3 = new ImageIcon(img);
                Rectangle rec3 = bluelbl.getBounds();
                Image scaledimage3 = image3.getImage().getScaledInstance(rec3.width,rec3.height,Image.SCALE_DEFAULT);
                image3 = new ImageIcon(scaledimage3);
                bluelbl.setIcon(image3);
                
                break;
            case 3:
                //Yellow
                ImageIcon image4 = new ImageIcon(img);
                Rectangle rec4 = yellowlbl.getBounds();
                Image scaledimage4 = image4.getImage().getScaledInstance(rec4.width,rec4.height,Image.SCALE_DEFAULT);
                image4 = new ImageIcon(scaledimage4);
                yellowlbl.setIcon(image4);
                
                break;
            case 4:
                //Black
                ImageIcon image5 = new ImageIcon(img);
                Rectangle rec5 = darklbl.getBounds();
                Image scaledimage5 = image5.getImage().getScaledInstance(rec5.width,rec5.height,Image.SCALE_DEFAULT);
                image5 = new ImageIcon(scaledimage5);
                darklbl.setIcon(image5);
                
                break;
            case 5:
                //Sunlight
                ImageIcon image6 = new ImageIcon(img);
                Rectangle rec6 = daylbl.getBounds();
                Image scaledimage6 = image6.getImage().getScaledInstance(rec6.width,rec6.height,Image.SCALE_DEFAULT);
                image6 = new ImageIcon(scaledimage6);
                daylbl.setIcon(image6);
                break;
            case 6:
                //Sunlight
                ImageIcon image7 = new ImageIcon(img);
                Rectangle rec7 = whlbl.getBounds();
                Image scaledimage7 = image7.getImage().getScaledInstance(rec7.width,rec7.height,Image.SCALE_DEFAULT);
                image7 = new ImageIcon(scaledimage7);
                whlbl.setIcon(image7);
                break;
           }

}
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        dislbl = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        greenlbl = new javax.swing.JLabel();
        redlbl = new javax.swing.JLabel();
        yellowlbl = new javax.swing.JLabel();
        darklbl = new javax.swing.JLabel();
        daylbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        bluelbl = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        whlbl = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        stabtn3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        stabtn = new javax.swing.JButton();
        stabtn1 = new javax.swing.JButton();
        stabtn2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(dislbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 630, 440));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, 650, 460));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(greenlbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 100, 110));
        jPanel3.add(redlbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, 110));
        jPanel3.add(yellowlbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 100, 110));
        jPanel3.add(darklbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 100, 110));
        jPanel3.add(daylbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 100, 110));

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(49, 150, 222));
        jLabel2.setText("<html><center><font color = green>Green</font></center>");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        jLabel12.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(49, 150, 222));
        jLabel12.setText("<html><center><font color = blue>Blue</font></center>");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        jLabel13.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(49, 150, 222));
        jLabel13.setText("<html><center><font color = red>Red</font></center>");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel14.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(49, 150, 222));
        jLabel14.setText("<html><center><font color = black>Dark</font></center>");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, -1, -1));

        jLabel15.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(49, 150, 222));
        jLabel15.setText("<html><center><font color = yellow>Yellow</font></center>");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));
        jPanel3.add(bluelbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 100, 110));

        jLabel18.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(49, 150, 222));
        jLabel18.setText("<html><center><font color = white>Daylight</font></center>");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, -1, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 360, 330));

        jLabel11.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(49, 150, 222));
        jLabel11.setText("Training Set");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 4, -1, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(whlbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 100, 110));

        jLabel19.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(49, 150, 222));
        jLabel19.setText("<html><center><font color = white>White light</font></center>");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 20, 140, 330));

        jLabel16.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(49, 150, 222));
        jLabel16.setText("White light");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 5, -1, -1));

        stabtn3.setBackground(new java.awt.Color(221, 221, 221));
        stabtn3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        stabtn3.setForeground(new java.awt.Color(49, 150, 222));
        stabtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/cam.png"))); // NOI18N
        stabtn3.setText("Diagnose");
        stabtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stabtn3ActionPerformed(evt);
            }
        });
        jPanel2.add(stabtn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 530, 460));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(49, 150, 222));
        jLabel1.setText("Take Facial Image");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, -1));

        stabtn.setBackground(new java.awt.Color(221, 221, 221));
        stabtn.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        stabtn.setForeground(new java.awt.Color(49, 150, 222));
        stabtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/cam.png"))); // NOI18N
        stabtn.setText("Stop");
        stabtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stabtnActionPerformed(evt);
            }
        });
        getContentPane().add(stabtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 500, -1, -1));

        stabtn1.setBackground(new java.awt.Color(221, 221, 221));
        stabtn1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        stabtn1.setForeground(new java.awt.Color(49, 150, 222));
        stabtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/cam.png"))); // NOI18N
        stabtn1.setText("Capture");
        stabtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stabtn1ActionPerformed(evt);
            }
        });
        getContentPane().add(stabtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 500, -1, -1));

        stabtn2.setBackground(new java.awt.Color(221, 221, 221));
        stabtn2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        stabtn2.setForeground(new java.awt.Color(49, 150, 222));
        stabtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/cam.png"))); // NOI18N
        stabtn2.setText("Start");
        stabtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stabtn2ActionPerformed(evt);
            }
        });
        getContentPane().add(stabtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 500, -1, -1));

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(49, 150, 222));
        jLabel4.setText("Samples");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void stabtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stabtnActionPerformed
        mythread.runnable = false;
        cvReleaseCapture(capture);
        stabtn.setEnabled(true);
        
    }//GEN-LAST:event_stabtnActionPerformed

    private void stabtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stabtn1ActionPerformed
        //frame
        switch (count) {
            case 0:
                //Red
                opencv_highgui.cvSaveImage("C:\\ProjectImages\\image"+count+".png", frame);
        {
            try {
                buf = ImageIO.read(new File("C:\\ProjectImages\\image"+count+".png"));
                displayImg(buf, count);
            } catch (IOException ex) {
                Logger.getLogger(Diagnosis.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                count++;
                break;
            case 1:
                //Green
                opencv_highgui.cvSaveImage("C:\\ProjectImages\\image"+count+".png", frame);
        {
            try {
                buf = ImageIO.read(new File("C:\\ProjectImages\\image"+count+".png"));
                displayImg(buf, count);
            } catch (IOException ex) {
                Logger.getLogger(Diagnosis.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                count++;
                break;
            case 2:
                //Blue
                opencv_highgui.cvSaveImage("C:\\ProjectImages\\image"+count+".png", frame);
        {
            try {
                buf = ImageIO.read(new File("C:\\ProjectImages\\image"+count+".png"));
                displayImg(buf, count);
            } catch (IOException ex) {
                Logger.getLogger(Diagnosis.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                count++;
                break;
            case 3:
                //Yellow
                opencv_highgui.cvSaveImage("C:\\ProjectImages\\image"+count+".png", frame);
        {
            try {
                buf = ImageIO.read(new File("C:\\ProjectImages\\image"+count+".png"));
                displayImg(buf, count);
            } catch (IOException ex) {
                Logger.getLogger(Diagnosis.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                count++;
                break;
            case 4:
                //Black
                opencv_highgui.cvSaveImage("C:\\ProjectImages\\image"+count+".png", frame);
        {
            try {
                buf = ImageIO.read(new File("C:\\ProjectImages\\image"+count+".png"));
                displayImg(buf, count);
            } catch (IOException ex) {
                Logger.getLogger(Diagnosis.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                count++;
                break;
            case 5:
                //Sunlight
                opencv_highgui.cvSaveImage("C:\\ProjectImages\\image"+count+".png", frame);
        {
            try {
                buf = ImageIO.read(new File("C:\\ProjectImages\\image"+count+".png"));
                displayImg(buf, count);
            } catch (IOException ex) {
                Logger.getLogger(Diagnosis.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                count++;
                break;
            case 6:
                //White light
                opencv_highgui.cvSaveImage("C:\\ProjectImages\\testimage.png", frame);
        {
            try {
                buf = ImageIO.read(new File("C:\\ProjectImages\\testimage.png"));
                displayImg(buf, count);
            } catch (IOException ex) {
                Logger.getLogger(Diagnosis.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                count++;
                break;
            
            default:
                JOptionPane.showMessageDialog(null,"Now proceed to diagnose the patient" );
                count = 0;
                break;
        }
        
    }//GEN-LAST:event_stabtn1ActionPerformed

    private void stabtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stabtn2ActionPerformed
        mythread = new Diagnosis.DaemonThread();
        Thread t = new Thread(mythread);
        t.setDaemon(true);
        mythread.runnable = true;
        t.start();
        //stabtn.setEnabled(false);
        
    }//GEN-LAST:event_stabtn2ActionPerformed

    private void stabtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stabtn3ActionPerformed
        try {
            CollectFeatures.diagnose();
        } catch (IOException ex) {
            Logger.getLogger(Diagnosis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_stabtn3ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        new HomePage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Diagnosis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Diagnosis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Diagnosis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Diagnosis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Diagnosis().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bluelbl;
    private javax.swing.JLabel darklbl;
    private javax.swing.JLabel daylbl;
    private javax.swing.JLabel dislbl;
    private javax.swing.JLabel greenlbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel redlbl;
    private javax.swing.JButton stabtn;
    private javax.swing.JButton stabtn1;
    private javax.swing.JButton stabtn2;
    private javax.swing.JButton stabtn3;
    private javax.swing.JLabel whlbl;
    private javax.swing.JLabel yellowlbl;
    // End of variables declaration//GEN-END:variables

}
