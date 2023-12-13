package ru.aleksandr.dictionaryweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.aleksandr.dictionaryweb.entity.EnglishWord;
import ru.aleksandr.dictionaryweb.entity.SpanishWord;
import ru.aleksandr.dictionaryweb.service.EnglishDictionaryService;
import ru.aleksandr.dictionaryweb.service.SpanishDictionaryService;
import ru.aleksandr.dictionaryweb.util.EngRuMessageFormatter;
import ru.aleksandr.dictionaryweb.util.SpanishRuMessageFormatter;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/")
public class MainController {
    private final EnglishDictionaryService englishDictionaryService;
    private final SpanishDictionaryService spanishDictionaryService;
    private final EngRuMessageFormatter engRuMessageFormatter;
    private final SpanishRuMessageFormatter spanishRuMessageFormatter;

    public MainController(EnglishDictionaryService englishDictionaryService, SpanishDictionaryService spanishDictionaryService, EngRuMessageFormatter engRuMessageFormatter, SpanishRuMessageFormatter spanishRuMessageFormatter) {
        this.englishDictionaryService = englishDictionaryService;
        this.spanishDictionaryService = spanishDictionaryService;
        this.engRuMessageFormatter = engRuMessageFormatter;
        this.spanishRuMessageFormatter = spanishRuMessageFormatter;
    }

    @GetMapping
    public String showMainMenu(Model model) {
        List<EnglishWord> wordList = englishDictionaryService.showAll();
        List<String> valuesToShow = engRuMessageFormatter.listToListMessageFormatter(wordList);
        model.addAttribute("allValuesFromDictionary", valuesToShow);
        List<SpanishWord> secondWordList = spanishDictionaryService.showAll();
        List<String> secondValuesToShow = spanishRuMessageFormatter.listToListMessageFormatter(secondWordList);
        model.addAttribute("allSpanishValuesFromDictionary", secondValuesToShow);
        return "main";
    }

    @PostMapping
    public String getSelectedWord(@RequestParam(name = "keyWord", required = false) String keyWord,
                                  @RequestParam(name = "valueWord", required = false) String valueWord,
                                  @RequestParam(name = "dictionary", required = false) String dictionary,
                                  RedirectAttributes redirectAttributes) {

        if (dictionary == null) {
            redirectAttributes.addFlashAttribute("message", "Выберите словарь из списка сверху " +
                    "и введите значение по которому будет вестись поиск");
        } else if (dictionary.equals("engRuDict")) {
            searchInEngRuDict(keyWord, valueWord, redirectAttributes);
        } else if (dictionary.equals("spainRuDict")) {
            searchInSpainRuDict(keyWord, valueWord, redirectAttributes);
        } else {
            searchInBothDicts(keyWord, valueWord, redirectAttributes);
        }
        return "redirect:/";
    }

    @DeleteMapping
    public String deleteWord(@RequestParam(name = "select") String wordToDelete,
                             @RequestParam(name = "deleteButton", required = false) String deleteButton,
                             @RequestParam(name = "updateButton", required = false) String updateButton,
                             RedirectAttributes redirectAttributes) {

        if (deleteButton != null) {
            englishDictionaryService.deleteByKey(wordToDelete.split(" - ")[0]);
            redirectAttributes.addFlashAttribute("message", wordToDelete + " удалено");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("key", wordToDelete.split(" - ")[0]);
            redirectAttributes.addFlashAttribute("value", wordToDelete.split(" - ")[1]);
            return "redirect:/eng-ru-dict/edit";
        }
    }


    private void searchInEngRuDict(String key, String value, RedirectAttributes redirectAttributes) {
        if (key != null && !key.isEmpty()) {
            try {
                EnglishWord englishWord = englishDictionaryService.showByKey(key);
                StringBuffer sb = engRuMessageFormatter.listToStringMessageFormatter(List.of(englishWord));
                redirectAttributes.addFlashAttribute("message", sb);
            } catch (Exception e) {
                log.warn(e + " in / post mapping because of not correct key");
                redirectAttributes.addFlashAttribute("message", "Такого ключа не существует");
            }
        } else if (value != null && !value.isEmpty()) {
            List<EnglishWord> englishWordList = englishDictionaryService.showByValue(value);
            StringBuffer sb = engRuMessageFormatter.listToStringMessageFormatter(englishWordList);
            redirectAttributes.addFlashAttribute("message", sb);
        }
    }

    private void searchInSpainRuDict(String key, String value, RedirectAttributes redirectAttributes) {
        if (key != null && !key.isEmpty()) {
            try {
                SpanishWord spanishWord = spanishDictionaryService.showByKey(key);
                StringBuffer sb = spanishRuMessageFormatter.listToStringMessageFormatter(List.of(spanishWord));
                redirectAttributes.addFlashAttribute("message", sb);
            } catch (Exception e) {
                log.warn(e + " in / post mapping because of not correct key");
                redirectAttributes.addFlashAttribute("message", "Такого ключа не существует");
            }
        } else if (value != null && !value.isEmpty()) {
            List<SpanishWord> spanisWordList = spanishDictionaryService.showByValue(value);
            StringBuffer sb = spanishRuMessageFormatter.listToStringMessageFormatter(spanisWordList);
            redirectAttributes.addFlashAttribute("message", sb);
        }
    }

    private void searchInBothDicts(String key, String value, RedirectAttributes redirectAttributes) {
        if (key != null && !key.isEmpty()) {
            EnglishWord englishWord = englishDictionaryService.showByKey(key);
            StringBuffer sb = engRuMessageFormatter.listToStringMessageFormatter(List.of(englishWord));
            SpanishWord spanishWord = spanishDictionaryService.showByKey(key);
            sb.append(spanishRuMessageFormatter.listToStringMessageFormatter(List.of(spanishWord)));
            redirectAttributes.addFlashAttribute("message", sb);
        } else if (value != null && !value.isEmpty()) {
            List<EnglishWord> englishWordList = englishDictionaryService.showByValue(value);
            StringBuffer sb = engRuMessageFormatter.listToStringMessageFormatter(englishWordList);
            List<SpanishWord> spanishWordList = spanishDictionaryService.showByValue(value);
            sb.append(spanishRuMessageFormatter.listToStringMessageFormatter(spanishWordList));
            redirectAttributes.addFlashAttribute("message", sb);
        }
    }
}
