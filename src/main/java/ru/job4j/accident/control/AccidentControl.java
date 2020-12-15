package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentJdbcTemplate;
import ru.job4j.accident.service.AccidentService;
import ru.job4j.accident.service.AccidentTypeService;
import ru.job4j.accident.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.function.BiConsumer;

@Controller
public class AccidentControl {
    private final AccidentService accidentService;
    private final RuleService ruleService;
    private final AccidentTypeService typeService;

    public AccidentControl(AccidentService accidentService, RuleService ruleService, AccidentTypeService typeService) {
        this.accidentService = accidentService;
        this.ruleService = ruleService;
        this.typeService = typeService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", typeService.findAllAccidentTypes());
        model.addAttribute("rules", ruleService.findAllRules());
        return "accident/create";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidentService.findAccidentById(id));
        model.addAttribute("types", typeService.findAllAccidentTypes());
        model.addAttribute("rules", ruleService.findAllRules());
        return "accident/update";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest request) {
        return saveUpdate(accident, request, (acc, rIds) -> accidentService.save(acc, rIds));
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Accident accident, HttpServletRequest request) {
        return saveUpdate(accident, request, (acc, rIds) -> accidentService.update(acc, rIds));
    }

    private String saveUpdate(Accident accident, HttpServletRequest request, BiConsumer<Accident, String[]> biCons) {
        String[] rIds = request.getParameterValues("rIds");
        biCons.accept(accident, rIds);
        return "redirect:/";
    }
}
