package TestBed;

import Common.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.ObjectType;

public class Bread implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {

        NWObject pc = NWScript.getLastUsedBy();
        NWLocation location = NWScript.getLocation(NWScript.getWaypointByTag("TEST_WP"));

        NWScript.createObject(ObjectType.CREATURE, "testzombie", location, false, "");

    }

}
