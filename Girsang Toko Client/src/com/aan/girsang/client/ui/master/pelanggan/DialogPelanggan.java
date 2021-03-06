/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.master.pelanggan;

import com.aan.girsang.api.model.constant.MasterRunningNumberEnum;
import com.aan.girsang.api.model.master.Pelanggan;
import com.aan.girsang.api.util.TextComponentUtils;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.frame.FrameUtama;

public class DialogPelanggan extends javax.swing.JDialog {
    
    private Pelanggan pelanggan;

    /**
     * Creates new form DialogPelanggan
     */
    public DialogPelanggan() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        initListener();
        isiCombo();
        TextComponentUtils.setAutoUpperCaseText(30, txtNamaPelanggan);
        TextComponentUtils.setAutoUpperCaseText(20, txtTempatLahir);
        TextComponentUtils.setAutoUpperCaseText(20, txtKota);
        TextComponentUtils.setAutoUpperCaseText(50, txtProvinsi);
        TextComponentUtils.setCurrency(txtDisc);
        TextComponentUtils.setCurrency(txtMaxPiutang);
        TextComponentUtils.setCurrency(txtSaldoPiutang);
        TextComponentUtils.setCurrency(txtPoint);
        
    }
    public Pelanggan showDialog(Pelanggan p, String tilte){
        if(p==null){
            clear();
        }else{
            loadModelToForm(p);
        }
        pack();
        setTitle(tilte);
        setLocationRelativeTo(null);
        setVisible(true);
        return this.pelanggan;
    }
    private void isiCombo(){
        cboAgama.removeAllItems();
        cboAgama.addItem("ISLAM");
        cboAgama.addItem("KRISTEN KATOLIK");
        cboAgama.addItem("KRISTEN PROTESTAN");
        cboAgama.addItem("HINDU");
        cboAgama.addItem("BUDHA");
        cboAgama.addItem("KONGHUCU");
        
        CboJenisKelamin.removeAllItems();
        CboJenisKelamin.addItem("LAKI - LAKI");
        CboJenisKelamin.addItem("PEREMPUAN");
    }
    private void clear(){
        txtId.setText(ClientLauncher.getConstantService().ambilBerikutnya(MasterRunningNumberEnum.PELANGGAN));
        txtNamaPelanggan.setText("");
        txtTempatLahir.setText("");
        jdcTanggalLahir.setDate(null);
        txtAlamat.setText("");
        txtKota.setText("");
        txtProvinsi.setText("");
        txtKodePos.setText("");
        txtHP1.setText("");
        txtHP2.setText("");
        txtTelepon.setText("");
        txtDisc.setText("0");
        cboAgama.setSelectedItem(null);
        CboJenisKelamin.setSelectedItem(null);
        
        jcbMaxPiutang.setSelected(false);
        txtMaxPiutang.setText("0");
        txtSaldoPiutang.setText("0");
        
        txtPoint.setText("0");
        
        jdcTanggalLahir.setDateFormatString("dd MMMM yyyy");
    }
    private void loadModelToForm(Pelanggan p){
        txtId.setText(p.getIdPelanggan());
        txtNamaPelanggan.setText(p.getNama());
        txtTempatLahir.setText(p.getTempatLahir());
        jdcTanggalLahir.setDate(p.getTanggalLahir());
        txtAlamat.setText(p.getAlamat());
        txtKota.setText(p.getKota());
        txtProvinsi.setText(p.getProvinsi());
        txtKodePos.setText(p.getKodePos());
        txtHP1.setText(p.getCp1());
        txtHP2.setText(p.getCp2());
        txtTelepon.setText(p.getTelepon());
        txtDisc.setText(TextComponentUtils.formatNumber(p.getDisc()));
        cboAgama.setSelectedItem(p.getAgama());
        CboJenisKelamin.setSelectedItem(p.getJenisKelamin());
        
        jcbMaxPiutang.setSelected(p.getIsMaxPiutang());
        txtMaxPiutang.setText(TextComponentUtils.formatNumber(p.getMaxPiutang()));
        txtSaldoPiutang.setText(TextComponentUtils.formatNumber(p.getSaldoPiutang()));
        
        txtPoint.setText(TextComponentUtils.formatNumber(p.getPoint()));
    }
    private void loadFormToModel(){
        pelanggan.setIdPelanggan(txtId.getText());
        pelanggan.setNama(txtNamaPelanggan.getText());
        pelanggan.setTempatLahir(txtTempatLahir.getText());
        pelanggan.setTanggalLahir(jdcTanggalLahir.getDate());
        pelanggan.setAlamat(txtAlamat.getText());
        pelanggan.setKota(txtKota.getText());
        pelanggan.setProvinsi(txtProvinsi.getText());
        pelanggan.setKodePos(txtKodePos.getText());
        pelanggan.setCp1(txtHP1.getText());
        pelanggan.setCp2(txtHP2.getText());
        pelanggan.setTelepon(txtTelepon.getText());
        pelanggan.setDisc(Integer.valueOf(txtDisc.getText()));
        pelanggan.setAgama(cboAgama.getSelectedItem().toString());
        pelanggan.setJenisKelamin(CboJenisKelamin.getSelectedItem().toString());
        
        pelanggan.setIsMaxPiutang(jcbMaxPiutang.isSelected());
        pelanggan.setMaxPiutang(TextComponentUtils.parseNumberToBigDecimal(txtMaxPiutang.getText()));
        pelanggan.setSaldoPiutang(TextComponentUtils.parseNumberToBigDecimal(txtSaldoPiutang.getText()));
        
        pelanggan.setPoint(Integer.valueOf(txtPoint.getText()));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtNamaPelanggan = new javax.swing.JTextField();
        CboJenisKelamin = new javax.swing.JComboBox<>();
        txtTempatLahir = new javax.swing.JTextField();
        jdcTanggalLahir = new com.toedter.calendar.JDateChooser();
        cboAgama = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        txtKota = new javax.swing.JTextField();
        txtProvinsi = new javax.swing.JTextField();
        txtKodePos = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtHP1 = new javax.swing.JTextField();
        txtHP2 = new javax.swing.JTextField();
        txtTelepon = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtDisc = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jcbMaxPiutang = new javax.swing.JCheckBox();
        txtMaxPiutang = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSaldoPiutang = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtPoint = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.title")); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel5.text")); // NOI18N

        jLabel9.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel9.text")); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel6.text")); // NOI18N

        txtId.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.txtId.text")); // NOI18N

        txtNamaPelanggan.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.txtNamaPelanggan.text")); // NOI18N

        CboJenisKelamin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtTempatLahir.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.txtTempatLahir.text")); // NOI18N

        cboAgama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtAlamat.setColumns(20);
        txtAlamat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        txtAlamat.setLineWrap(true);
        txtAlamat.setRows(5);
        jScrollPane1.setViewportView(txtAlamat);

        txtKota.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.txtKota.text")); // NOI18N

        txtProvinsi.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.txtProvinsi.text")); // NOI18N

        txtKodePos.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.txtKodePos.text")); // NOI18N

        jLabel10.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel10.text")); // NOI18N

        jLabel1.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel3.text")); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel7.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel4.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel8.text")); // NOI18N

        jLabel12.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel12.text")); // NOI18N

        jLabel13.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel13.text")); // NOI18N

        jLabel14.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel14.text")); // NOI18N

        txtHP1.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.txtHP1.text")); // NOI18N

        txtHP2.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.txtHP2.text")); // NOI18N

        txtTelepon.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.txtTelepon.text")); // NOI18N

        jLabel15.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel15.text")); // NOI18N

        txtDisc.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.txtDisc.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CboJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdcTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboAgama, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKota, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProvinsi, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKodePos, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDisc, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelepon)
                            .addComponent(txtHP2, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(txtHP1))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(CboJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jdcTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(cboAgama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtProvinsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtKodePos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtHP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtHP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtDisc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        jcbMaxPiutang.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jcbMaxPiutang.text")); // NOI18N

        txtMaxPiutang.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.txtMaxPiutang.text")); // NOI18N

        jLabel11.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel11.text")); // NOI18N

        txtSaldoPiutang.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.txtSaldoPiutang.text")); // NOI18N

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tabel);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbMaxPiutang)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSaldoPiutang, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(txtMaxPiutang))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbMaxPiutang)
                    .addComponent(txtMaxPiutang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtSaldoPiutang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N

        jLabel16.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jLabel16.text")); // NOI18N

        txtPoint.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.txtPoint.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(433, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtPoint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(325, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.jPanel3.TabConstraints.tabTitle"), jPanel3); // NOI18N

        btnSimpan.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.btnSimpan.text")); // NOI18N

        btnBatal.setText(org.openide.util.NbBundle.getMessage(DialogPelanggan.class, "DialogPelanggan.btnBatal.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnBatal))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initListener(){
        btnBatal.addActionListener((ae) -> {
            pelanggan = null;
            dispose();
        });
        btnSimpan.addActionListener((ae) -> {
            pelanggan = new Pelanggan();
            loadFormToModel();
            dispose();
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboJenisKelamin;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cboAgama;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JCheckBox jcbMaxPiutang;
    private com.toedter.calendar.JDateChooser jdcTanggalLahir;
    private javax.swing.JTable tabel;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtDisc;
    private javax.swing.JTextField txtHP1;
    private javax.swing.JTextField txtHP2;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtKodePos;
    private javax.swing.JTextField txtKota;
    private javax.swing.JTextField txtMaxPiutang;
    private javax.swing.JTextField txtNamaPelanggan;
    private javax.swing.JTextField txtPoint;
    private javax.swing.JTextField txtProvinsi;
    private javax.swing.JTextField txtSaldoPiutang;
    private javax.swing.JTextField txtTelepon;
    private javax.swing.JTextField txtTempatLahir;
    // End of variables declaration//GEN-END:variables
}
