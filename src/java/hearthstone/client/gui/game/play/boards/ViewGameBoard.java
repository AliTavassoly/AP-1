package hearthstone.client.gui.game.play.boards;

import hearthstone.client.gui.controls.buttons.ImageButton;
import hearthstone.client.gui.controls.buttons.PassiveButton;
import hearthstone.client.gui.controls.dialogs.MessageDialog;
import hearthstone.client.gui.controls.panels.ImagePanel;
import hearthstone.client.gui.game.play.controls.*;
import hearthstone.models.player.Player;
import hearthstone.shared.GUIConfigs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewGameBoard extends GameBoard{
    public ViewGameBoard(Player myPlayer, Player enemyPlayer) {
        super(myPlayer, enemyPlayer);
    }

    @Override
    protected void makeCardOnHandMouseListener(BoardCardButton button, int startX, int startY, int width, int height) {
        // DO NOTHING
    }

    @Override
    protected void makeCardOnLandMouseListener(BoardCardButton button, int startX, int startY) {
        // DO NOTHING
    }

    @Override
    protected void makeWeaponMouseListener(WeaponButton button) {
        // DO NOTHING
    }

    @Override
    protected void makeHeroPowerMouseListener(HeroPowerButton button) {
        // DO NOTHING
    }

    @Override
    protected void makeHeroMouseListener(BoardHeroButton button) {
        // DO NOTHING
    }

    @Override
    protected void drawEndTurnTimeLine() {
        // DO NOTHING
    }

    @Override
    protected void makeGameStuff() {
        endTurnButton = new ImageButton("End Turn", "end_turn.png",
                "end_turn_hovered.png", 15, 1,
                GUIConfigs.endTurnButtonWidth, GUIConfigs.endTurnButtonHeight);

        sparkImage = new SparkImage(
                endTurnTimeLineStartX,
                endTurnTimeLineY - GUIConfigs.endTurnFireHeight / 2,
                GUIConfigs.endTurnFireWidth,
                GUIConfigs.endTurnFireHeight,
                "/images/spark_0.png");

        ropeImage = new ImagePanel("rope.png", GUIConfigs.endTurnRopeWidth + 7,
                GUIConfigs.endTurnRopeHeight, sparkImage.getX() + sparkImage.getWidth(),
                sparkImage.getY() + sparkImage.getHeight() / 2, true);
        add(ropeImage);

        myHero = new BoardHeroButton(myPlayer.getHero(),
                heroWidth, heroHeight, myPlayer.getPlayerId());
        makeHeroMouseListener(myHero);

        enemyHero = new BoardHeroButton(enemyPlayer.getHero(),
                heroWidth, heroHeight, enemyPlayer.getPlayerId());
        makeHeroMouseListener(enemyHero);

        myPassive = new PassiveButton(myPlayer.getPassive(),
                GUIConfigs.medCardWidth,
                GUIConfigs.medCardHeight);

        myMessageDialog = new MessageDialog("Not enough mana!", new Color(69, 27, 27),
                15, 0, -17, 2500, GUIConfigs.inGameErrorWidth, GUIConfigs.inGameErrorHeight);
        add(myMessageDialog);

        enemyMessageDialog = new MessageDialog("Not enough mana!", new Color(69, 27, 27),
                15, 0, -17, 2500, GUIConfigs.inGameErrorWidth, GUIConfigs.inGameErrorHeight);
        add(enemyMessageDialog);
    }
}
