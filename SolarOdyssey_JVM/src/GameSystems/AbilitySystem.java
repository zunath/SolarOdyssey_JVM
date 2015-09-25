package GameSystems;

import Data.Repository.AbilityRepository;
import Entities.ClassAbilityEntity;
import NWNX.NWNX_Events;
import org.nwnx.nwnx2.jvm.NWObject;

public class AbilitySystem {

    public static void OnFeatUsed(NWObject pc)
    {
        int featID = NWNX_Events.GetEventSubType();
        NWObject target = NWNX_Events.GetEventTarget();
        AbilityRepository repo = new AbilityRepository();
        ClassAbilityEntity entity = repo.GetClassAbilityByFeatID(featID);
        if(entity == null) return;

        if(entity.getCastingTime() > 0.0f)
        {
            CastSpell(entity);
        }
        else
        {
            ActivateAbility(entity);
        }

    }

    private static void CastSpell(ClassAbilityEntity ability)
    {

    }

    private static void ActivateAbility(ClassAbilityEntity ability)
    {

    }

}
