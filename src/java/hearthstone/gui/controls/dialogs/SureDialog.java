package hearthstone.gui.controls.dialogs;

import hearthstone.gui.SizeConfigs;
import hearthstone.gui.controls.ImageButton;
import hearthstone.gui.controls.ImagePanel;
import hearthstone.gui.game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SureDialog extends MyDialog {
    private JLabel message;
    private ImageButton okButton, cancelButton;
    private String clicked;

    private int width, height;
    private String text;

    public SureDialog(JFrame frame, String text, int width, int height){
        super(frame, width, height);
        this.width = width;
        this.height = height;
        this.text = text;

        configDialog();

        makeButtons();

        makeLabels();

        layoutComponent();
    }

    private void configDialog(){
        ImagePanel backgroundPanel = new ImagePanel("dialog_background.png", width, height);
        backgroundPanel.setOpaque(false);
        setContentPane(backgroundPanel);
    }

    private void makeLabels(){
        message = new JLabel(text);
        message.setForeground(new Color(69, 28, 28));
        message.setFont(GameFrame.getInstance().getCustomFont(0, 15));
    }

    private void makeButtons(){
        okButton = new ImageButton("ok", "buttons/green_background.png", 0,
                Color.white, Color.yellow,
                15, 0,
                SizeConfigs.smallButtonWidth, SizeConfigs.smallButtonHeight);

        cancelButton = new ImageButton("cancel", "buttons/red_background.png", 0,
                Color.white, Color.yellow,
                15, 0,
                SizeConfigs.smallButtonWidth, SizeConfigs.smallButtonHeight);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clicked = "ok";
                setVisible(false);
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clicked = "cancel";
                setVisible(false);
                dispose();
            }
        });
    }

    private void layoutComponent(){
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        // first row
        grid.gridy = 0;
        grid.gridwidth = 2;

        grid.weighty = 1;
        grid.weightx = 5;

        grid.gridx = 0;
        add(message, grid);

        // second row
        grid.gridy = 1;

        grid.gridx = 0;
        grid.gridwidth = 1;
        grid.anchor = GridBagConstraints.FIRST_LINE_END;
        grid.insets = new Insets(0, 0, 0, 20);
        add(okButton, grid);

        grid.gridx = 1;
        grid.gridwidth = 1;
        grid.anchor = GridBagConstraints.FIRST_LINE_START;
        grid.insets = new Insets(0, 20, 0, 0);
        add(cancelButton, grid);
    }

    public boolean getValue() {
        setVisible(true);
        return clicked.equals("ok");
    }
}