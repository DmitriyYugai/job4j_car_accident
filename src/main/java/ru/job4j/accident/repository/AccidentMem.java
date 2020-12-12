package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private static final AtomicInteger ACCIDENT_ID = new AtomicInteger(4);

    private AccidentMem() {
        accidents.put(1, new Accident(1, "Accident1", "Accident1","Accident1"));
        accidents.put(2, new Accident(2, "Accident2", "Accident2","Accident2"));
        accidents.put(3, new Accident(3, "Accident3", "Accident3","Accident3"));
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
}
