package Entities;

import javax.persistence.*;

@Entity
@Table(name = "CharacterClasses")
public class CharacterClassEntity {
    @Id
    @Column(name = "CharacterClassID")
    private int characterClassID;

    @Column(name = "Callsign")
    private String callsign;
    @Column(name = "Name")
    private String name;
    @Column(name = "IsActive")
    private boolean isActive;

    public int getCharacterClassID() {
        return characterClassID;
    }

    public void setCharacterClassID(int characterClassID) {
        this.characterClassID = characterClassID;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
