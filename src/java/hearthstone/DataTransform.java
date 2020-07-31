package hearthstone;

import hearthstone.gui.game.play.boards.GameBoard;
import hearthstone.logic.GameConfigs;
import hearthstone.logic.behaviours.Character;
import hearthstone.logic.gamestuff.Game;
import hearthstone.models.Deck;
import hearthstone.models.card.Card;
import hearthstone.models.card.CardType;
import hearthstone.models.card.heropower.HeroPowerCard;
import hearthstone.models.card.minion.MinionCard;
import hearthstone.models.card.reward.RewardCard;
import hearthstone.models.card.weapon.WeaponCard;
import hearthstone.models.hero.Hero;
import hearthstone.models.passive.Passive;
import hearthstone.models.player.Player;

import java.util.ArrayList;

public class DataTransform {
    private static DataTransform instance;

    private DataTransform() {
    }

    public static DataTransform getInstance() {
        if (instance == null) {
            return instance = new DataTransform();
        } else {
            return instance;
        }
    }

    public Player getPlayer(int playerId){
        return HearthStone.currentGame.getPlayerById(playerId);
    }

    public Game getGame(){
        return HearthStone.currentGame;
    }

    public GameBoard getGameBoard(){
        return HearthStone.currentGameBoard;
    }

    public int getWhoseTurn() {
        return getGame().getWhoseTurn();
    }

    public ArrayList<Card> getLand(int playerId) {
        return getPlayer(playerId).getLand();
    }

    public ArrayList<Card> getHand(int playerId) {
        return getPlayer(playerId).getHand();
    }

    public ArrayList<MinionCard> getNeighbors(int playerId, Card card) {
        return getPlayer(playerId).neighborCards(card);
    }

    public Deck getDeck(int playerId) {
        return getPlayer(playerId).getDeck();
    }

    public int getMana(int playerId) {
        return getPlayer(playerId).getMana();
    }

    public int getMana(Card card) {
        return card.getManaCost();
    }

    public int getTurnNumber(int playerId) {
        return getPlayer(playerId).getTurnNumber();
    }

    public Passive getPassive(int playerId) {
        return getPlayer(playerId).getPassive();
    }

    public HeroPowerCard getHeroPower(int playerId) {
        return getPlayer(playerId).getHeroPower();
    }

    public WeaponCard getWeapon(int playerId) {
        return getPlayer(playerId).getWeapon();
    }

    public RewardCard getReward(int playerId) {
        return getPlayer(playerId).getReward();
    }

    public Hero getHero(int playerId) {
        return getPlayer(playerId).getHero();
    }

    public String getPlayerName(int playerId){
        return getPlayer(playerId).getUsername();
    }

    public int spentManaOnMinions(int playerId) {
        return getPlayer(playerId).getManaSpentOnMinions();
    }

    public int spentManaOnSpells(int playerId) {
        return getPlayer(playerId).getManaSpentOnSpells();
    }

    public int getMaxManaInGame() {
        return GameConfigs.maxManaInGame;
    }

    public int getNumberOfPassive() {
        return GameConfigs.initialPassives;
    }

    public ArrayList<Card> getTopCards(int playerId, int numberOfTopCards){
        return getPlayer(playerId).getTopCards(numberOfTopCards);
    }

    public boolean isLost(int playerId){
        return getPlayer(playerId).getHero().getHealth() <= 0;
    }

    public boolean haveTaunt(int playerId){
        return getPlayer(playerId).haveTaunt();
    }

    public int getEnemyId(int playerId){
        if(playerId == 0)
            return 1;
        return 0;
    }

    public Card getRandomCardFromOriginalDeck(int playerId, CardType cardType){
        return getPlayer(playerId).getFactory().getRandomCardFromOriginalDeck(cardType);
    }

    public MinionCard getRandomMinionFromLand(int playerId){
        return getPlayer(playerId).getFactory().getRandomMinionFromLand();
    }

    public Card getRandomCardFromHand(int playerId){
        return getPlayer(playerId).getFactory().getRandomCardFromHand();
    }

    public Card getRandomCardFromCurrentDeck(int playerId){
        return getPlayer(playerId).getFactory().getRandomCardFromCurrentDeck();
    }
    public int getHealth(Character character){
        return character.getHealth();
    }

    public Passive getBasePassive(int ind){
        return HearthStone.basePassives.get(ind);
    }

    public boolean haveWeapon(int playerId){
        return getPlayer(playerId).haveWeapon();
    }

    public boolean isSpellSafe(MinionCard minionCard){
        return minionCard.isSpellSafe();
    }

    public Card deckCard(int playerId, int ind){
        return getPlayer(playerId).getDeck().getCards().get(ind);
    }
}
