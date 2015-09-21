package Entities;

import javax.persistence.*;

@Entity
@Table(name = "PCClasses")
public class PCClassEntity {
    private int playerClassID;
    private int characterClassID;
    private int levelID;
    private int experience;

    public int getPlayerClassID() {
        return playerClassID;
    }

    public void setPlayerClassID(int playerClassID) {
        this.playerClassID = playerClassID;
    }

    public int getCharacterClassID() {
        return characterClassID;
    }

    public void setCharacterClassID(int characterClassID) {
        this.characterClassID = characterClassID;
    }

    public int getLevelID() {
        return levelID;
    }

    public void setLevelID(int levelID) {
        this.levelID = levelID;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
