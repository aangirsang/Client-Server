/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.popup;

import com.aan.girsang.client.ui.tansaksi.pembelian.PanelPelunasanHutang;
import com.aan.girsang.client.ui.tansaksi.pembelian.PanelPembelian;
import com.aan.girsang.client.ui.tansaksi.pembelian.PanelReturPembelian;
import com.aan.girsang.client.ui.tansaksi.penjualan.PenjualanPanel;
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
public class PopUpMenuTransaksi extends AbstractButton {

    JButton actionButton;
    JPopupMenu popupMenu;
    
    PanelReturPembelian returBeliPanel = new PanelReturPembelian();
    PanelPembelian pembelianPanel = new PanelPembelian();
    PanelPelunasanHutang hutangPanel = new PanelPelunasanHutang();
    PenjualanPanel penjualanPanel = new PenjualanPanel();


    public PopUpMenuTransaksi(JTabbedPane TP, JPopupMenu popupMenuTransaksi, JButton btnTransaksi) {
        popupMenuTransaksi.add(new JMenuItem(new AbstractAction("Penjualan") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (penjualanPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(penjualanPanel.getIndexTab());
                } else {
                    penjualanPanel = new PenjualanPanel();
                    penjualanPanel.setName("Daftar Penjualan Barang");
                    penjualanPanel.setAktifPanel(penjualanPanel.getAktifPanel() + 1);
                    TP.addTab(penjualanPanel.getName(), penjualanPanel);
                    penjualanPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(penjualanPanel.getIndexTab());

                    penjualanPanel.getToolbarDenganFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(penjualanPanel);
                        penjualanPanel.setAktifPanel(penjualanPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(penjualanPanel.getIndexTab() - 1);
                    });
                }
            }
        }));
        popupMenuTransaksi.add(new JMenuItem(new AbstractAction("Pembelian") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (pembelianPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(pembelianPanel.getIndexTab());
                } else {
                    pembelianPanel = new PanelPembelian();
                    pembelianPanel.setName("Daftar Pembelian Barang");
                    pembelianPanel.setAktifPanel(pembelianPanel.getAktifPanel() + 1);
                    TP.addTab(pembelianPanel.getName(), pembelianPanel);
                    pembelianPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(pembelianPanel.getIndexTab());

                    pembelianPanel.getToolbarDenganFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(pembelianPanel);
                        pembelianPanel.setAktifPanel(pembelianPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(pembelianPanel.getIndexTab() - 1);
                    });
                }
            }
        }));
        popupMenuTransaksi.add(new JMenuItem(new AbstractAction("Daftar Pelunasan Hutang") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (hutangPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(hutangPanel.getIndexTab());
                } else {
                    hutangPanel = new PanelPelunasanHutang();
                    hutangPanel.setName("Daftar Pelunasan Hutang");
                    hutangPanel.setAktifPanel(hutangPanel.getAktifPanel() + 1);
                    TP.addTab(hutangPanel.getName(), hutangPanel);
                    hutangPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(hutangPanel.getIndexTab());

                    hutangPanel.getToolbarDenganFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(hutangPanel);
                        hutangPanel.setAktifPanel(hutangPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(hutangPanel.getIndexTab()-1);
                    });
                }
            }
        }));
        popupMenuTransaksi.add(new JMenuItem(new AbstractAction("Daftar Retur Pembelian") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (returBeliPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(returBeliPanel.getIndexTab());
                } else {
                    returBeliPanel = new PanelReturPembelian();
                    returBeliPanel.setName("Daftar Retur Pembelian");
                    returBeliPanel.setAktifPanel(returBeliPanel.getAktifPanel() + 1);
                    TP.addTab(returBeliPanel.getName(), returBeliPanel);
                    returBeliPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(returBeliPanel.getIndexTab());

                    returBeliPanel.getToolbarDenganFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(returBeliPanel);
                        returBeliPanel.setAktifPanel(returBeliPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(returBeliPanel.getIndexTab()-1);
                    });
                }
            }
        }));
        btnTransaksi.addActionListener((ae) -> {
            popupMenuTransaksi.show(btnTransaksi, 0, btnTransaksi.getSize().height);
        });
    }

}
