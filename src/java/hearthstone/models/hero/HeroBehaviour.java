package hearthstone.models.hero;

import hearthstone.util.HearthStoneException;

public interface HeroBehaviour {
    void gotDamage(int damage) throws HearthStoneException;
    void gotHeal(int heal) throws HearthStoneException;
    void restoreHealth(int heal);

    void startTurnBehave();
}
