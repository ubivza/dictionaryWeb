package ru.aleksandr.dictionaryweb.service;

import org.springframework.stereotype.Service;
import ru.aleksandr.dictionaryweb.model.EngWordModel;
import ru.aleksandr.dictionaryweb.repository.EngRuRepository;

import java.util.List;

@Service
public class EnglishDictionaryService {

    private final EngRuRepository engRuRepository;

    public EnglishDictionaryService(EngRuRepository engRuRepository) {
        this.engRuRepository = engRuRepository;
    }


    public List<EngWordModel> showAll() {
        return engRuRepository.getAll();
    }

    public void saveString(String word) {
        EngWordModel model = EngWordModel.builder()
                .word(Integer.valueOf(word.split(" ")[0]))
                .translations(word.split(" ")[1])
                .build();
        engRuRepository.save(model);
    }

    public EngWordModel showByKey(String key) {
        return engRuRepository.getByKey(key);
    }

    public List<EngWordModel> showByValue(String value) {
        return engRuRepository.getByValue(value);
    }

    public void deleteByKey(String deleteWord) {
        engRuRepository.deleteByKey(deleteWord);
    }

    public void updateById(Long id, String word) {
        EngWordModel model = EngWordModel.builder()
                .word(Integer.valueOf(word.split(" ")[0]))
                .translations(word.split(" ")[1])
                .build();

        engRuRepository.update(id, model);
    }

    public void deleteById(Long id) {
        engRuRepository.deleteById(id);
    }
}