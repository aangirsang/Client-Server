/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.tansaksi.pembelian;

import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.api.model.transaksi.ReturPembelian;
import com.aan.girsang.api.util.BigDecimalRenderer;
import com.aan.girsang.api.util.DateRenderer;
import com.aan.girsang.api.util.IntegerRenderer;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ITSUSAHBRO
 */
public class PanelReturPembelian extends javax.swing.JPanel {

    private List<ReturPembelian> daftarReturPembelian;
    private ReturPembelian returPembelian;
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

    public PanelReturPembelian() {
        initComponents();
        initListener();
        tblReturPembelian.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        tblReturPembelian.setDefaultRenderer(Date.class, new DateRenderer());
        tblReturPembelian.setDefaultRenderer(Integer.class, new IntegerRenderer());
        isiTabelKategori();
    }
    private void ukuranTabelBarang() {
        tblReturPembelian.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblReturPembelian.getColumnModel().getColumn(0).setPreferredWidth(200);//Tanggal
        tblReturPembelian.getColumnModel().getColumn(1).setPreferredWidth(100);//No. Ref
        tblReturPembelian.getColumnModel().getColumn(2).setPreferredWidth(100);//No. Faktur
        tblReturPembelian.getColumnModel().getColumn(3).setPreferredWidth(350);//Supplier
        tblReturPembelian.getColumnModel().getColumn(4).setPreferredWidth(50);//Kredit
        tblReturPembelian.getColumnModel().getColumn(5).setPreferredWidth(100);//Tgl. Tempo
    }
    private void isiTabelKategori() {
        daftarReturPembelian = ClientLauncher.getTransaksiService().semuaReturPembelian();
        RowSorter<TableModel> sorter = new TableRowSorter<>(new TabelModel(daftarReturPembelian));
        tblReturPembelian.setRowSorter(sorter);
        tblReturPembelian.setModel(new TabelModel(daftarReturPembelian));
        toolbar.getTxtCari().setText("");
        ukuranTabelBarang();
        lblJumlahData.setText(daftarReturPembelian.size() + " Data Pembelian");
        idSelect = "";
    }
    private void loadFormToModel(ReturPembelian rP) {
        returPembelian = rP;
    }
    private void cariSelect() {
        returPembelian = new ReturPembelian();
        returPembelian = ClientLauncher.getTransaksiService().cariReturPembelian(idSelect);
    }
    private class TabelModel extends AbstractTableModel {
        private final List<ReturPembelian> daftarRetur;
        public TabelModel(List<ReturPembelian> daftarReturPembelian) {
            this.daftarRetur = daftarReturPembelian;
        }
        @Override
        public int getRowCount() {
            return daftarRetur.size();
        }
        @Override
        public int getColumnCount() {
            return 7;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0:return "Tanggal";
                case 1:return "No. Ref";
                case 2:return "Pembelian";
                case 3:return "Supplier";
                case 4:return "Total Retur";
                case 5:return "Total Refund";
                case 6:return "Pembuat";
                default:return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            ReturPembelian rP = daftarRetur.get(rowIndex);
            switch (colIndex) {
                case 0:return rP.getTanggal();
                case 1:return rP.getNoRef();
                case 2:return rP.getPembelian().getNoRef();
                case 3:
                    if(rP.getSupplier()!=null){
                        return rP.getSupplier().getNamaSupplier();
                    }else{
                        return "-";
                    }
                case 4:return rP.getTotal();
                case 5:return rP.getTotalRefund();
                case 6:return rP.getPembuat().getIdPengguna();
                default:return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:return Date.class;
                case 4:return BigDecimal.class;
                case 5:return BigDecimal.class;
                default:return String.class;
            }
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReturPembelian = new javax.swing.JTable();
        lblJumlahData = new javax.swing.JLabel();
        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/1411950132.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel2.setText("Daftar Transaksi Pembelian");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel3.setText("Disini anda bisa menambah, mengedit atau menghapus data Transaksi Pembelian");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblReturPembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblReturPembelian);

        lblJumlahData.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblJumlahData.setText("jLabel4");

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
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
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

    private void initListener() {
        tblReturPembelian.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tblReturPembelian.getSelectedRow() >= 0) {
                idSelect = tblReturPembelian.getValueAt(tblReturPembelian.getSelectedRow(), 1).toString();
            }
        });
        tblReturPembelian.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    title = "Edit Data Pembelian";
                    if ("".equals(idSelect)) {
                        JOptionPane.showMessageDialog(null, "Data Pembelian Belum Terpilih");
                    } else {
                        cariSelect();
                        ReturPembelian retur = new DialogReturPembelian().showDialog(returPembelian, title);
                        returPembelian = new ReturPembelian();
                        if (retur != null) {
                            loadFormToModel(retur);
                            ClientLauncher.getTransaksiService().simpan(returPembelian);
                            isiTabelKategori();
                            JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                            title = null;
                        }
                    }
                }
            }
        });
        

        toolbar.getBtnRefresh().addActionListener((ActionEvent ae) -> {
            isiTabelKategori();
        });

        toolbar.getBtnBaru().addActionListener((ActionEvent ae) -> {
            isiTabelKategori();
            returPembelian = null;
            supplier = null;
            title = "Tambah Data Retur Pembelian";
            ReturPembelian retur = new DialogReturPembelian().showDialog(returPembelian, title);
            returPembelian = new ReturPembelian();
            if (retur != null) {
                loadFormToModel(retur);
                returPembelian.setNoRef("");
                ClientLauncher.getTransaksiService().simpan(returPembelian);
                isiTabelKategori();
                JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                title = null;
            }
            returPembelian = null;
        });

        toolbar.getBtnEdit().addActionListener((ActionEvent ae) -> {
            title = "Edit Data Barang";
            if ("".equals(idSelect)) {
                        JOptionPane.showMessageDialog(null, "Data Pembelian Belum Terpilih");
                    } else {
                        cariSelect();
                        ReturPembelian p = new DialogReturPembelian().showDialog(returPembelian, title);
                        returPembelian = new ReturPembelian();
                        if (p != null) {
                            loadFormToModel(p);
                            ClientLauncher.getTransaksiService().simpan(returPembelian);
                            isiTabelKategori();
                            JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                            title = null;
                        }
                    }
        });

        toolbar.getBtnHapus().addActionListener((ActionEvent ae) -> {
            /*if (pembelian == null) {
            JOptionPane.showMessageDialog(null, "Data Barang Belum Terpilih");
            } else {
            FrameUtama.getMasterService().hapus(pembelian);
            isiTabelKategori();
            JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
            }*/
        });
        toolbar.getBtnFilter().addActionListener((ActionEvent ae) -> {
            /*List <Barang> list = new FilterBarang().showDialog();
            System.out.println("Fiter Barang");*/
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJumlahData;
    private javax.swing.JTable tblReturPembelian;
    private com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter toolbar;
    // End of variables declaration//GEN-END:variables
}
