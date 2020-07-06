package hearthstone.logic.models.passives;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hearthstone.logic.models.card.Card;
import hearthstone.logic.models.hero.Hero;
import hearthstone.util.AbstractAdapter;

public abstract class Passive {
    private int id;
    private String name;
    private int playerId;

    public Passive() {
    }

    public Passive(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Passive copy() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Card.class, new AbstractAdapter<Card>());
        gsonBuilder.registerTypeAdapter(Hero.class, new AbstractAdapter<Hero>());
        gsonBuilder.registerTypeAdapter(Passive.class, new AbstractAdapter<Passive>());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(gson.toJson(this, Passive.class), Passive.class);
    }
}
