package GameSystems;

import Common.Constants;
import Data.Repository.CharacterClassRepository;
import Data.Repository.PCSystemVersionRepository;
import Data.Repository.PlayerRepository;
import Data.Repository.ServerConfigurationRepository;
import Entities.*;
import GameObject.CreatureGO;
import GameObject.PlayerGO;
import Helper.ColorToken;
import Helper.MenuHelper;
import NWNX.NWNX_Funcs;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.Ability;
import org.nwnx.nwnx2.jvm.constants.DurationType;
import org.nwnx.nwnx2.jvm.constants.Feat;
import org.nwnx.nwnx2.jvm.constants.VfxImp;

import java.util.List;

public class ClassSystem
{
    public static void OnModuleEnter()
    {
        HandleCharacterInitialization();
    }

    private static void HandleCharacterInitialization()
    {
        NWObject oPC = NWScript.getEnteringObject();
        if(NWScript.getIsDM(oPC) || !NWScript.getIsPC(oPC)) return;
        PlayerGO pcGO = new PlayerGO(oPC);
        PCSystemVersionRepository repo = new PCSystemVersionRepository();
        PCSystemVersionEntity entity = repo.GetPCSystemVersion(pcGO.getUUID());

        if(entity.getClassSystemVersion() <= 0)
        {
            ApplyClassStatChanges(oPC);
            entity.setClassSystemVersion(1);
            repo.Save(entity);
        }
    }

    public static void OnCreatureDeath(NWObject npc)
    {
        AwardPartyExperience(npc);
    }

    public static void ChangeClass(NWObject pc, int classID)
    {
        if(!NWScript.getIsPC(pc) || NWScript.getIsDM(pc)) return;
        PlayerRepository repo = new PlayerRepository();
        PlayerGO pcGO = new PlayerGO(pc);
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());
        entity.setActiveClassID(classID);
        repo.save(entity);

