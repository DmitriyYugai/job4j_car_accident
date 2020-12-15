package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentJdbcTemplate;
import ru.job4j.accident.repository.AccidentTypeJdbcTemplate;

import java.util.List;

@Service
public class AccidentTypeService {
    private final AccidentTypeJdbcTemplate store;

    public AccidentTypeService(AccidentTypeJdbcTemplate store) {
        this.store = store;
    }

    public List<AccidentType> findAllAccidentTypes() {
        return store.findAllAccidentTypes();
    }
}
