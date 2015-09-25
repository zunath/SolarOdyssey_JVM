package Entities;

import javax.persistence.*;

@Entity
@Table(name = "ClassAbilities")
public class ClassAbilityEntity {

    @Id
    @Column(name = "ClassAbilityID")
    private int classAbilityID;

    @Column(name = "FeatID")
    private int featID;

    @Column(name = "EssenceCost")
    private int essenceCost;

    @Column(name = "ScriptClassName")
    private String scriptClassName;

    @Column(name = "CastingTime")
    private float CastingTime;

    @Column(name = "CooldownTime")
    private float CooldownTime;

    public int getClassAbilityID() {
        return classAbilityID;
    }

    public void setClassAbilityID(int classAbilityID) {
        this.classAbilityID = classAbilityID;
    }

    public int getFeatID() {
        return featID;
    }

    public void setFeatID(int featID) {
        this.featID = featID;
    }

    public int getEssenceCost() {
        return essenceCost;
    }

    public void setEssenceCost(int essenceCost) {
        this.essenceCost = essenceCost;
    }

    public String getScriptClassName() {
        return scriptClassName;
    }

    public void setScriptClassName(String scriptClassName) {
        this.scriptClassName = scriptClassName;
    }

    public float getCastingTime() {
        return CastingTime;
    }

    public void setCastingTime(float castingTime) {
        CastingTime = castingTime;
    }

    public float getCooldownTime() {
        return CooldownTime;
    }

    public void setCooldownTime(float cooldownTime) {
        CooldownTime = cooldownTime;
    }
}
