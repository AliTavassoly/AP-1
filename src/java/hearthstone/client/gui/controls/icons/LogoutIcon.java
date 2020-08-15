package hearthstone.client.gui.controls.icons;

import hearthstone.client.network.ClientMapper;
import hearthstone.client.gui.controls.buttons.ImageButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutIcon extends ImageButton {
    public LogoutIcon(String normalPath, String hoveredPath,
                      int width, int height) {
        super(normalPath, hoveredPath, width, height);
        configIcon();
    }

    private void configIcon() {
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ClientMapper.logoutRequest();
            }
        });
    }
}
