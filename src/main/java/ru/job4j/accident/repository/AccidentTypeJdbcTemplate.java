package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.AccidentType;

import java.util.List;
import java.util.Optional;

//@Repository
public class AccidentTypeJdbcTemplate {
    private final JdbcTemplate jdbc;

    public AccidentTypeJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<AccidentType> findAllAccidentTypes() {
        return jdbc.query(
                "select * from types",
                (rs, rowNum) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }

    public Optional<AccidentType> findAccidentTypeById(int id) {
        return jdbc.query("select * " +
                "from types " +
                "where id = ?", rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }
            AccidentType type = new AccidentType();
            type.setId(rs.getInt("id"));
            type.setName(rs.getString("name"));
            return Optional.of(type);
        }, id);
    }
}
