/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.tansaksi.pembelian;

import com.aan.girsang.api.model.transaksi.Pembelian;
import com.aan.girsang.api.model.transaksi.PembelianDetail;
import com.aan.girsang.api.util.BigDecimalRenderer;
import com.aan.girsang.api.util.DateRenderer;
import com.aan.girsang.api.util.IntegerRenderer;
import com.aan.girsang.api.util.POSTableCellRenderer;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.frame.FrameUtama;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author GIRSANG PC
 */
public class PilihPembelianDetail extends javax.swing.JDialog {
    
    private Pembelian pembelian;
    private PembelianDetail detail;
    private List<PembelianDetail> daftarDetail;
    private String idSelect;

    /**
     * Creates new form PilihPembelianDetail
     */
    public PilihPembelianDetail() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        initListener();
        toolbar.getTxtCari().addKeyListener(keyListener);
        tabel.addKeyListener(keyListener);
        tabel.setDefaultRenderer(Object.class, new POSTableCellRenderer());
        tabel.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        tabel.setDefaultRenderer(Date.class, new DateRenderer());
        tabel.setDefaultRenderer(Integer.class, new IntegerRenderer());
    }
    public PembelianDetail showDialog(Pembelian pembelian, String title){
        isiTabel(pembelian);
        setTitle(title);
        setLocationRelativeTo(null);
        setVisible(true);
        return this.detail;
    }
    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent ke) {
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            if(ke.getKeyChar()==KeyEvent.VK_ESCAPE){
                detail = new PembelianDetail();
                detail = null;
                dispose();
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {
        }
    };
    private class TabelModel extends AbstractTableModel{
        
        private List<PembelianDetail> details;
        String columnName [] = {"PLU", "Nama Produk", "Harga Beli","Satuan Beli", "QTY", "Sub Total","ID"};
        public TabelModel(List<PembelianDetail> details){
            this.details = details;
        }
        
        @Override
        public int getRowCount() {
            return details.size();
        }

        @Override
        public int getColumnCount() {
            return columnName.length;
        }
        @Override
        public String getColumnName(int col){
            return columnName[col];
        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            PembelianDetail detail = details.get(rowIndex);
            switch(colIndex){
                case 0:return detail.getBarang().getPlu();
                case 1:return detail.getBarang().getNamaBarang();
                case 2:return detail.getHargaBarang();
                case 3:return detail.getSatuanPembelian();
                case 4:return detail.getKuantitas();
                case 5:return detail.getSubTotal();
                case 6:return detail.getId();
                default:return"";
            }
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 2:return BigDecimal.class;
                case 4:return Integer.class;
                case 5:return BigDecimal.class;
                default:return String.class;
            }
        }
        
    }
    private void isiTabel(Pembelian p){
        daftarDetail = ClientLauncher.getTransaksiService().cariPembelianDetail(p);
        RowSorter<TableModel> sorter = new TableRowSorter<>(new TabelModel(daftarDetail));
        tabel.setRowSorter(sorter);
        tabel.setModel(new TabelModel(daftarDetail));
        toolbar.getTxtCari().setText("");
        hide(6);
    }
    private void hide(int index) {
        TableColumn column = tabel.getColumnModel().getColumn(index);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setPreferredWidth(0);
}
    private void cariData(){
        if(idSelect == null){
             JOptionPane.showMessageDialog(null, "Data Barang Belum Terpilih");
        }else{
            detail = new PembelianDetail();
            detail = ClientLauncher.getTransaksiService().cariDetailBeli(idSelect);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolBarSelectTanpaInput();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initListener(){
        tabel.getSelectionModel().addListSelectionListener((lse) -> {
            if(tabel.getSelectedRow()>=0){
                idSelect = tabel.getValueAt(tabel.getSelectedRow(), 6).toString();
                System.out.println(idSelect);
            }
        });
        tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    cariData();
                    if (detail == null) {
                        JOptionPane.showMessageDialog(null, "Data Barang Belum Dipilih");
                    } else {
                        dispose();
                    }
                }
            }
        });
        toolbar.getBtnPilih().addActionListener((ae) -> {
            cariData();
                    if (detail == null) {
                        JOptionPane.showMessageDialog(null, "Data Barang Belum Dipilih");
                    } else {
                        dispose();
                    }
        });
        toolbar.getBtnRefresh().addActionListener((ae) -> {
            isiTabel(pembelian);
        });
        toolbar.getBtnKeluar().addActionListener((ae) -> {
            detail = null;
            dispose();
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel;
    private com.aangirsang.girsang.toko.toolbar.ToolBarSelectTanpaInput toolbar;
    // End of variables declaration//GEN-END:variables
}