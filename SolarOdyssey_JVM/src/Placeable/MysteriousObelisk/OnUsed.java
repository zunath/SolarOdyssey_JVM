package Placeable.MysteriousObelisk;

import Common.IScriptEventHandler;
import GameSystems.DeathSystem;
import org.nwnx.nwnx2.jvm.NWObject;

public class OnUsed implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        DeathSystem.BindStone_OnUse();
    }
}
