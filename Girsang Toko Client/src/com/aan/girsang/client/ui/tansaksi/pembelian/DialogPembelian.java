/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.tansaksi.pembelian;

import com.aan.girsang.api.model.constant.TransaksiRunningNumberEnum;
import com.aan.girsang.api.model.master.Barang;
import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.api.model.transaksi.Kredit;
import com.aan.girsang.api.model.transaksi.Pembelian;
import com.aan.girsang.api.model.transaksi.PembelianDetail;
import com.aan.girsang.api.util.BigDecimalRenderer;
import com.aan.girsang.api.util.IntegerRenderer;
import com.aan.girsang.api.util.TextComponentUtils;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.frame.FrameUtama;
import com.aan.girsang.client.ui.master.barang.PilihBarangDialog;
import com.aan.girsang.client.ui.master.supplier.PilihSupplierDialog;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ITSUSAHBRO
 */
public class DialogPembelian extends javax.swing.JDialog {

    private Pembelian pembelian;
    private Kredit kredit;
    private Barang barang;
    private Supplier supplier;
    private List<PembelianDetail> pembelianDetails = new ArrayList<>();
    private PembelianDetail pembelianDetail;
    
    public DialogPembelian() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        initListener();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        tblPembelianDetail.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        tblPembelianDetail.setDefaultRenderer(Integer.class, new IntegerRenderer());
        jdcTglPembelian.setDateFormatString("EEE, dd MMM yyyy        HH:mm:ss");
        jdcTglTempo.setDateFormatString("EE, dd MMMM yyyy");
        txtNoRef.setEditable(false);
        txtNamaSupplier.setEditable(false);
        txtNamaBarang.setEditable(false);
        txtTotal.setEditable(false);
        txtSisa.setEditable(false);
        jspQty.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
        jspQty.setEditor(new JSpinner.NumberEditor(jspQty, "0"));
        clearAll();
        ukuranTabelBarang();
        TextComponentUtils.setCurrency(txtHargaBeli);
        TextComponentUtils.setCurrency(txtTotal);
        TextComponentUtils.setCurrency(txtBayar);
        TextComponentUtils.setCurrency(txtSisa);
    }
    public Pembelian showDialog(Pembelian p, Supplier s, String Title,Boolean edit) {
            pembelian = new Pembelian();
        if (p == null) {
            clearAll();
            loadFormToModel();
        }else{
            loadModelToForm(p, s);
        }
        setTitle(Title);
        setEditPembelian(edit);
        setLocationRelativeTo(null);
        setVisible(true);
        return this.pembelian;
    }
    private void clearAll() {
        txtNoRef.setText(ClientLauncher.getConstantService()
                .ambilBerikutnya(TransaksiRunningNumberEnum.PEMBELIAN));
        jdcTglPembelian.setDate(new Date());
        txtKodeSupplier.setText("");
        txtNamaSupplier.setText("");
        txtNoFaktur.setText("");
        jcbKredit.setSelected(false);
        lblTempo.setVisible(false);
        jdcTglTempo.setVisible(false);
        jdcTglTempo.setDate(new Date());
        txtTotal.setText("0");
        txtBayar.setText("0");
        txtSisa.setText("0");
        lblItem.setText("0 Item");
        lblPLU.setText("O Jenis Barang");
        
        cboLokasi.removeAllItems();
        cboLokasi.addItem("Toko");
        cboLokasi.addItem("Gudang");
        cboLokasi.setSelectedItem(null);

        cboSatuanBeli.removeAllItems();
        cboSatuanBeli.addItem("Botol");
        cboSatuanBeli.addItem("Box");
        cboSatuanBeli.addItem("Buah");
        cboSatuanBeli.addItem("Dusin");
        cboSatuanBeli.addItem("Pack");
        cboSatuanBeli.addItem("PCS");
        cboSatuanBeli.addItem("Toples");
        clearBarang();
    }
    private void clearBarang() {
        txtBarcode.setText("");
        txtNamaBarang.setText("");
        cboSatuanBeli.setSelectedItem(null);
        txtIsi.setText("0");
        jspQty.setValue(0);
        txtHargaBeli.setText("0");
    }
    private boolean validateAddDetail() {
        if (barang == null
                || "0".equals(jspQty.getValue())
                || "".equals(jspQty.getValue())
                || "".equals(txtNamaBarang.getText())) {
            JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                     "Data Barang Belum Lengkap!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    private boolean validateSimpan() {
        if (supplier == null) {
            JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                     "Suplier Harus Di Isi",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtKodeSupplier.requestFocus();
            return false;
        }
        boolean ckredit = jcbKredit.isSelected();
        Double sisa = Double.valueOf(TextComponentUtils.getValueFromTextNumber(txtSisa));
        
        if(ckredit!=true && sisa>0){
            JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                     "Jumlah Pembayaran Tidak Boleh Kurang",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtBayar.requestFocus();
            return false;
        }
        if(cboLokasi.getSelectedIndex()<0){
            JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                     "Lokasi Harus Di Isi",
                    "Error", JOptionPane.ERROR_MESSAGE);
            cboLokasi.requestFocus();
            return false;
        }
        return true;
    }
    private void setEditPembelian(Boolean edit){
        for (Component component : panel.getComponents()) {
            component.setEnabled(edit);
        }
        btnSimpan.setVisible(edit);
        
        if(edit==false){
            btnBatal.setText("Tutup");
        } else {
            btnBatal.setText("Batal");
        }
    }
    private void kredit() {
        if (jcbKredit.isSelected() == true) {
            kredit.setTanggalTempo(jdcTglTempo.getDate());
            kredit.setJumlahKredit(TextComponentUtils
                    .parseNumberToBigDecimal(txtTotal.getText())
                    .subtract(TextComponentUtils
                            .parseNumberToBigDecimal(txtBayar.getText())));
            kredit.setJumlahBayar(TextComponentUtils
                    .parseNumberToBigDecimal(txtBayar.getText()));
            kredit.setSisaKredit(TextComponentUtils
                    .parseNumberToBigDecimal(txtTotal.getText())
                    .subtract(TextComponentUtils
                            .parseNumberToBigDecimal(txtBayar.getText())));
        } else {
            kredit.setTanggalTempo(null);
            kredit.setJumlahKredit(null);
            kredit.setJumlahBayar(null);
            kredit.setSisaKredit(null);
        }
    }
    private void loadFormToModel() {
        kredit = new Kredit();
        kredit();
        pembelian.setNoRef(txtNoRef.getText());
        pembelian.setTanggal(jdcTglPembelian.getDate());
        pembelian.setLokasi((String) cboLokasi.getSelectedItem());
        pembelian.setSupplier(supplier);
        if(!"".equals(txtNoFaktur.getText())){
            pembelian.setNoFaktur(txtNoFaktur.getText());
        }else{
            pembelian.setNoFaktur("-");
        }
        pembelian.setKredit(jcbKredit.isSelected());
        pembelian.setDaftarKredit(kredit);
        pembelian.setPembelianDetails(pembelianDetails);
        pembelian.setTotal(TextComponentUtils
                .parseNumberToBigDecimal(txtTotal.getText()));
        pembelian.setPembuat(ClientLauncher.getPenggunaAktif());
    }
    private void loadModelToForm(Pembelian p, Supplier s){
        txtNoRef.setText(p.getNoRef());
        jdcTglPembelian.setDate(p.getTanggal());
        txtKodeSupplier.setText(s.getId());
        txtNamaSupplier.setText(s.getNamaSupplier());
        txtNoFaktur.setText(p.getNoFaktur());
        jcbKredit.setSelected(p.getKredit());
        txtBayar.setText(TextComponentUtils.formatNumber(p.getDaftarKredit().getJumlahBayar()));
        if(p.getKredit()==true){
            jdcTglTempo.setDate(p.getDaftarKredit().getTanggalTempo());
        }
        cboLokasi.setSelectedItem(p.getLokasi());
        pembelianDetail = new PembelianDetail();
        pembelianDetails = ClientLauncher.getTransaksiService().cariPembelianDetail(p);
        tblPembelianDetail.setModel(new PembelianDetailTabelModel(pembelianDetails));
        supplier = s;
        ukuranTabelBarang();
        kalkulasiTotal();
        kalkulasiJumlahBarang(pembelianDetails);
        kalkulasiPembayaran();
}
    private void loadFormToDomain() {
        pembelianDetail.setPembelian(pembelian);
        pembelianDetail.setBarang(barang);
        pembelianDetail.setHargaBarang(TextComponentUtils
                .parseNumberToBigDecimal(txtHargaBeli.getText()));
        pembelianDetail.setKuantitas((Integer) jspQty.getValue());
        pembelianDetail.setSubTotal(TextComponentUtils
                .parseNumberToBigDecimal(txtHargaBeli.getText())
                .multiply(new BigDecimal(pembelianDetail
                        .getKuantitas())));
        pembelianDetail.setSatuanPembelian((String) cboSatuanBeli
                .getSelectedItem());
        pembelianDetail.setIsiPembelian(Integer.valueOf(txtIsi.getText()));
        pembelianDetail.setUpdate(jcbUpdate.isSelected());
    }
    private void addBarang() {
        boolean data = false;
        if (validateAddDetail()) {
            pembelianDetail = new PembelianDetail();
            loadFormToDomain();
            if (pembelianDetails != null) {
                for (int i = 0; i < pembelianDetails.size(); i++) {
                    if (pembelianDetails.get(i).getBarang().getPlu()
                            .equals(pembelianDetail.getBarang().getPlu())) {
                        pembelianDetail = pembelianDetails.get(i);
                        pembelianDetail.setHargaBarang(TextComponentUtils
                                .parseNumberToBigDecimal(txtHargaBeli.getText()));
                        pembelianDetail.setKuantitas(pembelianDetails.get(i)
                                .getKuantitas() + (Integer) jspQty.getValue());
                        pembelianDetail.setSubTotal(TextComponentUtils
                                .parseNumberToBigDecimal(txtHargaBeli.getText())
                                .multiply(new BigDecimal(pembelianDetail
                                        .getKuantitas())));
                        data = true;
                    }
                }
            }
            if (data == false) {
                pembelianDetails.add(pembelianDetail);
                tblPembelianDetail.setModel(new PembelianDetailTabelModel(pembelianDetails));
                ukuranTabelBarang();
                clearBarang();
                kalkulasiTotal();
                kalkulasiJumlahBarang(pembelianDetails);
            } else if (data == true) {
                tblPembelianDetail.tableChanged(
                        new TableModelEvent(tblPembelianDetail.getModel(),
                                tblPembelianDetail.getSelectedRow()));
                clearBarang();
                kalkulasiTotal();
                kalkulasiJumlahBarang(pembelianDetails);
            }
        }
    }
    private void kalkulasiTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (PembelianDetail p : pembelianDetails) {
            total = total.add(p.getSubTotal());
        }
        txtTotal.setText(TextComponentUtils.formatNumber(total));
        kalkulasiPembayaran();
    }
    private void kalkulasiPembayaran(){
        if(!"".equals(txtBayar.getText())){
        Double Total = Double.valueOf(TextComponentUtils.getValueFromTextNumber(txtTotal));
        Double Bayar = Double.valueOf(TextComponentUtils.getValueFromTextNumber(txtBayar));
        Double Sisa;
        if(Bayar >= Total){
            Sisa = Double.valueOf("0");
        } else {
            Sisa = Total - Bayar;
        }
        BigDecimal s = new BigDecimal(Sisa, MathContext.DECIMAL64);
        txtSisa.setText(TextComponentUtils.formatNumber(s));
        }else{
            txtSisa.setText("0");
        }
    }
    private void kalkulasiJumlahBarang(List<PembelianDetail> pembelianDetail){
        lblPLU.setText(String.valueOf(pembelianDetail.size()) + " PLU");
        Integer item = 0;
        for(int i=0;i<pembelianDetail.size();i++){
            item = item + pembelianDetail.get(i).getKuantitas();
        }
        lblItem.setText(String.valueOf(item) + " Item");
    }
    private void initListener() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Ya", "Tidak"};
                int PromptResult = JOptionPane.showOptionDialog(null, "Apakah Anda Yakin Ingin Membatalkan Editing", "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    pembelian = null;
                    dispose();
                }
            }
        });
    }
    private void ukuranTabelBarang() {
        tblPembelianDetail.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblPembelianDetail.getColumnModel().getColumn(0).setPreferredWidth(200);//Nama Barang
        tblPembelianDetail.getColumnModel().getColumn(1).setPreferredWidth(100);//Satuan Beli
        tblPembelianDetail.getColumnModel().getColumn(2).setPreferredWidth(50);//Qty
        tblPembelianDetail.getColumnModel().getColumn(3).setPreferredWidth(50);//Isi
        tblPembelianDetail.getColumnModel().getColumn(4).setPreferredWidth(100);//Satuan Jual
        tblPembelianDetail.getColumnModel().getColumn(5).setPreferredWidth(100);//Harga Beli
        tblPembelianDetail.getColumnModel().getColumn(6).setPreferredWidth(100);//Sub Total
        tblPembelianDetail.getColumnModel().getColumn(7).setPreferredWidth(100);//Update Harga
    }
    private class PembelianDetailTabelModel extends AbstractTableModel {
        String columnNames[] = {
            "Nama Barang", 
            "Satuan Beli", 
            "Qty", 
            "Isi", 
            "Satuan Jual", 
            "Harga Beli", 
            "Sub Total", 
            "Update Harga"};
        private List<PembelianDetail> pembelianDetails;
        public PembelianDetailTabelModel(List<PembelianDetail> daftarPembelian) {
            this.pembelianDetails = daftarPembelian;
        }
        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
        @Override
        public int getRowCount() {
            return pembelianDetails.size();
        }
        @Override
        public int getColumnCount() {
            return 8;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            PembelianDetail p = pembelianDetails.get(rowIndex);
            switch (columnIndex) {
                case 0:return p.getBarang().getNamaBarang();
                case 1:return p.getSatuanPembelian();
                case 2:return p.getKuantitas();
                case 3:return p.getIsiPembelian();
                case 4:return p.getBarang().getSatuan();
                case 5:return p.getHargaBarang();
                case 6:return p.getSubTotal();
                case 7:return p.getUpdate();
                default:return "";
            }
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 2:return Integer.class;
                case 3:return Integer.class;
                case 5:return BigDecimal.class;
                case 6:return BigDecimal.class;
                case 7:return Boolean.class;
                default:return String.class;
            }
        }
        @Override
        public boolean isCellEditable(int row, int columnIndex) {
            if (columnIndex == 2 ||
                    columnIndex == 3 ||
                    columnIndex == 5 ||
                    columnIndex == 7) {
                return true;
            } else {
                return false;
            }
        }
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            PembelianDetail p = pembelianDetails.get(rowIndex);
            switch (columnIndex) {
            case 1:
                p.setSatuanPembelian((String) aValue);
                fireTableCellUpdated(rowIndex, columnIndex); // Total may also have changed
                break;
            case 2:
                p.setKuantitas((Integer) aValue);
                p.setSubTotal(p.getHargaBarang().multiply(new BigDecimal(p.getKuantitas())));
                fireTableCellUpdated(rowIndex, columnIndex); // Total may also have changed
                fireTableCellUpdated(rowIndex, 6); // Total may also have changed
                kalkulasiTotal();
                break;
            case 3:
                p.setIsiPembelian((Integer) aValue);
                fireTableCellUpdated(rowIndex, columnIndex);
                // Total may also have changed
                break;
            case 5:
                p.setHargaBarang((BigDecimal) aValue);
                p.setSubTotal(p.getHargaBarang().multiply(new BigDecimal(p.getKuantitas())));
                fireTableCellUpdated(rowIndex, columnIndex);
                fireTableCellUpdated(rowIndex, 6);// Total may also have changed
                break;
            case 7:
                p.setUpdate((Boolean) aValue);
                fireTableCellUpdated(rowIndex, columnIndex); // Total may also have changed
                break;
            }
            kalkulasiJumlahBarang(pembelianDetails);
            kalkulasiTotal();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        panel = new javax.swing.JPanel();
        txtNoRef = new javax.swing.JTextField();
        jcbUpdate = new javax.swing.JCheckBox();
        jdcTglPembelian = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtKodeSupplier = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnCari = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        lblTempo = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPembelianDetail = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnDaftarBarang = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        lblItem = new javax.swing.JLabel();
        lblPLU = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cboLokasi = new javax.swing.JComboBox<>();
        txtNamaSupplier = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txtNoFaktur = new javax.swing.JTextField();
        txtBarcode = new javax.swing.JTextField();
        jcbKredit = new javax.swing.JCheckBox();
        txtNamaBarang = new javax.swing.JTextField();
        jdcTglTempo = new com.toedter.calendar.JDateChooser();
        cboSatuanBeli = new javax.swing.JComboBox<>();
        txtTotal = new javax.swing.JTextField();
        txtIsi = new javax.swing.JTextField();
        txtBayar = new javax.swing.JTextField();
        jspQty = new javax.swing.JSpinner();
        txtSisa = new javax.swing.JTextField();
        txtHargaBeli = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnSimpan.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.btnSimpan.text")); // NOI18N
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.btnBatal.text")); // NOI18N
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        txtNoRef.setBackground(new java.awt.Color(255, 255, 204));
        txtNoRef.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.txtNoRef.text")); // NOI18N

        jcbUpdate.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jcbUpdate.text")); // NOI18N

        jdcTglPembelian.setDateFormatString(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jdcTglPembelian.dateFormatString")); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel5.text")); // NOI18N

        txtKodeSupplier.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.txtKodeSupplier.text")); // NOI18N
        txtKodeSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeSupplierActionPerformed(evt);
            }
        });

        jLabel4.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel4.text")); // NOI18N

        jLabel9.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel9.text")); // NOI18N

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/find-icon16.png"))); // NOI18N
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        jLabel10.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel10.text")); // NOI18N

        lblTempo.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.lblTempo.text")); // NOI18N

        jLabel11.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel11.text")); // NOI18N

        tblPembelianDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Barcode", "Nama Barang", "Satuan Beli", "Qty", "Isi", "Satuan Jual", "Harga Beli", "Update Harga"
            }
        ));
        jScrollPane1.setViewportView(tblPembelianDetail);
        if (tblPembelianDetail.getColumnModel().getColumnCount() > 0) {
            tblPembelianDetail.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.tblPembelianDetail.columnModel.title0")); // NOI18N
            tblPembelianDetail.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.tblPembelianDetail.columnModel.title1")); // NOI18N
            tblPembelianDetail.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.tblPembelianDetail.columnModel.title2")); // NOI18N
            tblPembelianDetail.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.tblPembelianDetail.columnModel.title3")); // NOI18N
            tblPembelianDetail.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.tblPembelianDetail.columnModel.title4")); // NOI18N
            tblPembelianDetail.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.tblPembelianDetail.columnModel.title5")); // NOI18N
            tblPembelianDetail.getColumnModel().getColumn(6).setHeaderValue(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.tblPembelianDetail.columnModel.title6")); // NOI18N
            tblPembelianDetail.getColumnModel().getColumn(7).setHeaderValue(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.tblPembelianDetail.columnModel.title7")); // NOI18N
        }

        jLabel12.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel12.text")); // NOI18N

        jLabel13.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel13.text")); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel6.text")); // NOI18N

        btnDaftarBarang.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.btnDaftarBarang.text")); // NOI18N
        btnDaftarBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaftarBarangActionPerformed(evt);
            }
        });

        jLabel7.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel7.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel8.text")); // NOI18N

        btnTambah.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.btnTambah.text")); // NOI18N
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        lblItem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblItem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblItem.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.lblItem.text")); // NOI18N

        lblPLU.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPLU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPLU.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.lblPLU.text")); // NOI18N

        jLabel14.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel14.text")); // NOI18N

        cboLokasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtNamaSupplier.setBackground(new java.awt.Color(255, 255, 204));
        txtNamaSupplier.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.txtNamaSupplier.text")); // NOI18N

        txtNoFaktur.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.txtNoFaktur.text")); // NOI18N

        txtBarcode.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.txtBarcode.text")); // NOI18N

        jcbKredit.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jcbKredit.text")); // NOI18N
        jcbKredit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbKreditActionPerformed(evt);
            }
        });

        txtNamaBarang.setBackground(new java.awt.Color(255, 255, 204));
        txtNamaBarang.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.txtNamaBarang.text")); // NOI18N

        jdcTglTempo.setDateFormatString(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jdcTglTempo.dateFormatString")); // NOI18N

        cboSatuanBeli.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.txtTotal.text")); // NOI18N

        txtIsi.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.txtIsi.text")); // NOI18N

        txtBayar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtBayar.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.txtBayar.text")); // NOI18N
        txtBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBayarKeyReleased(evt);
            }
        });

        jspQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jspQtyKeyPressed(evt);
            }
        });

        txtSisa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSisa.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.txtSisa.text")); // NOI18N

        txtHargaBeli.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.txtHargaBeli.text")); // NOI18N

        jLabel1.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(DialogPembelian.class, "DialogPembelian.jLabel3.text")); // NOI18N

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panelLayout.createSequentialGroup()
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtNoRef, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel14)
                                    .addComponent(cboLokasi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelLayout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(24, 24, 24)
                                    .addComponent(jdcTglPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(97, 97, 97)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addGap(33, 33, 33)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelLayout.createSequentialGroup()
                                    .addComponent(txtKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelLayout.createSequentialGroup()
                                    .addComponent(jcbKredit)
                                    .addGap(18, 18, 18)
                                    .addComponent(lblTempo)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jdcTglTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jScrollPane1)
                        .addGroup(panelLayout.createSequentialGroup()
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10))
                            .addGap(25, 25, 25)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboSatuanBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelLayout.createSequentialGroup()
                                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel12))
                                    .addGap(40, 40, 40)
                                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtIsi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panelLayout.createSequentialGroup()
                                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jspQty, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(41, 41, 41)
                                            .addComponent(jcbUpdate))))
                                .addComponent(jLabel13)))
                        .addComponent(jSeparator3)
                        .addComponent(jSeparator2)
                        .addGroup(panelLayout.createSequentialGroup()
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panelLayout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addGap(35, 35, 35)
                                            .addComponent(txtSisa, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblItem)
                                .addComponent(lblPLU))))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDaftarBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnCari)
                            .addComponent(jLabel3)
                            .addComponent(txtKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jcbKredit)
                                    .addComponent(lblTempo)
                                    .addComponent(jdcTglTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jLabel2)
                                    .addComponent(jdcTglPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(cboLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1))
                    .addComponent(txtNoRef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtIsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jspQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cboSatuanBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnDaftarBarang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(lblItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPLU)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtSisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBatal, btnSimpan});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnBatal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void txtKodeSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeSupplierActionPerformed
    private void jcbKreditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbKreditActionPerformed
        // TODO add your handling code here:
        if (jcbKredit.isSelected()) {
            lblTempo.setVisible(true);
            jdcTglTempo.setVisible(true);
        } else {
            lblTempo.setVisible(false);
            jdcTglTempo.setVisible(false);
        }
    }//GEN-LAST:event_jcbKreditActionPerformed
    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        pembelian = null;
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed
    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        String judul = "Pilih Supplier Pembelian";
        Supplier s = (Supplier) new PilihSupplierDialog().showDialog(judul);
        if (s != null) {
            txtKodeSupplier.setText(s.getId());
            txtNamaSupplier.setText(s.getNamaSupplier());
            supplier = new Supplier();
            supplier = ClientLauncher.getMasterService().cariIdSupplier(s.getId());
        }
    }//GEN-LAST:event_btnCariActionPerformed
    private void btnDaftarBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaftarBarangActionPerformed
        // TODO add your handling code here:
        String judul = "Pilih Data Barang";
        Barang b = (Barang) new PilihBarangDialog().showDialog(judul);
        if (b != null) {
            txtBarcode.setText(b.getBarcode1());
            txtNamaBarang.setText(b.getNamaBarang());
            cboSatuanBeli.setSelectedItem(b.getSatuanPembelian());
            txtIsi.setText(b.getIsiPembelian().toString());
            BigDecimal hargaBeli = b.getHargaBeli().multiply(new BigDecimal(b
                    .getIsiPembelian()));
            txtHargaBeli.setText(TextComponentUtils.formatNumber(hargaBeli));
            jspQty.requestFocus();
            barang = ClientLauncher.getMasterService().cariIdBarang(b.getPlu());
        }
    }//GEN-LAST:event_btnDaftarBarangActionPerformed
    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        addBarang();
    }//GEN-LAST:event_btnTambahActionPerformed
    private void jspQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jspQtyKeyPressed
        // TODO add your handling code here:
        addBarang();
    }//GEN-LAST:event_jspQtyKeyPressed
    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        if(validateSimpan()){
            pembelian = new Pembelian();
            loadFormToModel();
            dispose();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed
    private void txtBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyReleased
        // TODO add your handling code here:
        kalkulasiPembayaran();
    }//GEN-LAST:event_txtBayarKeyReleased

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnDaftarBarang;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cboLokasi;
    private javax.swing.JComboBox<String> cboSatuanBeli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JCheckBox jcbKredit;
    private javax.swing.JCheckBox jcbUpdate;
    private com.toedter.calendar.JDateChooser jdcTglPembelian;
    private com.toedter.calendar.JDateChooser jdcTglTempo;
    private javax.swing.JSpinner jspQty;
    private javax.swing.JLabel lblItem;
    private javax.swing.JLabel lblPLU;
    private javax.swing.JLabel lblTempo;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tblPembelianDetail;
    private javax.swing.JTextField txtBarcode;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtHargaBeli;
    private javax.swing.JTextField txtIsi;
    private javax.swing.JTextField txtKodeSupplier;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtNoFaktur;
    private javax.swing.JTextField txtNoRef;
    private javax.swing.JTextField txtSisa;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
