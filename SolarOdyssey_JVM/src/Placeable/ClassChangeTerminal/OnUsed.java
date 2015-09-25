package Placeable.ClassChangeTerminal;

import Common.IScriptEventHandler;
import Dialog.DialogManager;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class OnUsed implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = NWScript.getLastUsedBy();
        DialogManager.startConversation(oPC, objSelf, "ClassChangeTerminal");
    }
}
