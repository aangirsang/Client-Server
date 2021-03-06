/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.client.ui.security.tingkatAkses;

import com.aan.girsang.api.model.constant.MasterRunningNumberEnum;
import com.aan.girsang.api.model.security.TingkatAkses;
import com.aan.girsang.api.util.TextComponentUtils;
import com.aan.girsang.client.launcher.ClientLauncher;
import com.aan.girsang.client.ui.frame.FrameUtama;
import javax.swing.JOptionPane;

/**
 *
 * @author GIRSANG PC
 */
public class DialogTingkatAkses extends javax.swing.JDialog {
    
    private TingkatAkses tingkatAkses;
    private String title;

    /**
     * Creates new form DialogTingkatAkses
     */
    public DialogTingkatAkses() {
        super(FrameUtama.getInstance(), true);
        setTitle(title);
        initComponents();
        initListener();
        TextComponentUtils.setAutoUpperCaseText(30, txtAkses);
        TextComponentUtils.setAutoUpperCaseText(100, txtKet);
        txtId.setEditable(false);
        txtAkses.requestFocus();
    }
    public TingkatAkses showDialog(TingkatAkses t, String title){
        if(t==null){
            clear();
        }else{
            txtId.setText(t.getId());
            txtAkses.setText(t.getNamaTingkatAkses());
            txtKet.setText(t.getKetTingkatAkses());
        }
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        return this.tingkatAkses;
    }
    
    private void clear(){
        txtId.setText(ClientLauncher.getConstantService().ambilBerikutnya(MasterRunningNumberEnum.TINGKATAKSES));
        txtAkses.setText("");
        txtKet.setText("");
    }
    private boolean validateSimpan(){
        if("".equals(txtAkses.getText())){
            JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                     "Nama Tingkat Akses Harus Di Isi",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtAkses.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtId = new javax.swing.JTextField();
        txtAkses = new javax.swing.JTextField();
        txtKet = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtId.setText(org.openide.util.NbBundle.getMessage(DialogTingkatAkses.class, "DialogTingkatAkses.txtId.text")); // NOI18N

        txtAkses.setText(org.openide.util.NbBundle.getMessage(DialogTingkatAkses.class, "DialogTingkatAkses.txtAkses.text")); // NOI18N

        txtKet.setText(org.openide.util.NbBundle.getMessage(DialogTingkatAkses.class, "DialogTingkatAkses.txtKet.text")); // NOI18N

        jLabel1.setText(org.openide.util.NbBundle.getMessage(DialogTingkatAkses.class, "DialogTingkatAkses.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(DialogTingkatAkses.class, "DialogTingkatAkses.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(DialogTingkatAkses.class, "DialogTingkatAkses.jLabel3.text")); // NOI18N

        btnSimpan.setText(org.openide.util.NbBundle.getMessage(DialogTingkatAkses.class, "DialogTingkatAkses.btnSimpan.text")); // NOI18N

        btnBatal.setText(org.openide.util.NbBundle.getMessage(DialogTingkatAkses.class, "DialogTingkatAkses.btnBatal.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAkses, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBatal, btnSimpan});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAkses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnBatal))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initListener(){
        btnSimpan.addActionListener((ae) -> {
            if(validateSimpan()){
                tingkatAkses = new TingkatAkses();
                tingkatAkses.setId(txtId.getText());
                tingkatAkses.setNamaTingkatAkses(txtAkses.getText());
                tingkatAkses.setKetTingkatAkses(txtKet.getText());

                dispose();
            }
        });
        btnBatal.addActionListener((ae) -> {
            tingkatAkses = null;
            dispose();
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtAkses;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtKet;
    // End of variables declaration//GEN-END:variables
}
