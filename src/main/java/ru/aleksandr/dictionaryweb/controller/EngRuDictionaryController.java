package ru.aleksandr.dictionaryweb.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.aleksandr.dictionaryweb.model.EngWordModel;
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
    public String newWord(@ModelAttribute("newWord") @Valid EngWordModel model,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message",
                    "Ключ не может быть пустым или содержать не 5 цифровых значений");
            return "redirect:/eng-ru-dict/new";
        }
        try {
            englishDictionaryService.save(model);
            redirectAttributes.addFlashAttribute("message",
                    model +
                    " удачно добавлены в словарь!");
        } catch (Exception e) {
            log.warn(e + " while saving new word " + model);
            redirectAttributes.addFlashAttribute("message",
                    "Слово с таким ключом уже существует");
        }
        return "redirect:/eng-ru-dict/new";
    }

    @PostMapping("/edit")
    public String editExistingWord(@ModelAttribute("editWord") @Valid EngWordModel model,
                                   BindingResult bindingResult,
                                   @RequestParam(name = "id") String id,
                                   RedirectAttributes redirectAttributes) {
        if (model.getWord().toString().isEmpty()) {
            englishDictionaryService.deleteById(Long.valueOf(id));
            redirectAttributes.addFlashAttribute("message",
                    "Слово было удалено");
            return "redirect:/";
        } else if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("key", "");
            redirectAttributes.addFlashAttribute("value", model.getTranslations());
            redirectAttributes.addFlashAttribute("id", id);
            redirectAttributes.addFlashAttribute("message",
                    "Ключ не может содержать не 5 цифровых значений");
            return "redirect:/eng-ru-dict/edit";
        }
        try {
            englishDictionaryService.updateById(Long.valueOf(id), model);
            redirectAttributes.addFlashAttribute("message", model +
                    " были изменены");
        } catch (Exception e) {
            log.warn(e + " while saving new word " + model);
            redirectAttributes.addFlashAttribute("message",
                    "Слово с таким ключом уже существует");
        }
        return "redirect:/";
    }
}
