package Event.Module;

import Common.IScriptEventHandler;
import Data.Repository.ServerConfigurationRepository;
import Entities.ServerConfigurationEntity;
import GameSystems.ClassSystem;
import GameSystems.MigrationSystem;
import GameSystems.PersistentHPSystem;
import Helper.ColorToken;
import org.nwnx.nwnx2.jvm.NWEffect;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.Duration;
import GameSystems.PlayerAuthorizationSystem;

public class OnClientEnter implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        MigrationSystem.OnModuleEnter();

        NWScript.executeScript("x3_mod_def_enter", objSelf); // Bioware Default
        NWScript.executeScript("fky_chat_clenter", objSelf); // SIMTools
        NWScript.executeScript("dm_authorization", objSelf); // DM Validation
        NWScript.executeScript("auth_mod_enter", objSelf);   // PC Validation

        ClassSystem.OnModuleEnter();
        ShowMOTD();
        ApplyGhostwalk();
        PlayerAuthorizationSystem.OnModuleEnter();
        PersistentHPSystem.OnModuleEnter();
    }


    private void ApplyGhostwalk()
    {
        NWObject oPC = NWScript.getEnteringObject();

        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        NWEffect eGhostWalk = NWScript.effectCutsceneGhost();
        NWScript.applyEffectToObject(Duration.TYPE_PERMANENT, eGhostWalk, oPC, 0.0f);

    }


    private void ShowMOTD()
    {
        ServerConfigurationEntity config = ServerConfigurationRepository.GetServerConfiguration();

        final NWObject oPC = NWScript.getEnteringObject();
        final String message = ColorToken.Green() + "Welcome to " + config.getServerName() + "!\n\nMOTD:" + ColorToken.White() +  config.getMessageOfTheDay() + ColorToken.End();

        Scheduler.delay(oPC, 6500, new Runnable() {
            @Override
            public void run() {
                NWScript.sendMessageToPC(oPC, message);
            }
        });
    }
}
