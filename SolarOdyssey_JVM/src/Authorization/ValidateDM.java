package Authorization;

import Entities.AuthorizedDMEntity;
import Common.IScriptEventHandler;
import Data.Repository.AuthorizedDMRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class ValidateDM implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {

        String cdKey = NWScript.getPCPublicCDKey(objSelf, false);
        AuthorizedDMRepository repo = new AuthorizedDMRepository();
        AuthorizedDMEntity entity = repo.getByCDKey(cdKey);
        int isDM = 0;

        if(entity != null && entity.getDMRole() == 1) // 1 = DM
        {
            isDM = 1;
        }

        NWScript.setLocalInt(objSelf, "AUTH_IS_DM", isDM);
    }
}
