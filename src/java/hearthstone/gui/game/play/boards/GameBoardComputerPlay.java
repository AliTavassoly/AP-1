package hearthstone.gui.game.play.boards;

import hearthstone.HearthStone;
import hearthstone.gui.SizeConfigs;
import hearthstone.gui.controls.dialogs.PassiveDialog;
import hearthstone.gui.game.GameFrame;
import hearthstone.gui.game.play.boards.GameBoard;
import hearthstone.gui.game.play.controls.BoardCardButton;
import hearthstone.logic.GameConfigs;
import hearthstone.logic.gamestuff.Game;
import hearthstone.models.player.Player;
import hearthstone.models.card.Card;
import hearthstone.util.Rand;

import java.util.ArrayList;

public class GameBoardComputerPlay extends GameBoard {
    public GameBoardComputerPlay(Player myPlayer, Player enemyPlayer, Game game) {
        super(myPlayer, enemyPlayer, game);
    }

    @Override
    protected void drawEnemyCardsOnHand() {
        ArrayList<Card> cards = enemyPlayer.getHand();
        if (cards.size() == 0)
            return;
        int dis = enemyHandDisCard / cards.size();

        for (int i = 0; i < cards.size(); i++) {
            BoardCardButton cardButton = new BoardCardButton(SizeConfigs.smallCardWidth,
                    SizeConfigs.smallCardHeight);

            cardButton.setBounds(enemyHandX + dis * (i - cards.size() / 2),
                    enemyHandY,
                    SizeConfigs.smallCardWidth, SizeConfigs.smallCardHeight);
            add(cardButton);
        }
    }

    @Override
    protected void showPassiveDialogs() {
        PassiveDialog passiveDialog0 = new PassiveDialog(
                GameFrame.getInstance(),
                GameConfigs.initialPassives * SizeConfigs.medCardWidth + extraPassiveX,
                SizeConfigs.medCardHeight + extraPassiveY,
                Rand.getInstance().getRandomArray(
                        GameConfigs.initialPassives,
                        HearthStone.basePassives.size())
        );
        myPlayer.setPassive(passiveDialog0.getPassive());
        myPlayer.doPassives();
    }
}