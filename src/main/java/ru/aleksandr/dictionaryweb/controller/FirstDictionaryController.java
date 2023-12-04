package ru.aleksandr.dictionaryweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aleksandr.dictionaryweb.service.EnglishDictionaryService;

@Controller
@RequestMapping("eng-ru-dict")
public class FirstDictionaryController {

    private final EnglishDictionaryService englishDictionaryService;

    public FirstDictionaryController(EnglishDictionaryService englishDictionaryService) {
        this.englishDictionaryService = englishDictionaryService;
    }

    @GetMapping
    public String showMainDictionaryPage(Model model) {
        model.addAttribute("allValuesFromDictionary", englishDictionaryService.showAll());
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
