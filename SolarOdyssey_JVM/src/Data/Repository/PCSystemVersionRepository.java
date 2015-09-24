package Data.Repository;

import Data.DataContext;
import Entities.PCSystemVersionEntity;
import org.hibernate.criterion.Restrictions;

public class PCSystemVersionRepository {

    public PCSystemVersionEntity GetPCSystemVersion(String uuid)
    {
        PCSystemVersionEntity entity;

        try(DataContext context = new DataContext())
        {
            entity = (PCSystemVersionEntity)context.getSession()
                    .createCriteria(PCSystemVersionEntity.class)
                    .add(Restrictions.eq("playerID", uuid))
                    .uniqueResult();

            if(entity == null)
            {
                entity = new PCSystemVersionEntity();
                entity.setPlayerID(uuid);
            }

        }

        return entity;
    }

    public void Save(PCSystemVersionEntity entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }

}
