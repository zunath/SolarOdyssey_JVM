package Entities;

import javax.persistence.*;

@Entity
@Table(name = "ClassAbilities")
public class ClassAbilityEntity {

    @Id
    @Column(name = "ClassAbilityID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int classAbilityID;

    @Column(name = "CharacterClassID")
    private int characterClassID;

    @Column(name = "ClassLevelID")
    private int classLevelID;

    @Column(name = "FeatID")
    private int featID;

    public int getClassAbilityID() {
        return classAbilityID;
    }

    public void setClassAbilityID(int classAbilityID) {
        this.classAbilityID = classAbilityID;
    }

    public int getClassLevelID() {
        return classLevelID;
    }

    public void setClassLevelID(int classLevelID) {
        this.classLevelID = classLevelID;
    }

    public int getFeatID() {
        return featID;
    }

    public void setFeatID(int featID) {
        this.featID = featID;
    }

    public int getCharacterClassID() {
        return characterClassID;
    }

    public void setCharacterClassID(int characterClassID) {
        this.characterClassID = characterClassID;
    }
}
