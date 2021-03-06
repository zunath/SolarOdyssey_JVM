package Event.Module;

import Common.IScriptEventHandler;
import GameSystems.FactionSystem;
import org.nwnx.nwnx2.jvm.NWObject;

public class OnExamine implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        FactionSystem.OnModuleExamine(objSelf);
    }
}
