package ru.dmitrii.chat.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.dmitrii.chat.server.ChatServer;

import java.util.concurrent.Executors;

public class ClientSwingTest {
    private RobotFestEnum clicker = RobotFestEnum.INSTANCE;

    @BeforeClass
    public static void setUpOnce() throws Exception {
        Executors.newSingleThreadExecutor().submit(() -> new ChatServer());
        Thread.sleep(500);
    }

    @Before
    public void init() {
        Main.main(null);
    }

    @Test
    public void testAuthorizationForm_addTextInJTextField_onePageReturned() throws Exception {
        String values[] = {"  ip address", "some reg", "QWER_J", "678H09", "90-90-11"};
        for(int i = 0; i < values.length - 1; i++) {
            clicker.changeTextInJTextField(values[i], values[i+1]);
            clicker.clickEnterReg();
        }
    }

    @Test
    public void testMainForm_allComponentsStart_CorrectPanelReturned() throws Exception{
        String nic = "Dmitry";
        clicker.clickDefineIP();

        clicker.chooseJTextFieldReg(nic);
        clicker.getText();
        clicker.clickEnterReg();
        clicker.clickNewMessage();
        clicker.changeTextInJTextField("new message", "my message");
        clicker.clickEnterMessage();

        clicker.checkValue(nic + ": my message");

        clicker.clickNewMessage();
        clicker.changeTextInJTextField("new message", "my super new message");
        clicker.clickEnterMessage();
        clicker.checkValue(nic + ": my super new message");

        clicker.clickNewMessage();
        clicker.changeTextInJTextField("new message", "t78t88t89ty8t 78t7t7t 7t78 t7igt 7yt78 yt78y" +
                "uugiuguighiughiu7gtigiuhioblkbklbn");

        clicker.clickEnterMessage();
        clicker.clickNewMessage();
        clicker.changeTextInJTextField("new message", "!@##$$%^&^&*((())_+");
        clicker.clickEnterMessage();
        clicker.checkValue(nic + ": !@##$$%^&^&*((())_+");

        Assert.assertEquals(AuthorizationWindow.HEIGHT, 2);
        Assert.assertEquals(AuthorizationWindow.WHEN_IN_FOCUSED_WINDOW, 2);
        Assert.assertEquals(AuthorizationWindow.WIDTH, 1);
    }

    @Test
    public void testMainForm_addTextInChatForm_CorrectPanelReturned() throws Exception{
        String nic = "8u87r465478670989-09865e5e67ty890-";
        clicker.clickDefineIP();

        clicker.chooseJTextFieldReg(nic);
        clicker.getText();
        clicker.clickEnterReg();
        clicker.clickNewMessage();
        clicker.changeTextInJTextField("new message", "");
        clicker.clickEnterMessage();
        for(int i = 0; i < 20; i++) {
            clicker.clickNewMessage();
            clicker.changeTextInJTextField("new message", "!@##$$%^&^&*((())_+");
            clicker.clickEnterMessage();
        }

        Assert.assertEquals(ClientWindow.HEIGHT, 2);
        Assert.assertEquals(ClientWindow.WHEN_IN_FOCUSED_WINDOW, 2);
        Assert.assertEquals(ClientWindow.WIDTH, 1);
    }
}
