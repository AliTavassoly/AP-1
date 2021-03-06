package hearthstone.server.logic;

import hearthstone.models.player.Player;
import hearthstone.server.data.ServerData;
import hearthstone.server.model.GameType;
import hearthstone.server.network.HSServer;
import hearthstone.server.network.ServerMapper;
import hearthstone.shared.GameConfigs;
import hearthstone.util.HearthStoneException;
import hearthstone.util.Rand;

public class SoloGame extends Game{
    public SoloGame(Player player0, Player player1, int id0, int id1, GameType gameType) {
        super(player0, player1, id0, id1, gameType);
    }

    @Override
    protected void selectPassive() throws HearthStoneException {
        selectPassive(player0);
        player1.setPassive(ServerData.basePassives.get(Rand.getInstance().getRandomNumber(ServerData.basePassives.size())).copy());
    }

    protected void selectPassive(Player player) throws HearthStoneException {
        ServerMapper.selectPassiveRequest(player.getPlayerId(), HSServer.getInstance().getClientHandlerByPlayer(player));

        long startTime = System.currentTimeMillis();

        while (player.getPassive() == null) {
            if ((System.currentTimeMillis() - startTime) / 1000 > 10) {
                throw new HearthStoneException("Player didn't choose passive!");
            }
            try {
                sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void discardInitialCards() throws HearthStoneException {
        discardInitialCards(player0);
    }

    protected void discardInitialCards(Player player) throws HearthStoneException {
        ServerMapper.selectNotWantedCardsRequest(player.getPlayerId(), player.getTopCards(GameConfigs.initialDiscardCards), HSServer.getInstance().getClientHandlerByPlayer(player));

        long startTime = System.currentTimeMillis();

        while (!player.isDiscardedCards()) {
            if ((System.currentTimeMillis() - startTime) / 1000 > 10) {
                throw new HearthStoneException("Player didn't discard initial cards!");
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
