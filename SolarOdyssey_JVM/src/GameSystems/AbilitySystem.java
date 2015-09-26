package GameSystems;

import Bioware.Position;
import Data.Repository.AbilityRepository;
import Entities.ClassAbilityEntity;
import Helper.ErrorHelper;
import Abilities.IAbility;
import NWNX.NWNX_Events;
import NWNX.NWNX_Funcs;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.Animation;
import org.nwnx.nwnx2.jvm.constants.DurationType;
import org.nwnx.nwnx2.jvm.constants.VfxDur;

public class AbilitySystem {

    public static void OnFeatUsed(NWObject pc)
    {
        int featID = NWNX_Events.GetEventSubType();
        NWObject target = NWNX_Events.GetEventTarget();
        AbilityRepository repo = new AbilityRepository();
        ClassAbilityEntity entity = repo.GetClassAbilityByFeatID(featID);
        if(entity == null) return;
        IAbility ability = LoadAbilityScript(entity.getScriptClassName());
        if(ability == null) return;

        if(!ability.CanCastSpell())
        {
            NWScript.sendMessageToPC(pc,
                    ability.CannotCastSpellMessage() == null ?
                            "That ability cannot be used at this time." :
                            ability.CannotCastSpellMessage());
            return;
        }

        if(entity.getCastingTime() > 0.0f)
        {
            CastSpell(pc, target, entity, ability);
        }
        else
        {
            ability.OnImpact(pc, target);
        }
    }

    private static void CastSpell(final NWObject pc, final NWObject target, final ClassAbilityEntity ability, final IAbility abilityScript)
    {
        NWScript.clearAllActions(false);
        Position.TurnToFaceObject(target, pc);
        NWScript.applyEffectToObject(DurationType.TEMPORARY,
                NWScript.effectVisualEffect(VfxDur.ELEMENTAL_SHIELD, false),
                pc,
                ability.getCastingTime() + 0.2f);
        NWScript.actionPlayAnimation(Animation.LOOPING_CONJURE1, 1.0f, ability.getCastingTime() - 0.1f);

        NWNX_Funcs.StartTimingBar(pc, ability.getCastingTime(), "");
        Scheduler.delay(pc, 1050 * ability.getCastingTime(), new Runnable() {
            @Override
            public void run() {
                abilityScript.OnImpact(pc, target);
            }
        });
    }


    private static IAbility LoadAbilityScript(String scriptName)
    {
        IAbility ability = null;

        try
        {
            Class scriptClass = Class.forName("Abilities." + scriptName);
            ability = (IAbility) scriptClass.newInstance();
        }
        catch (Exception ex)
        {
            ErrorHelper.HandleException(ex, "AbilitySystem.LoadAbilityScript() error");
        }

        return ability;
    }
}
