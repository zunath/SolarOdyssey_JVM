package Event.Module;

import Common.IScriptEventHandler;
import GameSystems.DeathSystem;
import org.nwnx.nwnx2.jvm.NWObject;

public class OnPlayerDeath implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        DeathSystem.Module_OnDeath();
    }
}
