package ru.aleksandr.dictionaryweb.controller;

import org.springframework.stereotype.Component;
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

    @GetMapping("/{id}/edit")
    public String editWord(@PathVariable("id") Long id) {
        return "firstDict/edit";
    }

    @GetMapping("/new")
    public String addWord() {
        return "firstDict/new";
    }

    @PostMapping("/new")
    public String newWord(@RequestParam(name = "newKeyWord") String key,
                          @RequestParam(name = "newValueWord", required = false) String value,
                          Model model) {
        englishDictionaryService.saveString(key + " " + value);
        model.addAttribute("message", key + " - " +  value + " удачно добавлены в словарь!");
        return "firstDict/new";
    }
}
