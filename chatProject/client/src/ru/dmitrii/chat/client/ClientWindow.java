package ru.dmitrii.chat.client;

import ru.dmitrii.chat.network.TCPConnection;
import ru.dmitrii.chat.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JPanel implements ActionListener, TCPConnectionListener {
    private static final int PORT = 8189;

    private final JTextArea log = new JTextArea();
    private final JTextField fieldNikName = new JTextField(AuthorizationWindow.name);
    private final JTextField fieldInput = new JTextField();
    JButton newButton = new JButton("new message");
    JButton enterButton = new JButton("send message");
    private static String consoleLog;

    private TCPConnection connection;

    public ClientWindow(String IP_ADDR){
        setLayout(null);
        fieldInput.setBounds(0, 405, 700, 50);
        log.setBounds(0, 35, 700, 333);
        newButton.setBounds(0, 375, 350, 30);
        enterButton.setBounds(350, 375, 350, 30);
        fieldNikName.setBounds(0, 0, 700, 30);

        log.setEditable(false);
        log.setName("logging");
        log.setLineWrap(true);
        add(log);
        add(enterButton);
        add(newButton);

        fieldInput.addActionListener(this);
        add(fieldInput);
        add(fieldNikName);

        newButton.addActionListener(e -> fieldInput.setText("new message"));
        enterButton.addActionListener(this::actionPerformed);

        setVisible(true);
        try {
            connection = new TCPConnection(this, IP_ADDR, PORT);
        } catch (Exception e) {
            consoleLog = ("Connection exception: " + e);
            System.out.println("Connection exception: " + e);
            printMsg("Connection exception: " + e);
        }
    }

    public String getConsoleLog() {
        return consoleLog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String value = fieldInput.getText();
            if (value.equals("")) return;
            fieldInput.setText(null);
            connection.sentString(fieldNikName.getText() + ": " + value);
        } catch (Exception e1){
            onException(connection, e1);
        }
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMsg("Connection ready... ");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMsg(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMsg("Connection close ");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception: " + e);
    }

    private synchronized void printMsg(String value){
        SwingUtilities.invokeLater(() -> {
            log.append(value + "\n");
            consoleLog = value;
            System.out.println(consoleLog);
            log.setCaretPosition(log.getDocument().getLength());
        });
    }

    public synchronized void exitWindow(){

    }
}
