package Entities;

import javax.persistence.*;

@Entity
@Table(name = "ClassFeatLevels")
public class ClassFeatLevelEntity {

    @Id
    @Column(name = "ClassFeatLevelID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int classFeatLevelID;

    @Column(name = "CharacterClassID")
    private int characterClassID;

    @Column(name = "ClassLevelID")
    private int classLevelID;

    @Column(name = "FeatID")
    private int featID;

    public int getClassFeatLevelID() {
        return classFeatLevelID;
    }

    public void setClassFeatLevelID(int classFeatLevelID) {
        this.classFeatLevelID = classFeatLevelID;
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
