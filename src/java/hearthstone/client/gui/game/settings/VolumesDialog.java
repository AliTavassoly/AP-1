package hearthstone.client.gui.game.settings;

import hearthstone.client.gui.controls.buttons.ImageButton;
import hearthstone.client.gui.controls.panels.ImagePanel;
import hearthstone.client.gui.game.GameFrame;
import hearthstone.shared.GUIConfigs;
import hearthstone.util.FontType;
import hearthstone.util.SoundPlayer;
import hearthstone.util.getresource.ImageResource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VolumesDialog extends JDialog {
    private ImageButton volumeUp, volumeDown;
    private JLabel volumeUpLabel, volumeDownLabel;
    private ImagePanel backgroundPanel;

    private ImageButton saveButton, cancelButton;

    private int volumeChange;

    private int width, height;

    public VolumesDialog(JFrame frame) {
        super(frame);
        this.width = GUIConfigs.volumeSettingsWidth;
        this.height = GUIConfigs.volumeSettingsHeight;

        volumeChange = 0;

        configDialog();

        makeButtons();

        makeLabels();

        makeVolumesButtons();

        layoutComponent();

        setVisible(true);
    }

    private void configDialog() {
        setSize(new Dimension(width, height));

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

        setModal(true);
        setResizable(false);

        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        backgroundPanel = new ImagePanel("settings_background.png", width, height);
        backgroundPanel.setOpaque(false);
        setContentPane(backgroundPanel);

        Image cursorImage = null;
        try{
            cursorImage = ImageResource.getInstance().getImage(
                    "/images/normal_cursor.png");
        } catch (Exception e){
            e.printStackTrace();
        }
        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,
                new Point(0, 0), "customCursor");
        setCursor(customCursor);
    }

    private void makeButtons() {
        saveButton = new ImageButton("save", "buttons/green_background.png", 0,
                Color.white, Color.yellow,
                15, 0,
                GUIConfigs.medButtonWidth, GUIConfigs.medButtonHeight);

        cancelButton = new ImageButton("cancel", "buttons/red_background.png", 0,
                Color.white, Color.yellow,
                15, 0,
                GUIConfigs.medButtonWidth, GUIConfigs.medButtonHeight);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                SoundPlayer.changeVolume(-volumeChange);
                dispose();
            }
        });
    }

    private void makeVolumesButtons(){
        volumeDown = new ImageButton("/icons/volume_down.png",
                "/icons/volume_down_hovered.png",
                GUIConfigs.iconHeight, GUIConfigs.iconWidth);
        volumeDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                volumeChange -= 3;
                SoundPlayer.changeVolume(-3);
            }
        });

        volumeUp = new ImageButton("/icons/volume_up.png",
                "/icons/volume_up_hovered.png",
                GUIConfigs.iconHeight, GUIConfigs.iconWidth);
        volumeUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                volumeChange += 3;
                SoundPlayer.changeVolume(+3);
            }
        });
    }

    private void makeLabels(){
        volumeDownLabel = new JLabel("Volume Down");
        volumeDownLabel.setFont(GameFrame.getInstance().getCustomFont(FontType.TEXT,0, 20));
        volumeDownLabel.setForeground(new Color(69, 28, 28));

        volumeUpLabel = new JLabel("Volume Up");
        volumeUpLabel.setFont(GameFrame.getInstance().getCustomFont(FontType.TEXT,0, 20));
        volumeUpLabel.setForeground(new Color(69, 28, 28));
    }

    private void layoutComponent() {
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        // first row
        grid.gridy = 0;

        grid.gridx = 0;
        add(volumeUpLabel, grid);

        grid.gridx = 1;
        add(volumeUp, grid);

        // second row
        grid.gridy = 1;

        grid.gridx = 0;
        grid.insets = new Insets(30, 0, 0, 0);
        add(volumeDownLabel, grid);

        grid.gridx = 1;
        grid.insets = new Insets(30, 0, 0, 0);
        add(volumeDown, grid);

        // third row
        grid.gridy = 2;

        grid.gridx = 0;
        grid.insets = new Insets(30, 0, 0, 0);
        add(saveButton, grid);

        grid.gridx = 1;
        grid.insets = new Insets(30, 0, 0, 0);
        add(cancelButton, grid);
    }
}