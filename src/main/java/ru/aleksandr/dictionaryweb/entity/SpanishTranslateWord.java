package ru.aleksandr.dictionaryweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "spanish_translate")
@Data
@Builder
@AllArgsConstructor
public class SpanishTranslateWord {
    @Id
    @Column(name = "row_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spanish_word_id", referencedColumnName = "id")
    @ToString.Exclude
    private SpanishWord spanishWord;

    @Column(name = "translate_word")
    private String translation;

    public SpanishTranslateWord() {}

}
