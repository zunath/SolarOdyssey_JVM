package TestBed;

import Common.IScriptEventHandler;
import NWNX.NWNX_Funcs;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class Bread implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = NWScript.getLastUsedBy();

        NWNX_Funcs.AddKnownFeat(oPC, 1134, 1);
    }
}
