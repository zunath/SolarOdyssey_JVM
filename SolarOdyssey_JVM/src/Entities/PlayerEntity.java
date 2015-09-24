package Entities;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name="PlayerCharacters")
public class PlayerEntity {

    @Id
    @Column(name="PlayerID")
    private String pcID;
    @Column(name="CharacterName")
    private String characterName;
    @Column(name="HitPoints")
    private int hitPoints;
    @Column(name = "CurrentEssence")
    private int currentEssence;
    @Column(name="LocationAreaTag")
    private String locationAreaTag;
    @Column(name="LocationX")
    private float locationX;
    @Column(name="LocationY")
    private float locationY;
    @Column(name="LocationZ")
    private float locationZ;
    @Column(name="LocationOrientation")
    private float locationOrientation;
    @Column(name="CreateTimestamp")
    private Date createTimestamp;
    @Column(name = "VersionNumber")
    private int versionNumber;
    @Column(name = "BindLocationAreaTag")
    private String bindLocationAreaTag;
    @Column(name = "BindLocationOrientation")
    private float bindLocationOrientation;
    @Column(name = "BindLocationX")
    private float bindLocationX;
    @Column(name = "BindLocationY")
    private float bindLocationY;
    @Column(name = "BindLocationZ")
    private float bindLocationZ;
    @Column(name = "ActiveClassID")
    private int activeClassID;

    public PlayerEntity()
    {

    }

    public String getPCID()
    {
        return pcID;
    }

    public void setPCID(String pcID)
    {
        this.pcID = pcID;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String _characterName) {
        this.characterName = _characterName;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int _hitPoints) {
        this.hitPoints = _hitPoints;
    }

    public String getLocationAreaTag() {
        return locationAreaTag;
    }

    public void setLocationAreaTag(String _locationAreaTag) {
        this.locationAreaTag = _locationAreaTag;
    }

    public float getLocationX() {
        return locationX;
    }

    public void setLocationX(float _locationX) {
        this.locationX = _locationX;
    }

    public float getLocationY() {
        return locationY;
    }

    public void setLocationY(float _locationY) {
        this.locationY = _locationY;
    }

    public float getLocationZ() {
        return locationZ;
    }

    public void setLocationZ(float _locationZ) {
        this.locationZ = _locationZ;
    }

    public float getLocationOrientation() {
        return locationOrientation;
    }

    public void setLocationOrientation(float _locationOrientation) {
        this.locationOrientation = _locationOrientation;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date _createTimestamp) {
        this.createTimestamp = _createTimestamp;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getBindLocationAreaTag() {
        return bindLocationAreaTag;
    }

    public void setBindLocationAreaTag(String bindLocationAreaTag) {
        this.bindLocationAreaTag = bindLocationAreaTag;
    }

    public float getBindLocationOrientation() {
        return bindLocationOrientation;
    }

    public void setBindLocationOrientation(float bindLocationOrientation) {
        this.bindLocationOrientation = bindLocationOrientation;
    }

    public float getBindLocationX() {
        return bindLocationX;
    }

    public void setBindLocationX(float bindLocationX) {
        this.bindLocationX = bindLocationX;
    }

    public float getBindLocationY() {
        return bindLocationY;
    }

    public void setBindLocationY(float bindLocationY) {
        this.bindLocationY = bindLocationY;
    }

    public float getBindLocationZ() {
        return bindLocationZ;
    }

    public void setBindLocationZ(float bindLocationZ) {
        this.bindLocationZ = bindLocationZ;
    }

    public int getActiveClassID() {
        return activeClassID;
    }

    public void setActiveClassID(int activeClassID) {
        this.activeClassID = activeClassID;
    }

    public int getCurrentEssence() {
        return currentEssence;
    }

    public void setCurrentEssence(int currentEssence) {
        this.currentEssence = currentEssence;
    }
}
