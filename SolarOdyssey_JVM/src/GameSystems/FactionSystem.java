package GameSystems;

import Data.Repository.FactionRepository;
import Data.Repository.ServerConfigurationRepository;
import Entities.FactionEntity;
import Entities.FactionRelationshipEntity;
import Entities.PCFactionReputationEntity;
import Entities.ServerConfigurationEntity;
import GameObject.CreatureGO;
import GameObject.PlayerGO;
import Helper.ColorToken;
import NWNX.NWNX_Events;
import NWNX.NWNX_Funcs;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.ObjectType;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FactionSystem {
    private static int ReputationMinimum = 0;
    private static int ReputationMaximum = 100;
    private static int RelationshipType_Friend = 1;
    private static int RelationshipType_Enemy = 2;

    public static void OnModuleExamine(NWObject objSelf)
    {
        NWObject target = NWNX_Events.GetEventTarget();
        CreatureGO creatureGO = new CreatureGO(target);
        int factionID = creatureGO.GetFactionID();

        if(NWScript.getIsPC(target) ||
           NWScript.getObjectType(target) != ObjectType.CREATURE ||
           NWScript.getIsDM(objSelf) ||
           !NWScript.getIsPC(objSelf) ||
           factionID <= 0) return;

        PlayerGO playerGO = new PlayerGO(objSelf);
        FactionRepository repo = new FactionRepository();
        PCFactionReputationEntity entity = repo.GetPCFactionReputationByID(playerGO.getUUID(), factionID);
        if(entity == null) return;

        FactionEntity faction = entity.getFaction();

        String description = ColorToken.Green() + faction.getName() + ColorToken.End() + "\n";
        description += GetReputationDescription(entity.getReputation()) + "\n\n";
        description += NWScript.getDescription(target, true, true);

        NWScript.setDescription(target, description, true);
    }

    public static void OnCreatureDeath(NWObject objSelf)
    {
        NWObject pc = NWScript.getLastKiller();
        if(!NWScript.getIsPC(pc) || NWScript.getIsDM(pc)) return;

        Random random = new Random();
        CreatureGO creatureGO = new CreatureGO(objSelf);
        FactionRepository repo = new FactionRepository();
        ServerConfigurationEntity config = ServerConfigurationRepository.GetServerConfiguration();
        List<FactionRelationshipEntity> relationships = repo.GetFactionRelationships(creatureGO.GetFactionID());

        FactionRelationshipEntity selfFaction = new FactionRelationshipEntity();
        selfFaction.setFactionID(creatureGO.GetFactionID());
        selfFaction.setRelationshipTowardsFactionID(creatureGO.GetFactionID());
        selfFaction.setRelationshipTypeID(RelationshipType_Friend);
        relationships.add(selfFaction);

        for(NWObject member: NWScript.getFactionMembers(pc, true))
        {
            if(NWScript.getDistanceBetween(member, objSelf) <= config.getMaxExpGainDistance()) {
                PlayerGO memberGO = new PlayerGO(member);
                HashMap<Integer, PCFactionReputationEntity> reputations = new HashMap<>();

                List<PCFactionReputationEntity> pcRelationships = repo.GetPCFactionReputations(memberGO.getUUID(), relationships);
                for(PCFactionReputationEntity rep : pcRelationships)
                    reputations.put(rep.getFaction().getFactionID(), rep);

                for (FactionRelationshipEntity rel : relationships)
                {
                    PCFactionReputationEntity rep = reputations.get(rel.getRelationshipTowardsFactionID());
                    FactionEntity faction = rep.getFaction();
                    int adjustmentAmount = 1;

                    if (rel.getRelationshipTypeID() == RelationshipType_Friend) {
                        adjustmentAmount = -(random.nextInt(3) + 1);
                        rep.setReputation(rep.getReputation() + adjustmentAmount);
                        if (rep.getReputation() < ReputationMinimum) rep.setReputation(ReputationMinimum);

                        if (rep.getReputation() == ReputationMinimum)
                            NWScript.sendMessageToPC(pc, "Your reputation with " + faction.getName() + " could not possibly get worse.");
                        else
                            NWScript.sendMessageToPC(pc, "Your reputation with " + faction.getName() + " decreased.");
                    }
                    else if (rel.getRelationshipTypeID() == RelationshipType_Enemy) {
                        rep.setReputation(rep.getReputation() + adjustmentAmount);
                        if(rep.getReputation() > ReputationMaximum) rep.setReputation(ReputationMaximum);

                        if(rep.getReputation() == ReputationMaximum)
                            NWScript.sendMessageToPC(pc, "Your reputation with " + faction.getName() + " could not possibly get better.");
                        else
                            NWScript.sendMessageToPC(pc, "Your reputation with " + faction.getName() + " increased.");

                    }

                    repo.Save(rep);
                    AdjustNWNFactionStanding(pc, rep.getFaction().getFactionID(), adjustmentAmount);
                }
            }
        }

    }

    private static String GetReputationDescription(int reputation)
    {
        String description;

        if(reputation <= 10) description = ColorToken.Red() + "Scowls at you, ready to attack!" + ColorToken.End();
        else if(reputation <= 30) description = ColorToken.Yellow() + "Glares at you threateningly." + ColorToken.End();
        else if(reputation <= 50) description = ColorToken.Blue() + "Looks at you apprehensively." + ColorToken.End();
        else if(reputation <= 70) description = ColorToken.White() + "Looks at you indifferently." + ColorToken.End();
        else if(reputation <= 90) description = ColorToken.Custom(0, 255, 255) + "Kindly considers you." + ColorToken.End();
        else description = ColorToken.Purple() + "Considers you an ally." + ColorToken.End();

        return description;
    }

    public static void AdjustNWNFactionStanding(NWObject oPC, int factionID, int adjustment)
    {
        NWObject npc = NWScript.getObjectByTag("FACTION_" + factionID, 0);
        NWScript.adjustReputation(oPC, npc, adjustment);
    }

    public static void OnModuleLoad()
    {
        FactionRepository repo = new FactionRepository();
        List<FactionEntity> factions = repo.GetAllFactions();
        NWLocation location = NWScript.getLocation(NWScript.getWaypointByTag("FACTION_NPC_WP"));

        for(FactionEntity faction : factions)
        {
            NWObject npc = NWScript.createObject(ObjectType.CREATURE, "faction_npc", location, false, "FACTION_" + faction.getFactionID());
            NWNX_Funcs.SetFactionId(npc, faction.getFactionID());
            NWScript.setName(npc, "Faction Holder: " + faction.getName());
        }
    }

    public static void OnModuleEnter()
    {
        NWObject oPC = NWScript.getEnteringObject();
        if(NWScript.getIsDM(oPC) || !NWScript.getIsPC(oPC)) return;

        PlayerGO pcGO = new PlayerGO(oPC);
        FactionRepository repo = new FactionRepository();
        List<FactionEntity> factions = repo.GetAllFactions();
        HashMap<Integer, PCFactionReputationEntity> reps = new HashMap<>();
        for(PCFactionReputationEntity rep : repo.GetAllPCFactionReputations(pcGO.getUUID()))
            reps.put(rep.getFaction().getFactionID(), rep);

        for(FactionEntity faction : factions)
        {
            NWObject factionNPC = NWScript.getObjectByTag("FACTION_" + faction.getFactionID(), 0);
            NWScript.adjustReputation(oPC, factionNPC, -100);
            PCFactionReputationEntity rep = reps.get(faction.getFactionID());

            if(rep == null)
                NWScript.adjustReputation(oPC, factionNPC, faction.getDefaultPCReputation());
            else
                NWScript.adjustReputation(oPC, factionNPC, rep.getReputation());


        }

    }

}
