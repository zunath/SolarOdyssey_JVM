package Event.Module;

import Common.IScriptEventHandler;
import NWNX.*;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class OnLoad implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {

        AddModuleEventHandlers();
        AddGlobalEventHandlers();
        AddAreaEventHandlers();

        // NWNX Setup
        NWScript.setLocalString(objSelf, "NWNX!INIT", "1");
        NWScript.getLocalObject(objSelf, "NWNX!INIT");
        NWScript.deleteLocalString(objSelf, "NWNX!INIT");

        // Bioware default
        NWScript.executeScript("x2_mod_def_load", objSelf);
        // SimTools and NWNX
        NWScript.executeScript("fky_chat_modload", objSelf);
    }


    private void AddAreaEventHandlers()
    {
        NWObject area = NWNX_Funcs.GetFirstArea();
        while(NWScript.getIsObjectValid(area))
        {
            String result = NWNX_Funcs.SetEventHandler(area, AreaScript.OnEnter, "soo_area_enter");
            NWNX_Funcs.SetEventHandler(area, AreaScript.OnExit, "soo_area_exit");
            NWNX_Funcs.SetEventHandler(area, AreaScript.OnHeartbeat, "soo_area_hb");
            NWNX_Funcs.SetEventHandler(area, AreaScript.OnUserDefinedEvent, "soo_area_user");

            area = NWNX_Funcs.GetNextArea();
        }
    }

    private void AddModuleEventHandlers()
    {
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnAcquireItem, "soo_mod_acquire");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnActivateItem, "soo_mod_activate");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnClientEnter, "soo_mod_enter");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnClientExit, "soo_mod_leave");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnPlayerCancelCutscene, "soo_mod_cutscene");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnHeartbeat, "soo_mod_hb");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnModuleLoad, "soo_mod_load");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnPlayerDeath, "soo_mod_death");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnPlayerDying, "soo_mod_dying");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnEquipItem, "soo_mod_equip");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnPlayerLevelUp, "soo_mod_level");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnRespawnButtonPressed, "soo_mod_respawn");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnPlayerRest, "soo_mod_rest");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnUnequipItem, "soo_mod_unequip");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnLoseItem, "soo_mod_unacquir");
        NWNX_Funcs.SetEventHandler(NWObject.MODULE, ModuleScripts.OnUserDefinedEvent, "soo_mod_userdef");
    }

    private void AddGlobalEventHandlers()
    {
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_ATTACK, "soo_mod_attack");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_CAST_SPELL, "soo_mod_castspel");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_EXAMINE, "soo_mod_examine");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_PICKPOCKET, "soo_mod_pickpock");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_SAVE_CHAR, "soo_mod_save");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_TOGGLE_MODE, "soo_mod_toggmode");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_TOGGLE_PAUSE, "soo_mod_toggpaus");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_USE_FEAT, "soo_mod_usefeat");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_USE_ITEM, "soo_mod_useitem");

        NWNX_DMActions.SetDMActionScript(DMActionType.CREATE_ITEM_ON_AREA, "soo_dm_areaitem");
        NWNX_DMActions.SetDMActionScript(DMActionType.CREATE_ITEM_ON_OBJECT, "soo_dm_objitem");
        NWNX_DMActions.SetDMActionScript(DMActionType.CREATE_PLACEABLE, "soo_dm_placeable");
        NWNX_DMActions.SetDMActionScript(DMActionType.GIVE_GOLD, "soo_dm_gold");
        NWNX_DMActions.SetDMActionScript(DMActionType.GIVE_LEVEL, "soo_dm_level");
        NWNX_DMActions.SetDMActionScript(DMActionType.GIVE_XP, "soo_dm_xp");
        NWNX_DMActions.SetDMActionScript(DMActionType.HEAL_CREATURE, "soo_dm_heal");
        NWNX_DMActions.SetDMActionScript(DMActionType.REST_CREATURE, "soo_dm_rest");
        NWNX_DMActions.SetDMActionScript(DMActionType.RUNSCRIPT, "soo_dm_runscript");
        NWNX_DMActions.SetDMActionScript(DMActionType.SPAWN_CREATURE, "soo_dm_spawn");
        NWNX_DMActions.SetDMActionScript(DMActionType.TOGGLE_IMMORTALITY, "soo_dm_immortal");
        NWNX_DMActions.SetDMActionScript(DMActionType.TOGGLE_INVULNERABILITY, "soo_dm_invuln");
    }
}