/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.tansaksi.penjualan;

import com.aan.girsang.api.model.master.Barang;
import com.aan.girsang.api.model.master.Pelanggan;
import com.aan.girsang.api.model.transaksi.Penjualan;
import com.aan.girsang.api.model.transaksi.PenjualanDetail;
import com.aan.girsang.api.util.BigDecimalRenderer;
import com.aan.girsang.api.util.IntegerRenderer;
import com.aan.girsang.api.util.TextComponentUtils;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.frame.FrameUtama;
import com.aan.girsang.client.ui.master.pelanggan.PilihPelanggan;
import java.awt.GraphicsEnvironment;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public class DialogKasir extends javax.swing.JDialog {
    
    private Penjualan penjualan;
    private PenjualanDetail detail;
    private Pelanggan pelanggan;
    private Barang barang;
    private List<PenjualanDetail> daftarDetail = new ArrayList<>();
    private List<Barang> daftarBarang;
    
    private Boolean isPanelBarang = false;

   
    public DialogKasir() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        tabel.setModel(new TabelModel(daftarDetail));
        tabel.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        tabel.setDefaultRenderer(Integer.class, new IntegerRenderer());
        initListener();
        setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());
        TextComponentUtils.setAutoUpperCaseText(100, txtCariNamaBarang);
        TextComponentUtils.setAutoUpperCaseText(10, txtKodePelanggan);
        TextComponentUtils.setCurrency(txtBayar);
        TextComponentUtils.setCurrency(txtDiscPersen);
        TextComponentUtils.setCurrency(txtDiscNilaiDiskon);
    }

    public Object showDialog(Penjualan p){
        if(p==null){
            clear();
            detailKosong();
        }else{
            clear();
            loadPenjualanToForm(p);
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
        lblJmlBarang.setText("");
        ClientLauncher.jam(lblJam);
        
        cboLokasi.removeAllItems();
        cboLokasi.addItem("Toko");
        cboLokasi.addItem("Gudang");
        
        panelBarang.setVisible(isPanelBarang);
        txtCariNamaBarang.setText("");

        kredit();
    }
    private void clearAll(){
        penjualan = null;
        detail = null;
        daftarDetail.removeAll(daftarDetail);
        pelanggan = null;
        barang = null;
        tabel.setModel(new TabelModel(daftarDetail));
        clear();
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
    private void tampilPelanggan(){
        txtKodePelanggan.setText(pelanggan.getIdPelanggan());
        txtNamaPelanggan.setText(pelanggan.getNama());
        lblKetPelanggan.setText("<html>"+pelanggan.getAlamat()+", "+pelanggan.getKota()+", "+pelanggan.getKodePos()+"<br>"+
                pelanggan.getCp1()+"<br>"+
                "Saldo Piutang = "+TextComponentUtils.formatNumber(pelanggan.getSaldoPiutang())+"</html>");
        txtDiscPersen.setText(String.valueOf(pelanggan.getDisc()));
        
        harga();
    }
    private void tampilBarang(Barang b){
        
        if(b!=null){
            BigDecimal hargaJual;
            if(pelanggan!=null){
                hargaJual = b.getHargaMember();
            }else{
                hargaJual = b.getHargaNormal();
            }
            lblPLU.setText(b.getPlu());
            lblNamaBarang.setText(b.getNamaBarang());
            lblSatuanBarang.setText(b.getSatuan());
            lblStokToko.setText(String.valueOf(b.getKalkulasiStokToko()));
            lblSatuanJualToko.setText(b.getSatuan());
            lblStokGudang.setText(String.valueOf(b.getKalkulasiStokGudang()));
            lblSatuanJualGudang.setText(b.getSatuan());
            lblHargaJual.setText(TextComponentUtils.formatNumber(hargaJual));
        } else{
            lblPLU.setText("");
            lblNamaBarang.setText("");
            lblSatuanBarang.setText("");
            lblStokToko.setText("");
            lblSatuanJualToko.setText("");
            lblStokGudang.setText("");
            lblSatuanJualGudang.setText("");
            lblHargaJual.setText("0");
        }
    }
    private void harga(){
        if(pelanggan!=null){
            for(PenjualanDetail p : daftarDetail){
                if(p.getBarang()!=null){
                    p.setHargaJual(p.getBarang().getHargaMember());
                    p.setSubTotal(p.getHargaJual().multiply(new BigDecimal(p.getKuantitas())));
                    tabel.tableChanged(new TableModelEvent(tabel
                            .getModel(),tabel.getSelectedRow()));
                }
            }
        }else{
            for(PenjualanDetail p : daftarDetail){
                if(p.getBarang()!=null){
                    p.setHargaJual(p.getBarang().getHargaNormal());
                    p.setSubTotal(p.getHargaJual().multiply(new BigDecimal(p.getKuantitas())));
                    tabel.tableChanged(new TableModelEvent(tabel
                            .getModel(),tabel.getSelectedRow()));
                }
            }
        }
        kalkulasiTotal();
    }
    public static BigDecimal round(BigDecimal d, int scale, boolean roundUp) {
        int mode = (roundUp) ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN;
        return d.setScale(scale, mode);
    }
    private void kalkulasiTotal(){
        BigDecimal subTotal, 
                totalFormated,
                diskonNilai, 
                diskonPersen, 
                pembulatan, 
                total, 
                tampil,
                bayar,
                seribu;
        seribu = new BigDecimal(1000);
        subTotal = new BigDecimal(0);
        Integer totalBarang = 0;
        for(int i=0;i<daftarDetail.size();i++){
            subTotal = subTotal.add(daftarDetail.get(i).getSubTotal());
            totalBarang = totalBarang + daftarDetail.get(i).getKuantitas();
        }
        
        if(txtDiscPersen.getText().equals("")){
            diskonPersen = new BigDecimal(0);
        }else{
            diskonPersen = TextComponentUtils.parseNumberToBigDecimal(txtDiscPersen.getText());
        }
        diskonNilai = subTotal
                .divide(new BigDecimal(100))
                .multiply(diskonPersen);
        total = subTotal.subtract(diskonNilai);
        totalFormated = total
                .divide(seribu).setScale(1, BigDecimal.ROUND_UP);
        totalFormated = totalFormated.multiply(seribu);
        pembulatan = totalFormated.subtract(total);
        
        if(txtBayar.getText().equals("")){
            bayar = new BigDecimal(0);
        }else{
            bayar = TextComponentUtils.parseNumberToBigDecimal(txtBayar.getText());
        }
        tampil = totalFormated.subtract(bayar);
        
        txtSubTotal.setText(TextComponentUtils.formatNumber(subTotal));
        txtDiscNilaiDiskon.setText(TextComponentUtils.formatNumber(diskonNilai));
        txtPembulatan.setText(TextComponentUtils.formatNumber(pembulatan));
        txtTotal.setText(TextComponentUtils.formatNumber(totalFormated));
        
        if(Double.parseDouble(TextComponentUtils.formatNumber(tampil))<=0){
            tampil = tampil.multiply(new BigDecimal(-1));
            lblKetSystem.setText("KEMBALI");
            lblTampilTotal.setText(TextComponentUtils.formatNumber(tampil));
        }else{
            lblKetSystem.setText("HUTANG");
            lblTampilTotal.setText(TextComponentUtils.formatNumber(tampil));
        }
        
        lblJmlBarang.setText(totalBarang + " Item");
    }
    private void detailKosong(){
        detail = new PenjualanDetail();
        detail.setBarang(null);
        detail.setSatuan("");
        daftarDetail.add(detail);
        tabel.setModel(new TabelModel(daftarDetail));
    }
    private void isiTabelBarang(){
        tblBarang.setModel(new TabelBarangModel(daftarBarang));

        tblBarang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblBarang.getColumnModel().getColumn(0).setPreferredWidth(80);//PLU
        tblBarang.getColumnModel().getColumn(1).setPreferredWidth(230);//Nama Barang
        tampilBarang(null);
    }
    private void loadFormToPenjualan(){
        BigDecimal piutangAwal = new BigDecimal(0);
        if(jcbKredit.isSelected()){
            if(new Double(txtTotal.getText()) >= new Double(txtBayar.getText())){
            piutangAwal = TextComponentUtils.parseNumberToBigDecimal(txtTotal.getText())
                    .subtract(TextComponentUtils.parseNumberToBigDecimal(txtBayar.getText()));
            }
        }
        penjualan = new Penjualan();
        penjualan.setNoRef(txtNoRef.getText());
        penjualan.setTanggal(new Date());
        penjualan.setPelanggan(pelanggan);
        penjualan.setLokasi(cboLokasi.getSelectedItem().toString());
        penjualan.setIsKredit(jcbKredit.isSelected());
        penjualan.setTanggalTempo(jdcTglTempo.getDate());
        penjualan.setSubTotal(TextComponentUtils
                .parseNumberToBigDecimal(txtSubTotal.getText()));
        penjualan.setDiscTotal(TextComponentUtils
                .parseNumberToBigDecimal(txtDiscNilaiDiskon.getText()));
        penjualan.setPembulatan(TextComponentUtils.parseNumberToBigDecimal(txtPembulatan.getText()));
        penjualan.setTotal(TextComponentUtils.parseNumberToBigDecimal(txtTotal.getText()));
        penjualan.setPiutangAwal(piutangAwal);
        penjualan.setKasir(ClientLauncher.getPenggunaAktif());
        
    }
    private void loadPenjualanToForm(Penjualan p){
        txtNoRef.setText(p.getNoRef());
        jdcTanggal.setDate(p.getTanggal());
        cboLokasi.setSelectedItem(p.getLokasi());
        jcbKredit.setSelected(p.getIsKredit());
        jdcTglTempo.setDate(p.getTanggalTempo());
        txtBayar.setText(TextComponentUtils.formatNumber(p.getBayar()));
        
        pelanggan = p.getPelanggan();
        if(pelanggan==null){
            txtNamaPelanggan.setText("UMUM");
            lblKetPelanggan.setText("Pelanggan Umum");
            txtDiscPersen.setText("0");
            jcbKredit.setSelected(false);
            tampilBarang(barang);
            harga();
        }else{
            tampilPelanggan();
            tampilBarang(barang);
        }
        daftarDetail = ClientLauncher.getTransaksiService().cariDetail(p);
        detailKosong();
        kalkulasiTotal();
        kredit();
    }
    private void addBarang(Barang b){
        boolean data = true;
        detail = new PenjualanDetail();
        loadFormToPenjualan();
        if(daftarDetail!=null){
        System.out.println(daftarDetail.size());
            for(PenjualanDetail p : daftarDetail){
                if(p.getBarang()!=null){
                    if(p.getBarang().getPlu().equals(b.getPlu())){
                        //data = true;
                        JOptionPane.showMessageDialog(null, 
                                "Data Barang Sudah Terpilih");
                        data = false;
                    }
                }else{
                    detail = p;
                }
            }
        }
        if(data==true){
            BigDecimal harga;
                    if(pelanggan==null){
                        harga = b.getHargaNormal();
                    }else{
                        harga = b.getHargaMember();
                    }
                    detail.setBarang(b);
                    detail.setSatuan(b.getSatuan());
                    detail.setKuantitas(0);
                    detail.setHargaJual(harga);
                    detail.setHpp(b.getHargaBeli());
                    detail.setSubTotal(new BigDecimal(0));

                    tabel.tableChanged(new TableModelEvent(tabel
                            .getModel(),tabel.getSelectedRow()));
        detailKosong();
        }
        kalkulasiTotal();
    }
    private Boolean validateSimpan(){
        boolean ckredit = jcbKredit.isSelected();
        Double sisa = Double.valueOf(TextComponentUtils.getValueFromTextNumber(txtSubTotal)) - 
                Double.valueOf(TextComponentUtils.getValueFromTextNumber(txtBayar));
        
        if(ckredit!=true && sisa>0){
            JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                     "Jumlah Pembayaran Tidak Boleh Kurang",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtBayar.requestFocus();
            return false;
        }
        if(txtSubTotal.getText().equals("0")){
            JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                     "Jumlah Barang Masih Kosong",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    private class TabelModel extends AbstractTableModel{
        private List<PenjualanDetail> listDetail;
        String columnNames[] = {
            "PLU", 
            "Nama Barang", 
            "Satuan", 
            "Harga Satuan", 
            "Qty", 
            "Sub Total"};
        public TabelModel(List<PenjualanDetail> listDetail){
            this.listDetail = listDetail;
        }
        @Override
        public int getRowCount() {
            return listDetail.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }
        @Override
        public String getColumnName(int column){
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            PenjualanDetail p = listDetail.get(rowIndex);
            switch(columnIndex){
                case 0:
                    if(p.getBarang()!=null){
                        return p.getBarang().getPlu();
                    }else{
                        return "";
                    }
                case 1:
                    if(p.getBarang()!=null){
                        return p.getBarang().getNamaBarang();
                    }else{
                        return "";
                    }
                case 2:return p.getSatuan();
                case 3:return p.getHargaJual();
                case 4:return p.getKuantitas();
                case 5:return p.getSubTotal();
                default:return "";
            }
            
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 3:return BigDecimal.class;
                case 4:return Integer.class;
                case 5:return BigDecimal.class;
                default:return String.class;
            }
        }
        @Override
        public boolean isCellEditable(int row, int columnIndex) {
            if (columnIndex == 4 ) {
                return true;
            } else {
                return false;
            }
        }
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            PenjualanDetail p = listDetail.get(rowIndex);
            switch(columnIndex){
                case 4 :
                    p.setKuantitas((Integer) aValue);
                    p.setSubTotal(p.getHargaJual()
                            .multiply(new BigDecimal(p.getKuantitas())));
                    fireTableCellUpdated(rowIndex, columnIndex); // Total may also have changed
                    fireTableCellUpdated(rowIndex, 5); // Total may also have changed
                    kalkulasiTotal();
                    break;
            }
        }
        
    }
    private class TabelBarangModel extends AbstractTableModel{
        private List<Barang> listBarang;
        public TabelBarangModel(List<Barang> listBarang){
            this.listBarang = listBarang;
        }
        @Override
        public int getRowCount() {
            return listBarang.size();
        }
        @Override
        public String getColumnName(int column){
            switch(column){
                case 0:return "PLU";
                case 1:return "Nama Barang";
                default:return"";
            }
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Barang b = listBarang.get(rowIndex);
            switch(columnIndex){
                case 0:return b.getPlu();
                case 1:return b.getNamaBarang();
                default:return "";
            }
        }
        
    }
    private class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if(e.getID() == KeyEvent.KEY_PRESSED){
                if(e.getKeyCode() == KeyEvent.VK_F2){
                    if(isPanelBarang==false){
                        panelBarang.setVisible(true);
                        isPanelBarang=true;
                        
                        txtCariNamaBarang.requestFocus();
                        daftarBarang = ClientLauncher.getMasterService().isJual(true);
                        isiTabelBarang();
                        
                        barang = new Barang();
                        tampilBarang(barang);
                        
                    }else if(isPanelBarang==true){
                        panelBarang.setVisible(false);
                        isPanelBarang=false;
                    }
                }
            }
            return false;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
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
        jPanel3 = new javax.swing.JPanel();
        lblKetSystem = new javax.swing.JLabel();
        lblTampilTotal = new javax.swing.JLabel();
        lblJam = new javax.swing.JLabel();
        lblKasir = new javax.swing.JLabel();
        lblJmlBarang = new javax.swing.JLabel();
        lblKetPelanggan = new javax.swing.JLabel();
        panelBarang = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBarang = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        lblPLU = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblNamaBarang = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblSatuanBarang = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblStokToko = new javax.swing.JLabel();
        lblSatuanJualToko = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblStokGudang = new javax.swing.JLabel();
        lblSatuanJualGudang = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblHargaJual = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtCariNamaBarang = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 255, 255));

        jLabel1.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel3.text")); // NOI18N

        txtNoRef.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.txtNoRef.text")); // NOI18N

        txtKodePelanggan.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.txtKodePelanggan.text")); // NOI18N

        txtNamaPelanggan.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.txtNamaPelanggan.text")); // NOI18N

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/find-icon16.png"))); // NOI18N

        cboLokasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel4.text")); // NOI18N

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabel.setCellSelectionEnabled(true);
        jScrollPane1.setViewportView(tabel);

        jcbKredit.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jcbKredit.text")); // NOI18N

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
                .addComponent(jdcTglTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jcbKredit)
                    .addComponent(lblTempo)
                    .addComponent(jdcTglTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

        lblJmlBarang.setForeground(new java.awt.Color(255, 255, 255));
        lblJmlBarang.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblJmlBarang.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblJmlBarang.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblTampilTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblJmlBarang))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblKetSystem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblJam, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblKasir, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblKetSystem)
                        .addGap(24, 24, 24)
                        .addComponent(lblTampilTotal))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblJam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblKasir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblJmlBarang)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblKetPelanggan.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblKetPelanggan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblKetPelanggan.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblKetPelanggan.text")); // NOI18N
        lblKetPelanggan.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNoRef, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtKodePelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cboLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKetPelanggan))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 914, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 914, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 914, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 914, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(lblKetPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cboLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tblBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblBarang);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jPanel9.border.title"))); // NOI18N

        lblPLU.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPLU.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblPLU.text")); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel7.text")); // NOI18N

        lblNamaBarang.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNamaBarang.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblNamaBarang.text")); // NOI18N

        jLabel15.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel15.text")); // NOI18N

        lblSatuanBarang.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSatuanBarang.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblSatuanBarang.text")); // NOI18N

        jLabel17.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel17.text")); // NOI18N

        jLabel18.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel18.text")); // NOI18N

        jLabel19.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel19.text")); // NOI18N

        lblStokToko.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStokToko.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblStokToko.text")); // NOI18N

        lblSatuanJualToko.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSatuanJualToko.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblSatuanJualToko.text")); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel5.text")); // NOI18N

        lblStokGudang.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStokGudang.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblStokGudang.text")); // NOI18N

        lblSatuanJualGudang.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSatuanJualGudang.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblSatuanJualGudang.text")); // NOI18N

        jLabel16.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel16.text")); // NOI18N

        lblHargaJual.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHargaJual.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.lblHargaJual.text")); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel17)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStokToko)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSatuanJualToko))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStokGudang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSatuanJualGudang))
                    .addComponent(lblPLU)
                    .addComponent(jLabel7)
                    .addComponent(lblNamaBarang)
                    .addComponent(jLabel15)
                    .addComponent(lblSatuanBarang)
                    .addComponent(jLabel16)
                    .addComponent(lblHargaJual))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPLU)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNamaBarang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSatuanBarang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStokToko, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSatuanJualToko, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel19)
                    .addComponent(lblStokGudang)
                    .addComponent(lblSatuanJualGudang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHargaJual)
                .addContainerGap())
        );

        jLabel24.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.jLabel24.text")); // NOI18N

        txtCariNamaBarang.setText(org.openide.util.NbBundle.getMessage(DialogKasir.class, "DialogKasir.txtCariNamaBarang.text")); // NOI18N

        javax.swing.GroupLayout panelBarangLayout = new javax.swing.GroupLayout(panelBarang);
        panelBarang.setLayout(panelBarangLayout);
        panelBarangLayout.setHorizontalGroup(
            panelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCariNamaBarang)
                    .addGroup(panelBarangLayout.createSequentialGroup()
                        .addGroup(panelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelBarangLayout.setVerticalGroup(
            panelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCariNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void initListener(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Ya", "Tidak"};
                int PromptResult = JOptionPane.showOptionDialog(null, "Apakah Anda Yakin Ingin Membatalkan Editing", "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    penjualan = null;
                    dispose();
                }
            }
        });
        tblBarang.getSelectionModel().addListSelectionListener((lse) -> {
            if(tblBarang.getSelectedRow() >=0){
                barang = daftarBarang.get(tblBarang.getSelectedRow());
                tampilBarang(barang);
            }
        });
        tblBarang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    addBarang(barang);
                }
            }
        });
        jcbKredit.addActionListener((ae) -> {
            if(pelanggan==null)jcbKredit.setSelected(false);
           kredit();
        });
        btnCari.addActionListener((ae) -> {
            Pelanggan p = (Pelanggan) new PilihPelanggan().showDialog("Plih Data Pelanggan");
            if(p!=null){
                pelanggan = p;
                tampilPelanggan();
            }
        });
        txtKodePelanggan.addKeyListener(new KeyListener() {
            
            @Override
            public void keyReleased(KeyEvent ke) {
                pelanggan = ClientLauncher.getMasterService().cariIdPelanggan(txtKodePelanggan.getText());
                if(pelanggan==null){
                    txtNamaPelanggan.setText("UMUM");
                    lblKetPelanggan.setText("Pelanggan Umum");
                    txtDiscPersen.setText("0");
                    jcbKredit.setSelected(false);
                    tampilBarang(barang);
                    kredit();
                    harga();
                }else{
                    tampilPelanggan();
                    tampilBarang(barang);
                }
            }
//<editor-fold defaultstate="collapsed" desc="--">
            
            @Override
            public void keyTyped(KeyEvent ke) {
            }
            
            @Override
            public void keyPressed(KeyEvent ke) {
            }
//</editor-fold>
        });
        txtBayar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                kalkulasiTotal();
            }
        });
        txtCariNamaBarang.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                daftarBarang = ClientLauncher.getMasterService().cariNamaBarang(txtCariNamaBarang.getText());
                isiTabelBarang();}
        });
        btnPending.addActionListener((ae) -> {
            penjualan = new Penjualan();
            loadFormToPenjualan();
            List<PenjualanDetail> list = new ArrayList<>();
            for(PenjualanDetail p : daftarDetail){
                if(p.getBarang()!=null){
                    list.add(p);
                }
            }
            penjualan.setPenjualanDetails(list);
            penjualan.setIsPending(true);
            
            ClientLauncher.getTransaksiService().simpan(penjualan);
            clearAll();
        });
        btnRecall.addActionListener((ae) -> {
            Penjualan p = new PilihPenjualanPending().showDialog("Pilih Penjualan Pending");
            if(p!=null){
                loadPenjualanToForm(p);
            }
        });
        btnSimpan.addActionListener((ae) -> {
            if(validateSimpan()){
                penjualan = new Penjualan();
                loadFormToPenjualan();
                List<PenjualanDetail> list = new ArrayList<>();
                for(PenjualanDetail p : daftarDetail){
                    if(p.getBarang()!=null){
                        list.add(p);
                    }
                }
                penjualan.setPenjualanDetails(list);
                penjualan.setIsPending(false);

                ClientLauncher.getTransaksiService().simpan(penjualan);
                clearAll();
            }
        });
    }
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
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox jcbKredit;
    private com.toedter.calendar.JDateChooser jdcTanggal;
    private com.toedter.calendar.JDateChooser jdcTglTempo;
    private javax.swing.JLabel lblHargaJual;
    private javax.swing.JLabel lblJam;
    private javax.swing.JLabel lblJmlBarang;
    private javax.swing.JLabel lblKasir;
    private javax.swing.JLabel lblKetPelanggan;
    private javax.swing.JLabel lblKetSystem;
    private javax.swing.JLabel lblNamaBarang;
    private javax.swing.JLabel lblPLU;
    private javax.swing.JLabel lblSatuanBarang;
    private javax.swing.JLabel lblSatuanJualGudang;
    private javax.swing.JLabel lblSatuanJualToko;
    private javax.swing.JLabel lblStokGudang;
    private javax.swing.JLabel lblStokToko;
    private javax.swing.JLabel lblTampilTotal;
    private javax.swing.JLabel lblTempo;
    private javax.swing.JPanel panelBarang;
    private javax.swing.JTable tabel;
    private javax.swing.JTable tblBarang;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtCariNamaBarang;
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
