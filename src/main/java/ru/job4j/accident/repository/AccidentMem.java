package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final Map<Integer, AccidentType> types = new HashMap<>();
    private final Map<Integer, Rule> rules = new HashMap<>();
    private static final AtomicInteger ACCIDENT_ID = new AtomicInteger(3);

    private AccidentMem() {
        rules.put(1, Rule.of(1, "Статья. 1"));
        rules.put(2, Rule.of(2, "Статья. 2"));
        rules.put(3, Rule.of(3, "Статья. 3"));
        types.put(1, AccidentType.of(1, "Две машины"));
        types.put(2, AccidentType.of(2, "Машина и человек"));
        types.put(3, AccidentType.of(3, "Машина и велосипед"));
        Accident accident1 = new Accident(1, "Accident1", "Accident1","Accident1",
                types.get(1));
        accident1.addRule(rules.get(1));
        accident1.addRule(rules.get(2));
        accident1.addRule(rules.get(3));
        Accident accident2 = new Accident(2, "Accident2", "Accident2","Accident2",
                types.get(2));
        accident2.addRule(rules.get(1));
        accident2.addRule(rules.get(2));
        accident2.addRule(rules.get(3));
        Accident accident3 = new Accident(3, "Accident3", "Accident3","Accident3",
                types.get(3));
        accident3.addRule(rules.get(1));
        accident3.addRule(rules.get(2));
        accident3.addRule(rules.get(3));
        accidents.put(1, accident1);
        accidents.put(2, accident2);
        accidents.put(3, accident3);
    }

    private static class Holder {
        private static final AccidentMem INST = new AccidentMem();
    }

    public static AccidentMem instOf() {
        return Holder.INST;
    }

    public void save(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(ACCIDENT_ID.incrementAndGet());
        }
        accidents.put(accident.getId(), accident);
    }

    public Collection<Accident> findAllAccidents() {
        return accidents.values();
    }

    public Optional<Accident> findAccidentById(int id) {
        return accidents.containsKey(id) ? Optional.of(accidents.get(id)) : Optional.empty();
    }

    public Collection<AccidentType> findAllAccidentTypes() {
        return types.values();
    }

    public Optional<AccidentType> findAccidentTypeById(int id) {
        return types.containsKey(id) ? Optional.of(types.get(id)) : Optional.empty();
    }

    public Collection<Rule> findAllRules() {
        return rules.values();
    }

    public Optional<Rule> findRuleById(int id) {
            return rules.containsKey(id) ? Optional.of(rules.get(id)) : Optional.empty();
    }
}
