package ru.dmitrii.chat.client;

import org.junit.*;
import ru.dmitrii.chat.network.TCPConnection;
import ru.dmitrii.chat.network.TCPConnectionListener;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Executors;

public class ClientWindowTest {
    private static String myLANIP;
    public static ClientWindow clientWindow;

    @BeforeClass
    public static void start() throws IOException {
        InetAddress addr = InetAddress.getLocalHost();
        myLANIP = addr.getHostAddress();
    }

    @Test
    public void onConnectionReadyTest_clientConnectedSend_ConnectExceptionReturned() throws InterruptedException {

        clientWindow = new ClientWindow(myLANIP);

        Executors.newSingleThreadExecutor()
                .submit(() -> {
                    clientWindow = new ClientWindow(myLANIP);
                });
        Thread.sleep(500);

        Assert.assertEquals(clientWindow.getConsoleLog(), "Connection exception: java.net.ConnectException: Connection refused: connect");
    }
}


