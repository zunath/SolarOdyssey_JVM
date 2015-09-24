package Entities;

import javax.persistence.*;

@Entity
@Table(name = "Factions")
public class FactionEntity {

    @Id
    @Column(name = "FactionID")
    private int factionID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    public int getFactionID() {
        return factionID;
    }

    public void setFactionID(int factionID) {
        this.factionID = factionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
