package Entities;

import javax.persistence.*;

@Entity
@Table(name = "ClassAbilities")
public class ClassAbilityEntity {

    @Id
    @Column(name = "ClassAbilityID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int classAbilityID;

    @Column(name = "Name")
    private String name;

    @Column(name = "FeatID")
    private int featID;

    @Column(name = "EssenceCost")
    private int essenceCost;

    @Column(name = "ScriptClassName")
    private String scriptClassName;

    @Column(name = "CastingTime")
    private int castingTime;

    @Column(name = "CooldownTime")
    private int cooldownTime;

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

    public int getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(int castingTime) {
        this.castingTime = castingTime;
    }

    public int getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(int cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
