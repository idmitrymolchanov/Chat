package ru.dmitrii.chat.client;

import org.junit.*;
import ru.dmitrii.chat.server.ChatServer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Executors;


public class ClientWindowIT {
    private static String myLANIP;
    private static ChatServer chatServer;
    public static ClientWindow clientWindow;

    @BeforeClass
    public static void start() throws InterruptedException, IOException {
        InetAddress addr = InetAddress.getLocalHost();
        myLANIP = addr.getHostAddress();
        Executors.newSingleThreadExecutor()
                .submit(() -> {
                    chatServer = new ChatServer();
                    return chatServer;
                });
        Thread.sleep(500);
    }

    @Test
    public void onConnectionReadyTest_clientConnectedSend_UnknownHostExceptionReturned() throws InterruptedException {

        Executors.newSingleThreadExecutor()
                .submit(() -> {
                    clientWindow = new ClientWindow("11111.111119");
                });
        Thread.sleep(500);

        Assert.assertEquals(clientWindow.getConsoleLog(), "Connection exception: java.net.UnknownHostException: 11111.111119");
    }

    @Test
    public void onConnectionReadyTest_clientConnectedSend_clientConnectedReturned() throws InterruptedException {

        Executors.newSingleThreadExecutor()
                .submit(() -> {
                    clientWindow = new ClientWindow(myLANIP);
                });
        Thread.sleep(500);

        Assert.assertTrue(clientWindow.getConsoleLog().contains("Client connected: TCPConnection: /192.168.56.1:"));
    }
}

