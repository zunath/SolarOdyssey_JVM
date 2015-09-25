package Data;
import Entities.*;
import Helper.ErrorHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.ini4j.Ini;

import java.io.File;

public class DataAccess {
    private static String _host;
    private static String _username;
    private static String _password;
    private static String _schema;

    private static SessionFactory _sessionFactory;

    public static void Initialize()
    {
        try
        {
            Ini ini = new Ini(new File("nwnx2.ini"));
            _host = ini.get("ODBC2", "server");
            _username = ini.get("ODBC2", "user");
            _password = ini.get("ODBC2", "pass");
            _schema = ini.get("ODBC2", "db");
        }
        catch (Exception ex)
        {
            ErrorHelper.HandleException(ex, "DataAccess Initialize()");
        }

        CreateSessionFactory();
    }


    private static void CreateSessionFactory()
    {
        Configuration _configuration = new Configuration();

        _configuration.setProperty("hibernate.connection.driver_class", "net.sourceforge.jtds.jdbc.Driver");
        _configuration.setProperty("hibernate.connection.url", "jdbc:jtds:sqlserver://" + _host + "/" + _schema);
        _configuration.setProperty("hibernate.connection.username", _username);
        _configuration.setProperty("hibernate.connection.password", _password);
        _configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        _configuration.setProperty("hibernate.cache.use_second_level_cache", "false");
        _configuration.setProperty("hibernate.cache.use_query_cache", "false");
        _configuration.setProperty("hibernate.current_session_context_class", "thread");

        // Link all DB entities to the configuration here.
        _configuration.addAnnotatedClass(AuthorizedDMEntity.class);
        _configuration.addAnnotatedClass(BaseItemTypeEntity.class);
        _configuration.addAnnotatedClass(BuildPrivacyEntity.class);
        _configuration.addAnnotatedClass(CharacterClassEntity.class);
        _configuration.addAnnotatedClass(ClassAbilityEntity.class);
        _configuration.addAnnotatedClass(ClassLevelEntity.class);
        _configuration.addAnnotatedClass(ClassStatEntity.class);
        _configuration.addAnnotatedClass(ConstructionSiteEntity.class);
        _configuration.addAnnotatedClass(CraftBlueprintCategoryEntity.class);
        _configuration.addAnnotatedClass(CraftBlueprintEntity.class);
        _configuration.addAnnotatedClass(CraftComponentEntity.class);
        _configuration.addAnnotatedClass(CraftEntity.class);
        _configuration.addAnnotatedClass(CraftLevelEntity.class);
        _configuration.addAnnotatedClass(DMRoleEntity.class);
        _configuration.addAnnotatedClass(FactionEntity.class);
        _configuration.addAnnotatedClass(KeyItemCategoryEntity.class);
        _configuration.addAnnotatedClass(KeyItemEntity.class);
        _configuration.addAnnotatedClass(NPCEntity.class);
        _configuration.addAnnotatedClass(PCAuthorizedCDKeyEntity.class);
        _configuration.addAnnotatedClass(PCBlueprintEntity.class);
        _configuration.addAnnotatedClass(PCClassEntity.class);
        _configuration.addAnnotatedClass(PCCraftEntity.class);
        _configuration.addAnnotatedClass(PCKeyItemEntity.class);
        _configuration.addAnnotatedClass(PCMigrationEntity.class);
        _configuration.addAnnotatedClass(PCMigrationItemEntity.class);
        _configuration.addAnnotatedClass(PCOutfitEntity.class);
        _configuration.addAnnotatedClass(PCOverflowItemEntity.class);
        _configuration.addAnnotatedClass(PCSystemVersionEntity.class);
        _configuration.addAnnotatedClass(PCTerritoryFlagEntity.class);
        _configuration.addAnnotatedClass(PCTerritoryFlagPermissionEntity.class);
        _configuration.addAnnotatedClass(PCTerritoryFlagStructureEntity.class);
        _configuration.addAnnotatedClass(PCTerritoryFlagStructureItemEntity.class);
        _configuration.addAnnotatedClass(PlayerEntity.class);
        _configuration.addAnnotatedClass(PortraitEntity.class);
        _configuration.addAnnotatedClass(ServerConfigurationEntity.class);
        _configuration.addAnnotatedClass(StorageContainerEntity.class);
        _configuration.addAnnotatedClass(StorageItemEntity.class);
        _configuration.addAnnotatedClass(StructureCategoryEntity.class);
        _configuration.addAnnotatedClass(StructureBlueprintEntity.class);
        _configuration.addAnnotatedClass(TerritoryFlagPermissionEntity.class);


        ServiceRegistry _serviceRegistry = new ServiceRegistryBuilder().applySettings(
                _configuration.getProperties()).buildServiceRegistry();

        _sessionFactory = _configuration.buildSessionFactory(_serviceRegistry);

    }

    public static Session getSession()
    {
        return _sessionFactory.getCurrentSession();
    }
}
