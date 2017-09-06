/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.tansaksi.penjualan;

import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.api.model.transaksi.Penjualan;
import com.aan.girsang.api.util.BigDecimalRenderer;
import com.aan.girsang.api.util.DateRenderer;
import com.aan.girsang.api.util.IntegerRenderer;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ITSUSAHBRO
 */
public class PenjualanPanel extends javax.swing.JPanel {

    private List<Penjualan> daftarPenjualan;
    private Penjualan penjualan;
    private Supplier supplier;


    int IndexTab = 0;
    int aktifPanel = 0;
    String title, idSelect;
    ToolbarDenganFilter toolBar = new ToolbarDenganFilter();
    public int getIndexTab() {
        return IndexTab;
    }
    public void setIndexTab(int IndexTab) {
        this.IndexTab = IndexTab;
    }
    public int getAktifPanel() {
        return aktifPanel;
    }
    public void setAktifPanel(int aktifPanel) {
        this.aktifPanel = aktifPanel;
    }
    public ToolbarDenganFilter getToolbarDenganFilter1() {
        return toolbar;
    }

    public PenjualanPanel() {
        initComponents();
        initListener();
        tabel.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        tabel.setDefaultRenderer(Date.class, new DateRenderer());
        tabel.setDefaultRenderer(Integer.class, new IntegerRenderer());
        jspTahun.setModel(new SpinnerNumberModel(0, 0, 5000, 1));
        jspTahun.setEditor(new JSpinner.NumberEditor(jspTahun, "0"));
        isiCombo();
        isiTabelPenjualan();
    }
    private void isiCombo(){
        Date tanggal = new Date();
        SimpleDateFormat dfBulan = new SimpleDateFormat("M");
        SimpleDateFormat dfTahun = new SimpleDateFormat("yyyy");
        
        cboBulan.removeAllItems();
        
        cboBulan.addItem("Januari");
        cboBulan.addItem("Februari");
        cboBulan.addItem("Maret");
        cboBulan.addItem("April");
        cboBulan.addItem("Mei");
        cboBulan.addItem("Juni");
        cboBulan.addItem("Juli");
        cboBulan.addItem("Agustus");
        cboBulan.addItem("September");
        cboBulan.addItem("Oktober");
        cboBulan.addItem("November");
        cboBulan.addItem("Desember");
        
        cboBulan.setSelectedIndex(Integer.parseInt(dfBulan.format(tanggal)) - 1);
        jspTahun.setValue(Integer.parseInt(dfTahun.format(tanggal)));
    }
    private void ukuranTabel() {
        tabel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabel.getColumnModel().getColumn(0).setPreferredWidth(200);//Tanggal
        tabel.getColumnModel().getColumn(1).setPreferredWidth(100);//No. Ref
        tabel.getColumnModel().getColumn(2).setPreferredWidth(100);//Pelanggan
        tabel.getColumnModel().getColumn(3).setPreferredWidth(70);//Kredit
        tabel.getColumnModel().getColumn(4).setPreferredWidth(100);//Tgl Tempo
        tabel.getColumnModel().getColumn(5).setPreferredWidth(100);//Janis
        tabel.getColumnModel().getColumn(6).setPreferredWidth(100);//Jlh. Penjualan
        tabel.getColumnModel().getColumn(7).setPreferredWidth(80);//pembulatan
        tabel.getColumnModel().getColumn(8).setPreferredWidth(80);//Total
        tabel.getColumnModel().getColumn(9).setPreferredWidth(90);//Kasir
    }
    private void isiTabelPenjualan() {
        //daftarPenjualan = ClientLauncher.getTransaksiService().semuaPenjualan();
        daftarPenjualan = ClientLauncher.getTransaksiService()
                .filterBulanTahunJual(cboBulan.getSelectedIndex(), (int) jspTahun.getValue());
        RowSorter<TableModel> sorter = new TableRowSorter<>(new TabelModel(daftarPenjualan));
        tabel.setRowSorter(sorter);
        tabel.setModel(new TabelModel(daftarPenjualan));
        toolbar.getTxtCari().setText("");
        ukuranTabel();
        lblJumlahData.setText(daftarPenjualan.size() + " Data Pembelian");
        idSelect = "";
    }
    private void loadFormToModel(Penjualan p) {
        penjualan = p;
    }
    private void cariSelect() {
        penjualan = new Penjualan();
        penjualan = ClientLauncher.getTransaksiService().cariIDPenjualan(idSelect);
    }
    private class TabelModel extends AbstractTableModel {
        private final List<Penjualan> listPenjualan;
        public TabelModel(List<Penjualan> listPenjualan) {
            this.listPenjualan = listPenjualan;
        }
        @Override
        public int getRowCount() {
            return listPenjualan.size();
        }
        @Override
        public int getColumnCount() {
            return 10;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0:return "Tanggal";
                case 1:return "No. Ref";
                case 2:return "Pelanggan";
                case 3:return "Kredit";
                case 4:return "Tgl. Tempo";
                case 5:return "Jenis";
                case 6:return "Jlh. Penjualan";
                case 7:return "Pembulatan";
                case 8:return "Total";
                case 9:return "Kasir";
                default:return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            Penjualan p = daftarPenjualan.get(rowIndex);
            switch (colIndex) {
                case 0:return p.getTanggal();
                case 1:return p.getNoRef();
                case 2:
                    if(p.getPelanggan()!=null){
                        return p.getPelanggan().getNama();
                    }else{
                        return "UMUM";
                    }
                case 3:return p.getIsKredit();
                case 4:
                    if(p.getIsKredit()==true){
                        return p.getTanggalTempo();
                    }else{
                        return "-";
                    }
                case 5:return p.getLokasi();
                case 6:return p.getSubTotal().subtract(p.getDiscTotal());
                case 7:return p.getPembulatan();
                case 8:return p.getTotal();
                case 9:return p.getKasir().getIdPengguna();
                default:return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:return Date.class;
                case 3:return Boolean.class;
                case 6:return BigDecimal.class;
                case 7:return BigDecimal.class;
                case 8:return BigDecimal.class;
                default:return String.class;
            }
        }
    }
    private void initListener() {
        tabel.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tabel.getSelectedRow() >= 0) {
                idSelect = tabel.getValueAt(tabel.getSelectedRow(), 1).toString();
            }
        });
        tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    title = "Edit Data Pembelian";
                    if ("".equals(idSelect)) {
                        JOptionPane.showMessageDialog(null, "Data Pembelian Belum Terpilih");
                    } else {
                        cariSelect();
                        Penjualan p = (Penjualan) new DialogKasir().showDialog(penjualan);
                        penjualan = new Penjualan();
                        if (p != null) {
                            loadFormToModel(p);
                            ClientLauncher.getTransaksiService().simpan(penjualan);
                            isiTabelPenjualan();
                            JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                            title = null;
                        }
                    }
                }
            }
        });

        toolbar.getBtnRefresh().addActionListener((ActionEvent ae) -> {
            isiCombo();
        });

        toolbar.getBtnBaru().addActionListener((ActionEvent ae) -> {
            Penjualan d = (Penjualan) new DialogKasir().showDialog(null);
            
        });

        toolbar.getBtnEdit().addActionListener((ActionEvent ae) -> {
            title = "Edit Data Pembelian";
                    if ("".equals(idSelect)) {
                        JOptionPane.showMessageDialog(null, "Data Pembelian Belum Terpilih");
                    } else {
                        cariSelect();
                        Penjualan p = (Penjualan) new DialogKasir().showDialog(penjualan);
                        penjualan = new Penjualan();
                        if (p != null) {
                            loadFormToModel(p);
                            ClientLauncher.getTransaksiService().simpan(penjualan);
                            isiTabelPenjualan();
                            JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                            title = null;
                        }
                    }
        });

        toolbar.getBtnHapus().addActionListener((ActionEvent ae) -> {
            cariSelect();
            if (penjualan == null) {
                JOptionPane.showMessageDialog(null, "Data Barang Belum Terpilih");
            } else {
                ClientLauncher.getTransaksiService().hapus(penjualan);
                isiTabelPenjualan();
                JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
            }   
        });
        toolbar.getBtnFilter().addActionListener((ActionEvent ae) -> {
            /*List <Barang> list = new FilterBarang().showDialog();
            System.out.println("Fiter Barang");*/
        });
        cboBulan.addActionListener((ActionEvent ae) -> {
            isiTabelPenjualan();
        });
        jspTahun.addChangeListener((ChangeEvent ce) -> {
            isiTabelPenjualan();
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        lblJumlahData = new javax.swing.JLabel();
        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter();
        cboBulan = new javax.swing.JComboBox<>();
        jspTahun = new javax.swing.JSpinner();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Cash register-64x64.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel2.setText("Daftar Transaksi Penjualan");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel3.setText("Disini anda bisa menambah, mengedit atau menghapus data Transaksi Pembelian");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabel);

        lblJumlahData.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblJumlahData.setText("jLabel4");

        cboBulan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 970, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblJumlahData, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addGap(5, 5, 5))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jspTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jspTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJumlahData)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboBulan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jspTahun;
    private javax.swing.JLabel lblJumlahData;
    private javax.swing.JTable tabel;
    private com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter toolbar;
    // End of variables declaration//GEN-END:variables
}
