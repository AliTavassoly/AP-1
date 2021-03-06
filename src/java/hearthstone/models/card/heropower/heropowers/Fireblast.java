package hearthstone.models.card.heropower.heropowers;

import hearthstone.models.behaviours.IsAttacked;
import hearthstone.models.card.CardType;
import hearthstone.models.card.heropower.HeroPowerCard;
import hearthstone.models.card.minion.MinionCard;
import hearthstone.models.hero.Hero;
import hearthstone.models.hero.HeroType;
import hearthstone.server.network.HSServer;
import hearthstone.util.CursorType;
import hearthstone.util.HearthStoneException;

import javax.persistence.Entity;

@Entity
public class Fireblast extends HeroPowerCard {
    public Fireblast() {
    }

    public Fireblast(int id, String name, String description, int manaCost, HeroType heroType, CardType cardType) {
        super(id, name, description, manaCost, heroType, cardType);
    }

    private void doAbility(Object object) throws HearthStoneException {
        if (object instanceof Hero) {
            Hero hero = (Hero) object;
            if (HSServer.getInstance().getPlayer(hero.getPlayerId()).haveTaunt())
                throw new HearthStoneException("There is taunt in front of you!");

            hero.gotDamage(1);

            HSServer.getInstance().getPlayer(getPlayerId()).reduceMana(this.getManaCost());

            numberOfAttack--;
        } else if (object instanceof MinionCard) {
            MinionCard minion = (MinionCard) object;
            if (minion.isHeroPowerSafe())
                throw new HearthStoneException("This minion is hero power safe!");
            if (!minion.isImmune() && minion.isDivineShield()) {
                minion.removeDivineShield();
                HSServer.getInstance().updateGame(playerId);
                return;
            }

            minion.gotDamage(1);

            HSServer.getInstance().getPlayer(getPlayerId()).reduceMana(this.getManaCost());

            if (minion instanceof IsAttacked) {
                ((IsAttacked) minion).isAttacked();
            }

            numberOfAttack--;
        }
        HSServer.getInstance().updateGame(playerId);
    }

    @Override
    public CursorType lookingForCursorType() {
        return CursorType.ATTACK;
    }

    @Override
    public void found(Object object) throws HearthStoneException {
        if(!canAttack)
            return;
        if (object instanceof MinionCard) {
            MinionCard minionCard = (MinionCard) object;
            if (minionCard.getPlayerId() == this.getPlayerId())
                throw new HearthStoneException("Choose enemy!");

            doAbility(minionCard);
            numberOfAttack--;
        } else if (object instanceof Hero) {
            Hero hero = (Hero) object;
            if (hero.getPlayerId() == this.getPlayerId())
                throw new HearthStoneException("Choose enemy!");
            if (HSServer.getInstance().getPlayer(hero.getPlayerId()).haveTaunt())
                throw new HearthStoneException("There is taunt in front of you!");

            doAbility(hero);
            numberOfAttack--;
        }
    }
}
