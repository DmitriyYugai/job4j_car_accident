package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

@Repository
public class AccidentCustomizedRepositoryImpl implements AccidentCustomizedRepository<Accident> {

    private final SessionFactory sf;

    public AccidentCustomizedRepositoryImpl(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public boolean update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(accident);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public <S extends Accident> S save(S accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(accident);
            session.getTransaction().commit();
            return accident;
        }
    }
}
