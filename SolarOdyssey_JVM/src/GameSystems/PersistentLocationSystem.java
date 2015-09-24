package GameSystems;

import Data.Repository.PlayerRepository;
import Entities.PlayerEntity;
import GameObject.PlayerGO;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;

public class PersistentLocationSystem
{
    public static void OnAreaEnter(NWObject oPC, NWObject oArea)
    {
        LoadLocation(oPC, oArea);
        SaveLocation(oPC, oArea);
    }

    public static void SaveLocation(NWObject oPC, NWObject oArea)
    {
        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        String sTag = NWScript.getTag(oArea);
        if(!sTag.equals("ooc_area"))
        {
            PlayerGO pcGO = new PlayerGO(oPC);
            NWLocation location = NWScript.getLocation(oPC);
            PlayerRepository repo = new PlayerRepository();
            PlayerEntity entity = repo.getByUUID(pcGO.getUUID());
            entity.setLocationAreaTag(sTag);
            entity.setLocationX(location.getX());
            entity.setLocationY(location.getY());
            entity.setLocationZ(location.getZ());
            entity.setLocationOrientation(NWScript.getFacing(oPC));

            repo.save(entity);
        }
    }

    public static void LoadLocation(NWObject oPC, NWObject oArea)
    {
        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        if(NWScript.getTag(oArea).equals("ooc_area"))
        {
            PlayerGO pcGO = new PlayerGO(oPC);
            PlayerEntity entity = new PlayerRepository().getByUUID(pcGO.getUUID());
            NWObject area = NWScript.getObjectByTag(entity.getLocationAreaTag(), 0);
            final NWLocation location = new NWLocation(area,
                    entity.getLocationX(),
                    entity.getLocationY(),
                    entity.getLocationZ(),
                    entity.getLocationOrientation());

            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.actionJumpToLocation(location);
                }
            });
        }
    }
}
