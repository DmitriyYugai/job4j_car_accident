package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexService {
    private final AccidentRepository store;

    public IndexService(AccidentRepository store) {
        this.store = store;
    }

    public List<Accident> findAllAccidents() {
        List<Accident> accidents = new ArrayList<>();
        store.findAll().forEach(accidents::add);
        return accidents;
    }
}


