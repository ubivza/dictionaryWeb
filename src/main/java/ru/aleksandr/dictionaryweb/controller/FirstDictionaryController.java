package ru.aleksandr.dictionaryweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("eng-ru-dict")
public class FirstDictionaryController {
    @GetMapping
    public String showMainDictionaryPage(Model model) {
        return "firstDict/engRuMain";
    }

    @GetMapping("/{id}")
    public String showWord(@PathVariable("id") int id,
                           Model model) {
        return "firstDict/engRuMain";
    }

    @PostMapping
    public String addWord() {
        return "firstDict/engRuMain";
    }
}
