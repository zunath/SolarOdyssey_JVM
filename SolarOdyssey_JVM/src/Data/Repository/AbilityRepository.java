package Data.Repository;

import Data.DataContext;
import Entities.ClassAbilityEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class AbilityRepository {

    public ClassAbilityEntity GetClassAbilityByFeatID(int featID)
    {
        ClassAbilityEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(ClassAbilityEntity.class)
                    .add(Restrictions.eq("featID", featID));

            entity = (ClassAbilityEntity)criteria.uniqueResult();
        }

        return entity;
    }

}
