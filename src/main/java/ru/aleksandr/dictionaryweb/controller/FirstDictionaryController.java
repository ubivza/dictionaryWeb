package ru.aleksandr.dictionaryweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aleksandr.dictionaryweb.entity.EnglishWord;
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
    public String showWord(@PathVariable("id") Long id,
                           Model model) {
        model.addAttribute("valueById", englishDictionaryService.showById(id));
        return "firstDict/showById";
    }

    @GetMapping("/{id}/edit")
    public String editWord(@PathVariable("id") Long id) {
        return "firstDict/edit";
    }

    @PostMapping
    public String addWord() {
        return "firstDict/engRuMain";
    }
}
