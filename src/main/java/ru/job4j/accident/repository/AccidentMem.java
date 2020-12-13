package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final Map<Integer, AccidentType> types = new HashMap<>();
    private static final AtomicInteger ACCIDENT_ID = new AtomicInteger(4);

    private AccidentMem() {
        types.put(1, AccidentType.of(1, "Две машины"));
        types.put(2, AccidentType.of(2, "Машина и человек"));
        types.put(3, AccidentType.of(3, "Машина и велосипед"));
        accidents.put(1, new Accident(1, "Accident1", "Accident1","Accident1",
                types.get(1)));
        accidents.put(2, new Accident(2, "Accident2", "Accident2","Accident2",
                types.get(2)));
        accidents.put(3, new Accident(3, "Accident3", "Accident3","Accident3",
                types.get(3)));
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
        if (accidents.containsKey(id)) {
            return Optional.of(accidents.get(id));
        }
        return Optional.empty();
    }

    public Collection<AccidentType> findAllAccidentTypes() {
        return types.values();
    }

    public Optional<AccidentType> findAccidentTypeById(int id) {
        if (types.containsKey(id)) {
            return Optional.of(types.get(id));
        }
        return Optional.empty();
    }
}
