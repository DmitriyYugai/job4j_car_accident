package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
public class AccidentTypeHbm {
    private final SessionFactory sf;

    public AccidentTypeHbm(SessionFactory sf) {
        this.sf = sf;
    }

    public List<AccidentType> findAllAccidentTypes() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from AccidentType", AccidentType.class)
                    .list();
        }
    }

    public Optional<AccidentType> findAccidentTypeById(int id) {
        AccidentType type = null;
        try (Session session = sf.openSession()) {
            type = session.find(AccidentType.class, id);
        }
        return type == null ? Optional.empty() : Optional.of(type);
    }
}
