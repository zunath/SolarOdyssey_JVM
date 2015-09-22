package TestBed;

import Common.IScriptEventHandler;
import NWNX.NWNX_Visibility;
import NWNX.VisibilityType;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class Visibility implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject obj1 = NWScript.getObjectByTag("TESTOBJ1", 0);
        NWObject obj2 = NWScript.getObjectByTag("TESTOBJ2", 0);
        NWObject oPC = NWScript.getLastUsedBy();

        if(NWScript.getIsObjectValid(obj1))
        {
            NWScript.sendMessageToPC(oPC, "valid1");
        }
        if(NWScript.getIsObjectValid(obj2))
        {
            NWScript.sendMessageToPC(oPC, "valid2");
        }


        NWNX_Visibility.SetVisibility(obj1, oPC, VisibilityType.Invisible);

    }
}
