package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentJdbcTemplate;
import ru.job4j.accident.repository.AccidentTypeJdbcTemplate;
import ru.job4j.accident.repository.RuleJdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class AccidentService {
    private final AccidentJdbcTemplate accidentStore;
    private final AccidentTypeJdbcTemplate typeStore;
    private final RuleJdbcTemplate ruleStore;

    public AccidentService(AccidentJdbcTemplate accidentStore,
                           AccidentTypeJdbcTemplate typeStore,
                           RuleJdbcTemplate ruleStore) {
        this.accidentStore = accidentStore;
        this.typeStore = typeStore;
        this.ruleStore = ruleStore;
    }

    public Accident findAccidentById(int id) {
        return accidentStore.findAccidentById(id).get();
    }

    public void save(Accident accident, String[] rIds) {
        saveUpdate(accident, rIds, accident1 -> accidentStore.save(accident));
    }

    public void update(Accident accident, String[] rIds) {
        saveUpdate(accident, rIds, accident1 -> accidentStore.update(accident));
    }

    private void saveUpdate(Accident accident, String[] rIds, Consumer<Accident> cons) {
        AccidentType type = typeStore.findAccidentTypeById(accident.getType().getId()).get();
        accident.setType(type);
        for (String rId : rIds) {
            accident.addRule(ruleStore.findRuleById(Integer.parseInt(rId)).get());
        }
        cons.accept(accident);
    }
}
