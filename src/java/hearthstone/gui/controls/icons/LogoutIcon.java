package hearthstone.gui.controls.icons;

import hearthstone.HearthStone;
import hearthstone.gui.SizeConfigs;
import hearthstone.gui.controls.ImageButton;
import hearthstone.gui.controls.SureDialog;
import hearthstone.gui.credetials.CredentialsFrame;
import hearthstone.gui.game.GameFrame;
import hearthstone.util.HearthStoneException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutIcon extends ImageButton {
    public LogoutIcon(String normalPath, String activePath,
                      int width, int height) {
        super(normalPath, activePath, width, height);
        configIcon();
    }

    private void configIcon() {
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    try {
                        if (HearthStone.currentAccount != null)
                            hearthstone.util.Logger.saveLog("Click_icon",
                                    "Logout_button");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    SureDialog sureDialog = new SureDialog(GameFrame.getInstance(), "Are you sure you want to logout ?",
                            SizeConfigs.dialogWidth, SizeConfigs.dialogHeight);
                    boolean sure = sureDialog.getValue();
                    if (sure) {
                        HearthStone.logout();
                        GameFrame.getInstance().setVisible(false);
                        GameFrame.getInstance().dispose();
                        CredentialsFrame.getNewInstance().setVisible(true);
                    }
                } catch (HearthStoneException e) {
                    try {
                        hearthstone.util.Logger.saveLog("ERROR",
                                e.getClass().getName() + ": " + e.getMessage()
                                        + "\nStack Trace: " + e.getStackTrace());
                    } catch (Exception f) {
                    }
                    System.out.println(e.getMessage());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }
}