package ru.job4j.accident.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into accidents(name, text, address, type_id) " +
                        "values(?, ?, ?, ?)", new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        accident.setId((int) keyHolder.getKey());
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accidents_rules (accident_id, rule_id) " +
                            "values (?, ?)",
                    accident.getId(), rule.getId());
        }
        return accident;
    }

    public boolean update(Accident accident) {
        int updated = jdbc.update("update accidents set " +
                "name = ?, text = ?, address = ?, type_id = ? " +
                "where id = ?",
                accident.getName(), accident.getText(),
                accident.getAddress(), accident.getType().getId(), accident.getId());
        int deleted = jdbc.update("delete from accidents_rules " +
                        "where accident_id = ?", accident.getId());
        int inserted = 0;
        for (Rule rule : accident.getRules()) {
            inserted += jdbc.update("insert into accidents_rules (accident_id, rule_id) " +
                            "values (?, ?)",
                    accident.getId(), rule.getId());
        }
        return updated > 0 && deleted > 0 && inserted > 0;
    }

    public List<Accident> findAllAccidents() {
        List<Accident> rsl = jdbc.query("select " +
                        "a.id as accident_id, a.name as accident_name, " +
                        "a.text, a.address, a.type_id, " +
                        "t.name as type_name " +
                        "from accidents as a " +
                        "left join types as t " +
                        "on a.type_id = t.id",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("accident_id"));
                    accident.setName(rs.getString("accident_name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("type_id"));
                    type.setName(rs.getString("type_name"));
                    accident.setType(type);
                    return accident;
                });
        for (Accident accident : rsl) {
            List<Rule> rules = jdbc.query("select * " +
                            "from accidents_rules as a " +
                            "left join rules as r " +
                            "on a.rule_id = r.id " +
                            "where a.accident_id = ?",
                    (rs, row) -> {
                        Rule rule = new Rule();
                        rule.setId(rs.getInt("id"));
                        rule.setName(rs.getString("name"));
                        return rule;
                    }, accident.getId());
            for (Rule rule : rules) {
                accident.addRule(rule);
            }
        }
        rsl.sort(Comparator.comparing(Accident::getId));
        return rsl;
    }

    public Optional<Accident> findAccidentById(int id) {
        Accident accident = jdbc.query("select " +
                "a.id as accident_id, a.name as accident_name, " +
                "a.text, a.address, a.type_id, " +
                "t.name as type_name " +
                "from accidents as a " +
                "left join types as t " +
                "on a.type_id = t.id " +
                "where a.id = ?", rs -> {
            if (!rs.next()) {
                return null;
            }
            Accident acc = new Accident();
            acc.setId(rs.getInt("accident_id"));
            acc.setName(rs.getString("accident_name"));
            acc.setText(rs.getString("text"));
            acc.setAddress(rs.getString("address"));
            AccidentType type = new AccidentType();
            type.setId(rs.getInt("type_id"));
            type.setName(rs.getString("type_name"));
            acc.setType(type);
            return acc;
        }, id);
        if (accident == null) {
            return Optional.empty();
        }
        List<Rule> rules = jdbc.query("select * " +
                        "from accidents_rules as a " +
                        "left join rules as r " +
                        "on a.rule_id = r.id " +
                        "where a.accident_id = ?",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                }, accident.getId());
        for (Rule rule : rules) {
            accident.addRule(rule);
        }
        return Optional.of(accident);
    }

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.load(new FileReader("src/main/resources/app.properties"));
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(props.getProperty("jdbc.driver"));
        ds.setUrl(props.getProperty("jdbc.url"));
        ds.setUsername(props.getProperty("jdbc.username"));
        ds.setPassword(props.getProperty("jdbc.password"));
        AccidentJdbcTemplate ajt = new AccidentJdbcTemplate(new JdbcTemplate(ds));
        Accident accident = new Accident("Accident1", "Accident1",
                "Accident1", AccidentType.of(1, "Две машины"));
        accident.addRule(Rule.of(1, "Статья. 1"));
        accident.addRule(Rule.of(2, "Статья. 2"));

        System.out.println(ajt.save(accident));

//        System.out.println(ajt.findAllAccidents());

//        System.out.println(ajt.findAccidentById(2));

//        System.out.println(ajt.findAllAccidentTypes());
//
//        System.out.println(ajt.findAccidentTypeById(2));

//        System.out.println(ajt.findAllRules());
//
//        System.out.println(ajt.findRuleById(3));
    }
}
