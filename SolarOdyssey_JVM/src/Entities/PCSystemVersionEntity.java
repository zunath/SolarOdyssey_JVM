package Entities;

import javax.persistence.*;

@Entity
@Table(name = "PCSystemVersions")
public class PCSystemVersionEntity {

    @Id
    @Column(name = "PlayerID")
    private String playerID;

    @Column(name = "ClassSystemVersion")
    private int classSystemVersion;


    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public int getClassSystemVersion() {
        return classSystemVersion;
    }

    public void setClassSystemVersion(int classSystemVersion) {
        this.classSystemVersion = classSystemVersion;
    }
}
