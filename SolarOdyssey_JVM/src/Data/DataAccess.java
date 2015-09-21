package Data;
import Entities.*;
import Helper.ErrorHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataAccess {
    private static String _host;
    private static String _username;
    private static String _password;
    private static String _schema;

    private static SessionFactory _sessionFactory;

    public static void Initialize()
    {
        Path path = Paths.get("./jvm/db-settings.txt");

        try
        {
            List<String> rows = Files.readAllLines(path, Charset.defaultCharset());


            for(String row : rows)
            {
                String[] parts = row.split("=");

                switch (parts[0]) {
                    case "host":
                        _host = parts[1];
                        break;
                    case "username":
                        _username = parts[1];
                        break;
                    case "password":
                        _password = parts[1];
                        break;
                    case "schema":
                        _schema = parts[1];
                        break;
                }

            }
        }
        catch (IOException ex) {
            ErrorHelper.HandleException(ex, "DataAccess.java");
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
        _configuration.addAnnotatedClass(ConstructionSiteEntity.class);
        _configuration.addAnnotatedClass(CraftBlueprintCategoryEntity.class);
        _configuration.addAnnotatedClass(CraftBlueprintEntity.class);
        _configuration.addAnnotatedClass(CraftComponentEntity.class);
        _configuration.addAnnotatedClass(CraftEntity.class);
        _configuration.addAnnotatedClass(CraftLevelEntity.class);
        _configuration.addAnnotatedClass(DMRoleEntity.class);
        _configuration.addAnnotatedClass(KeyItemCategoryEntity.class);
        _configuration.addAnnotatedClass(KeyItemEntity.class);
        _configuration.addAnnotatedClass(PCAuthorizedCDKeyEntity.class);
        _configuration.addAnnotatedClass(PCBlueprintEntity.class);
        _configuration.addAnnotatedClass(PCCraftEntity.class);
        _configuration.addAnnotatedClass(PCKeyItemEntity.class);
        _configuration.addAnnotatedClass(PCMigrationEntity.class);
        _configuration.addAnnotatedClass(PCMigrationItemEntity.class);
        _configuration.addAnnotatedClass(PCOutfitEntity.class);
        _configuration.addAnnotatedClass(PCOverflowItemEntity.class);
        _configuration.addAnnotatedClass(PCTerritoryFlagEntity.class);
        _configuration.addAnnotatedClass(PCTerritoryFlagPermissionEntity.class);
        _configuration.addAnnotatedClass(PCTerritoryFlagStructureEntity.class);
        _configuration.addAnnotatedClass(PCTerritoryFlagStructureItemEntity.class);
        _configuration.addAnnotatedClass(PlayerEntity.class);
        _configuration.addAnnotatedClass(PortraitEntity.class);
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
