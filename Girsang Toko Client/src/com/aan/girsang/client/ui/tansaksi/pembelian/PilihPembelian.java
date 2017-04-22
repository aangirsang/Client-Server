/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.tansaksi.pembelian;

import com.aan.girsang.client.ui.master.barang.*;
import com.aan.girsang.api.model.master.Barang;
import com.aan.girsang.api.model.master.Supplier;
import com.aan.girsang.api.model.transaksi.Pembelian;
import com.aan.girsang.api.util.BigDecimalRenderer;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.frame.FrameUtama;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
 * @author GIRSANG PC
 */
public class PilihPembelian extends javax.swing.JDialog {

    private Pembelian pembelian;
    private Supplier supplier;
    private List<Pembelian> daftarPembelian;
    String title, idSelect;
    /**
     * Creates new form PilihBarangDialog
     */
    public PilihPembelian() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        tabel.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        isiTabel();
        initListener();
    }
    public Object showDialog(String judul) {
        pack();
        setTitle(judul);
        setLocationRelativeTo(null);
        setVisible(true);
        return pembelian;
    }
    private void ukuranTabelBarang() {
        tabel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabel.getColumnModel().getColumn(0).setPreferredWidth(100);//PLU
        tabel.getColumnModel().getColumn(1).setPreferredWidth(150);//Barcode
        tabel.getColumnModel().getColumn(2).setPreferredWidth(350);//Nama
        tabel.getColumnModel().getColumn(3).setPreferredWidth(150);//Satuan
        tabel.getColumnModel().getColumn(4).setPreferredWidth(100);//Stok
        tabel.getColumnModel().getColumn(5).setPreferredWidth(100);//Stok Gudang
    }
    private void isiTabel() {
        daftarPembelian = ClientLauncher.getTransaksiService().semuaPembelian();
        RowSorter<TableModel> sorter = new TableRowSorter<>(new TabelModel(daftarPembelian));
        tabel.setRowSorter(sorter);
        tabel.setModel(new TabelModel(daftarPembelian));
        toolbar.getTxtCari().setText("");
        ukuranTabelBarang();
        lblJumlahData.setText(daftarPembelian.size() + " Data Barang");
        idSelect = "";
    }
    private void loadFormToModel(Pembelian p) {
        pembelian.setNoRef(p.getNoRef());
        pembelian.setTanggal(p.getTanggal());
        pembelian.setNoFaktur(p.getNoFaktur());
        pembelian.setSupplier(p.getSupplier());
        pembelian.setKredit(p.getKredit());
        pembelian.setDaftarKredit(p.getDaftarKredit());
        pembelian.setTotal(p.getTotal());
        pembelian.setPembuat(p.getPembuat());
        pembelian.setPembelianDetails(p.getPembelianDetails());
        pembelian.setLokasi(p.getLokasi());
    }
    private void cariSelect() {
        pembelian = new Pembelian();
        pembelian = ClientLauncher.getTransaksiService().cariPembelian(idSelect);
    }
    private class TabelModel extends AbstractTableModel {

        private final List<Pembelian> daftarPembelian;

        public TabelModel(List<Pembelian> daftarPembelian) {
            this.daftarPembelian = daftarPembelian;
        }

        @Override
        public int getRowCount() {
            return daftarPembelian.size();
        }

        @Override
        public int getColumnCount() {
            return 10;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0: return "Tanggal";
                case 1: return "No Ref";
                case 2: return "Supplier";
                case 3: return "Kredit";
                case 4: return "Total Pembelian";
                case 5: return "Pembuat";
                default: return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            Pembelian p = daftarPembelian.get(rowIndex);
            switch (colIndex) {
                case 0: return p.getTanggal();
                case 1: return p.getNoRef();
                case 2: return p.getSupplier().getNamaSupplier();
                case 3: return p.getKredit();
                case 4: return p.getTotal();
                case 5: return p.getPembuat().getNamaLengkap();
                default: return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0: return Date.class;
                case 3: return Boolean.class;
                case 4: return BigDecimal.class;
                default: return String.class;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        lblJumlahData = new javax.swing.JLabel();
        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolBarSelectTanpaInput();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabel);

        lblJumlahData.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblJumlahData.setText(org.openide.util.NbBundle.getMessage(PilihPembelian.class, "PilihPembelian.lblJumlahData.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblJumlahData, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(421, 421, 421))
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJumlahData)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void initListener() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Ya", "Tidak"};
                int PromptResult = JOptionPane.showOptionDialog(null, 
                        "Apakah Anda Yakin Ingin Membatalkan Pemilihan Pembelian", 
                        "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    pembelian = null;
                    dispose();
                }
            }
        });
        tabel.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tabel.getSelectedRow() >= 0) {
                idSelect = tabel.getValueAt(tabel.getSelectedRow(), 1).toString();
            }
        });
        tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    cariSelect();
                    if (pembelian == null) {
                        JOptionPane.showMessageDialog(null, "Data Pembelian Belum Dipilih");
                    } else {
                        cariSelect();
                        dispose();
                    }
                }
            }
        });
        toolbar.getTxtCari().addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if ("".equals(toolbar.getTxtCari().getText())) {
                    isiTabel();
                } else {
                    daftarPembelian = (List<Pembelian>) ClientLauncher.getTransaksiService().cariPembelian(toolbar.getTxtCari().getText());
                    tabel.setModel(new TabelModel(daftarPembelian));
                    RowSorter<TableModel> sorter = new TableRowSorter<>(new TabelModel(daftarPembelian));
                    tabel.setRowSorter(sorter);
                    ukuranTabelBarang();
                    int jml = daftarPembelian.size();
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
            isiTabel();
        });

        toolbar.getBtnPilih().addActionListener(((ae) -> {
            cariSelect();
            if (pembelian == null) {
                JOptionPane.showMessageDialog(null, "Data Pembelian Belum Dipilih");
            } else {
                cariSelect();
                dispose();
            }
        }));
        toolbar.getBtnKeluar().addActionListener((ae) -> {
            String ObjButtons[] = {"Ya", "Tidak"};
            int PromptResult = JOptionPane.showOptionDialog(null,
                    "Apakah Anda Yakin Ingin Membatalkan Pemilihan Pembelian", 
                    "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    pembelian = null;
                    dispose();
                }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJumlahData;
    private javax.swing.JTable tabel;
    private com.aangirsang.girsang.toko.toolbar.ToolBarSelectTanpaInput toolbar;
    // End of variables declaration//GEN-END:variables
}
