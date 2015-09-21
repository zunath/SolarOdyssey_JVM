package GameSystems;

import Data.Repository.PlayerRepository;
import Entities.PlayerEntity;
import GameObject.PlayerGO;
import Helper.ColorToken;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.DurationType;
import org.nwnx.nwnx2.jvm.constants.VfxImp;

public class DeathSystem {

    public static void BindStone_OnUse()
    {
        NWObject pc = NWScript.getLastUsedBy();
        PlayerGO pcGO = new PlayerGO(pc);
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());

        NWLocation location = NWScript.getLocation(pc);
        String areaTag = NWScript.getTag(location.getArea());

        entity.setBindLocationAreaTag(areaTag);
        entity.setBindLocationOrientation(location.getFacing());
        entity.setBindLocationX(location.getX());
        entity.setBindLocationY(location.getY());
        entity.setBindLocationZ(location.getZ());

        repo.save(entity);

        NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectVisualEffect(VfxImp.AC_BONUS, false), pc, 0.0f);
        NWScript.floatingTextStringOnCreature(ColorToken.Green() + "Your soul has been bound to this location." + ColorToken.End(), pc, false);
        NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectHeal(NWScript.getMaxHitPoints(pc)), pc, 0.0f);
    }

    public static void Module_OnRespawn()
    {
        NWObject pc = NWScript.getLastRespawnButtonPresser();
        PlayerGO pcGO = new PlayerGO(pc);
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());
        NWVector vector = NWScript.vector(entity.getBindLocationX(), entity.getBindLocationY(), entity.getBindLocationZ());

        final NWLocation location = NWScript.location(pc, vector, entity.getBindLocationOrientation());
        NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectResurrection(), pc, 0.0f);
        NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectHeal(NWScript.getMaxHitPoints(pc)), pc, 0.0f);

        Scheduler.assign(pc, new Runnable() {
            @Override
            public void run() {
                NWScript.actionJumpToLocation(location);
            }
        });
    }

    public static void Module_OnDeath()
    {
        NWObject pc = NWScript.getLastPlayerDied();
        NWObject hostile = NWScript.getLastHostileActor(pc);

        for(NWObject creature : NWScript.getFactionMembers(hostile, false))
            NWScript.clearPersonalReputation(pc, creature);

        NWScript.popUpDeathGUIPanel(pc, true, true, 0, "Press 'Respawn' to return to the last place you bound your soul.");
    }
}
