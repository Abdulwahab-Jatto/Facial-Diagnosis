package com.project;


//import csc409.Decoder;
//import csc409.Display;
//import static csc409.Sender.decodeBase64ToAESKey;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;


/**
 *
 * @author User 2
 */
public class Receiver extends javax.swing.JFrame {

    /**
     * Creates new form Receiver
     */
    public Receiver() throws IOException, IllegalArgumentException, NoSuchAlgorithmException, Exception {
        initComponents();
        docName = readName();
        soc();
        //embedMessage();
        
    }
    byte[] message = null;
    BufferedImage img = null;
    String docName;
    //SecretKey secKey = decodeBase64ToAESKey(sectxt.getText());//"ABDULWAHABAHMEDJATTO01");
    //SecretKey secKey;
    
    
    public String readName() throws IOException {
            String val = null;
            try {
                BufferedReader readval = new BufferedReader(new FileReader("docName.txt"));//C:\\Users\\User 2\\Desktop\\clinicdata.txt"));
                val = readval.readLine();
                //System.out.println(val);
                readval.close();
            } catch (FileNotFoundException ex) {
                
            }
            return val;
        }
    
    
    public void dis(BufferedImage im){
        ImageIcon image1 = new ImageIcon(im);
                Rectangle rec = dislbl.getBounds();
                Image scaledimage = image1.getImage().getScaledInstance(rec.width,rec.height,Image.SCALE_DEFAULT);
                image1 = new ImageIcon(scaledimage);
                dislbl.setIcon(image1);
    }
    public void soc() throws IOException, Exception{
        ServerSocket serverSocket = new ServerSocket(13085);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        //InputStream inputStream = socket.getInputStream();
        
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader (socket.getInputStream()));
          String fromclient = inFromClient.readLine();  
        JOptionPane.showMessageDialog(null, fromclient);
        
        System.out.println("Reading: " + System.currentTimeMillis());

        byte[] sizeAr = new byte[4];
        inputStream.read(sizeAr);
        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

        byte[] imageAr = new byte[size];
        inputStream.read(imageAr);

       BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
       //BufferedImage image = ImageIO.read(new File("C:\\Users\\User 2\\Documents\\g1.png"));
       img = image;
       //ImageIO.write(image, "png", new File("C:\\Users\\User 2\\Desktop\\test2.png"));
        //img = ImageIO.read(new File("C:\\Users\\User 2\\Desktop\\test2.png"));
        dis(img);
        //new Display(image).setVisible(true);
        System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
        //ImageIO.write(img, "png", new File("C:\\Users\\User 2\\Desktop\\test2.png"));
        
        
        DataInputStream dIn = new DataInputStream(socket.getInputStream());

int length = dIn.readInt();  
//byte[] message = null;// read length of incoming message
if(length>0) {
    message = new byte[length];
    dIn.readFully(message, 0, message.length); // read the message
}
System.out.println("Encrypted Text (Hex Form):"+bytesToHex(message));

//txt.setText(decryptText(message,secKey));
PrintWriter outToClient =new PrintWriter(socket.getOutputStream(),true);
outToClient.println("Data delivered to Dr."+ docName);
noticification();

