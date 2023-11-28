package ru.aleksandr.dictionaryweb.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "eng_word")
@Data
@AllArgsConstructor
public class EnglishWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "word")
    private int word;
    @OneToMany(mappedBy = "englishWord", cascade = CascadeType.ALL)
    private List<EnglishTranslateWord> englishTranslateWords;

    public EnglishWord() {}
}
