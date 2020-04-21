package hearthstone.logic.models;

import hearthstone.HearthStone;
import hearthstone.logic.models.card.Card;
import hearthstone.logic.models.card.cards.MinionCard;
import hearthstone.logic.models.hero.Hero;
import hearthstone.logic.models.hero.HeroType;
import hearthstone.util.HearthStoneException;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private String name;
    private int totalGames;
    private int winGames;
    private HashMap<Integer, Integer> cardGame = new HashMap<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private HeroType heroType;

    public Deck() {
    }

    public Deck(String name, HeroType heroType) {
        this.name = name;
        this.heroType = heroType;
    }

    public HashMap<Integer, Integer> getCardGame() {
        return cardGame;
    }

    public void setCardGame(HashMap<Integer, Integer> cardGame) {
        this.cardGame = cardGame;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getWinGames() {
        return winGames;
    }

    public void setWinGames(int winGames) {
        this.winGames = winGames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeroType(HeroType heroType) {
        this.heroType = heroType;
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public int getTotalWin() {
        if (totalGames == 0)
            return 0;
        return winGames * 100 / totalGames;
    }

    public int getManaAv() {
        if (cards.size() == 0)
            return 0;
        int totalMana = 0;
        for (Card card : cards) {
            totalMana += card.getManaCost();
        }
        return totalMana / cards.size();
    }

    private Card biggerCard(Card card1, Card card2) {
        cardGame.putIfAbsent(card1.getId(), 0);
        cardGame.putIfAbsent(card2.getId(), 0);

        if (cardGame.get(card1.getId()) > cardGame.get(card2.getId())) {
            return card1;
        } else if (cardGame.get(card1.getId()) < cardGame.get(card2.getId())) {
            return card2;
        } else {
            // ENUM
            if (card1.getManaCost() > card2.getManaCost()) {
                return card1;
            } else if (card1.getManaCost() < card2.getManaCost()) {
                return card2;
            } else {
                if (card1 instanceof MinionCard)
                    return card1;
                return card2;
            }
        }
    }

    public Card getBestCard() {
        if (cards.size() == 0)
            return null;
        Card mxCard = cards.get(0);
        for (int i = 1; i < cards.size(); i++) {
            Card card = cards.get(i);
            mxCard = biggerCard(mxCard, card);
        }
        return mxCard;
    }

    //End of getter setter !

    public int numberOfCards(Card baseCard) {
        int ans = 0;
        for (Card card : cards) {
            if (card.getId() == baseCard.getId()) {
                ans++;
            }
        }
        return ans;
    }

    public boolean canAdd(Card baseCard, int cnt) {
        if (cards.size() + cnt > HearthStone.maxDeckSize) {
            return false;
        }
        if (numberOfCards(baseCard) + cnt > HearthStone.maxNumberOfCard) {
            return false;
        }
        if (!HearthStone.currentAccount.getUnlockedCards().contains(baseCard.getId())){
            return false;
        }
        return true;
    }

    public void add(Card baseCard, int cnt) throws Exception {
        if (cards.size() + cnt > HearthStone.maxDeckSize) {
            throw new HearthStoneException("Not enough space in your deck!");
        }
        if (numberOfCards(baseCard) + cnt > HearthStone.maxNumberOfCard) {
            throw new HearthStoneException("You can not have " + cnt + " number of this card!");
        }
        if (!HearthStone.currentAccount.getUnlockedCards().contains(baseCard.getId())) {
            throw new HearthStoneException("This card is lock for you!");
        }
        for (int i = 0; i < cnt; i++)
            cards.add(baseCard.copy());
    }

    public boolean canRemove(Card baseCard, int cnt) {
        return numberOfCards(baseCard) - cnt >= 0;
    }

    public void remove(Card baseCard, int cnt) throws Exception {
        if (numberOfCards(baseCard) - cnt < 0) {
            throw new HearthStoneException("There is not " + cnt + " number of " + baseCard.getName() + " in your deck!");
        }
        for (int i = 0; i < cnt; i++) {
            for (int j = 0; j < cards.size(); j++) {
                if (cards.get(j).getId() == baseCard.getId()) {
                    cards.remove(j);
                    break;
                }
            }
        }
    }
}