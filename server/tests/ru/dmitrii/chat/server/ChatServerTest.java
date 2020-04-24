package ru.dmitrii.chat.server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.dmitrii.chat.client.ClientWindow;
import ru.dmitrii.chat.network.TCPConnection;

import java.io.PrintStream;
import java.net.InetAddress;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ChatServerTest {
    private static String myLANIP;
    private static TCPConnection connection;

    @Before
    public void init() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            myLANIP = addr.getHostAddress();
            connection = new TCPConnection(new ClientWindow(myLANIP), myLANIP, 8189);
        } catch (Exception e){}
    }

    @Test
    public void initServerTest_ChatServerClassRun_StringConnReturned() throws Exception{
        PrintStream stream = mock(PrintStream.class);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        System.setOut(stream);

        String params = "Server Running...";
        Executors.newSingleThreadExecutor()
                .submit(() -> new ChatServer());
        Thread.sleep(500);
        verify(stream).println(captor.capture());
        assertEquals(captor.getValue(), params);
        return;
    }

}
