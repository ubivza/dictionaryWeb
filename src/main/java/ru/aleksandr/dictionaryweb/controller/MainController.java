package ru.aleksandr.dictionaryweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.aleksandr.dictionaryweb.model.EngWordModel;
import ru.aleksandr.dictionaryweb.model.SpainWordModel;
import ru.aleksandr.dictionaryweb.service.EnglishDictionaryService;
import ru.aleksandr.dictionaryweb.service.SpanishDictionaryService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/")
public class MainController {
    private final EnglishDictionaryService englishDictionaryService;
    private final SpanishDictionaryService spanishDictionaryService;

    public MainController(EnglishDictionaryService englishDictionaryService,
                          SpanishDictionaryService spanishDictionaryService) {
        this.englishDictionaryService = englishDictionaryService;
        this.spanishDictionaryService = spanishDictionaryService;
    }

    @GetMapping
    public String showMainMenu(Model model) {
        List<EngWordModel> wordList = englishDictionaryService.showAll();
        model.addAttribute("allValuesFromDictionary", wordList);

        List<SpainWordModel> secondWordList = spanishDictionaryService.showAll();
        model.addAttribute("allSpanishValuesFromDictionary", secondWordList);
        return "main";
    }

    @PostMapping
    public String getSelectedWord(@RequestParam(name = "keyWord", required = false) String keyWord,
                                  @RequestParam(name = "valueWord", required = false) String valueWord,
                                  @RequestParam(name = "dictionary", required = false) String dictionary,
                                  RedirectAttributes redirectAttributes) {
        if (dictionary == null) {
            redirectAttributes.addFlashAttribute("message",
                    "Выберите словарь из списка сверху " +
                    "и введите корректное значение по которому будет вестись поиск");
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
                             @RequestParam(name = "deleteButtonEng", required = false) String deleteButton,
                             @RequestParam(name = "updateButtonEng", required = false) String updateButton,
                             @RequestParam(name = "deleteButtonSpain", required = false) String deleteButtonSp,
                             RedirectAttributes redirectAttributes) {

        if (deleteButton != null) {
            englishDictionaryService.deleteByKey(wordToDelete.split(" - ")[0]);
            redirectAttributes.addFlashAttribute("message", wordToDelete + " удалено");
            return "redirect:/";
        } else if(deleteButtonSp != null) {
            spanishDictionaryService.deleteByKey(wordToDelete.split(" - ")[0]);
            redirectAttributes.addFlashAttribute("message", wordToDelete + " удалено");
            return "redirect:/";
        } else if (updateButton != null) {
            redirectAttributes.addFlashAttribute("key", wordToDelete.split(" - ")[0]);
            redirectAttributes.addFlashAttribute("value", wordToDelete.split(" - ")[1]);
            return "redirect:/eng-ru-dict/edit";
        } else {
            redirectAttributes.addFlashAttribute("key", wordToDelete.split(" - ")[0]);
            redirectAttributes.addFlashAttribute("value", wordToDelete.split(" - ")[1]);
            return "redirect:/spain-ru-dict/edit";
        }
    }


    private void searchInEngRuDict(String key, String value, RedirectAttributes redirectAttributes) {
        if (key != null && !key.isEmpty()) {
            try {
                EngWordModel englishWord = englishDictionaryService.showByKey(key);
                redirectAttributes.addFlashAttribute("message", englishWord.toString());
            } catch (Exception e) {
                log.warn(e + " in / post mapping because of not correct key");
                redirectAttributes.addFlashAttribute("message", "Такого ключа не существует");
            }
        } else if (value != null && !value.isEmpty()) {
            List<EngWordModel> englishWordList = englishDictionaryService.showByValue(value);
            StringBuffer sb = new StringBuffer();
            for (EngWordModel ewm : englishWordList) {
                sb.append(ewm + " ");
            }
            redirectAttributes.addFlashAttribute("message", sb);
        }
    }

    private void searchInSpainRuDict(String key, String value, RedirectAttributes redirectAttributes) {
        if (key != null && !key.isEmpty()) {
            try {
                SpainWordModel spanishWord = spanishDictionaryService.showByKey(key);
                redirectAttributes.addFlashAttribute("message", spanishWord.toString());
            } catch (Exception e) {
                log.warn(e + " in / post mapping because of not correct key");
                redirectAttributes.addFlashAttribute("message", "Такого ключа не существует");
            }
        } else if (value != null && !value.isEmpty()) {
            List<SpainWordModel> spanisWordList = spanishDictionaryService.showByValue(value);
            StringBuffer sb = new StringBuffer();
            for (SpainWordModel swm : spanisWordList) {
                sb.append(swm + " ");
            }
            redirectAttributes.addFlashAttribute("message", sb);
        }
    }
    private void searchInBothDicts(String key, String value, RedirectAttributes redirectAttributes) {
        if (key != null && !key.isEmpty()) {
            if (key.matches("[0-9]{5}")) {
                searchInEngRuDict(key, null, redirectAttributes);
            } else if (key.matches("[A-Z, a-z]{4}")) {
                searchInSpainRuDict(key, null, redirectAttributes);
            } else {
                redirectAttributes.addFlashAttribute("message", "Такого ключа не существует");
            }
        } else if (value != null && !value.isEmpty()) {
            List<EngWordModel> englishWordList = englishDictionaryService.showByValue(value);
            StringBuffer sb = new StringBuffer().append(englishWordList).append(" ");
            List<SpainWordModel> spanishWordList = spanishDictionaryService.showByValue(value);
            sb.append(spanishWordList);
            if (englishWordList.size() != 0 || spanishWordList.size() != 0) {
                redirectAttributes.addFlashAttribute("message", sb);
            } else {
                redirectAttributes.addFlashAttribute("message", "Значение не найдено");
            }
        }
    }


   /* private void searchInBothDicts(String key, String value, RedirectAttributes redirectAttributes) {
        if (key != null && !key.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            try {
                EngWordModel englishWord = englishDictionaryService.showByKey(key);
                sb.append(englishWord).append(" ");
            } catch (Exception e) {
                log.warn(e + " happened while searching in both dicts by key " + key);
                sb.append("В Англо-русском словаре такого значения нет ");
            }
            try {
                SpainWordModel spanishWord = spanishDictionaryService.showByKey(key);
                sb.append(spanishWord);
            } catch (Exception e) {
                log.warn(e + " happened while searching in both dicts by key " + key);
                sb.append("В Испано-русском словаре такого значения нет");
            }
            redirectAttributes.addFlashAttribute("message", sb);
        } else if (value != null && !value.isEmpty()) {
            List<EngWordModel> englishWordList = englishDictionaryService.showByValue(value);
            StringBuffer sb = new StringBuffer().append(englishWordList).append(" ");
            List<SpainWordModel> spanishWordList = spanishDictionaryService.showByValue(value);
            sb.append(spanishWordList);
            redirectAttributes.addFlashAttribute("message", sb);
        }
    }*/
}
