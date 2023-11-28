package ru.aleksandr.dictionaryweb.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "spanish_word")
@Data
@AllArgsConstructor
public class SpanishWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "word")
    private String word;
    @OneToMany(mappedBy = "spanishWord", cascade = CascadeType.ALL)
    private List<SpanishTranslateWord> spanishTranslateWords;

    public SpanishWord() {}
}
