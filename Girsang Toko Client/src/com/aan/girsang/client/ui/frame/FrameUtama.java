/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.frame;


import com.aan.girsang.api.model.security.Pengguna;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.popup.PopUpMenuMaster;
import com.aan.girsang.client.popup.PopUpMenuTransaksi;
import com.twmacinta.util.MD5;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

/**
 *
 * @author ITSUSAHBRO
 */
public class FrameUtama extends javax.swing.JFrame {
    
    private Pengguna pengguna;
    private static FrameUtama instance;
    public static FrameUtama getInstance() {
        return instance;
    }
    /**
     * Creates new form FrameUtama
     */
    public FrameUtama() {
        initComponents();
        initListener();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPopupMenu popUpMenuMaster = new JPopupMenu();
        JPopupMenu popUpMenuTransaksi = new JPopupMenu();
        PopUpMenuMaster MenuMaster = new PopUpMenuMaster(tabbedPane, popUpMenuMaster, btnMaster);
        PopUpMenuTransaksi MenuTransaksi = new PopUpMenuTransaksi(tabbedPane, popUpMenuTransaksi, btnTransaksi);
        clear();
        tabbedPane.setSelectedIndex(1);
    }
    
    public JTabbedPane getTabbedPane(){
        return tabbedPane;
    }
    private void clear(){
        lblUser.setText("----");
        txtUsername.setText("AANGIRSANG");
        txtPassword.setText("1");
    }
    public void jam(){
        Thread t = new Thread(() -> {
            while(true){
                lblJam.setText(new SimpleDateFormat(
                        "EEE, MMM dd yyyy HH:mm:ss")
                        .format(new Date()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        t.start();
    }
    private boolean validateLogin(){
        if(txtUsername.equals("") || txtPassword.getPassword().equals("")){
            JOptionPane.showMessageDialog(getInstance(),
                    "Isi Semua Data",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            String pass = new MD5(new String(txtPassword.getPassword())).asHex();
            pengguna = new Pengguna();
            pengguna = ClientLauncher.getSecurityService().cariUserNamePengguna(txtUsername.getText());
            if(pengguna==null){
                JOptionPane.showMessageDialog(getInstance(),
                        "Username Tidak Terdaftar",
                        "Error", JOptionPane.ERROR_MESSAGE);
                txtUsername.requestFocus();
                return false;
            }else if(pengguna!=null && pengguna.getPassword().equals(pass)){
                return true;
            }
        return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        btnMaster = new javax.swing.JButton();
        btnTransaksi = new javax.swing.JButton();
        tabbedPane = new javax.swing.JTabbedPane();
        panelHome = new javax.swing.JPanel();
        loginPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        btnLogin = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        lblUser = new javax.swing.JLabel();
        lblJam = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        btnMaster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Master 32.png"))); // NOI18N
        btnMaster.setText("Master");
        btnMaster.setFocusable(false);
        btnMaster.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMaster.setMaximumSize(new java.awt.Dimension(51, 57));
        btnMaster.setMinimumSize(new java.awt.Dimension(51, 57));
        btnMaster.setPreferredSize(new java.awt.Dimension(55, 65));
        btnMaster.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnMaster);

        btnTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Mutasi 32.png"))); // NOI18N
        btnTransaksi.setText("Transaksi");
        btnTransaksi.setFocusable(false);
        btnTransaksi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTransaksi.setPreferredSize(new java.awt.Dimension(55, 65));
        btnTransaksi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnTransaksi);

        javax.swing.GroupLayout panelHomeLayout = new javax.swing.GroupLayout(panelHome);
        panelHome.setLayout(panelHomeLayout);
        panelHomeLayout.setHorizontalGroup(
            panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1026, Short.MAX_VALUE)
        );
        panelHomeLayout.setVerticalGroup(
            panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 291, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Home", panelHome);

        jLabel1.setText("Username");

        jLabel2.setText("Password");

        txtUsername.setText("jTextField1");

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");

        txtPassword.setText("jPasswordField1");

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap(384, Short.MAX_VALUE)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(loginPanelLayout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(txtPassword))
                        .addGroup(loginPanelLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(loginPanelLayout.createSequentialGroup()
                            .addComponent(btnLogin)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(385, Short.MAX_VALUE))
        );

        loginPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBatal, btnLogin});

        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnBatal))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Login", loginPanel);

        lblUser.setText("USER");

        lblJam.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblJam.setText("USER");

        jToolBar2.setBorder(null);
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton2);

        btnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/logout-icon-32.png"))); // NOI18N
        btnKeluar.setText("Keluar");
        btnKeluar.setFocusable(false);
        btnKeluar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKeluar.setMaximumSize(new java.awt.Dimension(51, 57));
        btnKeluar.setMinimumSize(new java.awt.Dimension(51, 57));
        btnKeluar.setPreferredSize(new java.awt.Dimension(55, 65));
        btnKeluar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(btnKeluar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblJam, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabbedPane)
                        .addGap(2, 2, 2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(306, 306, 306)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJam)
                    .addComponent(lblUser))
                .addGap(2, 2, 2))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoginActionPerformed

    private void initListener(){
        btnLogin.addActionListener((ae) -> {
            if(validateLogin()){
                lblUser.setText(pengguna.getNamaLengkap());
                ClientLauncher.setPenggunaAktif(pengguna);
                tabbedPane.remove(loginPanel);
            }
        });
        btnBatal.addActionListener((ae) -> {
            System.exit(0);
        });
        //<editor-fold defaultstate="collapsed" desc="close Windows">
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Ya", "Tidak"};
                int PromptResult = JOptionPane.showOptionDialog(null, 
                        "Apakah Anda Yakin Ingin Menutup Aplikasi Ini?", 
                        "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    ClientLauncher.setPenggunaAktif(null);
                    System.exit(0);
                }
            }
        });//</editor-fold>
        btnKeluar.addActionListener((ae) -> {
           String ObjButtons[] = {"Ya", "Tidak"};
                int PromptResult = JOptionPane.showOptionDialog(null, 
                        "Apakah Anda Yakin Ingin Menutup Aplikasi Ini?", 
                        "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    ClientLauncher.setPenggunaAktif(null);
                    System.exit(0);
                }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnMaster;
    private javax.swing.JButton btnTransaksi;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lblJam;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JPanel panelHome;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
