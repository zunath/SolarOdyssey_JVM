package Entities;

import javax.persistence.*;

@Entity
@Table(name = "PCFactionReputation")
public class PCFactionReputationEntity {

    @Id
    @Column(name = "PCFactionReputationID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pcFactionReputationID;

    @Column(name = "PlayerID")
    private String playerID;

    @Column(name = "FactionID")
    private int factionID;

    @Column(name = "Reputation")
    private int reputation;

    public int getPcFactionReputationID() {
        return pcFactionReputationID;
    }

    public void setPcFactionReputationID(int pcFactionReputationID) {
        this.pcFactionReputationID = pcFactionReputationID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public int getFactionID() {
        return factionID;
    }

    public void setFactionID(int factionID) {
        this.factionID = factionID;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }
}
