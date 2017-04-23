/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.master.pelanggan;

import com.aan.girsang.api.model.master.Pelanggan;
import com.aan.girsang.client.ui.master.supplier.*;
import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.api.util.BigDecimalRenderer;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
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
public class PanelPelanggan extends javax.swing.JPanel {

    private List<Pelanggan> daftarPelanggan;
    private Pelanggan pelanggan;

    int IndexTab = 0;
    int aktifPanel = 0;
    String title;
    ToolbarTanpaFilter toolBar = new ToolbarTanpaFilter();

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

    public ToolbarTanpaFilter getBtnKeluar() {
        return toolbar;
    }

    /**
     * Creates new form KategoriPanel
     */
    public PanelPelanggan() {
        initComponents();
        initListener();
        tabel.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        isiTabelKategori();
    }

    private void ukuranTabelProduk() {
        tabel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabel.getColumnModel().getColumn(0).setPreferredWidth(100);//Kode SUpplier
        tabel.getColumnModel().getColumn(1).setPreferredWidth(200);//Nama
        tabel.getColumnModel().getColumn(2).setPreferredWidth(350);//Alamat
        tabel.getColumnModel().getColumn(3).setPreferredWidth(100);//HP
        tabel.getColumnModel().getColumn(4).setPreferredWidth(100);//telepon
        tabel.getColumnModel().getColumn(5).setPreferredWidth(100);//Saldo
        tabel.getColumnModel().getColumn(6).setPreferredWidth(200);//Email
        tabel.getColumnModel().getColumn(7).setPreferredWidth(100);//Kota
    }

    private void isiTabelKategori() {
        daftarPelanggan = ClientLauncher.getMasterService().semuaPelanggan();
        RowSorter<TableModel> sorter = new TableRowSorter<>(new TabelModel(daftarPelanggan));
        tabel.setRowSorter(sorter);
        tabel.setModel(new TabelModel(daftarPelanggan));
        toolbar.getTxtCari().setText("");
        ukuranTabelProduk();
        lblJumlahData.setText(daftarPelanggan.size() + " Data Pelanggan");
    }

    private void loadFormToModel(Pelanggan p) {
        pelanggan = p;
    }

    private class TabelModel extends AbstractTableModel {

        private final List<Pelanggan> listPelanggan;
        String columnNames[] = {
            "Kode Pelanggan",
            "Nama Pelanggan",
            "Alamat",
            "CP 1",
            "CP 2",
            "Telepon",
            "Saldo Piutang",
            "Point"};

        public TabelModel(List<Pelanggan> list) {
            this.listPelanggan = list;
        }

        @Override
        public int getRowCount() {
            return listPelanggan.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Pelanggan p = listPelanggan.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return p.getIdPelanggan();
                case 1:
                    return p.getNama();
                case 2:
                    return p.getAlamat();
                case 3:
                    return p.getCp1();
                case 4:
                    return p.getCp2();
                case 5:
                    return p.getTelepon();
                case 6:
                    return p.getSaldoPiutang();
                case 7:
                    return p.getPoint();
                default:
                    return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 6:
                    return BigDecimal.class;
                default:
                    return String.class;
            }
        }
    }

    private void initListener() {
        tabel.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tabel.getSelectedRow() >= 0) {
                String id = tabel.getValueAt(tabel.getSelectedRow(), 0).toString();
                pelanggan = new Pelanggan();
                pelanggan = ClientLauncher.getMasterService().cariIdPelanggan(id);
            }
        });
        tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    title = "Edit Data Supplier";
                    if (pelanggan == null) {
                        JOptionPane.showMessageDialog(null, "Data Pelanggan Belum Terpilih");
                    } else {
                        Pelanggan p = new Pelanggan();//SupplierDialog().showDialog(supplier, title);
                        pelanggan = new Pelanggan();
                        if (p != null) {
                            loadFormToModel(p);
                            ClientLauncher.getMasterService().simpan(pelanggan);
                            isiTabelKategori();
                            JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                            title = null;
                        }
                    }
                }
            }
        });
        toolbar.getTxtCari().addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if ("".equals(toolbar.getTxtCari().getText())) {
                    isiTabelKategori();
                } else {
                    daftarPelanggan = ClientLauncher.getMasterService().cariNamaPelanggan(toolbar.getTxtCari().getText());
                    tabel.setModel(new TabelModel(daftarPelanggan));
                    RowSorter<TableModel> sorter = new TableRowSorter<>(new TabelModel(daftarPelanggan));
                    tabel.setRowSorter(sorter);
                    ukuranTabelProduk();
                    int jml = daftarPelanggan.size();
                }
            }

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }
        });

        toolbar.getBtnRefresh().addActionListener((ActionEvent ae) -> {
            isiTabelKategori();
        });

        toolbar.getBtnBaru().addActionListener((ActionEvent ae) -> {
            isiTabelKategori();
            pelanggan = null;
            title = "Tambah Data Supplier";
            Pelanggan s = new Pelanggan();//Supplier s = new SupplierDialog().showDialog(supplier, title);
            pelanggan = new Pelanggan();
            if (s != null) {
                loadFormToModel(s);
                pelanggan.setIdPelanggan("");
                ClientLauncher.getMasterService().simpan(pelanggan);
                isiTabelKategori();
                JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                title = null;
            }
            pelanggan = null;
        });

        toolbar.getBtnEdit().addActionListener((ActionEvent ae) -> {
            title = "Edit Data Supplier";
            if (pelanggan == null) {
                JOptionPane.showMessageDialog(null, "Data Pelanggan Belum Terpilih");
            } else {
                Pelanggan p = new Pelanggan();//SupplierDialog().showDialog(supplier, title);
                pelanggan = new Pelanggan();
                if (p != null) {
                    loadFormToModel(p);
                    ClientLauncher.getMasterService().simpan(pelanggan);
                    isiTabelKategori();
                    JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                    title = null;
                }
            }
        });

        toolbar.getBtnHapus().addActionListener((ActionEvent ae) -> {
            if (pelanggan == null) {
                JOptionPane.showMessageDialog(null, "Data Supplier Belum Terpilih");
            } else {
                ClientLauncher.getMasterService().hapus(pelanggan);
                isiTabelKategori();
                JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
            }
        });
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
        tabel = new javax.swing.JTable();
        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter();
        lblJumlahData = new javax.swing.JLabel();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Pelanggan 64.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel2.setText("Daftar Pelanggan");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel3.setText("Disini anda bisa menambah, mengedit atau menghapus data pelanggan");

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(5, 5, 5))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblJumlahData, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJumlahData)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJumlahData;
    private javax.swing.JTable tabel;
    private com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter toolbar;
    // End of variables declaration//GEN-END:variables
}
