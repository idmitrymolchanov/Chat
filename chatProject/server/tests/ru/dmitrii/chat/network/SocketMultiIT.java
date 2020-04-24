package ru.dmitrii.chat.network;

import org.junit.*;
import ru.dmitrii.chat.client.ClientWindow;
import ru.dmitrii.chat.server.ChatServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class SocketMultiIT {

    private static int port;
    private static String myLANIP;

    @BeforeClass
    public static void start() throws InterruptedException, IOException {

        // Take an available port
        ServerSocket s = new ServerSocket(0);
        port = s.getLocalPort();
        s.close();

        Executors.newSingleThreadExecutor().submit(() -> new ChatServer());
        Thread.sleep(500);
    }

    @Before
    public void init() throws Exception{
        InetAddress addr = InetAddress.getLocalHost();
        myLANIP = addr.getHostAddress();
    }

    @Test
    public void givenClient_whenServerResponds_thenCorrect() throws Exception {
        ClientWindow clientWindow = new ClientWindow(myLANIP);
        TCPConnection tcpConnection = new TCPConnection(clientWindow, myLANIP, 8189);
        tcpConnection.sentString("hello");
        tcpConnection.sentString("world");
        tcpConnection.sentString("qwertyuiop[as sd90-=-09");

        Assert.assertTrue(tcpConnection.getString().contains("Client connected: "));

        assertEquals(tcpConnection.getString(), "hello");
        assertEquals(tcpConnection.getString(), "world");
        assertEquals(tcpConnection.getString(), "qwertyuiop[as sd90-=-09");

        tcpConnection.disconnect();
    }

    @Test
    public void givenClient2_whenServerResponds_thenCorrect() throws Exception {
        ClientWindow clientWindow = new ClientWindow(myLANIP);
        TCPConnection tcpConnection = new TCPConnection(clientWindow, myLANIP, 8189);
        tcpConnection.sentString("hello");
        tcpConnection.sentString("world");
        tcpConnection.sentString("qwertyuiop[as sd90-=-09");

        Assert.assertTrue(tcpConnection.getString().contains("Client connected: "));

        assertEquals(tcpConnection.getString(), "hello");
        assertEquals(tcpConnection.getString(), "world");
        assertEquals(tcpConnection.getString(), "qwertyuiop[as sd90-=-09");

        tcpConnection.disconnect();
    }

    @Test
    public void givenClient3_whenServerResponds_thenCorrect() throws Exception {
        ClientWindow clientWindow = new ClientWindow(myLANIP);
        TCPConnection tcpConnection = new TCPConnection(clientWindow, myLANIP, 8189);
        tcpConnection.sentString("hello");
        tcpConnection.sentString("world");
        tcpConnection.sentString("qwertyuiop[as sd90-=-09");

        Assert.assertTrue(tcpConnection.getString().contains("Client connected: "));

        assertEquals(tcpConnection.getString(), "hello");
        assertEquals(tcpConnection.getString(), "world");
        assertEquals(tcpConnection.getString(), "qwertyuiop[as sd90-=-09");

        tcpConnection.disconnect();
    }

}
