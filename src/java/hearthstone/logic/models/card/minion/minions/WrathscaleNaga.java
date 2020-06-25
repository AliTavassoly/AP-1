package hearthstone.logic.models.card.minion.minions;

import hearthstone.DataTransform;
import hearthstone.Mapper;
import hearthstone.logic.models.card.Card;
import hearthstone.logic.models.card.CardType;
import hearthstone.logic.models.card.Rarity;
import hearthstone.logic.models.card.interfaces.FriendlyMinionDies;
import hearthstone.logic.models.card.minion.MinionCard;
import hearthstone.logic.models.card.minion.MinionType;
import hearthstone.logic.models.hero.HeroType;
import hearthstone.util.Rand;

import java.util.ArrayList;

public class WrathscaleNaga extends MinionCard implements FriendlyMinionDies {
    public WrathscaleNaga(){ }

    public WrathscaleNaga(int id, String name, String description, int manaCost, HeroType heroType, Rarity rarity, CardType cardType, int health, int attack,
                          boolean isDeathRattle, boolean isTriggeredEffect, boolean isSpellDamage, boolean isDivineShield,
                          boolean isTaunt, boolean isCharge, boolean isRush, MinionType minionType) {
        super(id, name, description, manaCost, heroType, rarity, cardType, health, attack,
                isDeathRattle, isTriggeredEffect, isSpellDamage, isDivineShield,
                isTaunt, isCharge, isRush, minionType);
    }


    @Override
    public void friendlyMinionDies() {
        ArrayList<Card> land = DataTransform.getInstance().getLand(getPlayer().getEnemyPlayerId());
        if(land.size() == 0)
            return;
        int ind = Rand.getInstance().getRandomNumber(land.size());
        MinionCard card = (MinionCard) land.get(ind);
        try {
            Mapper.getInstance().damage(3, card);
        } catch (Exception ignore) {}
    }
}