package hearthstone.gui.credetials;

import hearthstone.gui.BaseFrame;
import hearthstone.gui.DefaultSizes;
import hearthstone.gui.game.GameFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.File;

public class CredentialsFrame extends BaseFrame {
    private LogisterPanel logisterPanel;
    private static CredentialsFrame credentialsFrame;
    private Clip clip;

    private CredentialsFrame() {
        logisterPanel = new LogisterPanel();

        playSound();

        stopSound();

        configFrame();
    }

    public static CredentialsFrame getInstance() {
        if (credentialsFrame == null) {
            return credentialsFrame = new CredentialsFrame();
        } else {
            return credentialsFrame;
        }
    }

    public static CredentialsFrame getNewInstance() {
        if(credentialsFrame != null){
            credentialsFrame.stopSound();
        }
        return credentialsFrame = new CredentialsFrame();
    }

    public void volumeChange(int x){
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(x);
    }

    public void playSound() {
        try {
            File file = new File(this.getClass().getResource(
                    "/sounds/credentials.wav").getFile());
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(file.getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void stopSound() {
        if (clip != null)
            clip.stop();
    }

    private void configFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setSize(DefaultSizes.credentialFrameWidth, DefaultSizes.credentialFrameHeight);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setContentPane(logisterPanel);

        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        this.setVisible(true);
    }
}