package ru.aleksandr.dictionaryweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("spain-ru-dict")
public class SecondDictionaryController {

    @GetMapping
    public String showMainDictionaryPage() {
        return "spainRuMain";
    }
}
