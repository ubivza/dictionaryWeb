package ru.aleksandr.dictionaryweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.aleksandr.dictionaryweb.service.SpanishDictionaryService;

@Controller
@RequestMapping("spain-ru-dict")
@Slf4j
public class SpanishRuDictionaryController {

    private final SpanishDictionaryService spanishDictionaryService;

    public SpanishRuDictionaryController(SpanishDictionaryService spanishDictionaryService) {
        this.spanishDictionaryService = spanishDictionaryService;
    }

    @GetMapping("/edit")
    public String editWord(Model model) {
        try {
            model.addAttribute("id", spanishDictionaryService.showByKey((String) model.getAttribute("key")).getId());
        } catch (Exception e) {
            log.warn(e + " in /edit controller because of redirect and not correct key redirected");
        }
        return "secondDict/edit";
    }

    @GetMapping("/new")
    public String addWord() {
        return "secondDict/new";
    }

    @PostMapping("/new")
    public String newWord(@RequestParam(name = "newKeyWord", required = false) String key,
                          @RequestParam(name = "newValueWord", required = false) String value,
                          RedirectAttributes redirectAttributes) {
        if (key.isEmpty() || !key.matches("[A-Z, a-z]{4}")) {
            redirectAttributes.addFlashAttribute("message", "Ключ не может быть пустым или содержать не 4 латинских буквы");
            return "redirect:/spain-ru-dict/new";
        }
        spanishDictionaryService.saveString(key + " " + value);
        redirectAttributes.addFlashAttribute("message", key + " - " +  value + " удачно добавлены в словарь!");
        return "redirect:/spain-ru-dict/new";
    }

    @PostMapping("/edit")
    public String editExistingWord(@RequestParam(name = "newKeyWord", required = false) String key,
                                   @RequestParam(name = "newValueWord", required = false) String value,
                                   @RequestParam(name = "id") String id,
                                   RedirectAttributes redirectAttributes) {
        if (key.isEmpty()) {
            spanishDictionaryService.deleteById(Long.valueOf(id));
            redirectAttributes.addFlashAttribute("message", "Слово было удалено");
            return "redirect:/";
        }
        if (!key.matches("[A-Z, a-z]{4}")) {
            redirectAttributes.addFlashAttribute("key", key);
            redirectAttributes.addFlashAttribute("value", value);
            redirectAttributes.addFlashAttribute("id", id);
            redirectAttributes.addFlashAttribute("message", "Ключ не может содержать не 4 латинских буквы");
            return "redirect:/spain-ru-dict/edit";
        }
        spanishDictionaryService.updateById(Long.valueOf(id), key + " " + value);
        redirectAttributes.addFlashAttribute("message", key + " - " + value + " были изменены");
        return "redirect:/";
    }
}
