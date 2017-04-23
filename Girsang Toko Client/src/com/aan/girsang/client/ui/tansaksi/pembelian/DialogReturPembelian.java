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
import com.aan.girsang.api.util.TextComponentUtils;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.frame.FrameUtama;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author GIRSANG PC
 */
public class DialogReturPembelian extends javax.swing.JDialog {
    
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
        tabel.setColumnSelectionAllowed(true);
        tabel.setRowSelectionAllowed(true);
        
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());
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
        txtFaktur.setText("");
        txtPembelian.setText("");
        txtTanggalBeli.setValue(null);
        txtUangKembali.setText("");
        txtTotal.setText("0");
    }
    private void loadModelToForm(ReturPembelian retur){
        txtNoRef.setText(retur.getNoRef());
        txtDate.setValue(retur.getTanggal());
        txtKodeSupplier.setText(retur.getSupplier().getId());
        txtNamaSupplier.setText(retur.getSupplier().getNamaSupplier());
        txtTanggalBeli.setValue(retur.getPembelian().getTanggal());
        txtPembelian.setText(retur.getPembelian().getNoRef());
        txtTanggalBeli.setValue(retur.getPembelian().getTanggal());
        txtFaktur.setText(retur.getFaktur());
        txtAlasan.setText(retur.getAlasan());
        txtUangKembali.setText(TextComponentUtils.formatNumber(retur.getTotalRefund()));
        daftarDetail = new ArrayList<>();
        daftarDetail = retur.getReturPembelianDetails();
        
        pembelian = new Pembelian();
        pembelian = retur.getPembelian();
        tabel.setModel(new TabelModel(daftarDetail));
        kalkulasiTotal();
    }
    private void loadFormToModel(){
        returPembelian.setNoRef(txtNoRef.getText());
        returPembelian.setTanggal(new Date());
        returPembelian.setSupplier(pembelian.getSupplier());
        returPembelian.setPembelian(pembelian);
        returPembelian.setPembuat(ClientLauncher.getPenggunaAktif());
        returPembelian.setFaktur(txtFaktur.getText());
        returPembelian.setTotal(TextComponentUtils.parseNumberToBigDecimal(txtTotal.getText()));
        returPembelian.setTotalRefund(TextComponentUtils.parseNumberToBigDecimal(txtUangKembali.getText()));
        returPembelian.setAlasan(txtAlasan.getText());
        
        txtTanggalBeli.setValue(returPembelian.getPembelian().getTanggal());
        txtKodeSupplier.setText(returPembelian.getPembelian().getSupplier().getKotaSupplier());
        txtNamaSupplier.setText(returPembelian.getPembelian().getSupplier().getNamaSupplier());
    }
    private void loadFormToDomain(PembelianDetail detailBeli){
        detail.setReturPembelian(returPembelian);
        detail.setBarang(detailBeli.getBarang());
        detail.setKuantitas(0);
        detail.setSatuanPembelian(detailBeli.getSatuanPembelian());
        detail.setIsiReturPembelian(detailBeli.getIsiPembelian());
        detail.setHargaBarang(detailBeli.getHargaBarang()
                .divide(new BigDecimal(detailBeli.getIsiPembelian())));
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
                case 1:return "Satuan";
                case 2:return "Isi";
                case 3:return "Qty";
                case 4:return "Harga Satuan";
                case 5:return "Sub Total";
                default:return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            ReturPembelianDetail p = daftarDetailRetur.get(rowIndex);
            switch (colIndex) {
                case 0:return p.getBarang().getNamaBarang();
                case 1:return p.getSatuanPembelian();
                case 2:return p.getIsiReturPembelian();
                case 3:return p.getKuantitas();
                case 4:return p.getHargaBarang();
                case 5:return p.getSubTotal();
                default:return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 2:return Integer.class;
                case 4:return BigDecimal.class;
                case 3:return Integer.class;
                case 5:return BigDecimal.class;
                default:return String.class;
            }
        }
        @Override
        public boolean isCellEditable(int row, int columnIndex) {
            switch(columnIndex){
                case 2:return true;
                case 3:return true;
                case 4:return true;
                default:return false;
            }
        }
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            ReturPembelianDetail p = daftarDetailRetur.get(rowIndex);
            switch (columnIndex) {
            case 2:
                p.setIsiReturPembelian((Integer) aValue);
                p.setSubTotal(p.getHargaBarang().
                        multiply(new BigDecimal(p.getIsiReturPembelian())
                                .multiply(new BigDecimal(p.getKuantitas()))));
                fireTableCellUpdated(rowIndex, columnIndex); // Total may also have changed
                fireTableCellUpdated(rowIndex, 5); // Total may also have changed
                kalkulasiTotal();
                break;
            case 3:
                p.setKuantitas((Integer) aValue);
                p.setSubTotal(p.getHargaBarang().
                        multiply(new BigDecimal(p.getIsiReturPembelian())
                                .multiply(new BigDecimal(p.getKuantitas()))));
                fireTableCellUpdated(rowIndex, columnIndex); // Total may also have changed
                fireTableCellUpdated(rowIndex, 5); // Total may also have changed
                kalkulasiTotal();
                break;
            case 4:
                p.setHargaBarang((BigDecimal) aValue);
                p.setSubTotal(p.getHargaBarang().
                        multiply(new BigDecimal(p.getIsiReturPembelian())
                                .multiply(new BigDecimal(p.getKuantitas()))));
                fireTableCellUpdated(rowIndex, columnIndex); // Total may also have changed
                fireTableCellUpdated(rowIndex, 5); // Total may also have changed
                kalkulasiTotal();
                break;
            }
        }
    }
    private class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if(e.getID() == KeyEvent.KEY_PRESSED){
                if(e.getKeyChar()==KeyEvent.VK_ESCAPE){
                    returPembelian = null;
                    dispose();
                }
            }
            return false;
        }
    }
    private void kalkulasiTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ReturPembelianDetail p : daftarDetail) {
            total = total.add(p.getSubTotal());
        }
        txtTotal.setText(TextComponentUtils.formatNumber(total));
    }

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
        btnCariPembelian = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        txtPembelian = new javax.swing.JTextField();
        txtTanggalBeli = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtFaktur = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnBarang = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtUangKembali = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAlasan = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.jLabel3.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.jLabel4.text")); // NOI18N

        txtNoRef.setBackground(new java.awt.Color(255, 255, 204));
        txtNoRef.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.txtNoRef.text")); // NOI18N

        txtDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL))));

        txtKodeSupplier.setEditable(false);
        txtKodeSupplier.setBackground(new java.awt.Color(255, 255, 204));
        txtKodeSupplier.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.txtKodeSupplier.text")); // NOI18N

        txtNamaSupplier.setBackground(new java.awt.Color(255, 255, 204));
        txtNamaSupplier.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.txtNamaSupplier.text")); // NOI18N

        btnCariPembelian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/find-icon16.png"))); // NOI18N
        btnCariPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPembelianActionPerformed(evt);
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
        txtPembelian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPembelianKeyPressed(evt);
            }
        });

        txtTanggalBeli.setEditable(false);
        txtTanggalBeli.setBackground(new java.awt.Color(255, 255, 204));
        txtTanggalBeli.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd MMMM yyyy, HH:mm:ss"))));

        jLabel5.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.jLabel5.text")); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.jLabel6.text")); // NOI18N

        txtFaktur.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.txtFaktur.text")); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.jLabel7.text")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btnBarang.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.btnBarang.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBarang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBarang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.jLabel8.text")); // NOI18N

        jLabel9.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.jLabel9.text")); // NOI18N

        txtTotal.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.txtTotal.text")); // NOI18N

        txtUangKembali.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtUangKembali.setText(org.openide.util.NbBundle.getMessage(DialogReturPembelian.class, "DialogReturPembelian.txtUangKembali.text")); // NOI18N

        txtAlasan.setColumns(20);
        txtAlasan.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        txtAlasan.setLineWrap(true);
        txtAlasan.setRows(5);
        jScrollPane3.setViewportView(txtAlasan);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNoRef, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCariPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtTanggalBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUangKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoRef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtUangKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariPembelian)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTanggalBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSimpan)
                            .addComponent(btnBatal)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
        
        btnBarang.addActionListener((ae) -> {
            boolean input=true;
            if(pembelian==null){
                JOptionPane.showMessageDialog(null, "Data Pembelian Belum Terpilih");
            }else{
                PembelianDetail detailBeli = new PilihPembelianDetail().
                    showDialog(pembelian, "Pilih Barang Untuk Di Retur");
                if(detailBeli!=null){
                    if(daftarDetail!=null){
                        detail = new ReturPembelianDetail();
                        for(int i=0;i<daftarDetail.size();i++){
                            if(daftarDetail.get(i).getBarang().getPlu().
                                    equals(detailBeli.getBarang().getPlu())){
                                JOptionPane.showMessageDialog(null, 
                                        "Data Barang Sudah Terpilih");
                                input = false;
                            }
                        }
                    }
                    if(input==true){
                        loadFormToDomain(detailBeli);
                        daftarDetail.add(detail);
                        tabel.setModel(new TabelModel(daftarDetail));
                        kalkulasiTotal();
                    }
                }
            }
        });
    }
    private void btnCariPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPembelianActionPerformed
        // TODO add your handling code here:
        String judul ="Pilih Pembelian";
        Pembelian p = (Pembelian) new PilihPembelian().showDialog(judul);
        if(p!=null){
            txtPembelian.setText(p.getNoRef());
            pembelian = new Pembelian();
            pembelian = p;
            
            returPembelian = new ReturPembelian();
            loadFormToModel();
        }
    }//GEN-LAST:event_btnCariPembelianActionPerformed
    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        returPembelian = new ReturPembelian();
        loadFormToModel();
        returPembelian.setReturPembelianDetails(daftarDetail);
        dispose();
    }//GEN-LAST:event_btnSimpanActionPerformed
    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        returPembelian = null;
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed
    private void txtPembelianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPembelianKeyPressed
        // TODO add your handling code here:
        Pembelian p = ClientLauncher.getTransaksiService().cariPembelian(txtPembelian.getText());
        if(p!=null){
            txtPembelian.setText(p.getNoRef());
            pembelian = new Pembelian();
            pembelian = p;
            tampilDetails(p);
        }
    }//GEN-LAST:event_txtPembelianKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBarang;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCariPembelian;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tabel;
    private javax.swing.JTextArea txtAlasan;
    private javax.swing.JFormattedTextField txtDate;
    private javax.swing.JTextField txtFaktur;
    private javax.swing.JTextField txtKodeSupplier;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtNoRef;
    private javax.swing.JTextField txtPembelian;
    private javax.swing.JFormattedTextField txtTanggalBeli;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtUangKembali;
    // End of variables declaration//GEN-END:variables
}
