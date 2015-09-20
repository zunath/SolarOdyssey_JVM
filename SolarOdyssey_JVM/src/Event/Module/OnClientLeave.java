package Event.Module;

import Common.IScriptEventHandler;
import Data.Repository.PlayerRepository;
import Entities.PlayerEntity;
import GameObject.PlayerGO;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class OnClientLeave implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject pc = NWScript.getExitingObject();
        NWScript.exportSingleCharacter(pc);
        SaveCharacter(pc);

        // SimTools
        NWScript.executeScript("fky_chat_clexit", objSelf);
    }

    private void SaveCharacter(NWObject pc) {

        if(NWScript.getIsDM(pc)) return;

        PlayerGO gameObject = new PlayerGO(pc);
        PlayerRepository repo = new PlayerRepository();
        String uuid = gameObject.getUUID();

        PlayerEntity entity = repo.getByUUID(uuid);
        entity.setCharacterName(NWScript.getName(pc, false));
        entity.setHitPoints(NWScript.getCurrentHitPoints(pc));

        repo.save(entity);
    }
}
