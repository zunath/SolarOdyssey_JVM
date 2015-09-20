package Item.OmniTool;

import Dialog.DialogManager;
import Common.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("unused")
public class OpenRestMenu implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        DialogManager.startConversation(oPC, oPC, "RestMenu");
    }
}
