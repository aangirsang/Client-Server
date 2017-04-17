/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.tansaksi.pembelian;

import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.api.model.transaksi.PelunasanHutang;
import com.aan.girsang.api.util.BigDecimalRenderer;
import com.aan.girsang.api.util.DateRenderer;
import com.aan.girsang.api.util.IntegerRenderer;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.tansaksi.pembelian.DialogPelunasanHutang;
import com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author ITSUSAHBRO
 */
public class PanelPelunasanHutang extends javax.swing.JPanel {

    private List<PelunasanHutang> daftarPelunasan;
    private PelunasanHutang pelunasanHutang;
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

    public PanelPelunasanHutang() {
        initComponents();
        initListener();
        tblPelunasan.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        tblPelunasan.setDefaultRenderer(Date.class, new DateRenderer());
        tblPelunasan.setDefaultRenderer(Integer.class, new IntegerRenderer());
        isiTabelKategori();
    }
    private void ukuranTabelBarang() {
        tblPelunasan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblPelunasan.getColumnModel().getColumn(0).setPreferredWidth(200);//Tanggal
        tblPelunasan.getColumnModel().getColumn(1).setPreferredWidth(100);//No. Ref
        tblPelunasan.getColumnModel().getColumn(2).setPreferredWidth(100);//No. Kwitansi
        tblPelunasan.getColumnModel().getColumn(3).setPreferredWidth(350);//Supplier
        tblPelunasan.getColumnModel().getColumn(4).setPreferredWidth(100);//Jlh Bayar
        tblPelunasan.getColumnModel().getColumn(5).setPreferredWidth(300);//Pembuat
    }
    private void isiTabelKategori() {
        daftarPelunasan = ClientLauncher.getTransaksiService().semua();
        RowSorter<TableModel> sorter = new TableRowSorter<>(new PembelianTabelModel(daftarPelunasan));
        tblPelunasan.setRowSorter(sorter);
        tblPelunasan.setModel(new PembelianTabelModel(daftarPelunasan));
        toolbar.getTxtCari().setText("");
        ukuranTabelBarang();
        lblJumlahData.setText(daftarPelunasan.size() + " Data Pembelian");
        idSelect = "";
    }
    
    private void loadFormToModel(PelunasanHutang p) {
        pelunasanHutang.setNoRef(p.getNoRef());
        pelunasanHutang.setTanggal(p.getTanggal());
        pelunasanHutang.setNoKwitansi(p.getNoKwitansi());
        pelunasanHutang.setSupplier(p.getSupplier());
        pelunasanHutang.setJlhBayar(p.getJlhBayar());
        pelunasanHutang.setPembuat(p.getPembuat());
        pelunasanHutang.setPelunasanHutangDetails(p.getPelunasanHutangDetails());
    }
    private void cariSelect() {
        pelunasanHutang = new PelunasanHutang();
        pelunasanHutang = ClientLauncher.getTransaksiService().cariId(idSelect);
    }
    private class PembelianTabelModel extends AbstractTableModel {
        private final List<PelunasanHutang> daftarPelunasan;
        public PembelianTabelModel(List<PelunasanHutang> daftarPelunasan) {
            this.daftarPelunasan = daftarPelunasan;
        }
        @Override
        public int getRowCount() {
            return daftarPelunasan.size();
        }
        @Override
        public int getColumnCount() {
            return 8;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0:return "Tanggal";
                case 1:return "No. Ref";
                case 2:return "No. Kwitansi";
                case 3:return "Supplier";
                case 4:return "Jumlah Bayar";
                case 5:return "Pembuat";
                default:return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            PelunasanHutang p = daftarPelunasan.get(rowIndex);
            switch (colIndex) {
                case 0:return p.getTanggal();
                case 1:return p.getNoRef();
                case 2:return p.getNoKwitansi();
                case 3:
                    if(p.getSupplier()!=null){
                        return p.getSupplier().getNamaSupplier();
                    }else{
                        return "-";
                    }
                case 4:return p.getJlhBayar();
                case 5:return p.getPembuat().getNamaLengkap();
                default:return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:return Date.class;
                case 4:return BigDecimal.class;
                default:return String.class;
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPelunasan = new javax.swing.JTable();
        lblJumlahData = new javax.swing.JLabel();
        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/1411950132.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel2.setText("Daftar Transaksi Pelunasan Hutang");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel3.setText("Disini anda bisa menambah, mengedit atau menghapus data Transaksi Pelunasan Hutang");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblPelunasan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tanggal", "No. Ref", "No. Faktur", "Supplier", "Kredit", "Tgl. Tempo", "Jumlah Pembelian", "Pembuat"
            }
        ));
        jScrollPane1.setViewportView(tblPelunasan);
        if (tblPelunasan.getColumnModel().getColumnCount() > 0) {
            tblPelunasan.getColumnModel().getColumn(0).setResizable(false);
            tblPelunasan.getColumnModel().getColumn(1).setResizable(false);
        }

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
        tblPelunasan.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tblPelunasan.getSelectedRow() >= 0) {
                idSelect = tblPelunasan.getValueAt(tblPelunasan.getSelectedRow(), 1).toString();
            }
        });
        tblPelunasan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    title = "Edit Data Pembelian";
                    if ("".equals(idSelect)) {
                        JOptionPane.showMessageDialog(null, "Data Pembelian Belum Terpilih");
                    } else {
                        cariSelect();
                        PelunasanHutang p = new DialogPelunasanHutang().showDialog(pelunasanHutang, title);
                        pelunasanHutang = new PelunasanHutang();
                        if (p != null) {
                            loadFormToModel(p);
                            ClientLauncher.getTransaksiService().simpan(pelunasanHutang);
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
            pelunasanHutang = null;
            supplier = null;
            title = "Tambah Data Barang";
            PelunasanHutang p = new DialogPelunasanHutang().showDialog(pelunasanHutang, title);
            pelunasanHutang = new PelunasanHutang();
            if (p != null) {
                loadFormToModel(p);
                pelunasanHutang.setNoRef("");
                ClientLauncher.getTransaksiService().simpan(pelunasanHutang);
                isiTabelKategori();
                JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                title = null;
            }
            pelunasanHutang = null;
        });

        toolbar.getBtnEdit().addActionListener((ActionEvent ae) -> {
            title = "Edit Data Barang";
            if ("".equals(idSelect)) {
                        JOptionPane.showMessageDialog(null, "Data Pembelian Belum Terpilih");
                    } else {
                        cariSelect();
                        PelunasanHutang p = new DialogPelunasanHutang().showDialog(pelunasanHutang, title);
                        pelunasanHutang = new PelunasanHutang();
                        if (p != null) {
                            loadFormToModel(p);
                            ClientLauncher.getTransaksiService().simpan(pelunasanHutang);
                            isiTabelKategori();
                            JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                            title = null;
                        }
                    }
        });

        toolbar.getBtnHapus().addActionListener((ActionEvent ae) -> {
            if ("".equals(idSelect)) {
                JOptionPane.showMessageDialog(null, "Data Pembelian Belum Terpilih");
            } else {
                cariSelect();
                ClientLauncher.getTransaksiService().hapus(pelunasanHutang);
                isiTabelKategori();
                JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
            }
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
    private javax.swing.JTable tblPelunasan;
    private com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter toolbar;
    // End of variables declaration//GEN-END:variables
}
