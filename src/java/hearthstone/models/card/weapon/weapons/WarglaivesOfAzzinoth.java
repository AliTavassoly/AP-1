package hearthstone.models.card.weapon.weapons;

import hearthstone.models.card.CardType;
import hearthstone.models.card.Rarity;
import hearthstone.models.card.weapon.WeaponCard;
import hearthstone.models.hero.HeroType;

public class WarglaivesOfAzzinoth extends WeaponCard {
    public WarglaivesOfAzzinoth() { }

    public WarglaivesOfAzzinoth(int id, String name, String description, int manaCost, HeroType heroType, Rarity rarity, CardType cardType, int durability, int attack){
        super(id, name, description, manaCost, heroType, rarity, cardType, durability, attack);
    }
}
