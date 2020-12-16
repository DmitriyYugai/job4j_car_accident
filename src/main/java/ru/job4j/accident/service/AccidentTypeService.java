package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccidentTypeService {
    private final AccidentTypeRepository store;

    public AccidentTypeService(AccidentTypeRepository store) {
        this.store = store;
    }

    public List<AccidentType> findAllAccidentTypes() {
        List<AccidentType> types = new ArrayList<>();
        store.findAll().forEach(types::add);
        return types;
    }
}
