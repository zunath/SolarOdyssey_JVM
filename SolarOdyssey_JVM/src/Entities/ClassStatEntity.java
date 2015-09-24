package Entities;

import javax.persistence.*;

@Entity
@Table(name = "ClassStats")
public class ClassStatEntity {

    @Id
    @Column(name = "ClassStatID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int classStatID;

    @Column(name = "CharacterClassID")
    private int characterClassID;

    @Column(name = "LevelID")
    private int levelID;

    @Column(name = "HitPoints")
    private int hitPoints;

    @Column(name = "Essence")
    private int essence;

    @Column(name = "Strength")
    private int strength;

    @Column(name = "Dexterity")
    private int dexterity;

    @Column(name = "Constitution")
    private int constitution;

    @Column(name = "Wisdom")
    private int wisdom;

    @Column(name = "Intelligence")
    private int intelligence;

    @Column(name = "Charisma")
    private int charisma;

    public int getClassStatID() {
        return classStatID;
    }

    public void setClassStatID(int classStatID) {
        this.classStatID = classStatID;
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

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getEssence() {
        return essence;
    }

    public void setEssence(int essence) {
        this.essence = essence;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }
}
