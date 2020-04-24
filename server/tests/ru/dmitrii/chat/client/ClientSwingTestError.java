package ru.dmitrii.chat.client;

import org.junit.BeforeClass;
import org.junit.Test;

public class ClientSwingTestError {
    private RobotFestEnum clicker = RobotFestEnum.INSTANCE;

    @BeforeClass
    public static void setUpOnce() {
        Main.main(null);
    }

    @Test
    public void testClientWithoutServer_exceptionReturned() throws Exception{
        String nic = "Dmitry";
        clicker.clickDefineIP();

        clicker.chooseJTextFieldReg(nic);
        clicker.getText();
        clicker.clickEnterReg();
        clicker.clickNewMessage();
        clicker.changeTextInJTextField("new message", "my message");
        clicker.clickEnterMessage();

        clicker.checkValue("Connection exception: java.net.ConnectException: Connection refused: connect");
    }

}
