package ru.dmitrii.chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class AuthorizationWindow extends JPanel {
    public static String name = "";
    public JButton buttonEnter = new JButton("Enter");
    public JButton buttonIP = new JButton("Define a local ip address");
    JTextField jTextFieldIP = new JTextField("  ip address");
    JTextField jTextFieldName = new JTextField("  your name (nic)");

    public AuthorizationWindow() {
        setLayout(null);
        addElementsOnBoard();
    }

    public void addElementsOnBoard() {
        JTextField jTextField = new JTextField();
        jTextField.setBounds(-10,-10,1,1);
        jTextField.setVisible(true);
        add(jTextField);
        buttonEnter.setBounds(75,285,430,55);
        buttonIP.setBounds(25,115,530,55);
        jTextFieldIP.setBounds(25,30,530,55);
        jTextFieldName.setBounds(25,200,530,55);
        add(buttonEnter);
        add(buttonIP);
        add(jTextFieldName);
        add(jTextFieldIP);

        buttonIP.addActionListener(e -> {
            try {
                InetAddress addr = InetAddress.getLocalHost();
                String myLANIP = addr.getHostAddress();
                jTextFieldIP.setText(myLANIP);
            } catch (UnknownHostException e1) {}
        });

        buttonEnter.addActionListener(e -> {
            String regex = "[0-9,.]+";
            name = jTextFieldName.getText();
            if(jTextFieldIP.getText().matches(regex)) {
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                win.dispose();
                new Main(1, jTextFieldIP.getText());
            }
        });

        jTextFieldIP.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                jTextFieldIP.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(jTextFieldIP.getText().equals(""))
                    jTextFieldIP.setText("  ip address");
            }
        });

        jTextFieldName.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                jTextFieldName.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(jTextFieldName.getText().equals(""))
                    jTextFieldName.setText("  your name (nic)");
            }
        });
    }

}
