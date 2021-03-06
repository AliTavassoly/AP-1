package hearthstone.models.card.minion.minions;

import com.fasterxml.jackson.annotation.JsonProperty;
import hearthstone.models.behaviours.Battlecry;
import hearthstone.models.behaviours.EndTurnBehave;
import hearthstone.models.card.Card;
import hearthstone.models.card.CardType;
import hearthstone.models.card.Rarity;
import hearthstone.models.card.minion.MinionCard;
import hearthstone.models.card.minion.MinionType;
import hearthstone.models.hero.HeroType;
import hearthstone.server.data.ServerData;
import hearthstone.server.network.HSServer;
import hearthstone.util.CursorType;
import hearthstone.util.HearthStoneException;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class Sathrovarr extends MinionCard implements Battlecry, EndTurnBehave {
    @Transient
    @JsonProperty("didBattlecry")
    private boolean didBattlecry;

    public Sathrovarr() {
    }

    public Sathrovarr(int id, String name, String description, int manaCost, HeroType heroType, Rarity rarity, CardType cardType, int health, int attack,
                      boolean isDeathRattle, boolean isTriggeredEffect,
                      boolean isSpellSafe, boolean isHeroPowerSafe, boolean isDivineShield,
                      boolean isTaunt, boolean isCharge, boolean isRush, MinionType minionType) {
        super(id, name, description, manaCost, heroType, rarity, cardType, health, attack,
                isDeathRattle, isTriggeredEffect, isSpellSafe, isHeroPowerSafe, isDivineShield,
                isTaunt, isCharge, isRush, minionType);
    }

    private void doBattlecry(Object object) throws HearthStoneException {
        if (!(object instanceof MinionCard))
            throw new HearthStoneException("choose a minion!");

        Card card = (Card) object;
        if (card.getPlayerId() != this.getPlayerId()) {
            throw new HearthStoneException("choose a friendly minion!");
        } else if (card == this) {
            throw new HearthStoneException("you cant choose this minion for it's behave!");
        } else {
            HSServer.getInstance().getPlayer(getPlayerId()).getFactory().makeAndPutDeck(ServerData.getCardByName(card.getName()));

            HSServer.getInstance().getPlayer(getPlayerId()).getFactory().makeAndPutHand(ServerData.getCardByName(card.getName()));

            HSServer.getInstance().getPlayer(getPlayerId()).getFactory().makeAndSummonMinion(ServerData.getCardByName(card.getName()));

            HSServer.getInstance().deleteMouseWaitingRequest(playerId);

            HSServer.getInstance().updateGame(playerId);
        }
    }

    @Override
    public void battlecry() {
        HSServer.getInstance().createMouseWaiting(playerId, CursorType.SEARCH, this);
    }

    @Override
    public void endTurnBehave() {
        isFirstTurn = false;
    }

    @Override
    public void found(Object object) throws HearthStoneException {
        if (isFirstTurn) {
            if (!didBattlecry) {
                doBattlecry(object);
                didBattlecry = true;
            }
        } else
            super.found(object);
    }
}
