package ru.dmitrii.chat.network;

import org.junit.*;
import ru.dmitrii.chat.server.ChatServer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class SocketIT {
    private static String myLANIP;
    private static TCPConnection connection;

    @BeforeClass
    public static void start() throws InterruptedException, IOException {
        InetAddress addr = InetAddress.getLocalHost();
        myLANIP = addr.getHostAddress();
        Executors.newSingleThreadExecutor()
                .submit(() -> new ChatServer());
        Thread.sleep(500);
    }

    @Before
    public void init() throws IOException{
        connection = new TCPConnection(new TCPConnectionListener() {
            @Override
            public void onConnectionReady(TCPConnection tcpConnection) { }

            @Override
            public void onReceiveString(TCPConnection tcpConnection, String value) { }

            @Override
            public void onDisconnect(TCPConnection tcpConnection) { }

            @Override
            public void onException(TCPConnection tcpConnection, Exception e) { }
        }, myLANIP, 8189);
    }

    @After
    public void tearDown() {
        connection.disconnect();
    }

    @Test
    public void onConnectionReadyTest_clientConnectedSend_checkConnectReturned() {
        String resp = connection.getString();
        Assert.assertTrue(resp.contains("Client connected: TCPConnection: "));
        Assert.assertTrue(resp.contains(myLANIP));
    }

    @Test
    public void onConnectionReadyTest_clientConnectedSend_exceptionReturned() {
        connection.disconnect();
        String resp = connection.getString();
        Assert.assertTrue(resp.contains("exception!"));
    }

    @Test
    public void givenClient_whenServerEchosMessage_thenCorrect() {
        connection.sentString("hello");
        connection.sentString("HELLO");
        connection.sentString("world");
        connection.sentString("!@#$%^&*()./,_");
        connection.sentString("hello_world120813501789494");
        connection.sentString("qwertyuiop[as sdfghjkl;  ddfhjjkjnnafsacas" +
                    "jascnklnlkn onknknlknlkn lknmklmnklnkln klmlamwipjmknmmnlmlms" +
                    "sacascac fasfacasfceaf aefeafef e3rfwef csdcvsvcsdvsdvseve ewfew");
        connection.sentString("русский язык проверка");
        connection.sentString("");
        connection.sentString("\n");
        connection.getString();

        assertEquals("hello", connection.getString());
        assertEquals("HELLO", connection.getString());
        assertEquals("world", connection.getString());
        assertEquals("!@#$%^&*()./,_", connection.getString());
        assertEquals("hello_world120813501789494", connection.getString());
        assertEquals("qwertyuiop[as sdfghjkl;  ddfhjjkjnnafsacas" +
                    "jascnklnlkn onknknlknlkn lknmklmnklnkln klmlamwipjmknmmnlmlms" +
                    "sacascac fasfacasfceaf aefeafef e3rfwef csdcvsvcsdvsdvseve ewfew", connection.getString());
        assertEquals("русский язык проверка", connection.getString());
        assertEquals("", connection.getString());
        assertEquals("", connection.getString());
    }
}
