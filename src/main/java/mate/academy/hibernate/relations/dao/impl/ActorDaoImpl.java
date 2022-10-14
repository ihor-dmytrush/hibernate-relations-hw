package mate.academy.hibernate.relations.dao.impl;

import java.util.Optional;
import mate.academy.hibernate.relations.dao.ActorDao;
import mate.academy.hibernate.relations.exception.DataProcessingException;
import mate.academy.hibernate.relations.model.Actor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ActorDaoImpl extends AbstractDao implements ActorDao {
    public ActorDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Actor add(Actor actor) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            actor.setId((Long) session.save(actor));
            transaction.commit();
        } catch (Exception e) {
            transactionRollBack(transaction);
            throw new DataProcessingException("Couldn't add actor '" + actor + "'!", e);
        } finally {
            sessionClose(session);
        }
        return actor;
    }

    @Override
    public Optional<Actor> get(Long id) {
        try {
            Session session = factory.openSession();
            return Optional.ofNullable(session.get(Actor.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get actor by id = " + id);
        }
    }
}
