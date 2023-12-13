package ru.aleksandr.dictionaryweb.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

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

    @OneToMany(mappedBy = "englishWord", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<EnglishTranslateWord> englishTranslateWords;

    public EnglishWord() {}

}
