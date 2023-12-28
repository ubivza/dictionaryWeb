package ru.aleksandr.dictionaryweb.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "eng_translate")
@Data
@Builder
@AllArgsConstructor
public class EnglishTranslateWord {
    @Id
    @Column(name = "row_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eng_word_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private EnglishWord englishWord;

    @Column(name = "translate_word")
    private String translation;

    public EnglishTranslateWord() {}

}
