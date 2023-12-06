package ru.aleksandr.dictionaryweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aleksandr.dictionaryweb.entity.EnglishTranslateWord;
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

    @PostMapping
    public String getSelectedWord(@RequestParam(name = "keyWord", required = false) String keyWord,
                                  @RequestParam(name = "valueWord", required = false) String valueWord,
                                  @RequestParam(name = "dictionary") String dictionary,
                                  Model model) {
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
        if (dictionary.equals("engRuDict")) {
            searchInEngRuDict(keyWord, valueWord, model);
        }
        return "main";
    }
    private void searchInEngRuDict(String key, String value, Model model) {

        if (key != null && !key.isEmpty()) {
            EnglishWord englishWord = englishDictionaryService.showByKey(key);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(englishWord.getWord());
            for (EnglishTranslateWord et : englishWord.getEnglishTranslateWords()) {
                stringBuilder.append(et.getTranslation());
            }
            model.addAttribute("message", stringBuilder);
        } else if (value != null && !value.isEmpty()) {
            EnglishWord englishWord = englishDictionaryService.showByValue(value);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(englishWord.getWord());
            for (EnglishTranslateWord et : englishWord.getEnglishTranslateWords()) {
                stringBuilder.append(et.getTranslation());
            }
            model.addAttribute("message", stringBuilder);
        }
    }

}
