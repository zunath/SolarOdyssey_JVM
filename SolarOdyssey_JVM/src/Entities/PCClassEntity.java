package Entities;

import javax.persistence.*;

@Entity
@Table(name = "PCClasses")
public class PCClassEntity {

    @Id
    @Column(name = "PCClassID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pcClassID;

    @Column(name = "PlayerID")
    private String playerID;

    @Column(name = "CharacterClassID")
    private int characterClassID;

    @Column(name = "LevelID")
    private int levelID;

    @Column(name = "Experience")
    private int experience;

    public PCClassEntity()
    {
    }

    public PCClassEntity(String playerID, int characterClassID, int levelID, int exp)
    {
        this.playerID = playerID;
        this.characterClassID = characterClassID;
        this.levelID = levelID;
        this.experience = exp;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
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

    public int getPcClassID() {
        return pcClassID;
    }

    public void setPcClassID(int pcClassID) {
        this.pcClassID = pcClassID;
    }

}
