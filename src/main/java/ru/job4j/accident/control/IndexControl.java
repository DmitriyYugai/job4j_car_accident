package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.repository.AccidentJdbcTemplate;
import ru.job4j.accident.repository.AccidentMem;

@Controller
public class IndexControl {
    private final AccidentJdbcTemplate store;

    public IndexControl(AccidentJdbcTemplate store) {
        this.store = store;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("accidents", store.findAllAccidents());
        return "index";
    }
}
