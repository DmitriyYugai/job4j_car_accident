package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.RuleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleService {
    private final RuleRepository store;

    public RuleService(RuleRepository store) {
        this.store = store;
    }

    public List<Rule> findAllRules() {
        List<Rule> rules = new ArrayList<>();
        store.findAll().forEach(rules::add);
        return rules;
    }
}
