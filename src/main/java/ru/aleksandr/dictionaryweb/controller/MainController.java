package ru.aleksandr.dictionaryweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.aleksandr.dictionaryweb.entity.EnglishWord;
import ru.aleksandr.dictionaryweb.service.EnglishDictionaryService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final EnglishDictionaryService englishDictionaryService;

    public MainController(EnglishDictionaryService englishDictionaryService) {
        this.englishDictionaryService = englishDictionaryService;
    }

    @GetMapping
    public String showMainMenu(Model model) {
        List<String> valuesToShow = new ArrayList<>();
        for (EnglishWord ew : englishDictionaryService.showAll()) {
            StringBuffer translations = new StringBuffer();

            if (ew.getEnglishTranslateWords().isEmpty()) {
                translations.append("there is no translation yet");
            } else {

                translations.append(ew.getEnglishTranslateWords().get(0).getTranslation());
                for (int i = 1; i < ew.getEnglishTranslateWords().size(); i++) {
                    translations.append(", ");
                    translations.append(ew.getEnglishTranslateWords().get(i).getTranslation());
                }
            }
            valuesToShow.add(ew.getWord() + " - " + translations);
        }
        model.addAttribute("allValuesFromDictionary", valuesToShow);
        return "main";
    }
}
