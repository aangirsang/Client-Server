/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.tansaksi.penjualan;

import com.aan.girsang.api.model.transaksi.Penjualan;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.frame.FrameUtama;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.util.Date;
import javax.swing.ImageIcon;

/**
 *
 * @author GIRSANG PC
 */
public class DialogKasir extends javax.swing.JDialog {
    
    private Penjualan penjualan;

    /**
     * Creates new form DialogKasir
     */
    public DialogKasir() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
    }

    public Object showDialog(Penjualan p){
        if(p==null){
            clear();
        }
        setLocationRelativeTo(null);
        setVisible(true);
        return penjualan;
    }
    private void clear(){
        txtNoRef.setText("");
        txtKodePelanggan.setText("");
        txtNamaPelanggan.setText("UMUM");
        txtSubTotal.setText("0");
        txtDiscPersen.setText("0");
        txtDiscNilaiDiskon.setText("0");
        txtPembulatan.setText("0");
        txtTotal.setText("0");
        txtBayar.setText("0");
        
        txtNoRef.setEditable(false);
        txtNamaPelanggan.setEditable(false);
        txtSubTotal.setEditable(false);
        txtPembulatan.setEditable(false);
        txtTotal.setEditable(false);
        
        jdcTanggal.setDate(new Date());
        jdcTglTempo.setDate(new Date());
        jdcTanggal.setDateFormatString("dd MMMM yyyy");
        jdcTglTempo.setDateFormatString("dd MMMM yyyy");
        
        jcbKredit.setSelected(false);
        
        lblKetPelanggan.setText("Pelanggan Umum");
        lblKasir.setText(ClientLauncher.getPenggunaAktif().getNamaLengkap());
        lblKetSystem.setText("SYSTEM STANBY");
        lblTampilTotal.setText("0");
        ClientLauncher.jam(lblJam);
        
        cboLokasi.removeAllItems();
        cboLokasi.addItem("Toko");
        cboLokasi.addItem("Gudang");
        kredit();
    }
    
    private void kredit(){
        if (jcbKredit.isSelected()) {
            lblTempo.setVisible(true);
            jdcTglTempo.setVisible(true);
        } else {
            lblTempo.setVisible(false);
            jdcTglTempo.setVisible(false);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNoRef = new javax.swing.JTextField();
        jdcTanggal = new com.toedter.calendar.JDateChooser();
        txtKodePelanggan = new javax.swing.JTextField();
        txtNamaPelanggan = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        cboLokasi = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblKetSystem = new javax.swing.JLabel();
        lblTampilTotal = new javax.swing.JLabel();
        lblJam = new javax.swing.JLabel();
        lblKasir = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        lblKetPelanggan = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jcbKredit = new javax.swing.JCheckBox();
        lblTempo = new javax.swing.JLabel();
        jdcTglTempo = new com.toedter.calendar.JDateChooser();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        txtDiscPersen = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDiscNilaiDiskon = new javax.swing.JTextField();
        txtPembulatan = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtBayar = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnSimpan = new javax.swing.JButton();
        btnPending = new javax.swing.JButton();
        btnRecall = new javax.swing.JButton();
        btnSwitch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 255, 255));

        jLabel1.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel3.text")); // NOI18N

        txtNoRef.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.txtNoRef.text")); // NOI18N

        txtKodePelanggan.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.txtKodePelanggan.text")); // NOI18N

        txtNamaPelanggan.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.txtNamaPelanggan.text")); // NOI18N

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/find-icon16.png"))); // NOI18N
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        cboLokasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel4.text")); // NOI18N

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        lblKetSystem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblKetSystem.setForeground(new java.awt.Color(255, 255, 255));
        lblKetSystem.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblKetSystem.text")); // NOI18N

        lblTampilTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTampilTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTampilTotal.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblTampilTotal.text")); // NOI18N

        lblJam.setForeground(new java.awt.Color(255, 255, 255));
        lblJam.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblJam.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblJam.text")); // NOI18N

        lblKasir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblKasir.setForeground(new java.awt.Color(255, 255, 255));
        lblKasir.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblKasir.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblKasir.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblKetSystem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblJam, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblKasir, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblTampilTotal)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblKetSystem)
                        .addGap(18, 18, 18)
                        .addComponent(lblTampilTotal))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblJam)
                        .addGap(4, 4, 4)
                        .addComponent(lblKasir)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabel);

        lblKetPelanggan.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblKetPelanggan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblKetPelanggan.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblKetPelanggan.text")); // NOI18N
        lblKetPelanggan.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jcbKredit.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jcbKredit.text")); // NOI18N
        jcbKredit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbKreditActionPerformed(evt);
            }
        });

        lblTempo.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblTempo.text")); // NOI18N

        jdcTglTempo.setDateFormatString(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jdcTglTempo.dateFormatString")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcbKredit)
                .addGap(18, 18, 18)
                .addComponent(lblTempo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdcTglTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jcbKredit)
                    .addComponent(lblTempo)
                    .addComponent(jdcTglTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel8.text")); // NOI18N

        jLabel9.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel9.text")); // NOI18N

        jLabel10.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel10.text")); // NOI18N

        txtSubTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubTotal.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.txtSubTotal.text")); // NOI18N

        txtDiscPersen.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscPersen.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.txtDiscPersen.text")); // NOI18N

        jLabel11.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel11.text")); // NOI18N

        txtDiscNilaiDiskon.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscNilaiDiskon.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.txtDiscNilaiDiskon.text")); // NOI18N

        txtPembulatan.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPembulatan.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.txtPembulatan.text")); // NOI18N

        jLabel12.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel12.text")); // NOI18N

        jLabel13.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel13.text")); // NOI18N

        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.txtTotal.text")); // NOI18N

        txtBayar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtBayar.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.txtBayar.text")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(36, 36, 36)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPembulatan, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtDiscPersen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDiscNilaiDiskon)))
                        .addGap(154, 154, 154)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtDiscPersen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtDiscNilaiDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtPembulatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel5);

        btnSimpan.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.btnSimpan.text")); // NOI18N

        btnPending.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.btnPending.text")); // NOI18N

        btnRecall.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.btnRecall.text")); // NOI18N

        btnSwitch.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.btnSwitch.text")); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPending, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRecall, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSwitch, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnPending, btnRecall, btnSimpan, btnSwitch});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnPending)
                    .addComponent(btnRecall)
                    .addComponent(btnSwitch))
                .addGap(0, 0, 0))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnPending, btnRecall, btnSimpan, btnSwitch});

        jPanel8.add(jPanel7);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtNoRef, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(txtKodePelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lblKetPelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(cboLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNoRef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel2)
                            .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnCari)
                            .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKodePelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblKetPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cboLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCariActionPerformed

    private void jcbKreditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbKreditActionPerformed
        // TODO add your handling code here:
        kredit();
    }//GEN-LAST:event_jcbKreditActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnPending;
    private javax.swing.JButton btnRecall;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnSwitch;
    private javax.swing.JComboBox<String> cboLokasi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox jcbKredit;
    private com.toedter.calendar.JDateChooser jdcTanggal;
    private com.toedter.calendar.JDateChooser jdcTglTempo;
    private javax.swing.JLabel lblJam;
    private javax.swing.JLabel lblKasir;
    private javax.swing.JLabel lblKetPelanggan;
    private javax.swing.JLabel lblKetSystem;
    private javax.swing.JLabel lblTampilTotal;
    private javax.swing.JLabel lblTempo;
    private javax.swing.JTable tabel;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtDiscNilaiDiskon;
    private javax.swing.JTextField txtDiscPersen;
    private javax.swing.JTextField txtKodePelanggan;
    private javax.swing.JTextField txtNamaPelanggan;
    private javax.swing.JTextField txtNoRef;
    private javax.swing.JTextField txtPembulatan;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
