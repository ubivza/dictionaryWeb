package ru.aleksandr.dictionaryweb.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "eng_word")
@Data
public class EnglishWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "word")
    private Integer word;
    @OneToMany(mappedBy = "englishWord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnglishTranslateWord> englishTranslateWords;

    public EnglishWord() {}

    public EnglishWord(Integer word, List<EnglishTranslateWord> englishTranslateWords) {
        this.word = word;
        this.englishTranslateWords = englishTranslateWords;
    }
}
