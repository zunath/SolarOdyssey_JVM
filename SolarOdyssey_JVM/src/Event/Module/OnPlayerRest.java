package Event.Module;

import Common.IScriptEventHandler;
import Dialog.DialogManager;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.RestEventtype;

public class OnPlayerRest implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        final NWObject oPC = NWScript.getLastPCRested();
        int restType = NWScript.getLastRestEventType();

        if(restType != RestEventtype.REST_STARTED ||
                oPC.equals(NWObject.INVALID) ||
                NWScript.getIsDM(oPC)) return;

        Scheduler.assignNow(oPC, new Runnable() {
            @Override
            public void run() {
                NWScript.clearAllActions(false);
            }
        });

        DialogManager.startConversation(oPC, oPC, "RestMenu");

    }
}
