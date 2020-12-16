package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentHbm;
import ru.job4j.accident.repository.AccidentJdbcTemplate;

import java.util.List;

@Service
public class IndexService {
    private final AccidentHbm store;

    public IndexService(AccidentHbm store) {
        this.store = store;
    }

    public List<Accident> findAllAccidents() {
        return store.findAllAccidents();
    }
}
