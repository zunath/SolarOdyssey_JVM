package Entities;

import javax.persistence.*;

@Entity
@Table(name = "FactionRelationships")
public class FactionRelationshipEntity {

    @Id
    @Column(name = "FactionRelationshipID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int factionRelationshipID;

    @Column(name = "RelationshipTypeID")
    private int relationshipTypeID;

    @Column(name = "FactionID")
    private int factionID;

    @Column(name = "RelationshipTowardsFactionID")
    private int relationshipTowardsFactionID;


    public int getFactionRelationshipID() {
        return factionRelationshipID;
    }

    public void setFactionRelationshipID(int factionRelationshipID) {
        this.factionRelationshipID = factionRelationshipID;
    }

    public int getRelationshipTypeID() {
        return relationshipTypeID;
    }

    public void setRelationshipTypeID(int relationshipTypeID) {
        this.relationshipTypeID = relationshipTypeID;
    }

    public int getFactionID() {
        return factionID;
    }

    public void setFactionID(int factionID) {
        this.factionID = factionID;
    }

    public int getRelationshipTowardsFactionID() {
        return relationshipTowardsFactionID;
    }

    public void setRelationshipTowardsFactionID(int relationshipTowardsFactionID) {
        this.relationshipTowardsFactionID = relationshipTowardsFactionID;
    }
}
