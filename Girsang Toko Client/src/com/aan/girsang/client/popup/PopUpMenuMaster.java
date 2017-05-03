/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.popup;

import com.aan.girsang.client.ui.master.barang.BarangPanel;
import com.aan.girsang.client.ui.master.golongan.PanelGolonganBarang;
import com.aan.girsang.client.ui.master.pelanggan.PanelPelanggan;
import com.aan.girsang.client.ui.master.satuan.SatuanBarangPanel;
import com.aan.girsang.client.ui.master.supplier.SupplierPanel;
import com.aan.girsang.client.ui.report.TestReportPanel;
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

    PanelGolonganBarang golonganBarangPanel = new PanelGolonganBarang();
    BarangPanel barangPanel = new BarangPanel();
    SatuanBarangPanel satuanBarangPanel = new SatuanBarangPanel();
    SupplierPanel supplierPanel = new SupplierPanel();
    PenggunaPanel penggunaPanel = new PenggunaPanel();
    TingkatAksesPanel aksesPanel = new TingkatAksesPanel();
    PanelPelanggan panelPelanggan = new PanelPelanggan();
    TestReportPanel testReportPanel = new TestReportPanel();
    
    
    public PopUpMenuMaster(JTabbedPane TP, JPopupMenu popupMenuMaster, JButton btnMaster) {
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Test Report") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (testReportPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(testReportPanel.getIndexTab());
                } else {
                    testReportPanel = new TestReportPanel();
                    testReportPanel.setName("Test Report");
                    testReportPanel.setAktifPanel(testReportPanel.getAktifPanel() + 1);
                    TP.addTab(testReportPanel.getName(), testReportPanel);
                    testReportPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(testReportPanel.getIndexTab());
                    
                    testReportPanel.getBtnTutup().addActionListener((ae1) -> {
                        TP.remove(testReportPanel);
                        testReportPanel.setAktifPanel(testReportPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(testReportPanel.getIndexTab() -1);
                    });
                }
            }
        }));
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Golongan Barang") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (golonganBarangPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(golonganBarangPanel.getIndexTab());
                } else {
                    golonganBarangPanel = new PanelGolonganBarang();
                    golonganBarangPanel.setName("Daftar Kategori Barang");
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
                if (barangPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(barangPanel.getIndexTab());
                } else {
                    barangPanel = new BarangPanel();
                    barangPanel.setName("Daftar Barang");
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
                if (satuanBarangPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(satuanBarangPanel.getIndexTab());
                } else {
                    satuanBarangPanel = new SatuanBarangPanel();
                    satuanBarangPanel.setName("Daftar Satuan Barang");
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
                if (supplierPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(supplierPanel.getIndexTab());
                } else {
                    supplierPanel = new SupplierPanel();
                    supplierPanel.setName("Daftar Supplier");
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
                if (penggunaPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(penggunaPanel.getIndexTab());
                } else {
                    penggunaPanel = new PenggunaPanel();
                    penggunaPanel.setName("Daftar Pengguna");
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
                if (aksesPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(aksesPanel.getIndexTab());
                } else {
                    aksesPanel = new TingkatAksesPanel();
                    aksesPanel.setName("Daftar Tingkat Akses");
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
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Pelanggan") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (panelPelanggan.getAktifPanel() == 1) {
                    TP.setSelectedIndex(panelPelanggan.getIndexTab());
                } else {
                    panelPelanggan = new PanelPelanggan();
                    panelPelanggan.setName("Daftar Tingkat Akses");
                    panelPelanggan.setAktifPanel(panelPelanggan.getAktifPanel() + 1);
                    TP.addTab(panelPelanggan.getName(), panelPelanggan);
                    panelPelanggan.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(panelPelanggan.getIndexTab());
                    
                    panelPelanggan.getBtnKeluar().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(panelPelanggan);
                        panelPelanggan.setAktifPanel(panelPelanggan.getAktifPanel() - 1);
                        TP.setSelectedIndex(panelPelanggan.getIndexTab()-1);
                    });
                }
            }
        }));
        btnMaster.addActionListener((ae) -> {
            popupMenuMaster.show(btnMaster, 0, btnMaster.getSize().height);
        });
    }

}
