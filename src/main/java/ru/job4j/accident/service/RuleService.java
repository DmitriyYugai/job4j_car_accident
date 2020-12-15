package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentJdbcTemplate;
import ru.job4j.accident.repository.RuleJdbcTemplate;

import java.util.List;

@Service
public class RuleService {
    private final RuleJdbcTemplate store;

    public RuleService(RuleJdbcTemplate store) {
        this.store = store;
    }

    public List<Rule> findAllRules() {
        return store.findAllRules();
    }
}
