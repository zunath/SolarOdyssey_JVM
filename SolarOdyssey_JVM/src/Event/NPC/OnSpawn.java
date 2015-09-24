package Event.NPC;

import Common.IScriptEventHandler;
import GameObject.CreatureGO;
import org.nwnx.nwnx2.jvm.NWObject;

public class OnSpawn implements IScriptEventHandler {
    @Override
    public void runScript(NWObject creature) {
        CreatureGO creatureGO = new CreatureGO(creature);
        creatureGO.LoadNPCStats();
    }
}
