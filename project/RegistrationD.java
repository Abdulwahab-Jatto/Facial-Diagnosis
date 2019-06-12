/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author User 2
 */
public class RegistrationD extends javax.swing.JFrame {

    /**
     * Creates new form RegistrationD
     */
    public RegistrationD() {
        initComponents();
        rndID();
        utf.setEditable(false);
    }

    public void rndID() {
        String contains = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(contains.charAt(rnd.nextInt(contains.length())));
        }
        utf.setText(sb.toString());
        //
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        canbtn = new javax.swing.JButton();
        regbtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lft = new javax.swing.JTextField();
        utf = new javax.swing.JTextField();
        ftf = new javax.swing.JTextField();
        ptf = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registration");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setBackground(new java.awt.Color(49, 150, 222));
        jSeparator1.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 150, 2));

        jSeparator2.setBackground(new java.awt.Color(49, 150, 222));
        jSeparator2.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 130, 150, 2));

        jSeparator3.setBackground(new java.awt.Color(49, 150, 222));
        jSeparator3.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 340, 2));

        jSeparator4.setBackground(new java.awt.Color(49, 150, 222));
        jSeparator4.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 340, 2));

        canbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/canD.png"))); // NOI18N
        canbtn.setBorder(null);
        canbtn.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                canbtnMouseMoved(evt);
            }
        });
        canbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canbtnActionPerformed(evt);
            }
        });
        getContentPane().add(canbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 285, 80, 40));

        regbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/regiD.png"))); // NOI18N
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
        getContentPane().add(regbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 285, 80, 40));

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(49, 150, 222));
        jLabel2.setText("First Name");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(49, 150, 222));
        jLabel3.setText("Last Name");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, -1, -1));

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(49, 150, 222));
        jLabel4.setText("Doctor ID");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, -1, -1));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(49, 150, 222));
        jLabel5.setText("Password");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, -1, -1));

        lft.setBackground(new java.awt.Color(223, 218, 218));
        lft.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lft.setBorder(null);
        lft.setOpaque(true);
        getContentPane().add(lft, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 150, 30));

        utf.setBackground(new java.awt.Color(223, 218, 218));
        utf.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        utf.setBorder(null);
        utf.setOpaque(true);
        getContentPane().add(utf, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 340, 30));

        ftf.setBackground(new java.awt.Color(223, 218, 218));
        ftf.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        ftf.setBorder(null);
        ftf.setOpaque(true);
        getContentPane().add(ftf, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 150, 30));

        ptf.setBackground(new java.awt.Color(223, 218, 218));
        ptf.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        ptf.setBorder(null);
        getContentPane().add(ptf, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 340, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/regis.png"))); // NOI18N
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel1MouseMoved(evt);
            }
        });
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void canbtnMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canbtnMouseMoved
        canbtn.setIcon(new ImageIcon(getClass().getResource("/pics/canC.png")));
    }//GEN-LAST:event_canbtnMouseMoved

    private void canbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canbtnActionPerformed
        new Login1().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_canbtnActionPerformed

    private void regbtnMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_regbtnMouseMoved
        regbtn.setIcon(new ImageIcon(getClass().getResource("/pics/regiC.png")));
    }//GEN-LAST:event_regbtnMouseMoved

    private void regbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regbtnActionPerformed
        try{
            Connection conn = DBConn.dataBase1();
            String sql = "INSERT INTO Doctor (doctorID,password,FIRSTNAME,LASTNAME) VALUES (?,?,?,?)";
            PreparedStatement ps=conn.prepareStatement (sql);
            ps.setString(1,utf.getText());
            ps.setString(2,ptf.getText());

            ps.setString(3,ftf.getText());
            ps.setString(4,lft.getText());
            
            int InsertRec = ps.executeUpdate();
            if( InsertRec > 0){
                JOptionPane.showMessageDialog(null, "Inserted into the Database");
                this.dispose();
                new Login1().setVisible(true);

            }
            else
            JOptionPane.showMessageDialog(null, "Inserted not in Database");
        }
        catch(SQLException e){} catch (ClassNotFoundException ex) {

        }
    }//GEN-LAST:event_regbtnActionPerformed

    private void jLabel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseMoved
        canbtn.setIcon(new ImageIcon(getClass().getResource("/pics/canD.png")));
        regbtn.setIcon(new ImageIcon(getClass().getResource("/pics/regiD.png")));
    }//GEN-LAST:event_jLabel1MouseMoved

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        new Login1().setVisible(true);
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
            java.util.logging.Logger.getLogger(RegistrationD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrationD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrationD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrationD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrationD().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton canbtn;
    private javax.swing.JTextField ftf;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField lft;
    private javax.swing.JPasswordField ptf;
    private javax.swing.JButton regbtn;
    private javax.swing.JTextField utf;
    // End of variables declaration//GEN-END:variables
}
