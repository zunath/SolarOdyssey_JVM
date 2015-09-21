package Event.Module;

import Common.IScriptEventHandler;
import GameSystems.PersistentHPSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class OnClientLeave implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject pc = NWScript.getExitingObject();
        NWScript.exportSingleCharacter(pc);
        NWScript.executeScript("fky_chat_clexit", objSelf); // SIMTools
        PersistentHPSystem.OnModuleLeave();
    }

}
