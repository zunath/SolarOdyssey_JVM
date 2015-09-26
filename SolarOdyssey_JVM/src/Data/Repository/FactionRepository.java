package Data.Repository;

import Data.DataContext;
import Entities.FactionEntity;
import Entities.FactionRelationshipEntity;
import Entities.PCFactionReputationEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class FactionRepository {

    public PCFactionReputationEntity GetPCFactionReputationByID(String uuid, int factionID)
    {
        PCFactionReputationEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCFactionReputationEntity.class)
                    .createAlias("faction", "f")
                    .add(Restrictions.eq("playerID", uuid))
                    .add(Restrictions.eq("f.factionID", factionID));

            entity = (PCFactionReputationEntity)criteria.uniqueResult();

            if(entity == null)
            {
                FactionEntity faction = (FactionEntity)context.getSession()
                        .createCriteria(FactionEntity.class)
                        .add(Restrictions.eq("factionID", factionID))
                        .uniqueResult();

                entity = new PCFactionReputationEntity();
                entity.setPlayerID(uuid);
                entity.setFaction(faction);
                entity.setReputation(faction.getDefaultPCReputation());

                context.getSession().saveOrUpdate(entity);
            }

        }

        return entity;
    }

    public List<PCFactionReputationEntity> GetPCFactionReputations(String uuid, List<FactionRelationshipEntity> relationships)
    {
        List<PCFactionReputationEntity> entities;
        ArrayList<Integer> factionIDs = new ArrayList<>();

        for(FactionRelationshipEntity rep : relationships)
        {
            factionIDs.add(rep.getRelationshipTowardsFactionID());
        }

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCFactionReputationEntity.class)
                    .createAlias("faction", "f")
                    .add(Restrictions.eq("playerID", uuid))
                    .add(Restrictions.in("f.factionID", factionIDs));

            entities = criteria.list();

            for(PCFactionReputationEntity pcRep : entities)
            {
                factionIDs.remove(pcRep.getFaction().getFactionID());
            }

            criteria = context.getSession()
                    .createCriteria(FactionEntity.class)
                    .add(Restrictions.in("factionID", factionIDs));

            List<FactionEntity> missingFactions = criteria.list();
            for(FactionEntity faction : missingFactions)
            {
                PCFactionReputationEntity newRep = new PCFactionReputationEntity();
                newRep.setReputation(faction.getDefaultPCReputation());
                newRep.setFaction(faction);
                newRep.setPlayerID(uuid);
                entities.add(newRep);
            }
        }

        return entities;
    }

    public List<FactionRelationshipEntity> GetFactionRelationships(int factionID)
    {
        List<FactionRelationshipEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(FactionRelationshipEntity.class)
                    .add(Restrictions.eq("factionID", factionID));

            entities = criteria.list();
        }

        return entities;
    }

    public void Save(Object entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }
}
