/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.report;

import com.aan.girsang.client.launcher.ClientLauncher;
import java.awt.BorderLayout;
import javax.swing.JButton;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;

/**
 *
 * @author ITSUSAHBRO
 */
public class ReportPenjualanBarangPanel extends javax.swing.JPanel {

    int IndexTab = 0;
    int aktifPanel = 0;
    String title, idSelect;

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

    public JButton getBtnTutup() {
        return btnKeluar;
    }
    /**
     * Creates new form ReportPenjualanBarang
     */
    public ReportPenjualanBarangPanel() {
        initComponents();
        initListener();
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
        jdcTanggal = new com.toedter.calendar.JDateChooser();
        btnView = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        reportPanel = new javax.swing.JPanel();

        jLabel1.setText(org.openide.util.NbBundle.getMessage(ReportPenjualanBarangPanel.class, "ReportPenjualanBarangPanel.jLabel1.text")); // NOI18N

        btnView.setText(org.openide.util.NbBundle.getMessage(ReportPenjualanBarangPanel.class, "ReportPenjualanBarangPanel.btnView.text")); // NOI18N

        btnKeluar.setText(org.openide.util.NbBundle.getMessage(ReportPenjualanBarangPanel.class, "ReportPenjualanBarangPanel.btnKeluar.text")); // NOI18N

        javax.swing.GroupLayout reportPanelLayout = new javax.swing.GroupLayout(reportPanel);
        reportPanel.setLayout(reportPanelLayout);
        reportPanelLayout.setHorizontalGroup(
            reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        reportPanelLayout.setVerticalGroup(
            reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 465, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
            .addComponent(reportPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(btnView)
                    .addComponent(btnKeluar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reportPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initListener(){
        btnView.addActionListener((ae) -> {
            JasperPrint print = ClientLauncher.getReportService().penjualanBarang();
            JRViewer viewer = new JRViewer(print);
            System.out.println(getPreferredSize());
            viewer.setPreferredSize(getPreferredSize());
            reportPanel.removeAll();
            reportPanel.setLayout(new BorderLayout());
            reportPanel.add(viewer, BorderLayout.CENTER);
            reportPanel.revalidate();
            reportPanel.repaint();
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnView;
    private javax.swing.JLabel jLabel1;
    private com.toedter.calendar.JDateChooser jdcTanggal;
    private javax.swing.JPanel reportPanel;
    // End of variables declaration//GEN-END:variables
}
