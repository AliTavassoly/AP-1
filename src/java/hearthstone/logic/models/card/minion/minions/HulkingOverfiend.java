package hearthstone.logic.models.card.minion.minions;

import hearthstone.DataTransform;
import hearthstone.Mapper;
import hearthstone.logic.models.card.Card;
import hearthstone.logic.models.card.CardType;
import hearthstone.logic.models.card.Rarity;
import hearthstone.logic.models.card.minion.MinionCard;
import hearthstone.logic.models.card.minion.MinionType;
import hearthstone.logic.models.hero.Hero;
import hearthstone.logic.models.hero.HeroType;
import hearthstone.util.HearthStoneException;

public class HulkingOverfiend extends MinionCard {
    private int thisTurnAttack;

    public HulkingOverfiend() {
    }

    public HulkingOverfiend(int id, String name, String description, int manaCost, HeroType heroType, Rarity rarity, CardType cardType, int health, int attack,
                            boolean isDeathRattle, boolean isTriggeredEffect, boolean isSpellDamage, boolean isDivineShield,
                            boolean isTaunt, boolean isCharge, boolean isRush, MinionType minionType) {
        super(id, name, description, manaCost, heroType, rarity, cardType, health, attack,
                isDeathRattle, isTriggeredEffect, isSpellDamage, isDivineShield,
                isTaunt, isCharge, isRush, minionType);
    }

    @Override
    public void startTurnBehave() {
        super.startTurnBehave();
        thisTurnAttack = 0;
    }

    @Override
    public void attack(Hero hero) throws HearthStoneException {
        if (DataTransform.getInstance().haveTaunt(hero.getPlayerId())) {
            throw new HearthStoneException("There is taunt in front of you!");
        } else if(isFirstTurn){
            throw new HearthStoneException("Rush card can not attack to hero in first turn!");
        }

        Mapper.getInstance().damage(this.attack, hero);
    }

    @Override
    public void found(Object object) throws HearthStoneException {
        if (object instanceof MinionCard) {
            if (((Card) object).getPlayerId() == this.getPlayerId()) {
                throw new HearthStoneException("Choose enemy!");
            } else {
                this.attack((MinionCard) object);
                if (!isFirstTurn)
                    numberOfAttack--;

                if (((MinionCard) object).getHealth() <= 0)
                    numberOfAttack++;

                numberOfAttackedMinion++;
                thisTurnAttack++;
            }
        } else if (object instanceof Hero) {
            if (((Hero) object).getPlayerId() == this.getPlayerId()) {
                throw new HearthStoneException("Choose enemy!");
            } else {
                this.attack((Hero) object);
                numberOfAttack--;
                numberOfAttackedHero++;
                thisTurnAttack++;
            }
        }
    }

    @Override
    public boolean pressed() {
        boolean minionPress = super.pressed();
        return (thisTurnAttack != 2 || (isFirstTurn && numberOfAttackedMinion == 0)) && minionPress;
    }
}
