package Event.DM;

import Common.IScriptEventHandler;
import NWNX.NWNX_DMActions;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class GiveXP implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oTarget = NWNX_DMActions.oGetDMAction_Target(false);
        int experience = NWNX_DMActions.nGetDMAction_Param(false);
        NWNX_DMActions.PreventDMAction();

        if(experience <= 0)
        {
            NWScript.sendMessageToPC(objSelf, "You can't take experience.");
            return;
        }

        if(!NWScript.getIsPC(oTarget) || NWScript.getIsDM(oTarget)) return;

        //ProgressionSystem.GiveExperienceToPC(oTarget, experience);
        NWScript.sendMessageToPC(objSelf, "You gave " + experience + " XP to " + NWScript.getName(oTarget, false) + "!");
    }
}
