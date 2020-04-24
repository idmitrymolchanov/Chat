package ru.dmitrii.chat.client;

import org.junit.Test;

import static java.lang.Thread.sleep;

public class OpenExitClientSwingTest {
    private RobotFestEnum clicker = RobotFestEnum.INSTANCE;

    @Test
    public void testAuthorizationForm_addTextInJTextField_onePageReturned() throws Exception {
        for(int i = 0; i < 10; i++) {
            new Main(0, "111");
            sleep(50);
            new Main(1, "111");
            sleep(100);
        }
    }
}
