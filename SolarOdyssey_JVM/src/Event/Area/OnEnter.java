package Event.Area;

import Entities.PlayerEntity;
import GameObject.PlayerGO;
import Common.IScriptEventHandler;
import Data.Repository.PlayerRepository;
import GameSystems.KeyItemSystem;
import GameSystems.MigrationSystem;
import GameSystems.PersistentLocationSystem;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("UnusedDeclaration")
public class OnEnter implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oArea) {
        NWObject oPC = NWScript.getEnteringObject();
        MigrationSystem.OnAreaEnter(oPC);

        // Temporary Sanctuary Effects
        NWScript.executeScript("sanctuary", oArea);

        PersistentLocationSystem.OnAreaEnter(oPC, oArea);
        // Initialize camera in designated areas.
        NWScript.executeScript("initialize_camer", oArea);

        // Save characters
        if(NWScript.getIsObjectValid(oPC) && NWScript.getIsPC(oPC) && !NWScript.getIsDM(oPC)) NWScript.exportSingleCharacter(oPC);
    }


    private void ShowMap(NWObject oPC, NWObject oArea)
    {
        int keyItemID = NWScript.getLocalInt(oArea, "MAP_ID");
        boolean areaShowsMap = NWScript.getLocalInt(oArea, "SHOW_MAP") == 1;
        boolean hasKeyItem = KeyItemSystem.PlayerHasKeyItem(oPC, keyItemID);

        if(areaShowsMap || hasKeyItem)
        {
            NWScript.exploreAreaForPlayer(oArea, oPC, true);
        }
    }

}
