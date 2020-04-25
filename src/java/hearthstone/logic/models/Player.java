package hearthstone.logic.models;

import hearthstone.HearthStone;
import hearthstone.logic.models.card.Card;
import hearthstone.logic.models.card.cards.HeroPowerCard;
import hearthstone.logic.models.card.cards.MinionCard;
import hearthstone.logic.models.card.cards.SpellCard;
import hearthstone.logic.models.card.cards.WeaponCard;
import hearthstone.logic.models.hero.Hero;
import hearthstone.util.HearthStoneException;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private Hero hero;
    private Deck originalDeck;
    private Deck deck;

    private int mana;
    private int turnNumber;

    private ArrayList<Card> hand;
    private ArrayList<Card> land;
    private Random random;

    public Player(Hero hero, Deck deck) {
        this.hero = hero.copy();
        originalDeck = deck;
        this.deck = deck.copy();

        hand = new ArrayList<>();
        land = new ArrayList<>();
        random = new Random(System.currentTimeMillis());
        mana = 0;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public ArrayList<Card> getLand() {
        return land;
    }

    public void setLand(ArrayList<Card> land) {
        this.land = land;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    // End pf getter setter

    public void readyForPlay() throws Exception{
        if(!deck.isFull())
            throw new HearthStoneException("You should complete your deck first!");
    }

    public void startGame() throws Exception{
        turnNumber = 1;
        mana = 1;

        pickCard();
        pickCard();
        pickCard();
    }

    public void pickCard() throws Exception{
        if(deck.getCards().size() == 0)
            throw new HearthStoneException("Game ended!");
        int cardInd = random.nextInt(deck.getCards().size());
        Card card = deck.getCards().get(cardInd);
        deck.getCards().remove(cardInd);
        if(hand.size() == HearthStone.maxCardInHand)
            return;
        hand.add(card);

    }

    public void playCard(Card baseCard) throws Exception{
        if(land.size() == HearthStone.maxCardInLand)
            throw new HearthStoneException("your land is full!");
        if(baseCard.getManaCost() > mana)
            throw new HearthStoneException("you don't have enough mana!");
        for(int i = 0; i < hand.size(); i++){
            Card card = hand.get(i);
            if(card.getName().equals(baseCard.getName())){
                hand.remove(card);
                if (card instanceof HeroPowerCard){
                    land.add(card);
                } else if (card instanceof SpellCard) {

                } else if (card instanceof MinionCard ) {
                    land.add(card);
                } else if (card instanceof WeaponCard){
                    land.add(card);
                }
                mana -= card.getManaCost();
                break;
            }
        }
        for(Card card : originalDeck.getCards()){
            if(card.getName().equals(baseCard.getName())){
                originalDeck.cardPlay(card);
            }
        }
    }

    public void startTurn() throws Exception{
        mana = ++turnNumber;
        mana = Math.min(mana, HearthStone.maxManaInGame);
        pickCard();
    }
}