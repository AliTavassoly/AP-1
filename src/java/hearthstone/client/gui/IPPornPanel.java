package hearthstone.client.gui;

import hearthstone.client.Main;
import hearthstone.client.data.ClientData;
import hearthstone.client.gui.controls.buttons.ImageButton;
import hearthstone.client.gui.controls.fields.PasswordField;
import hearthstone.client.gui.controls.fields.TextField;
import hearthstone.client.gui.credetials.CredentialsFrame;
import hearthstone.client.gui.credetials.LoginPanel;
import hearthstone.client.gui.credetials.LogisterPanel;
import hearthstone.client.network.ClientMapper;
import hearthstone.client.network.HSClient;
import hearthstone.shared.GUIConfigs;
import hearthstone.util.FontType;
import hearthstone.util.getresource.ImageResource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class IPPornPanel extends JPanel {
    private static IPPornPanel instance;

    private ImageButton enterButton, closeButton, minimizeButton;
    private TextField ipField, portField;

    private final String userText = "IP: : ";
    private final String passText = "Port: : ";

    private BufferedImage backgroundImage;

    private Color textColor;

    private final int startTextY = GUIConfigs.credentialFrameHeight / 2 - 10 - 45 + 30 + 1;
    private final int startFieldY = GUIConfigs.credentialFrameHeight / 2 - 22 - 50 + 30 + 1;
    private final int iconX = 20;
    private final int startIconY = 20;
    private final int endIconY = GUIConfigs.credentialFrameHeight - GUIConfigs.iconHeight - 20;
    private final int iconsDis = 70;
    private final int textFieldDis = 30;
    private final int constAddX = 20;
    private final int stringFieldDis = 3;
    private final int loginButtonY = 300;

    private IPPornPanel() {
        configPanel();

        configText();

        makeIcons();

        makeButtons();

        makeFields();

        layoutComponent();
    }

    public static IPPornPanel makeInstance() {
        return instance = new IPPornPanel();
    }

    public static IPPornPanel getInstance() {
        return instance;
    }

    @Override
    protected void paintComponent(Graphics g) {
        try {
            if (backgroundImage == null)
                backgroundImage = ImageResource.getInstance().getImage("/images/logister_background.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        g.drawImage(backgroundImage, 0, 0, null);

        drawString(userText,
                GUIConfigs.credentialFrameWidth / 2 - stringFieldDis,
                startTextY + 0, 20, Font.PLAIN, textColor, g);
        drawString(passText,
                GUIConfigs.credentialFrameWidth / 2 - stringFieldDis,
                startTextY + 1 * 30, 20, Font.PLAIN, textColor, g);
    }

    private void configPanel() {
        setSize(new Dimension(GUIConfigs.credentialFrameWidth, GUIConfigs.credentialFrameHeight));
        setLayout(null);
        setVisible(true);
    }

    private void configText() {
        textColor = new Color(255, 255, 68);
    }

    private void makeButtons() {
        enterButton = new ImageButton("Enter", "buttons/green_background.png",
                -1, Color.white, Color.yellow, 14, 0,
                GUIConfigs.medButtonWidth,
                GUIConfigs.medButtonHeight);

        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Main.createNewClient(ipField.getText(), Integer.valueOf(portField.getText()));
            }
        });
    }

    private void makeIcons() {
        closeButton = new ImageButton("icons/close.png", "icons/close_hovered.png",
                GUIConfigs.iconWidth,
                GUIConfigs.iconHeight);

        minimizeButton = new ImageButton("icons/minimize.png", "icons/minimize_hovered.png",
                GUIConfigs.iconWidth,
                GUIConfigs.iconHeight);

        minimizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                CredentialsFrame.getIpPortInstance().setState(Frame.ICONIFIED);
                CredentialsFrame.getIpPortInstance().setState(Frame.NORMAL);
            }
        });

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
    }

    private void makeFields() {
        ipField = new TextField(10);
        ipField.setBorder(null);
        ipField.addKeyListener(new MyAction());

        portField = new TextField(10);
        portField.setBorder(null);
        portField.addKeyListener(new MyAction());
    }

    private void drawString(String text, int x, int y, int size, int style,
                            Color color, Graphics graphic) {
        Graphics2D graphics2D = (Graphics2D) graphic;
        Font font = CredentialsFrame.getCustomFont(FontType.TEXT, style, size);
        FontMetrics fontMetrics = graphics2D.getFontMetrics(font);
        int width = fontMetrics.stringWidth(text);

        graphics2D.setColor(color);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        graphics2D.setFont(font);

        int isNotError = 1;
        if (color == Color.RED) {
            isNotError = 2;
            graphics2D.setColor(new Color(0, 0, 0, 150));
            graphics2D.fillRect(x - width / isNotError + 2 - 3, y - 11, width + 6, 14);
        }
        graphics2D.setColor(color);
        graphics2D.drawString(text, x - width / isNotError + 2, y);
    }

    private void layoutComponent() {
        ipField.setBounds(GUIConfigs.credentialFrameWidth / 2 + constAddX - 12,
                startFieldY + 0 * textFieldDis,
                100, 20);
        add(ipField);

        portField.setBounds(GUIConfigs.credentialFrameWidth / 2 + constAddX - 12,
                startFieldY + 1 * textFieldDis,
                100, 20);
        add(portField);

        enterButton.setBounds(GUIConfigs.credentialFrameWidth / 2 - GUIConfigs.medButtonWidth / 2,
                loginButtonY,
                GUIConfigs.medButtonWidth,
                GUIConfigs.medButtonHeight);
        add(enterButton);

        //
        minimizeButton.setBounds(iconX, endIconY - iconsDis,
                GUIConfigs.iconWidth,
                GUIConfigs.iconHeight);
        add(minimizeButton);

        closeButton.setBounds(iconX, endIconY,
                GUIConfigs.iconWidth,
                GUIConfigs.iconHeight);
        add(closeButton);
    }

    class MyAction implements KeyListener {
        public void keyTyped(KeyEvent keyEvent) {
        }

        public void keyReleased(KeyEvent keyEvent) {
        }

        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                enterButton.doClick();
            }
        }
    }
}
