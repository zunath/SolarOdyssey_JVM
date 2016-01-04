package GameSystems;

import GameSystems.Models.AreaInstanceModel;
import NWNX.NWNX_Areas;
import org.ini4j.Ini;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AreaInstanceSystem {

    private static List<AreaInstanceModel> instanceList;

    static
    {
        instanceList = Arrays.asList(
                new AreaInstanceModel("cardgame001", 20));
    }

    public static void OnModuleLoad()
    {
        DuplicateAreas();
        LoadAreasFromDisk();
    }

    private static void DuplicateAreas()
    {
        for(AreaInstanceModel model : instanceList)
        {
            for(int x = 1; x <= model.getInstanceCount(); x++)
            {
                NWNX_Areas.LoadArea(model.getAreaResref());
            }
        }
    }

    private static void LoadAreasFromDisk()
    {

        String folderPath;
        try
        {
            Ini ini = new Ini(new File("./nwnx.ini"));
            folderPath = ini.get("RESOURCEMANAGER", "sourcepath");
        }
        catch (Exception ex)
        {
            folderPath = "resman";
        }
        File folder = new File("./" + folderPath + "/are");
        File[] files = folder.listFiles();
        ArrayList<String> resrefs = new ArrayList<>();

        if(files != null)
        {
            for (File file : files) {
                if (file.isFile()) {
                    String resref = file.getName();
                    int pos = resref.lastIndexOf(".");
                    if (pos > 0) {
                        resref = resref.substring(0, pos);
                    }

                    resrefs.add(resref);
                }
            }
        }

        for(String resref : resrefs)
        {
            NWNX_Areas.LoadArea(resref);
        }
    }

}
