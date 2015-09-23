package Data.Repository;

import Data.DataContext;
import Entities.PCMigrationEntity;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

public class PCMigrationRepository {

    public PCMigrationEntity GetByMigrationID(int migrationID)
    {
        PCMigrationEntity entity;

        try(DataContext context = new DataContext())
        {
            entity = (PCMigrationEntity)context.getSession()
                    .createCriteria(PCMigrationEntity.class)
                    .add(Restrictions.eq("pcMigrationID", migrationID)).uniqueResult();
        }

        return entity;
    }

    public int GetLatestMigrationID()
    {
        try(DataContext context = new DataContext())
        {
            return (Integer)context.getSession()
                    .createCriteria(PCMigrationEntity.class)
                    .setProjection(Projections.max("pcMigrationID"))
                    .uniqueResult();
        }
    }

}
