package GameSystems;

import Common.Constants;
import Entities.PCMigrationEntity;
import Entities.PCMigrationItemEntity;
import Entities.PCOverflowItemEntity;
import Entities.PlayerEntity;
import GameObject.ItemGO;
import GameObject.PlayerGO;
import Helper.ColorToken;
import Data.Repository.OverflowItemRepository;
import Data.Repository.PCMigrationRepository;
import Data.Repository.PlayerRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.SCORCO;
import org.nwnx.nwnx2.jvm.Scheduler;

import java.util.ArrayList;
import java.util.HashMap;

public class MigrationSystem {

    public static void OnModuleEnter()
    {
        InitializeNewCharacter();
    }

    private static void InitializeNewCharacter()
    {
        NWObject oPC = NWScript.getEnteringObject();
        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        PlayerGO pcGO = new PlayerGO(oPC);
        NWObject databaseItem = pcGO.GetDatabaseItem();

        if(!NWScript.getIsObjectValid(databaseItem))
        {
            pcGO.destroyAllInventoryItems(false);
            pcGO.destroyAllEquippedItems();

            databaseItem = NWScript.createItemOnObject(Constants.PCDatabaseTag, oPC, 1, "");
            PlayerRepository repo = new PlayerRepository();
            PlayerEntity entity = pcGO.createEntity();
            repo.save(entity);

            NWScript.setLocalString(databaseItem, Constants.PCDatabaseTag, entity.getPCID());
        }
    }

    public static void OnAreaEnter(NWObject oPC)
    {
        if(!NWScript.getIsPC(oPC) ||
                NWScript.getIsDM(oPC) ||
                !NWScript.getTag(NWScript.getArea(oPC)).equals("ooc_area")) return;
        PerformMigration(oPC);
    }


    private static void PerformMigration(final NWObject oPC)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerRepository playerRepo = new PlayerRepository();
        PCMigrationRepository migrationRepo = new PCMigrationRepository();
        PlayerEntity entity = playerRepo.getByUUID(pcGO.getUUID());
        int latestMigrationID = migrationRepo.GetLatestMigrationID();

        // This piece of code migrates characters from MZS3 to the new version's database structure.
        if(entity == null &&
                (!NWScript.getLocalString(pcGO.GetDatabaseItem(), Constants.PCIDNumberVariable).equals("") ||
                        NWScript.getLocalInt(pcGO.GetDatabaseItem(), Constants.PCIDNumberVariable) > 0))
        {
            entity = pcGO.createEntity();
            entity.setVersionNumber(0);
            playerRepo.save(entity);
        }

        for(int version = entity.getVersionNumber() + 1; version <= latestMigrationID; version++)
        {
            HashMap<String, PCMigrationItemEntity> itemMap = new HashMap<>();
            ArrayList<Integer> stripItemList = new ArrayList<>();
            PCMigrationEntity migration = migrationRepo.GetByMigrationID(version);

            for(PCMigrationItemEntity item : migration.getPcMigrationItems())
            {
                if(!item.getCurrentResref().equals(""))
                {
                    itemMap.put(item.getCurrentResref(), item);
                }
                else if(item.getBaseItemTypeID() > -1 && item.isStripItemProperties())
                {
                    stripItemList.add(item.getBaseItemTypeID());
                }
            }

            for(int slot = 0; slot < Constants.NumberOfInventorySlots; slot++)
            {
                NWObject item = NWScript.getItemInSlot(slot, oPC);
                ProcessItem(oPC, item, itemMap, stripItemList);
            }

            for(NWObject item : NWScript.getItemsInInventory(oPC))
            {
                ProcessItem(oPC, item, itemMap, stripItemList);
            }

            RunCustomMigrationProcess(oPC, version);
            entity = playerRepo.getByUUID(pcGO.getUUID());
            entity.setVersionNumber(version);
            playerRepo.save(entity);
            NWScript.setLocalInt(oPC, "MIGRATION_SYSTEM_LOGGED_IN_ONCE", 1);

            OverflowItemRepository overflowRepo = new OverflowItemRepository();
            long overflowCount = overflowRepo.GetPlayerOverflowItemCount(pcGO.getUUID());

            final String message = ColorToken.Green() + "Your character has been updated!" +
                    (overflowCount > 0 ? " Items which could not be created have been placed into overflow inventory. You can access this from the rest menu." : "") +
                    ColorToken.End();
            Scheduler.delay(oPC, 8000, new Runnable() {
                @Override
                public void run() {
                    NWScript.floatingTextStringOnCreature(message, oPC, false);
                }
            });
        }
    }

    private static void ProcessItem(NWObject oPC, NWObject item, HashMap<String, PCMigrationItemEntity> itemMap, ArrayList<Integer> stripItemList)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        ItemGO itemGO = new ItemGO(item);
        String resref = NWScript.getResRef(item);
        int quantity = NWScript.getItemStackSize(item);
        int baseItemTypeID = NWScript.getBaseItemType(item);
        PCMigrationItemEntity migrationItem = itemMap.get(resref);
        OverflowItemRepository repo = new OverflowItemRepository();

        if(itemMap.containsKey(resref))
        {
            NWScript.destroyObject(item, 0.0f);
            if(!migrationItem.getNewResref().equals(""))
            {
                NWObject newItem = NWScript.createItemOnObject(migrationItem.getNewResref(), oPC, quantity, "");
                if(NWScript.getItemPossessor(newItem).equals(NWObject.INVALID))
                {
                    PCOverflowItemEntity overflow = new PCOverflowItemEntity();
                    overflow.setItemResref(NWScript.getResRef(newItem));
                    overflow.setItemTag(NWScript.getTag(newItem));
                    overflow.setItemName(NWScript.getName(newItem, false));
                    overflow.setItemObject(SCORCO.saveObject(newItem));
                    overflow.setPlayerID(pcGO.getUUID());
                    repo.Save(overflow);

                    NWScript.destroyObject(newItem, 0.0f);
                }
            }
        }
        else if(stripItemList.contains(baseItemTypeID))
        {
            itemGO.StripAllItemProperties();
        }
    }

    private static void RunCustomMigrationProcess(NWObject oPC, int versionNumber)
    {
        switch(versionNumber)
        {
            case 1:
            {
                break;
            }
        }
    }


}
