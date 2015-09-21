package Entities;

import javax.persistence.*;

@Entity
@Table(name = "ClassLevels")
public class ClassLevelEntity {
    private int classLevelID;
    private int experienceRequired;

    public int getClassLevelID() {
        return classLevelID;
    }

    public void setClassLevelID(int classLevelID) {
        this.classLevelID = classLevelID;
    }

    public int getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(int experienceRequired) {
        this.experienceRequired = experienceRequired;
    }
}
