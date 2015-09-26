package GameObject;

import Data.Repository.NPCRepository;
import Entities.NPCEntity;
import NWNX.NWNX_Funcs;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.Ability;

public class CreatureGO {

    private NWObject creature;

    public CreatureGO(NWObject creature)
    {
        this.creature = creature;
    }

    public void LoadNPCStats()
    {
        NPCRepository repo = new NPCRepository();
        int npcID = NWScript.getLocalInt(creature, "NPC_ID");
        if(npcID <= 0) return;

        NPCEntity entity = repo.GetNPC(npcID);
        if(entity == null) return;

        NWScript.setLocalInt(creature, "NPC_LEVEL", entity.getLevelID());
        NWScript.setLocalInt(creature, "NPC_ESSENCE", entity.getEssence());
        NWScript.setLocalInt(creature, "FACTION_ID", entity.getFactionID());

        if(entity.getHitPoints() > 0)
            NWNX_Funcs.SetMaxHitPoints(creature, entity.getHitPoints());
        if(entity.getStrength() > 0)
            NWNX_Funcs.SetAbilityScore(creature, Ability.STRENGTH, entity.getStrength());
        if(entity.getDexterity() > 0)
            NWNX_Funcs.SetAbilityScore(creature, Ability.DEXTERITY, entity.getDexterity());
        if(entity.getWisdom() > 0)
            NWNX_Funcs.SetAbilityScore(creature, Ability.WISDOM, entity.getWisdom());
        if(entity.getIntelligence() > 0)
            NWNX_Funcs.SetAbilityScore(creature, Ability.INTELLIGENCE, entity.getIntelligence());
        if(entity.getCharisma() > 0)
            NWNX_Funcs.SetAbilityScore(creature, Ability.CHARISMA, entity.getCharisma());
        if(entity.getConstitution() > 0)
            NWNX_Funcs.SetAbilityScore(creature, Ability.CONSTITUTION, entity.getConstitution());
    }

    public int GetLevel()
    {
        return NWScript.getLocalInt(creature, "NPC_LEVEL");
    }

    public int GetFactionID()
    {
        return NWScript.getLocalInt(creature, "FACTION_ID");
    }

}
