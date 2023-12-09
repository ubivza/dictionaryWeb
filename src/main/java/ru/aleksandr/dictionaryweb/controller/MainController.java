package ru.aleksandr.dictionaryweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aleksandr.dictionaryweb.entity.EnglishWord;
import ru.aleksandr.dictionaryweb.service.EnglishDictionaryService;
import ru.aleksandr.dictionaryweb.util.EngRuMessageFormatter;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final EnglishDictionaryService englishDictionaryService;
    private final EngRuMessageFormatter engRuMessageFormatter;

    public MainController(EnglishDictionaryService englishDictionaryService, EngRuMessageFormatter engRuMessageFormatter) {
        this.englishDictionaryService = englishDictionaryService;
        this.engRuMessageFormatter = engRuMessageFormatter;
    }

    @GetMapping
    public String showMainMenu(Model model) {
        List<EnglishWord> wordList = englishDictionaryService.showAll();
        List<String> valuesToShow = engRuMessageFormatter.listToListMessageFormatter(wordList);
        model.addAttribute("allValuesFromDictionary", valuesToShow);
        return "main";
    }

    @PostMapping
    public String getSelectedWord(@RequestParam(name = "keyWord", required = false) String keyWord,
                                  @RequestParam(name = "valueWord", required = false) String valueWord,
                                  @RequestParam(name = "dictionary", required = false) String dictionary,
                                  Model model) {

        List<EnglishWord> wordList = englishDictionaryService.showAll();
        List<String> valuesToShow = engRuMessageFormatter.listToListMessageFormatter(wordList);
        model.addAttribute("allValuesFromDictionary", valuesToShow);
        if (dictionary == null) {
            model.addAttribute("message", "Выберите словарь из списка сверху " +
                    "и введите значение по которому будет вестись поиск");
        } else if (dictionary.equals("engRuDict")) {
            searchInEngRuDict(keyWord, valueWord, model);
        } else if (dictionary.equals("spainRuDict")) {
            searchInSpainRuDict(keyWord, valueWord, model);
        } else {
            searchInBothDicts(keyWord, valueWord, model);
        }
        return "main";
    }

    @DeleteMapping
    public String deleteWord(@RequestParam(name = "select") String wordToDelete,
                             Model model) {
        englishDictionaryService.deleteByKey(wordToDelete.split(" - ")[0]);
        List<EnglishWord> wordList = englishDictionaryService.showAll();
        List<String> valuesToShow = engRuMessageFormatter.listToListMessageFormatter(wordList);
        model.addAttribute("allValuesFromDictionary", valuesToShow);
        model.addAttribute("message", wordToDelete + " удалено");
        return "main";
    }


    private void searchInEngRuDict(String key, String value, Model model) {
        if (key != null && !key.isEmpty()) {
            EnglishWord englishWord = englishDictionaryService.showByKey(key);
            StringBuffer sb = engRuMessageFormatter.listToStringMessageFormatter(List.of(englishWord));
            model.addAttribute("message", sb);
        } else if (value != null && !value.isEmpty()) {
            List<EnglishWord> englishWordList = englishDictionaryService.showByValue(value);
            StringBuffer sb = engRuMessageFormatter.listToStringMessageFormatter(englishWordList);
            model.addAttribute("message", sb);
        }
    }

    private void searchInSpainRuDict(String key, String value, Model model) {
        if (key != null && !key.isEmpty()) {
            //add logic
            model.addAttribute("message", "Ищу в словаре испанском по ключу " + key);
        } else if (value != null && !value.isEmpty()) {
            //add logic
            model.addAttribute("message", "Ищу в словаре испанском по значению " + value);
        }
    }

    private void searchInBothDicts(String key, String value, Model model) {
        if (key != null && !key.isEmpty()) {
            //add logic
            model.addAttribute("message", "Ищу в обоих словарях по ключу " + key);
        } else if (value != null && !value.isEmpty()) {
            //add logic
            model.addAttribute("message", "Ищу в обоих словарях по значению " + value);
        }
    }
}
