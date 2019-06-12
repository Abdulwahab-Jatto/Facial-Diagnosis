/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

//import static csc409.AESEncryption.decodeBase64ToAESKey;
//import static csc409.AESEncryption.encryptText;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.net.*;
import java.security.InvalidKeyException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 *
 * @author User 2
 */
public class Sender extends javax.swing.JFrame {

    /**
     * Creates new form Sender
     */
    public Sender() throws IOException, IllegalArgumentException, NoSuchAlgorithmException, Exception {
        
        initComponents();
        text = readData();
        nurName = readName();
        //SmsSender();
        //cipherText = encryptText(text, secKey);
        //send();
    }
    
    String text,nurName;
    //SecretKey secKey = decodeBase64ToAESKey(sec);//"ABDULWAHABAHMEDJATTO01");;
    byte[] cipherText; 
    
    public  void send() throws NoSuchAlgorithmException, Exception {
        try {//169.254.193.242
            Socket socket = new Socket("localhost", 13085);
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(),true);
            outToServer.println("Data sent by Nurse "+nurName);
            
            OutputStream outputStream = socket.getOutputStream();
            //OutputStream outputStream1 = socket.getOutputStream();
            BufferedImage image = ImageIO.read(new File("test1.jpg"));
            //byte[]msg = {95, -35, -113, -9, -70, -94, 86, 93, -47, -1, 23, 7, 72, -59, -59, 59, -96, 122, -81, 11, -17, -49, -84, -68, 68, -107, -76, -94, -25, -106, -13, 47};
            byte[]msg = cipherText;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            
            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            outputStream.write(size);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
            
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
            
            dOut.writeInt(msg.length); // write length of the message
            dOut.write(msg);
            dOut.flush();
            System.out.println("Flushed: " + System.currentTimeMillis());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String FromServer = inFromServer.readLine();
           
            noticification();
            JOptionPane.showMessageDialog(null, FromServer);
            
            System.out.println("Testing!!!!");
//            while(true){
//                if(testConnection()){
//                    System.out.println("Send SMS");
//                     //smsSender();
//                    break;
//                }else
//                   JOptionPane.showMessageDialog(null, "Please connect to internet"); 
//                    
//            }
            Thread.sleep(12000);
            System.out.println("Closing: " + System.currentTimeMillis());
            socket.close();
        } catch (IOException ex) {
            System.out.println("Please check your IP address or data not delivered to the destination");
        } catch (InterruptedException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
   
    
    
    boolean testConnection(){
        Socket soc = new Socket();
        InetSocketAddress addr = new InetSocketAddress("www.google.com",80);
        try {
            soc.connect(addr,3000);
            return true;
        } catch (IOException ex) {
            return false;
                }
        finally{
            try{
                soc.close();
            }
            catch(Exception e){}
        }
        
    }
    
    public void noticification(){
        try {
            Clip clip ;
            URL url = this.getClass().getResource("/pics/notify1.wav");
            
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
} 
  
    
    public String readData() throws IOException {
            String val = null;
            try {
                BufferedReader readval = new BufferedReader(new FileReader("clinicdata.txt"));//C:\\Users\\User 2\\Desktop\\clinicdata.txt"));
                val = readval.readLine();
                //System.out.println(val);
                readval.close();
            } catch (FileNotFoundException ex) {
                
            }
            return val;
        }
    
    public String readName() throws IOException {
            String val = null;
            try {
                BufferedReader readval = new BufferedReader(new FileReader("nurName.txt"));//C:\\Users\\User 2\\Desktop\\clinicdata.txt"));
                val = readval.readLine();
                //System.out.println(val);
                readval.close();
            } catch (FileNotFoundException ex) {
                
            }
            return val;
        }
    
    
    public static SecretKey decodeBase64ToAESKey(final String encodedKey)
        throws IllegalArgumentException, NoSuchAlgorithmException {
    try {
        // throws IllegalArgumentException - if src is not in valid Base64
        // scheme
        final byte[] keyData = Base64.getDecoder().decode(encodedKey);
        final int keysize = keyData.length * Byte.SIZE;
        System.out.println(keysize);
        // this should be checked by a SecretKeyFactory, but that doesn't exist for AES
        switch (keysize) {
        case 128:
        case 192:
        case 256:
            break;
        default:
            throw new IllegalArgumentException("Invalid key size for AES: " + keysize);
        }

        if (Cipher.getMaxAllowedKeyLength("AES") < keysize) {
            // this may be an issue if unlimited crypto is not installed
            throw new IllegalArgumentException("Key size of " + keysize
                    + " not supported in this runtime");
        }

        // throws IllegalArgumentException - if key is empty
        final SecretKeySpec aesKey = new SecretKeySpec(keyData, "AES");
        return aesKey;
    } catch (final NoSuchAlgorithmException e) {
        // AES functionality is a requirement for any Java SE runtime
        throw new IllegalStateException(
                "AES should always be present in a Java SE runtime", e);
    }
}

     public static byte[] encryptText(String plainText,SecretKey secKey){ 


        try {
            // AES defaults to AES/ECB/PKCS5Padding in Java 7
            
            
            Cipher aesCipher = Cipher.getInstance("AES");
            
            
            aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
            
            
            byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
            System.out.println(Arrays.toString(byteCipherText));
             
            return byteCipherText;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            System.out.println("Key too long 22 characters required");
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;


     } 

boolean validateKey(String s){
        return s.length()== 12 && s.matches("[a-zA-Z]+");
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        sectxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        iptxt = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        phonetxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(49, 150, 222));
        jLabel4.setText("<html><center> IP Address</center>");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 414, -1, -1));

        jSeparator5.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 436, 190, 2));

        sectxt.setBackground(new java.awt.Color(221, 221, 221));
        sectxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectxtActionPerformed(evt);
            }
        });
        getContentPane().add(sectxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 410, 190, -1));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(49, 150, 222));
        jLabel5.setText("<html><center>Key</center>");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 414, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/test1.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/sendW.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 455, 80, 40));

        iptxt.setBackground(new java.awt.Color(221, 221, 221));
        iptxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iptxtActionPerformed(evt);
            }
        });
        getContentPane().add(iptxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, 140, -1));

        jSeparator6.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 436, 140, 2));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/search2.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 410, 25, 25));

        jSeparator7.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 480, 140, 2));

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(49, 150, 222));
        jLabel6.setText("<html><center> Phone<br>Number</center>");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 455, -1, -1));

        phonetxt.setBackground(new java.awt.Color(221, 221, 221));
        phonetxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phonetxtActionPerformed(evt);
            }
        });
        getContentPane().add(phonetxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 455, 140, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/bac.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(!validateKey(sectxt.getText())){
    SecretKey secKey = null;
        try {
            secKey = decodeBase64ToAESKey(sectxt.getText());
        } catch (IllegalArgumentException ex) {
            System.out.println("Incorrect key");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            cipherText = encryptText(text, secKey);
        } catch (Exception ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            send();
        } catch (Exception ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }}
    else
        System.out.println("Check Key content");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/sendB.png")));
    }//GEN-LAST:event_jButton1MousePressed

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/sendW.png")));
    }//GEN-LAST:event_jButton1MouseReleased

    private void iptxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iptxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_iptxtActionPerformed

    private void sectxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sectxtActionPerformed

    private void phonetxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phonetxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phonetxtActionPerformed

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
            java.util.logging.Logger.getLogger(Sender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Sender().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField iptxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField phonetxt;
    private javax.swing.JTextField sectxt;
    // End of variables declaration//GEN-END:variables
}
