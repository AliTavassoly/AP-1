package hearthstone.models.card.weapon;

import com.fasterxml.jackson.annotation.JsonProperty;
import hearthstone.models.behaviours.IsAttacked;
import hearthstone.models.card.Card;
import hearthstone.models.card.CardType;
import hearthstone.models.card.Rarity;
import hearthstone.models.card.minion.MinionCard;
import hearthstone.models.hero.Hero;
import hearthstone.models.hero.HeroType;
import hearthstone.server.network.HSServer;
import hearthstone.util.HearthStoneException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public abstract class WeaponCard extends Card implements WeaponBehaviour {
    @Column
    protected int durability;
    @Column
    protected int attack;

    @Transient
    @JsonProperty("numberOfAttack")
    protected int numberOfAttack;

    @Transient
    @JsonProperty("isFirstTurn")
    protected boolean isFirstTurn;

    @Transient
    @JsonProperty("canAttack")
    protected boolean canAttack;

    public WeaponCard() {
    }

    public WeaponCard(int id, String name, String description, int manaCost, HeroType heroType, Rarity rarity, CardType cardType, int durability, int attack) {
        super(id, name, description, manaCost, heroType, rarity, cardType);
        this.durability = durability;
        this.attack = attack;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public boolean canAttack() {
        return numberOfAttack > 0 &&
                HSServer.getInstance().getPlayer(getPlayerId()).isMyTurn() && !HSServer.getInstance().getPlayer(playerId).getHero().isFreeze();
    }

    @Override
    public void startTurnBehave() {
        numberOfAttack = 1;
        durability--;
        isFirstTurn = false;
    }

    @Override
    public void attack(MinionCard minionCard) throws HearthStoneException {
        minionCard.gotDamage(this.attack);
        HSServer.getInstance().updateGame(playerId);

        try {
            HSServer.getInstance().getPlayer(playerId).getHero().gotDamage(minionCard.getAttack());

            HSServer.getInstance().updateGame(playerId);
        } catch (HearthStoneException ignore) {
        }

        if (minionCard instanceof IsAttacked)
            ((IsAttacked) minionCard).isAttacked();
    }

    @Override
    public void attack(Hero hero) throws HearthStoneException {
        if (HSServer.getInstance().getPlayer(hero.getPlayerId()).haveTaunt()) {
            throw new HearthStoneException("There is taunt in front of you!");
        }
        hero.gotDamage(this.attack);

        HSServer.getInstance().updateGame(playerId);
    }

    @Override
    public void found(Object object) throws HearthStoneException {
        if(!canAttack)
            return;
        if (object instanceof MinionCard) {
            if (((Card) object).getPlayerId() == this.getPlayerId()) {
                throw new HearthStoneException("Choose enemy!");
            } else {
                this.attack((MinionCard) object);
                numberOfAttack--;
            }
        } else if (object instanceof Hero) {
            if (((Hero) object).getPlayerId() == this.getPlayerId()) {
                throw new HearthStoneException("Choose enemy!");
            } else {
                this.attack((Hero) object);
                numberOfAttack--;
            }
        }
    }

    @Override
    public boolean pressed() {
        return canAttack();
    }
}