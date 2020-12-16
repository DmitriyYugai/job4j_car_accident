package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

@Service
public class AccidentService {
    private final AccidentRepository accidentStore;
    private final AccidentTypeRepository typeStore;
    private final RuleRepository ruleStore;

    public AccidentService(AccidentRepository accidentStore, AccidentTypeRepository typeStore, RuleRepository ruleStore) {
        this.accidentStore = accidentStore;
        this.typeStore = typeStore;
        this.ruleStore = ruleStore;
    }

    public Accident findAccidentById(int id) {
        return accidentStore.findById(id).get();
    }

    public void save(Accident accident, String[] rIds) {
        saveUpdate(accident, rIds, accident1 -> accidentStore.save(accident));
    }

    public void update(Accident accident, String[] rIds) {
        saveUpdate(accident, rIds, accident1 -> accidentStore.update(accident));
    }

    public List<Accident> findAllAccidents() {
        List<Accident> accidents = new ArrayList<>();
        accidentStore.findAll().forEach(accidents::add);
        accidents.sort(Comparator.comparing(Accident::getId));
        return accidents;
    }

    private void saveUpdate(Accident accident, String[] rIds, Consumer<Accident> cons) {
        AccidentType type = typeStore.findById(accident.getType().getId()).get();
        accident.setType(type);
        for (String rId : rIds) {
            Rule rule = ruleStore.findById(Integer.parseInt(rId)).get();
            accident.addRule(rule);
        }
        cons.accept(accident);
    }
}
