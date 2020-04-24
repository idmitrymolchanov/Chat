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
    private static String consoleLog;

    private TCPConnection connection;

    public ClientWindow(String IP_ADDR){
        setLayout(new BorderLayout());
        log.setEditable(false);
        log.setName("logging");
        log.setLineWrap(true);
        add(log, BorderLayout.CENTER);
        JButton newButton = new JButton("new");
        JButton enterButton = new JButton("Enter m-ge");
        newButton.setBounds(-20,-20,1,1);
        add(enterButton, BorderLayout.LINE_END);
        add(newButton, BorderLayout.WEST);

        fieldInput.addActionListener(this);
        add(fieldInput, BorderLayout.SOUTH);
        add(fieldNikName, BorderLayout.NORTH);

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
