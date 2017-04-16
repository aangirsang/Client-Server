/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.security.pengguna;

import com.aan.girsang.api.model.security.Pengguna;
import com.aan.girsang.api.util.BigDecimalRenderer;
import com.aan.girsang.api.util.DateRenderer;
import com.aan.girsang.api.util.IntegerRenderer;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.frame.FrameUtama;
import com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter;
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
public class PenggunaPanel extends javax.swing.JPanel {

    private List<Pengguna> penggunas;
    private Pengguna pengguna;

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
    public ToolbarDenganFilter getToolbarDenganFilter() {
        return toolbar;
    }

    public PenggunaPanel() {
        initComponents();
        initListener();
        tabel.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        tabel.setDefaultRenderer(Date.class, new DateRenderer());
        tabel.setDefaultRenderer(Integer.class, new IntegerRenderer());
        isiTabelKategori();
    }
    private void ukuranTabel() {
        tabel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabel.getColumnModel().getColumn(0).setPreferredWidth(100);//ID Pengguna
        tabel.getColumnModel().getColumn(1).setPreferredWidth(100);//Username
        tabel.getColumnModel().getColumn(2).setPreferredWidth(350);//Nama Lengkap
        tabel.getColumnModel().getColumn(3).setPreferredWidth(150);//Tingkat Akses
        tabel.getColumnModel().getColumn(4).setPreferredWidth(330);//Alamat
        tabel.getColumnModel().getColumn(5).setPreferredWidth(100);//Kontak HP
        tabel.getColumnModel().getColumn(6).setPreferredWidth(100);//Kontak Telepon
        tabel.getColumnModel().getColumn(7).setPreferredWidth(100);//status
    }
    private void isiTabelKategori() {
        penggunas = ClientLauncher.getSecurityService().semuaPengguna();
        RowSorter<TableModel> sorter = new TableRowSorter<>(new TabelModel(penggunas));
        tabel.setRowSorter(sorter);
        tabel.setModel(new TabelModel(penggunas));
        toolbar.getTxtCari().setText("");
        ukuranTabel();
        lblJumlahData.setText(penggunas.size() + " Data Pengguna");
        idSelect = "";
    }
    
    private void loadFormToModel(Pengguna p) {
        pengguna = p;
    }
    private void cariSelect() {
        pengguna = new Pengguna();
        pengguna = ClientLauncher.getSecurityService().cariIdPengguna(idSelect);
    }
    private class TabelModel extends AbstractTableModel {
        private final List<Pengguna> daftarPengguna;
        public TabelModel(List<Pengguna> daftarPengguna) {
            this.daftarPengguna = daftarPengguna;
        }
        @Override
        public int getRowCount() {
            return daftarPengguna.size();
        }
        @Override
        public int getColumnCount() {
            return 8;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0:return "ID Pengguna";
                case 1:return "Username";
                case 2:return "Nama Lengkap";
                case 3:return "Tingkat Akses";
                case 4:return "Alamat";
                case 5:return "Kontak HP";
                case 6:return "Kontak Telepon";
                case 7:return "Status";
                default:return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            Pengguna p = penggunas.get(rowIndex);
            switch (colIndex) {
                case 0:return p.getIdPengguna();
                case 1:return p.getUserName();
                case 2:return p.getNamaLengkap();
                case 3:return p.getTingkatAkses().getNamaTingkatAkses();
                case 4:return p.getAlamat();
                case 5:return p.getHp();
                case 6:return p.getTelepon();
                case 7:
                    String status = "Tidak Aktif";
                    if(p.getStatus()==true){
                        status = "Aktif";
                    }
                    return status;
                default:return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 7:return BigDecimal.class;
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
        tabel = new javax.swing.JTable();
        lblJumlahData = new javax.swing.JLabel();
        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/User 64.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel2.setText("Daftar Data Pengguna");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel3.setText("Disini anda bisa menambah, mengedit atau menghapus data Pengguna");

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
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
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
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
                if (me.getClickCount() == 2) {
                    title = "Edit Data Tingkat Akses";
                    if ("".equals(idSelect)) {
                        JOptionPane.showMessageDialog(null, "Data Tingkat Akses Belum Terpilih");
                    } else {
                        cariSelect();
                        Pengguna p = new DialogPengguna().showDialog(pengguna, title);
                        pengguna = new Pengguna();
                        if (p != null) {
                            loadFormToModel(p);
                            ClientLauncher.getSecurityService().simpan(p);
                            isiTabelKategori();
                            JOptionPane.showMessageDialog(FrameUtama.getInstance(), 
                                    "Penyimpanan Sukses",
                                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
                            title = null;
                        }
                    }
                }
            }
        });
        toolbar.getBtnBaru().addActionListener((ae) -> {
            pengguna = null;
            title = "Tambah Data Tingkat Akses";
            Pengguna t = new DialogPengguna().showDialog(pengguna, title);
            pengguna = new Pengguna();
            if(t!=null){
                loadFormToModel(t);
                pengguna.setIdPengguna("");
                ClientLauncher.getSecurityService().simpan(pengguna);
                isiTabelKategori();
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
                Pengguna t = new DialogPengguna().showDialog(pengguna, title);
                pengguna = new Pengguna();
                if (t != null) {
                    loadFormToModel(t);
                    ClientLauncher.getSecurityService().simpan(t);
                    isiTabelKategori();
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
                ClientLauncher.getSecurityService().hapus(pengguna);
                isiTabelKategori();
                JOptionPane.showMessageDialog(FrameUtama.getInstance(), 
                        "Hapus Sukses",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJumlahData;
    private javax.swing.JTable tabel;
    private com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter toolbar;
    // End of variables declaration//GEN-END:variables
}
