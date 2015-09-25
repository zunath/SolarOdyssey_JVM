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

    @Column(name = "Experience")
    private int experience;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CharacterClassID")
    private CharacterClassEntity characterClass;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LevelID")
    private ClassLevelEntity classLevel;

    public PCClassEntity()
    {
    }

    public PCClassEntity(String playerID, int characterClassID, int levelID, int exp)
    {
        this.playerID = playerID;
        this.experience = exp;
        this.setCharacterClass(new CharacterClassEntity());
        getCharacterClass().setCharacterClassID(characterClassID);
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
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

    public CharacterClassEntity getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClassEntity characterClass) {
        this.characterClass = characterClass;
    }

    public ClassLevelEntity getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(ClassLevelEntity classLevel) {
        this.classLevel = classLevel;
    }
}
