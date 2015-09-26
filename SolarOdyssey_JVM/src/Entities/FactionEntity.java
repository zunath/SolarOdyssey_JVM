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

    @Column(name = "DefaultPCReputation")
    private int defaultPCReputation;

    @Column(name = "NWNFactionID")
    private int nwnFactionID;

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

    public int getDefaultPCReputation() {
        return defaultPCReputation;
    }

    public void setDefaultPCReputation(int defaultPCReputation) {
        this.defaultPCReputation = defaultPCReputation;
    }

    public int getNwnFactionID() {
        return nwnFactionID;
    }

    public void setNwnFactionID(int nwnFactionID) {
        this.nwnFactionID = nwnFactionID;
    }
}
