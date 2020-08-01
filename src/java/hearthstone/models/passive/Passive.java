package hearthstone.models.passive;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hearthstone.DataTransform;
import hearthstone.data.Data;
import hearthstone.models.card.Card;
import hearthstone.models.hero.Hero;
import hearthstone.models.specialpower.SpecialHeroPower;
import hearthstone.util.AbstractAdapter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class Passive {
    @Id
    private int id;
    @Column
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

    public void log(){
        try {
            hearthstone.util.Logger.saveLog("Passive",
                    DataTransform.getInstance().getPlayerName(getPlayerId()) +
                    " player, chose " + this.getName() + " passive!");
        } catch (Exception e){
            e.printStackTrace();
        }
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
        Gson gson = Data.getDataGson();
        return gson.fromJson(gson.toJson(this, Passive.class), Passive.class);
    }
}
