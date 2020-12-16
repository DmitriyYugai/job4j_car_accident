package ru.job4j.accident.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import ru.job4j.accident.config.HbmConfig;
import ru.job4j.accident.config.WebConfig;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Repository
public class AccidentHbm {
    private final SessionFactory sf;

    public AccidentHbm(SessionFactory sf) {
        this.sf = sf;
    }

    public Accident save(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(accident);
            session.getTransaction().commit();
            return accident;
        }
    }

    public boolean update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(accident);
            session.getTransaction().commit();
            return true;
        }
    }

    public List<Accident> findAllAccidents() {
        try (Session session = sf.openSession()) {
           return session.createQuery("select distinct a from Accident a " +
                            "join fetch a.type " +
                            "join fetch a.rules", Accident.class)
                    .list();
        }
    }

    public Optional<Accident> findAccidentById(int id) {
        Accident accident = null;
        try (Session session = sf.openSession()) {
            accident = session.find(Accident.class, id);
        }
        return accident == null ? Optional.empty() : Optional.of(accident);
    }

    public static void main(String[] args) {
//        BasicDataSource ds = new BasicDataSource();
//        ds.setDriverClassName("org.postgresql.Driver");
//        ds.setUrl("jdbc:postgresql://127.0.0.1:5432/auto_crash");
//        ds.setUsername("postgres");
//        ds.setPassword("root");
//
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(ds);
//        sessionFactory.setPackagesToScan("ru.job4j.accident.model");
//        Properties cfg = new Properties();
//        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
//        cfg.setProperty("hibernate.hbm2ddl.auto","update");
//        cfg.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
//        cfg.setProperty("hibernate.connection.url", "jdbc:postgresql://127.0.0.1:5432/auto_crash");
//        cfg.setProperty("hibernate.connection.username", "postgres");
//        cfg.setProperty("hibernate.connection.password", "root");
//        cfg.setProperty("hibernate.connection.pool_size", "1");
//        sessionFactory.setHibernateProperties(cfg);
        StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory sessionFactory = new MetadataSources(REGISTRY)
                .buildMetadata().buildSessionFactory();

        AccidentHbm accidentHbm = new AccidentHbm(sessionFactory);
        Accident ac = new Accident();
        ac.setName("Accident1");
        ac.setText("Accident1");
        ac.setAddress("Accident1");
        ac.setType(AccidentType.of(0, "Две машины"));
        ac.addRule(Rule.of(0, "Статья 1"));
        accidentHbm.save(ac);

    }

}
