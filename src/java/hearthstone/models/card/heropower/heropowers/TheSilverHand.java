package hearthstone.models.card.heropower.heropowers;

import hearthstone.models.card.CardType;
import hearthstone.models.card.heropower.HeroPowerCard;
import hearthstone.models.hero.Hero;
import hearthstone.models.hero.HeroType;
import hearthstone.server.network.HSServer;
import hearthstone.util.CursorType;

import javax.persistence.Entity;

@Entity
public class TheSilverHand extends HeroPowerCard  {
    public TheSilverHand() { }

    public TheSilverHand(int id, String name, String description, int manaCost, HeroType heroType, CardType cardType){
        super(id, name, description, manaCost, heroType, cardType);
    }

    private void doAbility(){
        //Mapper.reduceMana(getPlayerId(), this.getManaCost());
        HSServer.getInstance().getPlayer(getPlayerId()).reduceMana(this.getManaCost());

        //Mapper.summonMinionFromCurrentDeck(getPlayerId(), 1, 1);
        //Mapper.summonMinionFromCurrentDeck(getPlayerId(), 1, 1);

        for(int i = 0; i < 2; i++){
            HSServer.getInstance().getPlayer(getPlayerId()).getFactory().summonMinionFromCurrentDeck(1, 1);
        }

        //Mapper.updateBoard();
        HSServer.getInstance().updateGameRequest(playerId);

        log();
    }

    @Override
    public CursorType lookingForCursorType() {
        return CursorType.SEARCH;
    }

    @Override
    public void found(Object object) {
        if(object instanceof Hero && ((Hero)object).getPlayerId() == this.getPlayerId()){
            doAbility();
            numberOfAttack--;
        }
    }
}
