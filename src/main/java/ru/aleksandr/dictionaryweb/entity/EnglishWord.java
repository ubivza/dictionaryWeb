package ru.aleksandr.dictionaryweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "eng_word")
@Data
@Builder
@AllArgsConstructor
public class EnglishWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @Column(name = "word")
    private Integer word;

    @OneToMany(mappedBy = "englishWord", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<EnglishTranslateWord> englishTranslateWords;

    public EnglishWord() {}

}
