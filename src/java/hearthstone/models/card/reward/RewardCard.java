package hearthstone.models.card.reward;

import hearthstone.models.card.Card;
import hearthstone.models.card.CardType;
import hearthstone.models.card.Rarity;
import hearthstone.models.hero.HeroType;

public abstract class RewardCard extends Card implements RewardBehaviour{
    public RewardCard(){ }

    public RewardCard(int id, String name, String description,
                      int manaCost, HeroType heroType, Rarity rarity, CardType cardType){
        super(id, name, description, manaCost, heroType, rarity, cardType);
    }
}
