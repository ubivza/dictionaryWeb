package ru.aleksandr.dictionaryweb.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.aleksandr.dictionaryweb.service.EnglishDictionaryService;

@Controller
@RequestMapping("eng-ru-dict")
public class EngRuDictionaryController {

    private final EnglishDictionaryService englishDictionaryService;

    public EngRuDictionaryController(EnglishDictionaryService englishDictionaryService) {
        this.englishDictionaryService = englishDictionaryService;
    }

    @GetMapping("/edit")
    public String editWord(Model model) {
        model.addAttribute("id", englishDictionaryService.showByKey((String) model.getAttribute("key")).getId());
        return "firstDict/edit";
    }

    @GetMapping("/new")
    public String addWord() {
        return "firstDict/new";
    }

    @PostMapping("/new")
    public String newWord(@RequestParam(name = "newKeyWord") String key,
                          @RequestParam(name = "newValueWord", required = false) String value,
                          RedirectAttributes redirectAttributes) {
        englishDictionaryService.saveString(key + " " + value);
        redirectAttributes.addFlashAttribute("message", key + " - " +  value + " удачно добавлены в словарь!");
        return "redirect:/eng-ru-dict/new";
    }

    @PostMapping("/edit")
    public String editExistingWord(@RequestParam(name = "newKeyWord", required = false) String key,
                                   @RequestParam(name = "newValueWord", required = false) String value,
                                   @RequestParam(name = "id") String id,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (key.isEmpty()) {
            englishDictionaryService.deleteById(Long.valueOf(id));
            redirectAttributes.addFlashAttribute("message", "Слово было удалено");
            return "redirect:/";
        }
        englishDictionaryService.updateById(Long.valueOf(id), key + " " + value);
        redirectAttributes.addFlashAttribute("message", key + " - " + value + " были изменены");
        return "redirect:/";
    }
}
