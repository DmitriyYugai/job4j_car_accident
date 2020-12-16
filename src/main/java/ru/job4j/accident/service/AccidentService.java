package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class AccidentService {
    private final AccidentHbm accidentStore;
    private final AccidentTypeHbm typeStore;
    private final RuleHbm ruleStore;

    public AccidentService(AccidentHbm accidentStore, AccidentTypeHbm typeStore, RuleHbm ruleStore) {
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
            Rule rule = ruleStore.findRuleById(Integer.parseInt(rId)).get();
            accident.addRule(rule);
        }
        cons.accept(accident);
    }
}
