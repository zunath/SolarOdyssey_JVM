package Event.Module;

import Common.IScriptEventHandler;
import GameSystems.AbilitySystem;
import org.nwnx.nwnx2.jvm.NWObject;

public class OnUseFeat implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        AbilitySystem.OnFeatUsed(oPC);
    }
}
