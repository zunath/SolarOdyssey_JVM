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
                    .add(Restrictions.eq("pcID", uuid)).uniqueResult();

            entity = (PCClassEntity)context.getSession()
                    .createCriteria(PCClassEntity.class)
                    .createAlias("characterClass", "c")
                    .add(Restrictions.eq("playerID", uuid))
                    .add(Restrictions.eq("c.characterClassID", player.getActiveClassID()))
                    .uniqueResult();

            if(entity == null)
            {
                ClassLevelEntity level = (ClassLevelEntity)context.getSession()
                        .createCriteria(ClassLevelEntity.class)
                        .add(Restrictions.eq("classLevelID", 1))
                        .uniqueResult();

                entity = new PCClassEntity(uuid, player.getActiveClassID() , 1, 0);
                entity.setClassLevel(level);
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

    public ClassStatEntity GetClassStat(int characterClassID, int levelID)
    {
        ClassStatEntity entity;

        try(DataContext context = new DataContext())
        {
            entity = (ClassStatEntity)context.getSession()
                    .createCriteria(ClassStatEntity.class)
                    .add(Restrictions.eq("characterClassID", characterClassID))
                    .add(Restrictions.eq("levelID", levelID))
                    .uniqueResult();
        }

        return entity;
    }

    public List<CharacterClassEntity> GetAllCharacterClasses()
    {
        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(CharacterClassEntity.class);

            return criteria.list();
        }
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

    public List<ClassFeatLevelEntity> GetClassFeatLevels(int characterClassID, int levelID)
    {
        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(ClassFeatLevelEntity.class)
                    .add(Restrictions.eq("characterClassID", characterClassID))
                    .add(Restrictions.le("classLevelID", levelID));

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
