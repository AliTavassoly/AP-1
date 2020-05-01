package hearthstone.gui;

import hearthstone.gui.controls.dialogs.ErrorDialog;
import hearthstone.gui.game.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BaseFrame extends JFrame {
    private Font hearthStoneFont;

    public BaseFrame() {
        try (InputStream in = new BufferedInputStream(
                this.getClass().getResourceAsStream("/fonts/text_font.ttf"))) {
            hearthStoneFont = Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Image cursorImage = null;
        try {
            cursorImage = ImageIO.read(this.getClass().getResourceAsStream(
                    "/images/cursor.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,
                new Point(0, 0), "customCursor");
        setCursor(customCursor);
    }

    public Font getCustomFont(int style, int size) {
        return hearthStoneFont.deriveFont(style, size);
    }

    public void switchPanelTo(JFrame frame, JPanel panel) {
        frame.getContentPane().setVisible(false);
        frame.setContentPane(panel);
    }

    public static void error(String text) {
        ErrorDialog errorDialog = new ErrorDialog(GameFrame.getInstance(), text,
                SizeConfigs.errorWidth, SizeConfigs.errorHeight);
    }
}
