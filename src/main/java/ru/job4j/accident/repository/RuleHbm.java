package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Rule;

import java.util.List;
import java.util.Optional;

//@Repository
public class RuleHbm {
    private final SessionFactory sf;

    public RuleHbm(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Rule> findAllRules() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Rule", Rule.class)
                    .list();
        }
    }

    public Optional<Rule> findRuleById(int id) {
        Rule rule = null;
        try (Session session = sf.openSession()) {
            rule = session.find(Rule.class, id);
        }
        return rule == null ? Optional.empty() : Optional.of(rule);
    }
}
