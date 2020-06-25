package hearthstone.logic.models.card.minion.minions;

import hearthstone.HearthStone;
import hearthstone.Mapper;
import hearthstone.logic.models.card.Card;
import hearthstone.logic.models.card.CardType;
import hearthstone.logic.models.card.Rarity;
import hearthstone.logic.models.card.interfaces.Battlecry;
import hearthstone.logic.models.card.minion.MinionCard;
import hearthstone.logic.models.card.minion.MinionType;
import hearthstone.logic.models.hero.HeroType;
import hearthstone.util.CursorType;
import hearthstone.util.HearthStoneException;

public class Sathrovarr extends MinionCard implements Battlecry {
    public Sathrovarr(){ }

    public Sathrovarr(int id, String name, String description, int manaCost, HeroType heroType, Rarity rarity, CardType cardType, int health, int attack,
                        boolean isDeathRattle, boolean isTriggeredEffect, boolean isSpellDamage, boolean isDivineShield,
                        boolean isTaunt, boolean isCharge, boolean isRush, MinionType minionType) {
        super(id, name, description, manaCost, heroType, rarity, cardType, health, attack,
                isDeathRattle, isTriggeredEffect, isSpellDamage, isDivineShield,
                isTaunt, isCharge, isRush, minionType);
    }

    private void doBattlecry(Object object) throws HearthStoneException{
        if(!(object instanceof MinionCard))
            throw new HearthStoneException("choose a minion!");

        Card card = (Card)object;
        if (card.getPlayer() != this.getPlayer()){
            throw new HearthStoneException("choose a friendly minion!");
        } else if (card == this){
            throw new HearthStoneException("you cant choose this minion for it's behave!");
        } else {
            this.getPlayer().makeAndPutDeck(HearthStone.getCardByName(card.getName()));
            this.getPlayer().makeAndPutHand(HearthStone.getCardByName(card.getName()));
            this.getPlayer().makeAndSummonMinion(HearthStone.getCardByName(card.getName()));

            Mapper.getInstance().updateBoard();
            Mapper.getInstance().deleteCurrentMouseWaiting();
        }
    }

    @Override
    public void battlecry() {
        Mapper.getInstance().makeNewMouseWaiting(CursorType.SEARCH, this);
    }

    @Override
    public void found(Object object) throws HearthStoneException{
        if (isFirstTurn)
            doBattlecry(object);
        super.found(object);
    }

    @Override
    public boolean pressed() {
        return numberOfAttack > 0;
    }
}