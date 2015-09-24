package Entities;

import javax.persistence.*;

@Entity
@Table(name = "ClassAbilities")
public class ClassAbilityEntity {

    @Id
    @Column(name = "ClassAbilityID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int classAbilityID;

    @Column(name = "LevelID")
    private int levelID;

    @Column(name = "FeatID")
    private int featID;

    public int getClassAbilityID() {
        return classAbilityID;
    }

    public void setClassAbilityID(int classAbilityID) {
        this.classAbilityID = classAbilityID;
    }

    public int getLevelID() {
        return levelID;
    }

    public void setLevelID(int levelID) {
        this.levelID = levelID;
    }

    public int getFeatID() {
        return featID;
    }

    public void setFeatID(int featID) {
        this.featID = featID;
    }
}
