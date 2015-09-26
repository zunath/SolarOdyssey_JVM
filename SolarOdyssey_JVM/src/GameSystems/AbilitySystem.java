package GameSystems;

import Bioware.Position;
import Data.Repository.AbilityRepository;
import Data.Repository.PlayerRepository;
import Entities.ClassAbilityEntity;
import Entities.PlayerEntity;
import GameObject.PlayerGO;
import Helper.ErrorHelper;
import Abilities.IAbility;
import NWNX.NWNX_Events;
import NWNX.NWNX_Funcs;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.NWVector;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.Animation;
import org.nwnx.nwnx2.jvm.constants.DurationType;
import org.nwnx.nwnx2.jvm.constants.VfxDur;

import java.util.UUID;

public class AbilitySystem {

    private static int SPELL_STATUS_STARTED = 1;
    private static int SPELL_STATUS_INTERRUPTED = 2;

    public static void OnFeatUsed(NWObject pc)
    {
        PlayerRepository playerRepo = new PlayerRepository();
        PlayerGO pcGO = new PlayerGO(pc);
        int featID = NWNX_Events.GetEventSubType();
        NWObject target = NWNX_Events.GetEventTarget();
        AbilityRepository repo = new AbilityRepository();
        ClassAbilityEntity entity = repo.GetClassAbilityByFeatID(featID);
        if(entity == null) return;
        IAbility ability = LoadAbilityScript(entity.getScriptClassName());
        if(ability == null) return;
        PlayerEntity playerEntity = playerRepo.getByUUID(pcGO.getUUID());

        if(!ability.CanCastSpell())
        {
            NWScript.sendMessageToPC(pc,
                    ability.CannotCastSpellMessage() == null ?
                            "That ability cannot be used at this time." :
                            ability.CannotCastSpellMessage());
            return;
        }

        if(playerEntity.getCurrentEssence() < entity.getEssenceCost())
        {
            NWScript.sendMessageToPC(pc, "You do not have enough essence.");
            return;
        }

        if(pcGO.isBusy() || pcGO.isCastingSpell())
        {
            NWScript.sendMessageToPC(pc, "You are too busy to activate that ability.");
            return;
        }

        if(entity.getCastingTime() > 0.0f)
        {
            CastSpell(pc, target, entity, ability);
        }
        else
        {
            ability.OnImpact(pc, target);
            playerEntity.setCurrentEssence(playerEntity.getCurrentEssence() - entity.getEssenceCost());
            playerRepo.save(playerEntity);
        }
    }

    private static void CastSpell(final NWObject pc, final NWObject target, final ClassAbilityEntity ability, final IAbility abilityScript)
    {
        final String spellUUID = UUID.randomUUID().toString();
        final PlayerGO pcGO = new PlayerGO(pc);
        NWScript.clearAllActions(false);
        Position.TurnToFaceObject(target, pc);
        NWScript.applyEffectToObject(DurationType.TEMPORARY,
                NWScript.effectVisualEffect(VfxDur.ELEMENTAL_SHIELD, false),
                pc,
                ability.getCastingTime() + 0.2f);
        NWScript.actionPlayAnimation(Animation.LOOPING_CONJURE1, 1.0f, ability.getCastingTime() - 0.1f);

        pcGO.setIsCastingSpell(true);
        CheckForSpellInterruption(pc, spellUUID, NWScript.getPosition(pc));
        NWScript.setLocalInt(pc, spellUUID, SPELL_STATUS_STARTED);

        NWNX_Funcs.StartTimingBar(pc, ability.getCastingTime(), "");
        Scheduler.delay(pc, 1050 * ability.getCastingTime(), new Runnable() {
            @Override
            public void run() {
                if(NWScript.getLocalInt(pc, spellUUID) == SPELL_STATUS_INTERRUPTED)
                {
                    NWScript.deleteLocalInt(pc, spellUUID);
                    NWScript.sendMessageToPC(pc, "Your spell has been interrupted.");
                    return;
                }

                NWScript.deleteLocalInt(pc, spellUUID);
                PlayerRepository repo = new PlayerRepository();
                PlayerEntity entity = repo.getByUUID(pcGO.getUUID());

                abilityScript.OnImpact(pc, target);
                entity.setCurrentEssence(entity.getCurrentEssence() - ability.getEssenceCost());
                repo.save(entity);

                pcGO.setIsCastingSpell(false);
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

    private static void CheckForSpellInterruption(final NWObject pc, final String spellUUID, final NWVector position)
    {
        NWVector currentPosition = NWScript.getPosition(pc);

        if(!currentPosition.equals(position))
        {
            PlayerGO pcGO = new PlayerGO(pc);
            NWNX_Funcs.StopTimingBar(pc, "");
            pcGO.setIsCastingSpell(false);
            NWScript.setLocalInt(pc, spellUUID, SPELL_STATUS_INTERRUPTED);
            return;
        }

        Scheduler.delay(pc, 1000, new Runnable() {
            @Override
            public void run() {
                CheckForSpellInterruption(pc, spellUUID, position);
            }
        });
    }

}
