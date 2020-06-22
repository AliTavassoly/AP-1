package hearthstone.models.card.spell.spells;

import hearthstone.models.card.CardType;
import hearthstone.models.card.Rarity;
import hearthstone.models.card.spell.SpellBehaviour;
import hearthstone.models.card.spell.SpellCard;
import hearthstone.models.hero.HeroType;

public class Sprint  extends SpellCard implements SpellBehaviour {
    public Sprint() { }

    public Sprint(int id, String name, String description, int manaCost, HeroType heroType, Rarity rarity, CardType cardType){
        super(id, name, description, manaCost, heroType, rarity, cardType);
    }
}
