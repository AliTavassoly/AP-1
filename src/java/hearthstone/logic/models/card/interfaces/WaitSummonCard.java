package hearthstone.logic.models.card.interfaces;

import hearthstone.logic.models.card.Card;
import hearthstone.util.HearthStoneException;

public interface WaitSummonCard {
    boolean waitSummonCard(Card card) throws HearthStoneException;
}