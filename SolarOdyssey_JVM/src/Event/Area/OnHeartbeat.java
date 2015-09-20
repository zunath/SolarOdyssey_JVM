package Event.Area;

import Common.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class OnHeartbeat implements IScriptEventHandler {

    @Override
    public void runScript(NWObject objSelf) {
        // NESS
        NWScript.executeScript("spawn_sample_hb", objSelf);
    }
}
