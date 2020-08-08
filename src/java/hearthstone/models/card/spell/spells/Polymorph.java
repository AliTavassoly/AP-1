package hearthstone.models.card.spell.spells;

import hearthstone.models.card.CardType;
import hearthstone.models.card.Rarity;
import hearthstone.models.card.minion.MinionCard;
import hearthstone.models.card.spell.SpellCard;
import hearthstone.models.hero.HeroType;
import hearthstone.server.data.ServerData;
import hearthstone.server.network.HSServer;
import hearthstone.util.CursorType;
import hearthstone.util.HearthStoneException;

import javax.persistence.Entity;

@Entity
public class Polymorph  extends SpellCard {
    public Polymorph() { }

    public Polymorph(int id, String name, String description, int manaCost, HeroType heroType, Rarity rarity, CardType cardType){
        super(id, name, description, manaCost, heroType, rarity, cardType);
    }

    @Override
    public void doAbility() {
        HSServer.getInstance().createMouseWaiting(playerId, getCursorType(),this);
    }

    @Override
    public CursorType getCursorType() {
        return CursorType.SEARCH;
    }

    @Override
    public void found(Object object) throws HearthStoneException {
        if(object instanceof MinionCard){
            MinionCard minionCard = (MinionCard)object;
            //Mapper.transformMinion(minionCard.getPlayerId(), minionCard.getCardGameId(), (MinionCard) HearthStone.getCardByName("Sheep"));
            HSServer.getInstance().getPlayer(minionCard.getPlayerId()).transformMinion(minionCard.getCardGameId(), (MinionCard) ServerData.getCardByName("Sheep"));

            // Mapper.updateBoard();
            HSServer.getInstance().updateGameRequest(playerId);
        }
    }
}
