package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Rule;

import java.util.List;
import java.util.Optional;

//@Repository
public class RuleJdbcTemplate {
    private final JdbcTemplate jdbc;

    public RuleJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Rule> findAllRules() {
        return jdbc.query(
                "select * from rules",
                (rs, rowNum) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    public Optional<Rule> findRuleById(int id) {
        return jdbc.query("select * " +
                "from rules " +
                "where id = ?", rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }
            Rule rule = new Rule();
            rule.setId(rs.getInt("id"));
            rule.setName(rs.getString("name"));
            return Optional.of(rule);
        }, id);
    }
}
