package Event.Module;

import Common.IScriptEventHandler;
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
        NWScript.executeScript("x3_mod_def_enter", objSelf); // Bioware Default
        NWScript.executeScript("fky_chat_clenter", objSelf); // SIMTools
        NWScript.executeScript("dm_authorization", objSelf); // DM Validation
        NWScript.executeScript("auth_mod_enter", objSelf);   // PC Validation

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
        final NWObject oPC = NWScript.getEnteringObject();
        final String sMOTD = NWScript.getLocalString(NWObject.MODULE, "MOTD");
        final String message = ColorToken.Green() + "Welcome to Outbreak: After Life!\n\nMOTD:" + ColorToken.White() +  sMOTD + ColorToken.End();

        Scheduler.delay(oPC, 6500, new Runnable() {
            @Override
            public void run() {
                NWScript.sendMessageToPC(oPC, message);
            }
        });
    }
}
