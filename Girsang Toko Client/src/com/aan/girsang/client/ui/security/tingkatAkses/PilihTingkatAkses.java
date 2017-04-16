/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.security.tingkatAkses;

import com.aan.girsang.api.model.security.TingkatAkses;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.frame.FrameUtama;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public class PilihTingkatAkses extends javax.swing.JDialog {
    private List<TingkatAkses> tingkatAkseses;
    private TingkatAkses tingkatAkses;

    int IndexTab = 0;
    int aktifPanel = 0;
    String title, idSelect;

    public PilihTingkatAkses() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        initListener();
        toolbar.getTxtCari().setText("");
        isiTabel();
    }
    public Object showDialog(String judul) {
        pack();
        setTitle(judul);
        setLocationRelativeTo(null);
        setVisible(true);
        return tingkatAkses;
    }
    private void ukuranTabel() {
        tabel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabel.getColumnModel().getColumn(0).setPreferredWidth(100);//ID Tingkat Akses
        tabel.getColumnModel().getColumn(1).setPreferredWidth(150);//Nama Tingkat Akses
        tabel.getColumnModel().getColumn(2).setPreferredWidth(350);//Keterangan
    }
    private void isiTabel() {
        tingkatAkseses = ClientLauncher.getSecurityService().semuaTingkatAkses();
        RowSorter<TableModel> sorter = new TableRowSorter<>(new TabelModel(tingkatAkseses));
        tabel.setRowSorter(sorter);
        tabel.setModel(new TabelModel(tingkatAkseses));
        toolbar.getTxtCari().setText("");
        ukuranTabel();
        idSelect = "";
    }
    private void cariSelect() {
        tingkatAkses = new TingkatAkses();
        tingkatAkses = ClientLauncher.getSecurityService().cariIdTingkatAkses(idSelect);
    }
    private class TabelModel extends AbstractTableModel {
        private final List<TingkatAkses> daftarTingkatAkses;
        public TabelModel(List<TingkatAkses> daftarTingkatAkses) {
            this.daftarTingkatAkses = daftarTingkatAkses;
        }
        @Override
        public int getRowCount() {
            return daftarTingkatAkses.size();
        }
        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0:return "ID Tingkat Akses";
                case 1:return "Tingkat Akses";
                case 2:return "Keterangan";
                default:return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            TingkatAkses t = tingkatAkseses.get(rowIndex);
            switch (colIndex) {
                case 0:return t.getId();
                case 1:return t.getNamaTingkatAkses();
                case 2:return t.getKetTingkatAkses();
                default:return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                default:return String.class;
            }
        }
    }
    private void loadFormToModel(TingkatAkses t) {
        tingkatAkses = t;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolBarSelect();
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
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

     private void initListener(){
        tabel.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tabel.getSelectedRow() >= 0) {
                idSelect = tabel.getValueAt(tabel.getSelectedRow(), 0).toString();
            }
        });
        tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if ("".equals(idSelect)) {
                    JOptionPane.showMessageDialog(null, "Data Tingkat Akses Belum Terpilih");
                } else {
                    tingkatAkses = new TingkatAkses();
                    cariSelect();
                    dispose();
                }
            }
        });
        toolbar.getBtnBaru().addActionListener((ae) -> {
            tingkatAkses = null;
            title = "Tambah Data Tingkat Akses";
            TingkatAkses t = new DialogTingkatAkses().showDialog(tingkatAkses, title);
            tingkatAkses = new TingkatAkses();
            if(t!=null){
                loadFormToModel(t);
                tingkatAkses.setId("");
                ClientLauncher.getSecurityService().simpan(tingkatAkses);
                isiTabel();
                JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                        "Penyimpanan Sukses",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                title = null;
            }
        });
        toolbar.getBtnEdit().addActionListener((ae) -> {
            title = "Edit Data Tingkat Akses";
            if ("".equals(idSelect)) {
                JOptionPane.showMessageDialog(null, "Data Tingkat Akses Belum Terpilih");
            } else {
                cariSelect();
                TingkatAkses t = new DialogTingkatAkses().showDialog(tingkatAkses, title);
                tingkatAkses = new TingkatAkses();
                if (t != null) {
                    loadFormToModel(t);
                    ClientLauncher.getSecurityService().simpan(t);
                    isiTabel();
                    JOptionPane.showMessageDialog(FrameUtama.getInstance(), 
                            "Penyimpanan Sukses",
                            "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    title = null;
                }
            }
        });
        toolbar.getBtnHapus().addActionListener((ae) -> {
            if ("".equals(idSelect)) {
                JOptionPane.showMessageDialog(null, "Data Tingkat Akses Belum Terpilih");
            } else {
                cariSelect();
                ClientLauncher.getSecurityService().hapus(tingkatAkses);
                isiTabel();
                JOptionPane.showMessageDialog(FrameUtama.getInstance(), 
                        "Hapus Sukses",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        toolbar.getBtnPilih().addActionListener((ae) -> {
            if ("".equals(idSelect)) {
                JOptionPane.showMessageDialog(null, "Data Tingkat Akses Belum Terpilih");
            } else {
                tingkatAkses = new TingkatAkses();
                cariSelect();
                dispose();
            }
        });
        toolbar.getBtnKeluar().addActionListener((ae) -> {
            tingkatAkses = null;
            dispose();
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel;
    private com.aangirsang.girsang.toko.toolbar.ToolBarSelect toolbar;
    // End of variables declaration//GEN-END:variables
}