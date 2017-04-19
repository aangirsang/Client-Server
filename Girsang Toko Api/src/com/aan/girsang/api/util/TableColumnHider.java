/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.api.util;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class TableColumnHider {

    private JTable table;
    private TableColumnModel tcm;
    private Map hiddenColumns;

    public TableColumnHider(JTable table) {
        this.table = table;
        tcm = table.getColumnModel();
        hiddenColumns = new HashMap();
    }

    public void hide(String columnName) {
        int index = tcm.getColumnIndex(columnName);
        TableColumn column = tcm.getColumn(index);
        hiddenColumns.put(columnName, column);
        hiddenColumns.put(":" + columnName, new Integer(index));
        tcm.removeColumn(column);
    }

    public void show(String columnName) {
        Object o = hiddenColumns.remove(columnName);
        if (o == null) {
            return;
        }
        tcm.addColumn((TableColumn) o);
        o = hiddenColumns.remove(":" + columnName);
        if (o == null) {
            return;
        }
        int column = ((Integer) o).intValue();
        int lastColumn = tcm.getColumnCount() - 1;
        if (column < lastColumn) {
            tcm.moveColumn(lastColumn, column);
        }
    }

    public static void main(String[] args) {
        String[] columnNames = {"Name", "Size", "Type", "Date Modified", "Permissions"};
        String[][] data = {
            {"bin", "2", "dir", "Jun 9", "drwxr-xr-x"},
            {"boot", "3", "dir", "Jun 9", "drwxr-xr-x"},
            {"dev", "6", "dir", "Jul 12", "drwxr-xr-x"},
            {"etc", "34", "dir", "Jul 12", "drwxr-xr-x"},};
        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        final TableColumnHider hider = new TableColumnHider(table);
        JPanel checkBoxes = new JPanel();
        for (int i = 0; i < columnNames.length; i++) {
            JCheckBox checkBox = new JCheckBox(columnNames[i]);
            checkBox.setSelected(true);
            checkBox.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {
                    JCheckBox cb = (JCheckBox) evt.getSource();
                    String columnName = cb.getText();

                    if (cb.isSelected()) {
                        hider.show(columnName);
                    } else {
                        hider.hide(columnName);
                    }
                }
            });
            checkBoxes.add(checkBox);
        }
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(scrollPane);
        frame.getContentPane().add(checkBoxes, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}