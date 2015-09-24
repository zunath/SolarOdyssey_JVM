package Entities;

import javax.persistence.*;

@Entity
@Table(name = "ClassLevels")
public class ClassLevelEntity {

    @Id
    @Column(name = "ClassLevelID")
    private int classLevelID;

    @Column(name = "ExperienceRequired")
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
