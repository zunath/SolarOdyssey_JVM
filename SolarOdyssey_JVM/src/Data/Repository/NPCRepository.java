package Data.Repository;

import Data.DataContext;
import Entities.NPCEntity;
import org.hibernate.criterion.Restrictions;

public class NPCRepository {

    public NPCEntity GetNPC(int npcID)
    {
        NPCEntity entity;

        try(DataContext context = new DataContext())
        {
            entity = (NPCEntity)context.getSession()
                    .createCriteria(NPCEntity.class)
                    .add(Restrictions.eq("npcID", npcID))
                    .uniqueResult();
        }

        return entity;
    }

}
