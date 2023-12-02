package ru.aleksandr.dictionaryweb.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "spanish_word")
@Data
public class SpanishWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "word")
    private String word;
    @OneToMany(mappedBy = "spanishWord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SpanishTranslateWord> spanishTranslateWords;

    public SpanishWord() {}

    public SpanishWord(String word, List<SpanishTranslateWord> spanishTranslateWords) {
        this.word = word;
        this.spanishTranslateWords = spanishTranslateWords;
    }
}
