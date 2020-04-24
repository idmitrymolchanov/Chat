package ru.dmitrii.chat.client;

import java.awt.Component;
import java.net.InetAddress;
import java.util.concurrent.Executors;

import javax.swing.*;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.ComponentMatcher;
import org.fest.swing.core.Robot;
import org.junit.Assert;

public enum RobotFestEnum {
    INSTANCE;

    private Robot robot;
    private ComponentFinder finder;

    RobotFestEnum() {
        robot = BasicRobot.robotWithCurrentAwtHierarchy();
        finder = robot.finder();
    }

    public void clickDefineIP() throws Exception {
        CaptionMatcher matcher = new CaptionMatcher("Define a local ip address");
        click(matcher);
    }

    public void clickEnterReg() throws Exception {
        CaptionMatcher matcher = new CaptionMatcher("Enter");
        click(matcher);
    }

    public void chooseJTextFieldReg(String value) throws Exception {
        CaptionMatcher2 matcher2 = new CaptionMatcher2("  your name (nic)");
        click(matcher2, value);
    }

    public void changeTextInJTextField(String value, String text) throws Exception {
        CaptionMatcher2 matcher2 = new CaptionMatcher2(value);
        click(matcher2, text);
    }

    public String getText() throws Exception{
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostAddress();
    }

    public void clickNewMessage() throws Exception{
        CaptionMatcher matcher = new CaptionMatcher("new message");
        click(matcher);
    }

    public void clickEnterMessage() throws Exception {
        CaptionMatcher matcher = new CaptionMatcher("send message");
        click(matcher);
    }


    private void click(CaptionMatcher matcher) throws Exception {
        Component tmp = null;
        long start = System.currentTimeMillis();

        while (tmp == null) {
            try {
                tmp = finder.find(matcher);
                final Component btn = tmp;
                Executors.newSingleThreadExecutor().execute(() -> ((JButton) btn).doClick());

            } catch (Exception e) {
            }
            Thread.yield();
            if (System.currentTimeMillis() - start > 5000) {
                throw new Exception("text to be thrown");
            }

        }
        Thread.sleep(100);
    }

    private void click(CaptionMatcher2 matcher, String value) throws Exception {
        Component tmp = null;
        long start = System.currentTimeMillis();

        while (tmp == null) {
            try {
                tmp = finder.find(matcher);
                final Component btn = tmp;
                Executors.newSingleThreadExecutor().execute(() -> ((JTextField) btn).setText(value));

            } catch (Exception e) {
            }
            Thread.yield();
            if (System.currentTimeMillis() - start > 5000) {
                throw new Exception("text to be thrown");
            }
        }
        Thread.sleep(100);
    }

    public void checkValue(String value) throws Exception {
        DisplayLabelMatcher displayLblMatcher = new DisplayLabelMatcher();

        JTextArea lbl = null;
        long start = System.currentTimeMillis();

        while (true) {
            try {
                lbl = (JTextArea) finder.find(displayLblMatcher);
                lbl.getText();
                String text = lbl.getText();
                Assert.assertTrue(text.contains(value));
                break;

            } catch (Throwable e) {
            }
            Thread.yield();
            if (System.currentTimeMillis() - start > 1000) {
                throw new Exception("text to be thrown");
            }

        }
    }

        private class DisplayLabelMatcher implements ComponentMatcher {

        @Override
        public boolean matches(Component component) {
            if (component != null && component instanceof JTextArea) {
                if (component.getName().equals("logging")) {
                    return true;
                }
            }
            return false;
        }

    }

    private class CaptionMatcher implements ComponentMatcher {
        private String caption;

        public CaptionMatcher(String caption) {
            this.setCaption(caption);
        }

        @Override
        public boolean matches(Component comp) {
            if (comp != null && comp instanceof JButton) {
                if (caption.equals(((JButton) comp).getText())) {
                    return true;
                }
            }
            return false;
        }
        public void setCaption(String caption) {
            this.caption = caption;
        }

    }

    private class CaptionMatcher2 implements ComponentMatcher {
        private String caption;
        public CaptionMatcher2(String caption) {
            this.setCaption(caption);
        }

        @Override
        public boolean matches(Component comp) {
            if (comp != null && comp instanceof JTextField) {
                if (caption.equals(((JTextField) comp).getText())) {
                    return true;
                }
            }
            return false;
        }
        public void setCaption(String caption) {
            this.caption = caption;
        }
    }

}