        serverSocket.close();
    }
    
  public void noticification(){
        try {
            Clip clip ;
            URL url = this.getClass().getResource("/pics/notify.wav");
            
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
        
  
    private void embedMessage(String mess) throws Exception {
   //String mess = decryptText(message,secKey);
    embedMessage(img, mess);
    //dis(img);
    ImageIO.write(img, "png", new File("C:\\Users\\User 2\\Desktop\\test2.png"));
        
    this.validate();
    }

 private void embedMessage(BufferedImage img, String mess) {
    int messageLength = mess.length();
  
    int imageWidth = img.getWidth(), imageHeight = img.getHeight(),
       imageSize = imageWidth * imageHeight;
    if(messageLength * 8 + 32 > imageSize) {
      JOptionPane.showMessageDialog(this, "Message is too long for the chosen image",
          "Message too long!", JOptionPane.ERROR_MESSAGE);
      return;
      }
    embedInteger(img, messageLength, 0, 0);

    byte b[] = mess.getBytes();
    for(int i=0; i<b.length; i++)
       embedByte(img, b[i], i*8+32, 0);
   }

 private void embedInteger(BufferedImage img, int n, int start, int storageBit) {
    int maxX = img.getWidth(), maxY = img.getHeight(),
       startX = start/maxY, startY = start - startX*maxY, count=0;
    for(int i=startX; i<maxX && count<32; i++) {
       for(int j=startY; j<maxY && count<32; j++) {
         int rgb = img.getRGB(i, j), bit = getBitValue(n, count);
          rgb = setBitValue(rgb, storageBit, bit);
          img.setRGB(i, j, rgb);
         count++;
         }
       }
    }

 private void embedByte(BufferedImage img, byte b, int start, int storageBit) {
 int maxX = img.getWidth(), maxY = img.getHeight(),
       startX = start/maxY, startY = start - startX*maxY, count=0;
    for(int i=startX; i<maxX && count<8; i++) {
       for(int j=startY; j<maxY && count<8; j++) {
          int rgb = img.getRGB(i, j), bit = getBitValue(b, count);
          rgb = setBitValue(rgb, storageBit, bit);
          img.setRGB(i, j, rgb);
          count++;
          }
       }
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

     public static String decryptText(byte[] byteCipherText, SecretKey secKey)  { 

String s = null ;
        try { 
            
            
            // AES defaults to AES/ECB/PKCS5Padding in Java 7
            
            
            Cipher aesCipher = null;
            try {
                aesCipher = Cipher.getInstance("AES");
            } catch (NoSuchAlgorithmException ex) {
                System.out.println("Incoree1");
            } catch (NoSuchPaddingException ex) {
                System.out.println("Incoree2");
            }
            
            
            aesCipher.init(Cipher.DECRYPT_MODE, secKey);
            
            
            byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
            
            
            s = new String(bytePlainText);
            
            
        } catch (InvalidKeyException ex) {
            System.out.println("Incoree3");
        } catch (IllegalBlockSizeException ex) {
            System.out.println("Incoree4");
        } catch (BadPaddingException ex) {
            System.out.println("Incoree5");
        }

return s;
     } 

    
  private static String  bytesToHex(byte[] hash) { 


         return DatatypeConverter.printHexBinary(hash); 


     } 
  
     private void decodeMessage() {
    int len = extractInteger(img, 0, 0);
    System.out.println(len);
    byte b[] = new byte[len];
    for(int i=0; i<len; i++)
       b[i] = extractByte(img, i*8+32, 0);
   
    }

 private int extractInteger(BufferedImage img, int start, int storageBit) {
    int maxX = img.getWidth(), maxY = img.getHeight(),
       startX = start/maxY, startY = start - startX*maxY, count=0;
    int length = 0;
    for(int i=startX; i<maxX && count<32; i++) {
       for(int j=startY; j<maxY && count<32; j++) {
          int rgb = img.getRGB(i, j), bit = getBitValue(rgb, storageBit);
          length = setBitValue(length, count, bit);
          count++;
          }
       }
    return length;
    }

 private byte extractByte(BufferedImage img, int start, int storageBit) {
  int maxX = img.getWidth(), maxY = img.getHeight(),
       startX = start/maxY, startY = start - startX*maxY, count=0;
    byte b = 0;
    for(int i=startX; i<maxX && count<8; i++) {
       for(int j=startY; j<maxY && count<8; j++) {
          int rgb = img.getRGB(i, j), bit = getBitValue(rgb, storageBit);
          b = (byte)setBitValue(b, count, bit);
          count++;
          }
       }
   return b;
    }
private int getBitValue(int n, int location) {
    int v = n & (int) Math.round(Math.pow(2, location));
    return v==0?0:1;
    }

 private int setBitValue(int n, int location, int bit) {
    int toggle = (int) Math.pow(2, location), bv = getBitValue(n, location);
    if(bv == bit)
       return n;
    if(bv == 0 && bit == 1)
       n |= toggle;
    else if(bv == 1 && bit == 0)
    n ^= toggle;
    return n;
    }
 
 void writeName(String s, String t) throws IOException{
        BufferedWriter writeval = new BufferedWriter(new FileWriter("patInfo.txt"));//"C:\\Users\\User 2\\Desktop\\clinicdata.txt"));
            writeval.write(s+" "+t);
            writeval.close();

    }
    
 
 void displayText(String s) throws IOException{
     String[] result = s.split("\\s");
     pattxt.setText(result[0]);
     temtxt.setText(result[1]);
     blotxt.setText(result[2]);  
     weitxt.setText(result[3]);
     heitxt.setText(result[4]);
     bmitxt.setText(result[5]);
     nametxt.setText(result[6]);
     writeName(result[0],result[6]);

 }
 boolean validateKey(String s){
        return s.length()== 12 && s.matches("[a-zA-Z]+");
}

 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        blotxt = new javax.swing.JTextField();
        heitxt = new javax.swing.JTextField();
        weitxt = new javax.swing.JTextField();
        bmitxt = new javax.swing.JTextField();
        pattxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        temtxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        sectxt = new javax.swing.JTextField();
        dislbl = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        nametxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Received Clinic Data");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/decryptW.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
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
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 380, 100, 50));

        jSeparator1.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 100, 150, 2));

        jSeparator2.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, 150, 2));

        jSeparator3.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 310, 150, 2));

        jSeparator5.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 240, 150, 2));

        jSeparator4.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, 150, 2));

        blotxt.setBackground(new java.awt.Color(221, 221, 221));
        blotxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        blotxt.setBorder(null);
        blotxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                blotxtFocusLost(evt);
            }
        });
        blotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blotxtActionPerformed(evt);
            }
        });
        getContentPane().add(blotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 130, 150, 30));

        heitxt.setBackground(new java.awt.Color(221, 221, 221));
        heitxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        heitxt.setBorder(null);
        heitxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                heitxtFocusLost(evt);
            }
        });
        heitxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heitxtActionPerformed(evt);
            }
        });
        getContentPane().add(heitxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 210, 150, 30));

        weitxt.setBackground(new java.awt.Color(221, 221, 221));
        weitxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        weitxt.setBorder(null);
        weitxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                weitxtFocusLost(evt);
            }
        });
        weitxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weitxtActionPerformed(evt);
            }
        });
        getContentPane().add(weitxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, 150, 30));

        bmitxt.setBackground(new java.awt.Color(221, 221, 221));
        bmitxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        bmitxt.setBorder(null);
        bmitxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                bmitxtFocusLost(evt);
            }
        });
        bmitxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bmitxtActionPerformed(evt);
            }
        });
        getContentPane().add(bmitxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 280, 150, 30));

        pattxt.setBackground(new java.awt.Color(221, 221, 221));
        pattxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        pattxt.setBorder(null);
        pattxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                pattxtFocusLost(evt);
            }
        });
        pattxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pattxtActionPerformed(evt);
            }
        });
        getContentPane().add(pattxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 150, 30));

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(49, 150, 222));
        jLabel6.setText("Weight (kg)");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, -1, -1));

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(49, 150, 222));
        jLabel7.setText("Body Mass Index (BMI)");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, -1, -1));

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(49, 150, 222));
        jLabel2.setText("PatientID");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 100, -1));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(49, 150, 222));
        jLabel5.setText("Blood Pressure");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 110, -1, -1));

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(49, 150, 222));
        jLabel4.setText("Height (m)");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 190, -1, -1));

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(49, 150, 222));
        jLabel8.setText("Temperature (C)");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, -1, -1));

        jSeparator6.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 240, 150, 2));

        temtxt.setBackground(new java.awt.Color(221, 221, 221));
        temtxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        temtxt.setBorder(null);
        temtxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                temtxtFocusLost(evt);
            }
        });
        temtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temtxtActionPerformed(evt);
            }
        });
        getContentPane().add(temtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 210, 150, 30));

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(49, 150, 222));
        jLabel3.setText("Key");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 340, 40, -1));

        jSeparator7.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 360, 320, 2));

        sectxt.setBackground(new java.awt.Color(221, 221, 221));
        sectxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        sectxt.setBorder(null);
        sectxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                sectxtFocusLost(evt);
            }
        });
        sectxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectxtActionPerformed(evt);
            }
        });
        getContentPane().add(sectxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 330, 320, 30));
        getContentPane().add(dislbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 350, 440));

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(49, 150, 222));
        jLabel9.setText("Patient Name");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 50, -1, -1));

        jSeparator8.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, 150, 2));

        nametxt.setBackground(new java.awt.Color(221, 221, 221));
        nametxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        nametxt.setBorder(null);
        nametxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nametxtFocusLost(evt);
            }
        });
        nametxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nametxtActionPerformed(evt);
            }
        });
        getContentPane().add(nametxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 70, 150, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/b.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(!validateKey(sectxt.getText())){
        try {
            //this.decodeMessage();
            SecretKey secKey = decodeBase64ToAESKey(sectxt.getText());//("ABDULWAHABAHMEDJATTO01");
       //     secKey = secKeys;
       String mess = decryptText(message,secKey);
        embedMessage(mess);
   
           String s=new Decoder(img).decodeMessage();
            displayText(s);
            
        } catch (IllegalArgumentException ex) {
          System.out.println("Incorrect key");
        } catch (NoSuchAlgorithmException ex) {
          Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.out.println("NullPointerException1");
        }
        }
        else
        System.out.println("Check Key content");
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void blotxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_blotxtFocusLost
        
    }//GEN-LAST:event_blotxtFocusLost

    private void blotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_blotxtActionPerformed

    private void heitxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_heitxtFocusLost
        
    }//GEN-LAST:event_heitxtFocusLost

    private void heitxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heitxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_heitxtActionPerformed

    private void weitxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_weitxtFocusLost
        
    }//GEN-LAST:event_weitxtFocusLost

    private void weitxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weitxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_weitxtActionPerformed

    private void bmitxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bmitxtFocusLost
        
    }//GEN-LAST:event_bmitxtFocusLost

    private void bmitxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bmitxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bmitxtActionPerformed

    private void pattxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pattxtFocusLost
        
    }//GEN-LAST:event_pattxtFocusLost

    private void pattxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pattxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pattxtActionPerformed

    private void temtxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_temtxtFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_temtxtFocusLost

    private void temtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_temtxtActionPerformed

    private void sectxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sectxtFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_sectxtFocusLost

    private void sectxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sectxtActionPerformed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/decryptB.png")));
    }//GEN-LAST:event_jButton1MousePressed

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/decryptW.png")));
    }//GEN-LAST:event_jButton1MouseReleased

    private void nametxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nametxtFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_nametxtFocusLost

    private void nametxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nametxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nametxtActionPerformed

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
            java.util.logging.Logger.getLogger(Receiver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Receiver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Receiver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Receiver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Receiver().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField blotxt;
    private javax.swing.JTextField bmitxt;
    private javax.swing.JLabel dislbl;
    private javax.swing.JTextField heitxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTextField nametxt;
    private javax.swing.JTextField pattxt;
    private javax.swing.JTextField sectxt;
    private javax.swing.JTextField temtxt;
    private javax.swing.JTextField weitxt;
    // End of variables declaration//GEN-END:variables
}
