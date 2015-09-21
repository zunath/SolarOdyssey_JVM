package GameSystems;

import Data.Repository.PlayerRepository;
import Entities.PlayerEntity;
import GameObject.PlayerGO;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.DamagePower;
import org.nwnx.nwnx2.jvm.constants.DamageType;
import org.nwnx.nwnx2.jvm.constants.DurationType;

public class PersistentHPSystem {

    public static void OnModuleEnter()
    {
        final NWObject oPC = NWScript.getEnteringObject();
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());

        if(entity == null) return;

        int hp = NWScript.getCurrentHitPoints(oPC);
        int damage;
        if(entity.getHitPoints() < 0)
        {
            damage = hp + Math.abs(entity.getHitPoints());
        }
        else
        {
            damage = hp - entity.getHitPoints();
        }

        if(damage != 0)
        {
            NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectDamage(damage, DamageType.MAGICAL, DamagePower.NORMAL), oPC, 0.0f);
        }
    }

    public static void OnModuleLeave()
    {
        NWObject pc = NWScript.getExitingObject();
        if(NWScript.getIsDM(pc)) return;

        PlayerGO gameObject = new PlayerGO(pc);
        PlayerRepository repo = new PlayerRepository();
        String uuid = gameObject.getUUID();

        PlayerEntity entity = repo.getByUUID(uuid);
        entity.setCharacterName(NWScript.getName(pc, false));
        entity.setHitPoints(NWScript.getCurrentHitPoints(pc));

        repo.save(entity);
    }

}
