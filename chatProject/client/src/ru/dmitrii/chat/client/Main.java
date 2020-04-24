package ru.dmitrii.chat.client;

import javax.swing.*;

public class Main {
    private JFrame window;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static String IP_ADDR = "";

    public Main(int i, String IP_ADDR) {
        this.IP_ADDR = IP_ADDR;
        initStart(i);
    }

    public void initStart(int i) {
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(WIDTH, HEIGHT);
        window.setLocationRelativeTo(null);
        window.setAlwaysOnTop(true);
        switch (i) {
            case 0: {
                window.setTitle("Authorization");
                window.add(new AuthorizationWindow());
                break;
            }
            case 1: {
                window.setTitle("Chat");
                window.add(new ClientWindow(IP_ADDR));
                break;
            }
        }
        window.setVisible(true);
    }

    public static void main(String[] args) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(() -> new Main(0, IP_ADDR));
    }
}
