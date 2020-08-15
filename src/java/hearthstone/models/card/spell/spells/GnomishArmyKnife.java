package hearthstone.models.card.spell.spells;

import com.fasterxml.jackson.annotation.JsonProperty;
import hearthstone.models.card.CardType;
import hearthstone.models.card.Rarity;
import hearthstone.models.card.minion.MinionCard;
import hearthstone.models.card.spell.SpellCard;
import hearthstone.models.hero.HeroType;
import hearthstone.server.network.HSServer;
import hearthstone.util.CursorType;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class GnomishArmyKnife  extends SpellCard {
    @Transient
    @JsonProperty("didAbility")
    private boolean didAbility;

    public GnomishArmyKnife() { }

    public GnomishArmyKnife(int id, String name, String description, int manaCost, HeroType heroType, Rarity rarity, CardType cardType){
        super(id, name, description, manaCost, heroType, rarity, cardType);
    }

    @Override
    public void doAbility() {
        HSServer.getInstance().createMouseWaiting(playerId, getCursorType(), this);
    }

    @Override
    public CursorType getCursorType() {
        return CursorType.SEARCH;
    }

    @Override
    public void found(Object object) {
        if(didAbility)
            return;
        if(object instanceof MinionCard){
            MinionCard minionCard = (MinionCard)object;

            minionCard.setDivineShield(true);

            minionCard.setTaunt(true);

            minionCard.setCharge(true);

            didAbility = true;

            HSServer.getInstance().updateGame(playerId);
        }
    }
}
