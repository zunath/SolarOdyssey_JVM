package Common;

import org.nwnx.nwnx2.jvm.ResManListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CustomResMan {
    private static List<ResManListener> handlers = new LinkedList();

    public static void addResManListener(ResManListener var0) {
        handlers.add(var0);
    }

    private CustomResMan() {
    }

    private static int exists(String var0) {
        int var1 = -1;
        Iterator var2 = handlers.iterator();

        while(var2.hasNext()) {
            ResManListener var3 = (ResManListener)var2.next();
            int var4 = var3.exists(var0);
            if(var4 > var1) {
                var1 = var4;
            }
        }

        return var1;
    }

    private static byte[] demandRes(String var0) {
        Object var1 = null;
        Iterator var2 = handlers.iterator();

        ResManListener var3;
        byte[] var4;
        do {
            if(!var2.hasNext()) {
                return null;
            }

            var3 = (ResManListener)var2.next();
        } while(null == (var4 = var3.demand(var0)));

        return var4;
    }
}
