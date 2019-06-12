/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

//import static csc409.AESEncryption.decodeBase64ToAESKey;
//import static csc409.AESEncryption.encryptText;
import pics.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
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
public class DataSend extends javax.swing.JFrame {

    /**
     * Creates new form Sender
     */
    public DataSend() throws IOException, IllegalArgumentException, NoSuchAlgorithmException, Exception {
        
        initComponents();
        text = readData();
        nurName = readName();
        
        //cipherText = encryptText(text, secKey);
        //send();
    }
    String text,sec,nurName;
    //SecretKey secKey = decodeBase64ToAESKey(sec);//"ABDULWAHABAHMEDJATTO01");;
    byte[] cipherText; 
    
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
    
    
    public  void send() throws NoSuchAlgorithmException, Exception {
        try {//169.254.193.242
            //Socket socket = new Socket(ShowIP.detectIP(), 13085);
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
            
            while(true){
                if(testConnection()){
                    System.out.println("Send SMS");
                     //smsSender();
                     smsText.smsSender(phonetxt.getText(), nurName, sectxt.getText());
                    break;
                }else
                   JOptionPane.showMessageDialog(null, "Please connect to internet"); 
                    
            }
            
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

     public static byte[] encryptText(String plainText,SecretKey secKey) throws Exception{ 


         // AES defaults to AES/ECB/PKCS5Padding in Java 7 


         Cipher aesCipher = Cipher.getInstance("AES"); 


         aesCipher.init(Cipher.ENCRYPT_MODE, secKey); 


         byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes()); 


         return byteCipherText; 


     } 

boolean validateKey(String s){
        return s.length()== 12 && s.matches("[a-zA-Z]+");
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        sectxt = new javax.swing.JTextField();
        phonetxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Send Clinical Data");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\User 2\\Documents\\NetBeansProjects\\ACM\\test1.jpg")); // NOI18N

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

        sectxt.setBackground(new java.awt.Color(221, 221, 221));

        phonetxt.setBackground(new java.awt.Color(221, 221, 221));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(49, 150, 222));
        jLabel5.setText("<html><center>Key</center>");

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(49, 150, 222));
        jLabel6.setText("<html><center> Phone<br>Number</center>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sectxt, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(phonetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addGap(0, 21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sectxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phonetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    //this.sec = sectxt.getText();
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
            System.out.println("Please check your IP address or data not delivered to the destination");
        }
        try {
            send();
        } catch (Exception ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }}
    else
        JOptionPane.showMessageDialog(null,"Key should be Alphabet only");
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

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
        new HomePage().setVisible(true);
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
                    new DataSend().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(DataSend.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(DataSend.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(DataSend.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(DataSend.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField phonetxt;
    private javax.swing.JTextField sectxt;
    // End of variables declaration//GEN-END:variables
}
