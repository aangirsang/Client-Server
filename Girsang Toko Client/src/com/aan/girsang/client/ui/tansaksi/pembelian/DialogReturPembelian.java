/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.tansaksi.pembelian;

import com.aan.girsang.api.model.constant.TransaksiRunningNumberEnum;
import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.api.model.transaksi.Pembelian;
import com.aan.girsang.api.model.transaksi.PembelianDetail;
import com.aan.girsang.api.model.transaksi.ReturPembelian;
import com.aan.girsang.api.model.transaksi.ReturPembelianDetail;
import com.aan.girsang.api.util.BigDecimalRenderer;
import com.aan.girsang.api.util.DateRenderer;
import com.aan.girsang.api.util.IntegerRenderer;
import com.aan.girsang.api.util.POSTableCellRenderer;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.frame.FrameUtama;
import com.aan.girsang.client.ui.master.supplier.PilihSupplierDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author GIRSANG PC
 */
public class DialogReturPembelian extends javax.swing.JDialog {
    
    private Supplier supplier;
    private ReturPembelian returPembelian;
    private ReturPembelianDetail detail;
    private List<ReturPembelianDetail> daftarDetail = new ArrayList<>();
    private Pembelian pembelian;
    
    public DialogReturPembelian() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        initListener();
        tabel.setDefaultRenderer(Object.class, new POSTableCellRenderer());
        tabel.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        tabel.setDefaultRenderer(Date.class, new DateRenderer());
        tabel.setDefaultRenderer(Integer.class, new IntegerRenderer());
    }
    public ReturPembelian showDialog(ReturPembelian retur, String title){
        if(retur==null){
            txtNoRef.setText(ClientLauncher.getConstantService().ambilBerikutnya(TransaksiRunningNumberEnum.RETURPEMBELIAN));
            clear();
        }else{
            loadModelToForm(retur);
        }
        setTitle(title);

        setLocationRelativeTo(null);
        setVisible(true);
        return this.returPembelian;
    }
    private void clear(){
        txtDate.setValue(new Date());
        txtKodeSupplier.setText("");
        txtNamaSupplier.setText("");
    }
    private void loadModelToForm(ReturPembelian retur){
        
        txtNoRef.setText(retur.getNoRef());
        txtDate.setValue(retur.getTanggal());
        txtKodeSupplier.setText(retur.getSupplier().getId());
        txtNamaSupplier.setText(retur.getSupplier().getNamaSupplier());
        
        isiCombo(retur.getSupplier());
        cboPembelian.setSelectedItem(retur.getPembelian().getNoRef());
    }
    private void loadFormToModel(){
        returPembelian.setNoRef(txtNoRef.getText());
        returPembelian.setTanggal(new Date());
        returPembelian.setSupplier(supplier);
        returPembelian.setPembelian(pembelian);
        returPembelian.setPembuat(ClientLauncher.getPenggunaAktif());
    }
    private void isiCombo(Supplier s){
        List<Pembelian> p = ClientLauncher.getTransaksiService().cariSupplierPembelian(s);
        cboPembelian.removeAllItems();
        for(int i=0;i<p.size();i++){
            cboPembelian.addItem(p.get(i).getNoRef());
        }
        cboPembelian.setSelectedItem(null);
    }
    private void tampilDetails(Pembelian p){
        daftarDetail = new ArrayList<>();
        if (p != null) {
            returPembelian = new ReturPembelian();
            loadFormToModel();
            for(PembelianDetail beliDetail : p.getPembelianDetails()){
                detail = new ReturPembelianDetail();
                System.out.println(returPembelian.getNoRef());
                System.out.println(returPembelian.getPembelian().getNoRef());
                System.out.println(returPembelian.getSupplier().getNamaSupplier());
                System.out.println(returPembelian.getPembuat().getNamaLengkap());
                detail.setReturPembelian(returPembelian);
                detail.setBarang(beliDetail.getBarang());
                detail.setKuantitas(beliDetail.getKuantitas());
                detail.setSatuanPembelian(beliDetail.getSatuanPembelian());
                detail.setIsiReturPembelian(0);
                detail.setHargaBarang(beliDetail.getHargaBarang());
                daftarDetail.add(detail);
            }
            tabel.setModel(new TabelModel(daftarDetail));
        }
    }
    private class TabelModel extends AbstractTableModel {
        private final List<ReturPembelianDetail> daftarDetailRetur;
        BigDecimal hutangAkhir, pembayaran = new BigDecimal(0);
        public TabelModel(List<ReturPembelianDetail> daftarRetur) {
            this.daftarDetailRetur = daftarRetur;
        }
        @Override
        public int getRowCount() {
            return daftarDetailRetur.size();
        }
        @Override
        public int getColumnCount() {
            return 6;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0:return "Barang";
                case 1:return "QTY";
                case 2:return "Satuan Pembelian";
                case 3:return "Harga Beli";
                case 4:return "Jumlah Retur";
                case 5:return "Sub Total";
                default:return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            ReturPembelianDetail p = daftarDetailRetur.get(rowIndex);
            switch (colIndex) {
                case 0:return p.getBarang().getNamaBarang();
                case 1:return p.getKuantitas();
                case 2:return p.getSatuanPembelian();
                case 3:return p.getHargaBarang();
                case 4:return p.getIsiReturPembelian();
                case 5:return p.getSubTotal();
                default:return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 1:return Integer.class;
                case 3:return BigDecimal.class;
                case 4:return Integer.class;
                case 5:return BigDecimal.class;
                default:return String.class;
            }
        }
        @Override
        public boolean isCellEditable(int row, int columnIndex) {
            if (columnIndex == 4){
                return true;
            }else{
                return false;
            }
        }
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            ReturPembelianDetail p = daftarDetailRetur.get(rowIndex);
            switch (columnIndex) {
            case 4:
                if((Integer) aValue > p.getKuantitas()){
                    JOptionPane.showMessageDialog(null, "Retur Tidak Bisa Melebihi QTY Pembelian");
                    break;
                }
                p.setIsiReturPembelian((Integer) aValue);
                p.setSubTotal(p.getHargaBarang().
                        multiply(new BigDecimal(p.getIsiReturPembelian())));
                fireTableCellUpdated(rowIndex, columnIndex); // Total may also have changed
                fireTableCellUpdated(rowIndex, 5); // Total may also have changed
                break;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNoRef = new javax.swing.JTextField();
        txtDate = new javax.swing.JFormattedTextField();
        txtKodeSupplier = new javax.swing.JTextField();
        txtNamaSupplier = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnCari1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        txtPembelian = new javax.swing.JTextField();
        cboPembelian = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.jLabel3.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.jLabel4.text")); // NOI18N

        txtNoRef.setBackground(new java.awt.Color(255, 255, 204));
        txtNoRef.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.txtNoRef.text")); // NOI18N

        txtDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL))));

        txtKodeSupplier.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.txtKodeSupplier.text")); // NOI18N
        txtKodeSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeSupplierActionPerformed(evt);
            }
        });

        txtNamaSupplier.setBackground(new java.awt.Color(255, 255, 204));
        txtNamaSupplier.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.txtNamaSupplier.text")); // NOI18N

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/find-icon16.png"))); // NOI18N
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/find-icon16.png"))); // NOI18N
        btnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCari1ActionPerformed(evt);
            }
        });

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabel);

        btnSimpan.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.btnSimpan.text")); // NOI18N
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.btnBatal.text")); // NOI18N
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        txtPembelian.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.txtPembelian.text")); // NOI18N

        cboPembelian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNoRef, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(110, 110, 110)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCari1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(275, 275, 275)
                .addComponent(cboPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jLabel3)
                                .addComponent(txtNoRef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(btnCari)
                                .addComponent(txtKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCari1)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSimpan)
                            .addComponent(btnBatal)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(cboPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initListener() {
        
        txtNoRef.setEditable(false);
        txtNamaSupplier.setEditable(false);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Ya", "Tidak"};
                int PromptResult = JOptionPane.showOptionDialog(null, "Apakah Anda Yakin Ingin Membatalkan Editing", "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    returPembelian = null;
                    dispose();
                }
            }
        });
    }
    private void txtKodeSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeSupplierActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        String judul = "Pilih Supplier Pembelian";
        Supplier s = (Supplier) new PilihSupplierDialog().showDialog(judul);
        if (s != null) {
            txtKodeSupplier.setText(s.getId());
            txtNamaSupplier.setText(s.getNamaSupplier());
            supplier = new Supplier();
            supplier = ClientLauncher.getMasterService().cariIdSupplier(s.getId());
            isiCombo(s);
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCari1ActionPerformed
        // TODO add your handling code here:
        String judul ="Pilih Pembelian";
        Pembelian p = (Pembelian) new PilihPembelian().showDialog(judul);
        if(p!=null){
            txtPembelian.setText(p.getNoRef());
            pembelian = new Pembelian();
            pembelian = p;
            tampilDetails(p);
        }
    }//GEN-LAST:event_btnCari1ActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        returPembelian = null;
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCari1;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cboPembelian;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tabel;
    private javax.swing.JFormattedTextField txtDate;
    private javax.swing.JTextField txtKodeSupplier;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtNoRef;
    private javax.swing.JTextField txtPembelian;
    // End of variables declaration//GEN-END:variables
}
