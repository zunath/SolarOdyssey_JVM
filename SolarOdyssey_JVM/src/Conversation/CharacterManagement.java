package Conversation;

import Conversation.ViewModels.CharacterManagementViewModel;
import Dialog.*;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class CharacterManagement extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog SetUp(NWObject oPC) {

        PlayerDialog dialog = new PlayerDialog();
        DialogPage characterManagementPage = new DialogPage(
                "Character Management & Information Page",
                "Manage CD Keys",
                "Back"
        );

        dialog.addPage("MainPage", characterManagementPage);

        return dialog;
    }

    @Override
    public void Initialize()
    {
        SetDialogCustomData(new CharacterManagementViewModel());
    }

    @Override
    public void DoAction(NWObject oPC, String pageName, int responseID) {
        switch (pageName)
        {
            case "MainPage":
            {
                switch (responseID)
                {
                    case 1: // Manage CD Keys
                        SwitchConversation("ManageCDKeys");
                        break;
                    case 2: // Back
                        SwitchConversation("RestMenu");
                        break;
                }
                break;
            }
        }
    }

    @Override
    public void EndDialog() {

    }
}
