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

    @Column(name = "Reputation")
    private int reputation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FactionID")
    private FactionEntity faction;

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

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public FactionEntity getFaction() {
        return faction;
    }

    public void setFaction(FactionEntity faction) {
        this.faction = faction;
    }
}
