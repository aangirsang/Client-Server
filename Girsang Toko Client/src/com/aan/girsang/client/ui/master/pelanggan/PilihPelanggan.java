/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.master.pelanggan;

import com.aan.girsang.api.model.master.Pelanggan;
import com.aan.girsang.api.util.TextComponentUtils;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.frame.FrameUtama;
import com.aangirsang.girsang.toko.toolbar.ToolBarSelect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ITSUSAHBRO
 */
public class PilihPelanggan extends javax.swing.JDialog {

    private List<Pelanggan> daftarPelanggan;
    private Pelanggan pelanggan;
    private String id;
    ToolBarSelect toolBar = new ToolBarSelect();

    /**
     * Creates new form KategoriProdukDialogPanel
     */
    public PilihPelanggan() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        initListener();
        isiTabelKategori();
        TextComponentUtils.setAutoUpperCaseText(50, toolbar.getTxtCari());

        tabel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
    public Object showDialog(String judul) {
        pack();
        setTitle(judul);
        setLocationRelativeTo(null);
        setVisible(true);
        return pelanggan;
    }
    private void isiTabelKategori() {
        daftarPelanggan = ClientLauncher.getMasterService().semuaPelanggan();
        tabel.setModel(new TabelModel(daftarPelanggan));
        toolbar.getTxtCari().setText("");
        tabel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabel.getColumnModel().getColumn(0).setPreferredWidth(120);//ID
        tabel.getColumnModel().getColumn(1).setPreferredWidth(350);//Kategori
        id = null;
    }
    private class TabelModel extends AbstractTableModel {

        private final List<Pelanggan> listPelanggan;
        String columnNames[] = {
            "Kode Pelanggan", 
            "Nama Pelanggan",
            "Alamat",
            "HP",
            "HP",
            "Telepon",
            "Saldo Piutang",
        };

        public TabelModel(List<Pelanggan> listPelanggan) {
            this.listPelanggan = listPelanggan;
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
            Pelanggan p = daftarPelanggan.get(rowIndex);
            switch (columnIndex) {
                case 0:return p.getIdPelanggan();
                case 1:return p.getNama();
                case 2:return p.getAlamat();
                case 3:return p.getCp1();
                case 4:return p.getCp2();
                case 5:return p.getTelepon();
                case 6:return p.getSaldoPiutang();
                default:return "";
            }
        }

    }
    private void initListener() {
        tabel.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tabel.getSelectedRow() >= 0) {
                id = (String) tabel.getValueAt(tabel.getSelectedRow(), 0);
            }
        });
        tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mE) {
                if (mE.getClickCount() == 2) {
                    if (id == null) {
                        JOptionPane.showMessageDialog(null, "Kategori Belum Dipilih");
                    } else {
                        pelanggan = ClientLauncher.getMasterService().cariIdPelanggan(id);
                        dispose();
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
                    daftarPelanggan = ClientLauncher.getMasterService()
                            .cariNamaPelanggan(toolbar.getTxtCari().getText());
                    tabel.setModel(new TabelModel(daftarPelanggan));
                    tabel.getColumnModel().getColumn(0).setPreferredWidth(220);
                    tabel.getColumnModel().getColumn(1).setPreferredWidth(520);
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

        toolbar.getBtnPilih().addActionListener((ActionEvent ae) -> {
            if (id == null) {
                JOptionPane.showMessageDialog(null, "Kategori Belum Dipilih");
            } else {
                pelanggan = ClientLauncher.getMasterService().cariIdPelanggan(id);
                dispose();

            }
        });

        toolbar.getBtnBaru().addActionListener((ActionEvent ae) -> {
            pelanggan = null;
            String judul = "Penambahan Data";
            Pelanggan p = new DialogPelanggan().showDialog(pelanggan, judul);
            pelanggan = new Pelanggan();
            if (p != null) {
                pelanggan = p;
                ClientLauncher.getMasterService().simpan(pelanggan);
                isiTabelKategori();
            }
        });

        toolbar.getBtnEdit().addActionListener((ActionEvent ae) -> {
            if (id == null) {
                JOptionPane.showMessageDialog(null, "Data Kategori Belum Terpilih");
            } else {
                pelanggan = ClientLauncher.getMasterService().cariIdPelanggan(id);
                String judul = "Penambahan Data";
                Pelanggan p = new DialogPelanggan().showDialog(pelanggan, judul);
                pelanggan = new Pelanggan();
                if (p != null) {
                    pelanggan = p;

                    ClientLauncher.getMasterService().simpan(pelanggan);
                    isiTabelKategori();
                    JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                }

            }
        });

        toolbar.getBtnHapus().addActionListener((ActionEvent ae) -> {
            if (id == null) {
                JOptionPane.showMessageDialog(null, "Data Kategori Belum Terpilih");
            } else {
                pelanggan = ClientLauncher.getMasterService().cariIdPelanggan(id);
                ClientLauncher.getMasterService().hapus(pelanggan);
                isiTabelKategori();
                JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
            }
        });
        toolbar.getBtnKeluar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                pelanggan = null;
                dispose();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolBarSelect();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabel.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(toolbar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel;
    private com.aangirsang.girsang.toko.toolbar.ToolBarSelect toolbar;
    // End of variables declaration//GEN-END:variables
}
