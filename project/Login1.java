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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author User 2
 */
public class Login1 extends javax.swing.JFrame {

    /**
     * Creates new form Login1
     */
    public Login1() {
        initComponents();
    }

    void writeName(String s) throws IOException{
        BufferedWriter writeval = new BufferedWriter(new FileWriter("docName.txt"));//"C:\\Users\\User 2\\Desktop\\clinicdata.txt"));
            writeval.write(s);
            writeval.close();

    }
    
    void writeName(String s, String t) throws IOException{
        BufferedWriter writeval = new BufferedWriter(new FileWriter("docInfo.txt"));//"C:\\Users\\User 2\\Desktop\\clinicdata.txt"));
            writeval.write(s+" "+t);
            writeval.close();

    }
 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userlbl = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        usertf = new javax.swing.JTextField();
        passlbl = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        passtf = new javax.swing.JPasswordField();
        chbox = new javax.swing.JCheckBox();
        regbtn = new javax.swing.JButton();
        sigbtn = new javax.swing.JButton();
        mainlbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LogIn");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userlbl.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        userlbl.setForeground(new java.awt.Color(49, 150, 222));
        userlbl.setText("Doctor ID");
        getContentPane().add(userlbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, -1, -1));

        jSeparator1.setBackground(new java.awt.Color(49, 150, 222));
        jSeparator1.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222), 2));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 220, 2));

        usertf.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        usertf.setBorder(null);
        usertf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usertfFocusGained(evt);
            }
        });
        getContentPane().add(usertf, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 168, 220, 25));

        passlbl.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        passlbl.setForeground(new java.awt.Color(49, 150, 222));
        passlbl.setText("Password");
        getContentPane().add(passlbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, -1, -1));

        jSeparator2.setBackground(new java.awt.Color(49, 150, 222));
        jSeparator2.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222), 2));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 220, 2));

        passtf.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        passtf.setBorder(null);
        passtf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passtfFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passtfFocusLost(evt);
            }
        });
        getContentPane().add(passtf, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 228, 220, 25));

        chbox.setForeground(new java.awt.Color(49, 150, 222));
        chbox.setText("show password");
        chbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chboxActionPerformed(evt);
            }
        });
        getContentPane().add(chbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(297, 257, -1, -1));

        regbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/regD.png"))); // NOI18N
        regbtn.setBorder(null);
        regbtn.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                regbtnMouseMoved(evt);
            }
        });
        regbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regbtnActionPerformed(evt);
            }
        });
        getContentPane().add(regbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 290, 100, 40));

        sigbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/signD.png"))); // NOI18N
        sigbtn.setBorder(null);
        sigbtn.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                sigbtnMouseMoved(evt);
            }
        });
        sigbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sigbtnActionPerformed(evt);
            }
        });
        getContentPane().add(sigbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 100, 40));

        mainlbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/Login.png"))); // NOI18N
        mainlbl.setBorder(null);
        mainlbl.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                mainlblMouseMoved(evt);
            }
        });
        getContentPane().add(mainlbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void usertfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usertfFocusGained

    }//GEN-LAST:event_usertfFocusGained

    private void passtfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passtfFocusGained

    }//GEN-LAST:event_passtfFocusGained

    private void passtfFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passtfFocusLost

    }//GEN-LAST:event_passtfFocusLost

    private void chboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chboxActionPerformed
        if(chbox.isSelected())
        passtf.setEchoChar((char)0);
        else
        passtf.setEchoChar('*');
    }//GEN-LAST:event_chboxActionPerformed

    private void regbtnMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_regbtnMouseMoved
        regbtn.setIcon(new ImageIcon(getClass().getResource("/pics/regC.png")));
    }//GEN-LAST:event_regbtnMouseMoved

    private void regbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regbtnActionPerformed
        new RegistrationD().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_regbtnActionPerformed

    private void sigbtnMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sigbtnMouseMoved
        sigbtn.setIcon(new ImageIcon(getClass().getResource("/pics/signC.png")));
    }//GEN-LAST:event_sigbtnMouseMoved

    private void sigbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sigbtnActionPerformed
        try
        {
            Connection con = DBConn.dataBase1();

            String sql = "SELECT * FROM Doctor WHERE doctorID = ? AND PASSWORD = ?";
            PreparedStatement ps4=con.prepareStatement (sql);
            ps4.setString(1,usertf.getText());
            ps4.setString(2,passtf.getText());
            ResultSet rs = ps4.executeQuery();
            if (rs.next()){
                JOptionPane.showMessageDialog(null, "Access Granted");
                new Login1().writeName(rs.getString("lastname"));
                writeName(rs.getString("doctorID"),rs.getString("lastname"));
                new HomePage().setVisible(true);
                this.dispose();
            }else
            JOptionPane.showMessageDialog(null, "Access Denied");
        }
        catch(SQLException e){} catch (ClassNotFoundException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Login1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sigbtnActionPerformed

    private void mainlblMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainlblMouseMoved
        regbtn.setIcon(new ImageIcon(getClass().getResource("/pics/regD.png")));
        sigbtn.setIcon(new ImageIcon(getClass().getResource("/pics/signD.png")));
    }//GEN-LAST:event_mainlblMouseMoved

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
            java.util.logging.Logger.getLogger(Login1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chbox;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel mainlbl;
    private javax.swing.JLabel passlbl;
    private javax.swing.JPasswordField passtf;
    private javax.swing.JButton regbtn;
    private javax.swing.JButton sigbtn;
    private javax.swing.JLabel userlbl;
    private javax.swing.JTextField usertf;
    // End of variables declaration//GEN-END:variables
}
