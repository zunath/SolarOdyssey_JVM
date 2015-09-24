package GameSystems;

import Data.Repository.CharacterClassRepository;
import Data.Repository.ServerConfigurationRepository;
import Entities.ClassLevelEntity;
import Entities.PCClassEntity;
import Entities.ServerConfigurationEntity;
import GameObject.PlayerGO;
import Helper.ColorToken;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

import java.util.List;

public class ClassSystem
{
    public static void OnModuleEnter()
    {
        HandleCharacterInitialization();
    }

    private static void HandleCharacterInitialization()
    {
        // TODO: Initialize character
    }

    public static void OnEnemyDeath(NWObject npc)
    {
        AwardPartyExperience(npc);
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

        int membersInArea = 0;
        int npcLevel = GetNPCLevel(oNPC);
        int partyLevel = pcClass.getLevelID();

        for(NWObject member : party)
        {
            if(NWScript.getArea(member).equals(area))
            {
                PlayerGO memberGO = new PlayerGO(member);
                PCClassEntity memberClass = repo.GetActiveClass(memberGO.getUUID());

                if(memberClass.getLevelID() > partyLevel)
                {
                    partyLevel = memberClass.getLevelID();
                }

                membersInArea++;
            }
        }

        int exp = CalculateExperience(partyLevel, npcLevel) / membersInArea;
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
        List<ClassLevelEntity> classLevels = classRepo.GetClassLevels();

        if(exp > 0)
        {
            NWScript.sendMessageToPC(player, ColorToken.Green() + "You earned class experience." + ColorToken.End());
            exp = pcClass.getExperience() + exp;

            if(pcClass.getLevelID() >= config.getMaxLevel() &&
               exp > classLevels.get(pcClass.getLevelID()-1).getExperienceRequired())
            {
                pcClass.setLevelID(pcClass.getLevelID()+1);
                NWScript.floatingTextStringOnCreature(NWScript.getName(player, false) + " attains level " + pcClass.getLevelID() + "!", player, true);
            }
            classRepo.Save(pcClass);
        }
    }

    public static int GetNPCLevel(NWObject npc)
    {
        return 0; // TODO: Implement way to pull NPC level
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
                case -4:
                    exp = 200;
                    break;
                case -3:
                    exp = 170;
                    break;
                case -2:
                    exp = 140;
                    break;
                case -1:
                    exp = 120;
                    break;
                case 0:
                    exp = 100;
                    break;
                case 1:
                    exp = 80;
                    break;
                case 2:
                    exp = 50;
                    break;
                case 3:
                    exp = 30;
                    break;
            }
        }

        return exp;
    }
}