        ApplyClassStatChanges(pc);
    }

    public static void AwardPartyExperience(NWObject oNPC)
    {
        NWObject oPC = NWScript.getLastKiller();

        if(NWScript.getFactionEqual(oPC, oNPC) ||
           NWScript.getIsDM(oPC) ||
           !NWScript.getIsPC(oPC)) return;

        ServerConfigurationEntity config = ServerConfigurationRepository.GetServerConfiguration();
        CharacterClassRepository repo = new CharacterClassRepository();
        PlayerGO pcGO = new PlayerGO(oPC);
        PCClassEntity pcClass = repo.GetActiveClass(pcGO.getUUID());

        NWObject area = NWScript.getArea(oNPC);
        NWObject[] party = NWScript.getFactionMembers(oPC, true);
        CreatureGO creatureGO = new CreatureGO(oNPC);

        int membersInArea = 0;
        int partyLevel = pcClass.getClassLevel().getClassLevelID();

        for(NWObject member : party)
        {
            if(NWScript.getArea(member).equals(area))
            {
                PlayerGO memberGO = new PlayerGO(member);
                PCClassEntity memberClass = repo.GetActiveClass(memberGO.getUUID());
                ClassLevelEntity classLevel = memberClass.getClassLevel();

                if(classLevel.getClassLevelID() > partyLevel)
                {
                    partyLevel = classLevel.getClassLevelID();
                }

                membersInArea++;
            }
        }

        int exp = CalculateExperience(partyLevel, creatureGO.GetLevel()) / membersInArea;
        if(exp > config.getMaxExpAcquirable()) exp = config.getMaxExpAcquirable();

        for(NWObject member : party)
        {
            if(NWScript.getArea(member).equals(area) &&
               NWScript.getDistanceBetween(member, oNPC) <= config.getMaxExpGainDistance())
            {
                AdjustClassExperience(member, exp);
            }
        }

    }

    public static void AdjustClassExperience(NWObject player, int exp)
    {
        PlayerGO playerGO = new PlayerGO(player);
        ServerConfigurationEntity config = ServerConfigurationRepository.GetServerConfiguration();
        CharacterClassRepository classRepo = new CharacterClassRepository();
        PCClassEntity pcClass = classRepo.GetActiveClass(playerGO.getUUID());
        ClassLevelEntity classLevel = pcClass.getClassLevel();
        List<ClassLevelEntity> classLevels = classRepo.GetClassLevels();

        if(exp > 0)
        {
            NWScript.sendMessageToPC(player, ColorToken.Green() + "You earned class experience." + ColorToken.End());
            exp = pcClass.getExperience() + exp;

            if(classLevel.getClassLevelID() >= config.getMaxLevel() &&
               exp > classLevels.get(classLevel.getClassLevelID()-1).getExperienceRequired())
            {
                exp = classLevels.get(classLevel.getClassLevelID()-1).getExperienceRequired()-1;
            }

            while(exp > classLevels.get(classLevel.getClassLevelID()-1).getExperienceRequired())
            {
                exp -= classLevels.get(classLevel.getClassLevelID()-1).getExperienceRequired();

                classLevel.setClassLevelID(classLevel.getClassLevelID() + 1);
                NWScript.floatingTextStringOnCreature(NWScript.getName(player, false) + " attains level " + classLevel.getClassLevelID() + "!", player, true);

                NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectVisualEffect(VfxImp.HEALING_G, false), player, 0.0f);
            }

            pcClass.setExperience(exp);
            classRepo.Save(pcClass);
        }
    }

    private static int CalculateExperience(int partyLevel, int enemyLevel)
    {
        int exp = 0;
        int delta = partyLevel - enemyLevel;

        if(delta <= -5) exp = 200;
        else if(delta >= 4) exp = 0;
        else
        {
            switch (delta)
            {
                case -4: exp = 200; break;
                case -3: exp = 170; break;
                case -2: exp = 140; break;
                case -1: exp = 120; break;
                case 0:  exp = 100; break;
                case 1:  exp = 80;  break;
                case 2:  exp = 50;  break;
                case 3:  exp = 30;  break;
            }
        }

        return exp;
    }

    private static void ApplyClassStatChanges(NWObject oPC)
    {
        if(NWScript.getIsDM(oPC) || !NWScript.getIsPC(oPC)) return;
        PlayerRepository playerRepo = new PlayerRepository();
        CharacterClassRepository classRepo = new CharacterClassRepository();
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerEntity pcEntity = playerRepo.getByUUID(pcGO.getUUID());
        PCClassEntity pcClass = classRepo.GetActiveClass(pcGO.getUUID());
        ClassLevelEntity classLevel = pcClass.getClassLevel();

        ClassStatEntity stat = classRepo.GetClassStat(
                pcClass.getCharacterClass().getCharacterClassID(),
                classLevel.getClassLevelID());

        // NWN Stats
        ApplyHitPoints(oPC, stat.getHitPoints());
        NWNX_Funcs.SetAbilityScore(oPC, Ability.CHARISMA, stat.getCharisma());
        NWNX_Funcs.SetAbilityScore(oPC, Ability.CONSTITUTION, stat.getConstitution());
        NWNX_Funcs.SetAbilityScore(oPC, Ability.DEXTERITY, stat.getDexterity());
        NWNX_Funcs.SetAbilityScore(oPC, Ability.INTELLIGENCE, stat.getIntelligence());
        NWNX_Funcs.SetAbilityScore(oPC, Ability.STRENGTH, stat.getStrength());
        NWNX_Funcs.SetAbilityScore(oPC, Ability.WISDOM, stat.getWisdom());

        // Database stats
        pcEntity.setCurrentEssence(stat.getEssence());
        playerRepo.save(pcEntity);

        // Feats
        ApplyFeats(oPC, pcClass.getCharacterClass().getCharacterClassID(), classLevel.getClassLevelID());
    }

    private static void ApplyHitPoints(NWObject oPC, int newHitPoints)
    {
        int maxHitPoints = Constants.MaxNWNLevels * Constants.MaxNWNHPPerLevel;
        if(newHitPoints > maxHitPoints) newHitPoints = maxHitPoints;

        for(int level = 1; level <= Constants.MaxNWNLevels; level++)
        {
            if(level == 1)
                NWNX_Funcs.SetMaxHitPointsByLevel(oPC, level, 1);
            else
                NWNX_Funcs.SetMaxHitPointsByLevel(oPC, level, 0);
        }

        int applyToLevel = 1;
        while(newHitPoints > Constants.MaxNWNHPPerLevel)
        {
            NWNX_Funcs.SetMaxHitPointsByLevel(oPC, applyToLevel, Constants.MaxNWNHPPerLevel);

            applyToLevel++;
            newHitPoints -= Constants.MaxNWNHPPerLevel;
        }
        // Apply leftover HP to next level.
        NWNX_Funcs.SetMaxHitPointsByLevel(oPC, applyToLevel, newHitPoints);

    }

    private static void ApplyFeats(NWObject oPC, int characterClassID, int level)
    {
        if(NWScript.getIsDM(oPC) || !NWScript.getIsPC(oPC) || level <= 0) return;
        CharacterClassRepository repo = new CharacterClassRepository();
        List<ClassFeatLevelEntity> abilities = repo.GetClassFeatLevels(characterClassID, level);

        for(int x = 0; x <= NWNX_Funcs.GetTotalKnownFeats(oPC); x++)
        {
            int featID = NWNX_Funcs.GetKnownFeat(oPC, x);
            NWNX_Funcs.RemoveKnownFeat(oPC, featID);
        }

        ApplyStaticFeats(oPC);
        for(ClassFeatLevelEntity ability : abilities)
        {
            NWNX_Funcs.AddKnownFeat(oPC, ability.getFeatID(), 1);
        }
    }

    private static void ApplyStaticFeats(NWObject oPC)
    {
        NWNX_Funcs.AddKnownFeat(oPC, Feat.ARMOR_PROFICIENCY_LIGHT, 1);
        NWNX_Funcs.AddKnownFeat(oPC, Feat.ARMOR_PROFICIENCY_MEDIUM, 1);
        NWNX_Funcs.AddKnownFeat(oPC, Feat.ARMOR_PROFICIENCY_HEAVY, 1);
        NWNX_Funcs.AddKnownFeat(oPC, Feat.SHIELD_PROFICIENCY, 1);
        NWNX_Funcs.AddKnownFeat(oPC, Feat.WEAPON_PROFICIENCY_EXOTIC, 1);
        NWNX_Funcs.AddKnownFeat(oPC, Feat.WEAPON_PROFICIENCY_MARTIAL, 1);
        NWNX_Funcs.AddKnownFeat(oPC, Feat.WEAPON_PROFICIENCY_SIMPLE, 1);
        NWNX_Funcs.AddKnownFeat(oPC, Feat.SPRING_ATTACK, 1);
    }

    public static String BuildMenuHeader(NWObject oPC)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        CharacterClassRepository repo = new CharacterClassRepository();
        PCClassEntity pcClass = repo.GetActiveClass(pcGO.getUUID());
        CharacterClassEntity charClass = pcClass.getCharacterClass();
        ClassLevelEntity classLevel = pcClass.getClassLevel();

        String header = ColorToken.Green() + "Class Change Terminal" + ColorToken.End() + "\n\n";
        header += "Active Class: " + charClass.getName() + " (" + charClass.getCallsign() + ")\n";
        header += "Level: " + classLevel.getClassLevelID() + "\n";
        header += "EXP: " + MenuHelper.BuildBar(pcClass.getExperience(), classLevel.getExperienceRequired(), 100) + "\n";

        return header;
    }

}
