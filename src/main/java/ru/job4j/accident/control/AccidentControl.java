package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentMem;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

@Controller
public class AccidentControl {
    private final AccidentMem store;

    public AccidentControl(AccidentMem store) {
        this.store = store;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", store.findAllAccidentTypes());
        model.addAttribute("rules", store.findAllRules());
        return "accident/create";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", store.findAccidentById(id).get());
        model.addAttribute("types", store.findAllAccidentTypes());
        model.addAttribute("rules", store.findAllRules());
        return "accident/update";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest request) {
        AccidentType type = store.findAccidentTypeById(accident.getType().getId()).get();
        accident.setType(type);
        String[] rIds = request.getParameterValues("rIds");
        for (String rId : rIds) {
            accident.addRule(store.findRuleById(Integer.parseInt(rId)).get());
        }
        store.save(accident);
        return "redirect:/";
    }
}
