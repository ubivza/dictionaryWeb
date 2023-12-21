package ru.aleksandr.dictionaryweb.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.aleksandr.dictionaryweb.model.SpainWordModel;
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
    public String newWord(@ModelAttribute("newWord") @Valid SpainWordModel model,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() ||
                !model.getWord().matches("[A-Z, a-z]{4}")) {
            redirectAttributes.addFlashAttribute("message",
                    "Ключ не может быть пустым или содержать не 4 латинских буквы");
            return "redirect:/spain-ru-dict/new";
        }
        try {
            spanishDictionaryService.saveString(model);
            redirectAttributes.addFlashAttribute("message",
                    model + " удачно добавлены в словарь!");
        } catch (Exception e) {
            log.warn(e + " while saving new word " + model);
            redirectAttributes.addFlashAttribute("message",
                    "Слово с таким ключом уже существует");
        }
        return "redirect:/spain-ru-dict/new";
    }

    @PostMapping("/edit")
    public String editExistingWord(@ModelAttribute("editWord") @Valid SpainWordModel model,
                                   BindingResult bindingResult,
                                   @RequestParam(name = "id") String id,
                                   RedirectAttributes redirectAttributes) {
        if (model.getWord().isEmpty()) {
            spanishDictionaryService.deleteById(Long.valueOf(id));
            redirectAttributes.addFlashAttribute("message",
                    "Слово было удалено");
            return "redirect:/";
        }
        if (!model.getWord().matches("[A-Z, a-z]{4}") ||
                bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("key", model.getWord());
            redirectAttributes.addFlashAttribute("value", model.getTranslations());
            redirectAttributes.addFlashAttribute("id", id);
            redirectAttributes.addFlashAttribute("message",
                    "Ключ не может содержать не 4 латинских буквы");
            return "redirect:/spain-ru-dict/edit";
        }
        try {
            spanishDictionaryService.updateById(Long.valueOf(id), model);
            redirectAttributes.addFlashAttribute("message",
                    model + " были изменены");
        } catch (Exception e) {
            log.warn(e + " while saving new word " + model);
            redirectAttributes.addFlashAttribute("message",
                    "Слово с таким ключом уже существует");
        }
        return "redirect:/";
    }
}
