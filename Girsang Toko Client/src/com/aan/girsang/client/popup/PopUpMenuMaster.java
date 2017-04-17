/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.popup;

import com.aan.girsang.client.ui.master.barang.BarangPanel;
import com.aan.girsang.client.ui.master.golongan.PanelGolonganBarang;
import com.aan.girsang.client.ui.master.satuan.SatuanBarangPanel;
import com.aan.girsang.client.ui.master.supplier.SupplierPanel;
import com.aan.girsang.client.ui.security.pengguna.PenggunaPanel;
import com.aan.girsang.client.ui.security.tingkatAkses.TingkatAksesPanel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

/**
 *
 * @author ITSUSAHBRO
 */
public class PopUpMenuMaster extends AbstractButton {

    JButton actionButton;
    JPopupMenu popupMenu;

    public PopUpMenuMaster(JTabbedPane TP, JPopupMenu popupMenuMaster, JButton btnMaster) {
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Golongan Barang") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                PanelGolonganBarang golonganBarangPanel = new PanelGolonganBarang();
                golonganBarangPanel.setName("Daftar Kategori Barang");
                if (golonganBarangPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(golonganBarangPanel.getIndexTab());
                } else {
                    golonganBarangPanel.setAktifPanel(golonganBarangPanel.getAktifPanel() + 1);
                    TP.addTab(golonganBarangPanel.getName(), golonganBarangPanel);
                    golonganBarangPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(golonganBarangPanel.getIndexTab());
                    
                    golonganBarangPanel.getToolbarTanpaFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(golonganBarangPanel);
                        golonganBarangPanel.setAktifPanel(golonganBarangPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(golonganBarangPanel.getIndexTab() -1);
                    });
                }
            }
        }));
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Barang") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                BarangPanel barangPanel = new BarangPanel();
                barangPanel.setName("Daftar Barang");
                if (barangPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(barangPanel.getIndexTab());
                } else {
                    barangPanel.setAktifPanel(barangPanel.getAktifPanel() + 1);
                    TP.addTab(barangPanel.getName(), barangPanel);
                    barangPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(barangPanel.getIndexTab());
                    
                    barangPanel.getToolbarDenganFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(barangPanel);
                        barangPanel.setAktifPanel(barangPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(barangPanel.getIndexTab()-1);
                    });
                }
            }
        }));
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Satuan Barang") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                SatuanBarangPanel satuanBarangPanel = new SatuanBarangPanel();
                satuanBarangPanel.setName("Daftar Satuan Barang");
                if (satuanBarangPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(satuanBarangPanel.getIndexTab());
                } else {
                    satuanBarangPanel.setAktifPanel(satuanBarangPanel.getAktifPanel() + 1);
                    TP.addTab(satuanBarangPanel.getName(), satuanBarangPanel);
                    satuanBarangPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(satuanBarangPanel.getIndexTab());
                    
                    satuanBarangPanel.getToolbarTanpaFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(satuanBarangPanel);
                        satuanBarangPanel.setAktifPanel(satuanBarangPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(satuanBarangPanel.getIndexTab()-1);
                    });
                }
            }
        }));
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Supplier") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                SupplierPanel supplierPanel = new SupplierPanel();
                supplierPanel.setName("Daftar Supplier");
                if (supplierPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(supplierPanel.getIndexTab());
                } else {
                    supplierPanel.setAktifPanel(supplierPanel.getAktifPanel() + 1);
                    TP.addTab(supplierPanel.getName(), supplierPanel);
                    supplierPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(supplierPanel.getIndexTab());
                    
                    supplierPanel.getToolbarTanpaFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(supplierPanel);
                        supplierPanel.setAktifPanel(supplierPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(supplierPanel.getIndexTab()-1);
                    });
                }
            }
        }));
        popupMenuMaster.add(new JPopupMenu.Separator());
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Pengguna") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                PenggunaPanel penggunaPanel = new PenggunaPanel();
                penggunaPanel.setName("Daftar Pengguna");
                if (penggunaPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(penggunaPanel.getIndexTab());
                } else {
                    penggunaPanel.setAktifPanel(penggunaPanel.getAktifPanel() + 1);
                    TP.addTab(penggunaPanel.getName(), penggunaPanel);
                    penggunaPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(penggunaPanel.getIndexTab());
                    
                    penggunaPanel.getToolbarDenganFilter().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(penggunaPanel);
                        penggunaPanel.setAktifPanel(penggunaPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(penggunaPanel.getIndexTab()-1);
                    });
                }
            }
        }));
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Tingkat Akses") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                TingkatAksesPanel aksesPanel = new TingkatAksesPanel();
                aksesPanel.setName("Daftar Tingkat Akses");
                if (aksesPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(aksesPanel.getIndexTab());
                } else {
                    aksesPanel.setAktifPanel(aksesPanel.getAktifPanel() + 1);
                    TP.addTab(aksesPanel.getName(), aksesPanel);
                    aksesPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(aksesPanel.getIndexTab());
                    
                    aksesPanel.getToolbar().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(aksesPanel);
                        aksesPanel.setAktifPanel(aksesPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(aksesPanel.getIndexTab()-1);
                    });
                }
            }
        }));
        btnMaster.addActionListener((ae) -> {
            popupMenuMaster.show(btnMaster, 0, btnMaster.getSize().height);
        });
    }

}
