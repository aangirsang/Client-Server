/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aan.girsang.api.util;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

public class MainClass {
  public static void main(String args[]) throws Exception {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JTextField nameTextField = new JTextField();
    frame.add(nameTextField, BorderLayout.NORTH);

    KeyListener keyListener = new KeyListener() {
      public void keyPressed(KeyEvent keyEvent) {
        printIt("Pressed", keyEvent);
      }

      public void keyReleased(KeyEvent keyEvent) {
        printIt("Released", keyEvent);
      }

      public void keyTyped(KeyEvent keyEvent) {
        printIt("Typed", keyEvent);
      }

      private void printIt(String title, KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        String keyText = KeyEvent.getKeyText(keyCode);
        System.out.println(title + " : " + keyText + " / " + keyEvent.getKeyChar());
      }
    };
    nameTextField.addKeyListener(keyListener);


    frame.setSize(250, 100);
    frame.setVisible(true);
  }
}
