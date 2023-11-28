package ru.aleksandr.dictionaryweb.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "eng_translate")
@Data
@AllArgsConstructor
public class EnglishTranslateWord {
    @Id
    @Column(name = "row_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eng_word_id", referencedColumnName = "id")
    private EnglishWord englishWord;
    @Column(name = "translate_word")
    private String translation;

    public EnglishTranslateWord() {}
}
