package Data.Repository;

import Data.DataContext;
import Entities.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import java.util.List;

public class CharacterClassRepository {

    public PCClassEntity GetActiveClass(String uuid)
    {
        PCClassEntity entity;

        try(DataContext context = new DataContext())
        {
            PlayerEntity player = (PlayerEntity)context.getSession()
                    .createCriteria(PlayerEntity.class)
                    .add(Restrictions.eq("playerID", uuid)).uniqueResult();

            entity = (PCClassEntity)context.getSession()
                    .createCriteria(PCClassEntity.class)
                    .add(Restrictions.eq("playerID", uuid))
                    .add(Restrictions.eq("characterClassID", player.getActiveClassID()))
                    .uniqueResult();

            if(entity == null)
            {
                entity = new PCClassEntity(uuid, player.getActiveClassID(), 1, 0);
            }
        }

        return entity;
    }

    public int GetMinimumClassID()
    {
        try(DataContext context = new DataContext())
        {
            DetachedCriteria minID = DetachedCriteria.forClass(CharacterClassEntity.class)
                    .setProjection(Projections.min("characterClassID"));
            CharacterClassEntity entity = (CharacterClassEntity)context.getSession()
                    .createCriteria(CharacterClassEntity.class)
                    .add(Property.forName("characterClassID").eq(minID))
                    .uniqueResult();
            return entity.getCharacterClassID();
        }
    }

    public CharacterClassEntity GetCharacterClass(int characterClassID)
    {
        CharacterClassEntity entity;

        try(DataContext context = new DataContext())
        {
            entity = (CharacterClassEntity)context.getSession()
                    .createCriteria(CharacterClassEntity.class)
                    .add(Restrictions.eq("characterClassID", characterClassID))
                    .uniqueResult();
        }

        return entity;
    }

    public List<ClassLevelEntity> GetClassLevels()
    {
        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(ClassLevelEntity.class);

            return criteria.list();
        }
    }

    public void Save(Object entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }
}
