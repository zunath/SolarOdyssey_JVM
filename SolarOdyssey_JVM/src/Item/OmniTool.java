package Item;

import Common.IScriptEventHandler;
import Dialog.DialogManager;
import GameSystems.StructureSystem;
import NWNX.NWNX_Events;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;

public class OmniTool implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        int castSpellType = NWNX_Events.GetEventSubType();

        if(castSpellType == 0)
        {
            HandleAutoFollow(oPC);
        }
        else if(castSpellType == 1)
        {
            HandleOpenRestMenu(oPC);
        }
        else if(castSpellType == 2)
        {
            HandleUseStructureTool(oPC);
        }

    }

    private void HandleAutoFollow(NWObject oPC)
    {

        final NWObject oTarget = NWNX_Events.GetEventTarget();

        if(!NWScript.getIsPC(oTarget) || NWScript.getIsDM(oTarget))
        {
            NWScript.sendMessageToPC(oPC, "You can only follow other players.");
            return;
        }

        NWScript.floatingTextStringOnCreature("Now following " + NWScript.getName(oTarget, false) + ".", oPC, false);
        Scheduler.delay(oPC, 2000, new Runnable() {
            @Override
            public void run() {
                NWScript.actionForceFollowObject(oTarget, 2.0f);
            }
        });
    }

    private void HandleOpenRestMenu(NWObject oPC)
    {
        DialogManager.startConversation(oPC, oPC, "RestMenu");
    }

    private void HandleUseStructureTool(NWObject oPC)
    {
        NWObject oTarget = NWNX_Events.GetEventTarget();
        NWLocation lTargetLocation = NWScript.location(NWScript.getArea(oPC), NWNX_Events.GetEventPosition(), 0.0f);
        boolean isMovingStructure = StructureSystem.IsPCMovingStructure(oPC);

        if(!oTarget.equals(NWObject.INVALID))
        {
            lTargetLocation = NWScript.getLocation(oTarget);
        }

        if(isMovingStructure)
        {
            StructureSystem.MoveStructure(oPC, lTargetLocation);
        }
        else
        {
            NWScript.setLocalLocation(oPC, "BUILD_TOOL_LOCATION_TARGET", lTargetLocation);
            DialogManager.startConversation(oPC, oPC, "BuildToolMenu");
        }
    }

}
