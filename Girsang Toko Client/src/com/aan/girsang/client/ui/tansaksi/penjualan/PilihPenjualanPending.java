/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.tansaksi.penjualan;

import com.aan.girsang.api.model.master.Pelanggan;
import com.aan.girsang.api.model.transaksi.Penjualan;
import com.aan.girsang.api.util.BigDecimalRenderer;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.frame.FrameUtama;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
 * @author GIRSANG PC
 */
public class PilihPenjualanPending extends javax.swing.JDialog {

    private List<Penjualan> daftarPenjualan;
    private Penjualan penjualan;
    private Pelanggan pelanggan;
    String title, idSelect;
    
    public PilihPenjualanPending() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());
        tblBarang.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        isiTabelBarang();
        initListener();
    }
    public Penjualan showDialog(String judul) {
        pack();
        setTitle(judul);
        setLocationRelativeTo(null);
        setVisible(true);
        return penjualan;
    }
    private void ukuranTabelBarang() {
        tblBarang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblBarang.getColumnModel().getColumn(0).setPreferredWidth(150);//PLU
        tblBarang.getColumnModel().getColumn(1).setPreferredWidth(100);//Barcode
        tblBarang.getColumnModel().getColumn(2).setPreferredWidth(200);//Nama
        tblBarang.getColumnModel().getColumn(3).setPreferredWidth(200);//Satuan
        tblBarang.getColumnModel().getColumn(4).setPreferredWidth(100);//Stok
        tblBarang.getColumnModel().getColumn(5).setPreferredWidth(100);//Stok Gudang
    }
    private void isiTabelBarang() {
        daftarPenjualan = ClientLauncher.getTransaksiService().pending(true);
        RowSorter<TableModel> sorter = new TableRowSorter<>(new BarangTabelModel(daftarPenjualan));
        tblBarang.setRowSorter(sorter);
        tblBarang.setModel(new BarangTabelModel(daftarPenjualan));
        toolbar.getTxtCari().setText("");
        ukuranTabelBarang();
        lblJumlahData.setText(daftarPenjualan.size() + " Data Barang");
        idSelect = "";
    }
    
    private void cariSelect() {
        penjualan = new Penjualan();
        penjualan = ClientLauncher.getTransaksiService().cariIDPenjualan(idSelect);
    }
    private class BarangTabelModel extends AbstractTableModel {

        private final List<Penjualan> listPenjualan;

        public BarangTabelModel(List<Penjualan> listPenjualan) {
            this.listPenjualan = listPenjualan;
        }

        @Override
        public int getRowCount() {
            return listPenjualan.size();
        }

        @Override
        public int getColumnCount() {
            return 6;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0: return "Tanggal";
                case 1: return "No. Ref";
                case 2: return "Kasir";
                case 3: return "Pelanggan";
                case 4: return "Total";
                case 5: return "Lokasi";
                default: return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            Penjualan p = daftarPenjualan.get(rowIndex);
            switch (colIndex) {
                case 0: return p.getTanggal();
                case 1: return p.getNoRef();
                case 2: return p.getKasir().getNamaLengkap();
                case 3: 
                    if(p.getPelanggan()!=null){
                        return p.getPelanggan().getNama();
                    }else{
                        return "UMUM";
                    }
                case 4: return p.getTotal();
                case 5: return p.getLokasi();
                default: return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 4: return BigDecimal.class;
                default: return String.class;
            }
        }
    }
    private class MyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                penjualan = null;
                dispose();
            }
            return false;
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
        tblBarang = new javax.swing.JTable();
        lblJumlahData = new javax.swing.JLabel();
        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolBarSelectTanpaInput();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblBarang);

        lblJumlahData.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblJumlahData.setText(org.openide.util.NbBundle.getMessage(PilihPenjualanPending.class, "PilihPenjualanPending.lblJumlahData.text")); // NOI18N

        btnCancel.setText(org.openide.util.NbBundle.getMessage(PilihPenjualanPending.class, "PilihPenjualanPending.btnCancel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblJumlahData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJumlahData)
                    .addComponent(btnCancel))
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
                        "Apakah Anda Yakin Ingin Membatalkan Pemilihan Barang", 
                        "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    penjualan = null;
                    dispose();
                }
            }
        });
        tblBarang.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tblBarang.getSelectedRow() >= 0) {
                idSelect = tblBarang.getValueAt(tblBarang.getSelectedRow(), 1).toString();
            }
        });
        tblBarang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    cariSelect();
                    if (penjualan == null) {
                        JOptionPane.showMessageDialog(null, "Data Barang Belum Dipilih");
                    } else {
                        cariSelect();
                        dispose();
                    }
                }
            }
        });

        toolbar.getBtnRefresh().addActionListener((ActionEvent ae) -> {
            isiTabelBarang();
        });

        toolbar.getBtnPilih().addActionListener(((ae) -> {
            cariSelect();
            if (penjualan == null) {
                JOptionPane.showMessageDialog(null, "Data Barang Belum Dipilih");
            } else {
                cariSelect();
                dispose();
            }
        }));
        toolbar.getBtnKeluar().addActionListener((ae) -> {
            String ObjButtons[] = {"Ya", "Tidak"};
            int PromptResult = JOptionPane.showOptionDialog(null,
                    "Apakah Anda Yakin Ingin Membatalkan Pemilihan Barang", 
                    "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    penjualan = null;
                    dispose();
                }
        });
        btnCancel.addActionListener((ActionEvent ae) -> {
            cariSelect();
            if(penjualan!=null){
                String ObjButtons[] = {"Ya", "Tidak"};
                int PromptResult = JOptionPane.showOptionDialog(null,
                    "Apakah Anda Yakin Ingin Membatalkan Pemilihan Barang", 
                    "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, ObjButtons, ObjButtons[1]);
                
                if (PromptResult == JOptionPane.YES_OPTION) {
                    ClientLauncher.getTransaksiService().hapus(penjualan);
                    isiTabelBarang();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJumlahData;
    private javax.swing.JTable tblBarang;
    private com.aangirsang.girsang.toko.toolbar.ToolBarSelectTanpaInput toolbar;
    // End of variables declaration//GEN-END:variables
}
