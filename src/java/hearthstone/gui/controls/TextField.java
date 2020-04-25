package hearthstone.gui.controls;

import hearthstone.gui.credetials.CredentialsFrame;

import javax.swing.*;
import java.awt.*;

public class TextField extends JTextField {
    public TextField(int columns){
        super(columns);
        configTextField();
    }

    private void configTextField(){
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 150));
        setForeground(new Color(255, 255, 68));
        setFont(CredentialsFrame.getInstance().getCustomFont(Font.PLAIN, 20));
        setBorder(null);
    }

    protected void paintComponent(Graphics g) {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
