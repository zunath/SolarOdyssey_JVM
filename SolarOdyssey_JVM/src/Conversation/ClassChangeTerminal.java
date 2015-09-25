package Conversation;

import Data.Repository.CharacterClassRepository;
import Dialog.*;
import Entities.CharacterClassEntity;
import GameSystems.ClassSystem;
import org.nwnx.nwnx2.jvm.NWObject;

import java.util.List;

public class ClassChangeTerminal extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog SetUp(NWObject oPC) {
        PlayerDialog dialog = new PlayerDialog();
        DialogPage page = new DialogPage(
                "<SET LATER>"
        );
        dialog.addPage("MainPage", page);

        return dialog;
    }

    @Override
    public void Initialize() {
        DialogPage page = GetPageByName("MainPage");
        CharacterClassRepository repo = new CharacterClassRepository();
        SetPageHeader("MainPage", ClassSystem.BuildMenuHeader(GetPC()));
        List<CharacterClassEntity> classes = repo.GetAllCharacterClasses();

        for(CharacterClassEntity entity : classes)
        {
            page.addResponse(entity.getName(), true, entity.getCharacterClassID());
        }
    }

    @Override
    public void DoAction(NWObject oPC, String pageName, int responseID) {
        DialogResponse response = GetResponseByID(pageName, responseID);
        ClassSystem.ChangeClass(oPC, (Integer)response.getCustomData());
        SetPageHeader("MainPage", ClassSystem.BuildMenuHeader(GetPC()));
    }

    @Override
    public void EndDialog() {

    }
}
