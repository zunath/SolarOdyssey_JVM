package Conversation;

import Dialog.DialogBase;
import Dialog.DialogPage;
import Dialog.IDialogHandler;
import Dialog.PlayerDialog;
import Entities.PlayerEntity;
import GameObject.PlayerGO;
import Helper.ColorToken;
import Data.Repository.OverflowItemRepository;
import Data.Repository.PlayerRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.ObjectType;

@SuppressWarnings("UnusedDeclaration")
public class RestMenu extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog SetUp(NWObject oPC) {

        PlayerDialog dialog = new PlayerDialog();
        DialogPage mainPage = new DialogPage(
                BuildMainPageHeader(oPC),
                ColorToken.Green() + "Open Overflow Inventory" + ColorToken.End(),
                "Dice Bag",
                "Emote Menu",
                "View Crafts",
                "View Key Items",
                "Modify Clothes",
                "Dye Clothes",
                "Character Management");

        dialog.addPage("MainPage", mainPage);

        return dialog;
    }

    @Override
    public void Initialize()
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        OverflowItemRepository repo = new OverflowItemRepository();
        long overflowCount = repo.GetPlayerOverflowItemCount(pcGO.getUUID());

        if(overflowCount <= 0)
        {
            SetResponseVisible("MainPage", 1, false);
        }
    }

    @Override
    public void DoAction(final NWObject oPC, String pageName, int responseID) {
        switch (pageName) {
            case "MainPage":
                switch (responseID) {
                    // Open Overflow Inventory
                    case 1:
                        final NWObject storage = NWScript.createObject(ObjectType.PLACEABLE, "overflow_storage", NWScript.getLocation(oPC), false, "");
                        Scheduler.assign(oPC, new Runnable() {
                            @Override
                            public void run() {
                                NWScript.actionInteractObject(storage);
                            }
                        });
                        break;
                    // Dice Bag
                    case 2:
                        NWScript.setLocalObject(oPC, "dmfi_univ_target", oPC);
                        NWScript.setLocalLocation(oPC, "dmfi_univ_location", NWScript.getLocation(oPC));
                        NWScript.setLocalString(oPC, "dmfi_univ_conv", "pc_dicebag");
                        Scheduler.assign(oPC, new Runnable() {
                            @Override
                            public void run() {
                                NWScript.clearAllActions(false);
                                NWScript.actionStartConversation(oPC, "dmfi_universal", true, false);
                            }
                        });
                        break;
                    // Emote Menu
                    case 3:
                        NWScript.setLocalObject(oPC, "dmfi_univ_target", oPC);
                        NWScript.setLocalLocation(oPC, "dmfi_univ_location", NWScript.getLocation(oPC));
                        NWScript.setLocalString(oPC, "dmfi_univ_conv", "pc_emote");
                        Scheduler.assign(oPC, new Runnable() {
                            @Override
                            public void run() {
                                NWScript.clearAllActions(false);
                                NWScript.actionStartConversation(oPC, "dmfi_universal", true, false);
                            }
                        });
                        break;
                    // View Crafts
                    case 4:
                        SwitchConversation("ViewCrafts");
                        break;
                    // Key Item Categories Page
                    case 5:
                        SwitchConversation("KeyItems");
                        break;
                    // Modify Clothes
                    case 6:
                        Scheduler.assign(oPC, new Runnable() {
                            @Override
                            public void run() {
                                NWScript.actionStartConversation(oPC, "x0_skill_ctrap", true, false);
                            }
                        });
                        break;
                    // Dye Clothes
                    case 7:
                        Scheduler.assign(oPC, new Runnable() {
                            @Override
                            public void run() {
                                NWScript.actionStartConversation(oPC, "dye_dyekit", true, false);
                            }
                        });

                        break;
                    // Character Management
                    case 8:
                        SwitchConversation("CharacterManagement");
                        break;
                }
                break;

        }
    }

    @Override
    public void EndDialog()
    {
    }

    private String BuildMainPageHeader(NWObject oPC)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());
        String header = ColorToken.Green() + "Name: " + ColorToken.End() + NWScript.getName(oPC, false) + "\n\n";
        //header += ColorToken.Green() + "Level: " + ColorToken.End() + entity.getLevel() + "\n";

        return header;
    }


}
