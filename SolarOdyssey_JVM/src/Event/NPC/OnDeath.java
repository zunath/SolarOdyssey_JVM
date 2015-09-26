package Event.NPC;

import Common.IScriptEventHandler;
import GameSystems.ClassSystem;
import GameSystems.FactionSystem;
import org.nwnx.nwnx2.jvm.NWObject;

public class OnDeath implements IScriptEventHandler {
    @Override
    public void runScript(NWObject npc) {
        ClassSystem.OnCreatureDeath(npc);
        FactionSystem.OnCreatureDeath(npc);
    }
}
