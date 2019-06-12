/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

import AppPackage.AnimationClass;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author User 2
 */
public class HomePage extends javax.swing.JFrame {

    /**
     * Creates new form HomePage
     */
    public HomePage() {
        initComponents();
        slideShow();
    }
    AnimationClass AC = new AnimationClass();
    int count = 0;
    
    public void slideShow(){
        new Thread(){
          
          @Override
          public void run(){
              try{
                  while(true){
                      switch (count){
                          case 0:
                              Thread.sleep(3000);
                              AC.jLabelXLeft(0,-450, 100,45,slide1);
                              AC.jLabelXLeft(450,0, 100,45,slide2);
                              AC.jLabelXLeft(900,450, 100,45,slide3);
                              AC.jLabelXLeft(1350,900, 100,45,slide4);
                              AC.jLabelXLeft(1800,1350, 100,45,slide5);
                              AC.jLabelXLeft(2250,1800, 100,45,slide6);
                              count = 1;
                              
                              break;
                              
                          case 1:
                              Thread.sleep(3000);
                              AC.jLabelXLeft(0,-450,100,45,slide2);
                              AC.jLabelXLeft(450,0, 100,45,slide3);
                              count = 2;
                              break;
                          
                         case 2:
                              Thread.sleep(3000);
                              AC.jLabelXLeft(0,-450,100,45,slide3);
                              AC.jLabelXLeft(450,0, 100,45,slide4);
                              count = 3;
                              break;
                        case 3:
                              Thread.sleep(3000);
                              AC.jLabelXLeft(0,-450,100,45,slide4);
                              AC.jLabelXLeft(450,0, 100,45,slide5);
                              count = 4;
                              break;
                        case 4:
                              Thread.sleep(3000);
                              AC.jLabelXLeft(0,-450,100,45,slide5);
                              AC.jLabelXLeft(450,0, 100,45,slide6);
                              count = 5;
                              break;
                        case 5:
                              Thread.sleep(3000);
                              AC.jLabelXRight(-450,0,100,45,slide5);
                              AC.jLabelXRight(0,450, 100,45,slide6);
                              count = 6;
                              break;
                        case 6:
                              Thread.sleep(3000);
                              AC.jLabelXRight(-450,0,100,45,slide4);
                              AC.jLabelXRight(0,450, 100,45,slide5);
                              count = 7;
                              break;
                              
                        case 7:
                              Thread.sleep(3000);
                              AC.jLabelXRight(-450,0 ,100,45,slide3);
                              AC.jLabelXRight(0,450, 100,45,slide4);
                              count = 8;
                              break;
                        case 8:
                              Thread.sleep(3000);
                              AC.jLabelXRight(-450,0,100,45,slide2);
                              AC.jLabelXRight(0,450, 100,45,slide3);
                              count = 9;
                              break;
                        case 9:
                              Thread.sleep(3000);
                              AC.jLabelXRight(-450,0,100,45,slide1);
                              AC.jLabelXRight(0,450, 100,45,slide2);
                              count = 0;
                              break;
                      }
                  }
              
              }
              catch(Exception e){}
          }
        
        }.start();
    
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        slide1 = new javax.swing.JLabel();
        slide2 = new javax.swing.JLabel();
        slide3 = new javax.swing.JLabel();
        slide4 = new javax.swing.JLabel();
        slide5 = new javax.swing.JLabel();
        slide6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HomePage");
        setForeground(new java.awt.Color(102, 102, 102));
        setLocation(new java.awt.Point(0, 0));
        setPreferredSize(new java.awt.Dimension(450, 450));
        setResizable(false);
        setSize(new java.awt.Dimension(450, 450));
        getContentPane().setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/regB.png"))); // NOI18N
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel1MouseMoved(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 175, 150, 125);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/authG.png"))); // NOI18N
        jLabel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel2MouseMoved(evt);
            }
        });
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel2);
        jLabel2.setBounds(150, 175, 150, 125);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/recB.png"))); // NOI18N
        jLabel3.setToolTipText("");
        jLabel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel3MouseMoved(evt);
            }
        });
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel3);
        jLabel3.setBounds(300, 175, 150, 125);

        slide1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/sa.jpg"))); // NOI18N
        slide1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                slide1MouseMoved(evt);
            }
        });
        getContentPane().add(slide1);
        slide1.setBounds(0, 0, 450, 299);

        slide2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/sa1.jpg"))); // NOI18N
        slide2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                slide2MouseMoved(evt);
            }
        });
        getContentPane().add(slide2);
        slide2.setBounds(450, 0, 450, 299);

        slide3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/sa2.jpg"))); // NOI18N
        slide3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                slide3MouseMoved(evt);
            }
        });
        getContentPane().add(slide3);
        slide3.setBounds(450, 0, 450, 299);

        slide4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/sa3.jpg"))); // NOI18N
        slide4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                slide4MouseMoved(evt);
            }
        });
        getContentPane().add(slide4);
        slide4.setBounds(450, 0, 450, 299);

        slide5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/sa4.jpg"))); // NOI18N
        slide5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                slide5MouseMoved(evt);
            }
        });
        getContentPane().add(slide5);
        slide5.setBounds(450, 0, 450, 299);

        slide6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/sa5.jpg"))); // NOI18N
        slide6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                slide6MouseMoved(evt);
            }
        });
        getContentPane().add(slide6);
        slide6.setBounds(450, 0, 450, 300);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/diaB.png"))); // NOI18N
        jLabel4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel4MouseMoved(evt);
            }
        });
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel4);
        jLabel4.setBounds(0, 300, 150, 125);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/clinicG.png"))); // NOI18N
        jLabel5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel5MouseMoved(evt);
            }
        });
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel5);
        jLabel5.setBounds(150, 300, 150, 125);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/senB.png"))); // NOI18N
        jLabel6.setToolTipText("");
        jLabel6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel6MouseMoved(evt);
            }
        });
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel6);
        jLabel6.setBounds(300, 300, 150, 125);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/bg.png"))); // NOI18N
        getContentPane().add(jLabel7);
        jLabel7.setBounds(0, 300, 450, 125);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseMoved
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/regG.png")));
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/pics/authG.png")));
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/pics/recB.png")));
        jLabel4.setIcon(new ImageIcon(getClass().getResource("/pics/diaB.png")));
        jLabel5.setIcon(new ImageIcon(getClass().getResource("/pics/clinicG.png")));
        jLabel6.setIcon(new ImageIcon(getClass().getResource("/pics/senB.png")));
    }//GEN-LAST:event_jLabel1MouseMoved

    private void jLabel2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseMoved
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/regB.png")));
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/pics/authY.png")));
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/pics/recB.png")));
        jLabel4.setIcon(new ImageIcon(getClass().getResource("/pics/diaB.png")));
        jLabel5.setIcon(new ImageIcon(getClass().getResource("/pics/clinicG.png")));
        jLabel6.setIcon(new ImageIcon(getClass().getResource("/pics/senB.png")));
    
    }//GEN-LAST:event_jLabel2MouseMoved

    private void jLabel3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseMoved
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/regB.png")));
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/pics/authG.png")));
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/pics/recY.png")));
        jLabel4.setIcon(new ImageIcon(getClass().getResource("/pics/diaB.png")));
        jLabel5.setIcon(new ImageIcon(getClass().getResource("/pics/clinicG.png")));
        jLabel6.setIcon(new ImageIcon(getClass().getResource("/pics/senB.png")));
    
    }//GEN-LAST:event_jLabel3MouseMoved

    private void jLabel4MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseMoved
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/regB.png")));
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/pics/authG.png")));
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/pics/recB.png")));
        jLabel4.setIcon(new ImageIcon(getClass().getResource("/pics/diaY.png")));
        jLabel5.setIcon(new ImageIcon(getClass().getResource("/pics/clinicG.png")));
        jLabel6.setIcon(new ImageIcon(getClass().getResource("/pics/senB.png")));
    
    }//GEN-LAST:event_jLabel4MouseMoved

    private void jLabel5MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseMoved
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/regB.png")));
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/pics/authG.png")));
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/pics/recB.png")));
        jLabel4.setIcon(new ImageIcon(getClass().getResource("/pics/diaB.png")));
        jLabel5.setIcon(new ImageIcon(getClass().getResource("/pics/clinicY.png")));
        jLabel6.setIcon(new ImageIcon(getClass().getResource("/pics/senB.png")));
    
    }//GEN-LAST:event_jLabel5MouseMoved

    private void jLabel6MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseMoved
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/regB.png")));
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/pics/authG.png")));
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/pics/recB.png")));
        jLabel4.setIcon(new ImageIcon(getClass().getResource("/pics/diaB.png")));
        jLabel5.setIcon(new ImageIcon(getClass().getResource("/pics/clinicG.png")));
        jLabel6.setIcon(new ImageIcon(getClass().getResource("/pics/senY.png")));
    
    }//GEN-LAST:event_jLabel6MouseMoved

    private void slide1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slide1MouseMoved
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/regB.png")));
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/pics/authG.png")));
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/pics/recB.png")));
        
    }//GEN-LAST:event_slide1MouseMoved

    private void slide2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slide2MouseMoved
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/regB.png")));
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/pics/authG.png")));
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/pics/recB.png")));
        
    }//GEN-LAST:event_slide2MouseMoved

    private void slide3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slide3MouseMoved
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/regB.png")));
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/pics/authG.png")));
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/pics/recB.png")));
        
    }//GEN-LAST:event_slide3MouseMoved

    private void slide4MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slide4MouseMoved
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/regB.png")));
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/pics/authG.png")));
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/pics/recB.png")));
        
    }//GEN-LAST:event_slide4MouseMoved

    private void slide5MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slide5MouseMoved
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/regB.png")));
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/pics/authG.png")));
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/pics/recB.png")));
        
    }//GEN-LAST:event_slide5MouseMoved

    private void slide6MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slide6MouseMoved
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/pics/regB.png")));
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/pics/authG.png")));
        jLabel3.setIcon(new ImageIcon(getClass().getResource("/pics/recB.png")));
        
    }//GEN-LAST:event_slide6MouseMoved

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
//        try {
//            new Registration1().setVisible(true);
//        } catch (IOException ex) {
//            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
//        }
  //      this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
//        try {
//            new Recognition().setVisible(true);
//            
//        } catch (IOException ex) {
//            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
//        }
 //       this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        new Diagnosis().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        try {
            this.dispose();
            new Receiver().setVisible(true);
            
        } catch (IOException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        try {
            new PatientRecord().setVisible(true);
            this.dispose();
        } catch (IOException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
//        try {
//            this.dispose();
//            new DataSend().setVisible(true);
//            
//        } catch (IllegalArgumentException ex) {
//            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }//GEN-LAST:event_jLabel6MouseClicked

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
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel slide1;
    private javax.swing.JLabel slide2;
    private javax.swing.JLabel slide3;
    private javax.swing.JLabel slide4;
    private javax.swing.JLabel slide5;
    private javax.swing.JLabel slide6;
    // End of variables declaration//GEN-END:variables
}
