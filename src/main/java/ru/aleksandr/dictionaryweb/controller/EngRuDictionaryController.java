package ru.aleksandr.dictionaryweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.aleksandr.dictionaryweb.service.EnglishDictionaryService;

@Controller
@Slf4j
@RequestMapping("eng-ru-dict")
public class EngRuDictionaryController {

    private final EnglishDictionaryService englishDictionaryService;

    public EngRuDictionaryController(EnglishDictionaryService englishDictionaryService) {
        this.englishDictionaryService = englishDictionaryService;
    }

    @GetMapping("/edit")
    public String editWord(Model model) {
        try {
            model.addAttribute("id", englishDictionaryService.showByKey((String) model.getAttribute("key")).getId());
        } catch (Exception e) {
            log.warn(e + " in /edit controller because of redirect and not correct key redirected");
        }
        return "firstDict/edit";
    }

    @GetMapping("/new")
    public String addWord() {
        return "firstDict/new";
    }

    @PostMapping("/new")
    public String newWord(@RequestParam(name = "newKeyWord", required = false) String key,
                          @RequestParam(name = "newValueWord", required = false) String value,
                          RedirectAttributes redirectAttributes) {
        if (key.isEmpty() || !key.matches("[0-9]{5}")) {
            redirectAttributes.addFlashAttribute("message", "Ключ не может быть пустым или содержать не 5 цифровых значений");
            return "redirect:/eng-ru-dict/new";
        }
        englishDictionaryService.saveString(key + " " + value);
        redirectAttributes.addFlashAttribute("message", key + " - " +  value + " удачно добавлены в словарь!");
        return "redirect:/eng-ru-dict/new";
    }

    @PostMapping("/edit")
    public String editExistingWord(@RequestParam(name = "newKeyWord", required = false) String key,
                                   @RequestParam(name = "newValueWord", required = false) String value,
                                   @RequestParam(name = "id") String id,
                                   RedirectAttributes redirectAttributes) {

        //попробовать переписать на @ModelAttribute


        if (key.isEmpty()) {
            englishDictionaryService.deleteById(Long.valueOf(id));
            redirectAttributes.addFlashAttribute("message", "Слово было удалено");
            return "redirect:/";
        }
        if (!key.matches("[0-9]{5}")) {
            redirectAttributes.addFlashAttribute("key", key);
            redirectAttributes.addFlashAttribute("value", value);
            redirectAttributes.addFlashAttribute("id", id);
            redirectAttributes.addFlashAttribute("message", "Ключ не может содержать не 5 цифровых значений");
            return "redirect:/eng-ru-dict/edit";
        }
        englishDictionaryService.updateById(Long.valueOf(id), key + " " + value);
        redirectAttributes.addFlashAttribute("message", key + " - " + value + " были изменены");
        return "redirect:/";
    }
}
