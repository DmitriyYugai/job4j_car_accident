package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentJdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class AccidentService {
    private final AccidentJdbcTemplate store;

    public AccidentService(AccidentJdbcTemplate store) {
        this.store = store;
    }

    public List<AccidentType> findAllAccidentTypes() {
        return store.findAllAccidentTypes();
    }

    public List<Rule> findAllRules() {
        return store.findAllRules();
    }

    public Accident findAccidentById(int id) {
        return store.findAccidentById(id).get();
    }

    public void save(Accident accident, String[] rIds) {
        saveUpdate(accident, rIds, accident1 -> store.save(accident));
    }

    public void update(Accident accident, String[] rIds) {
        saveUpdate(accident, rIds, accident1 -> store.update(accident));
    }

    private void saveUpdate(Accident accident, String[] rIds, Consumer<Accident> cons) {
        AccidentType type = store.findAccidentTypeById(accident.getType().getId()).get();
        accident.setType(type);
        for (String rId : rIds) {
            accident.addRule(store.findRuleById(Integer.parseInt(rId)).get());
        }
        cons.accept(accident);
    }
}
