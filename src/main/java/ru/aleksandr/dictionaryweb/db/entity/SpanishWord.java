package ru.aleksandr.dictionaryweb.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "spanish_word")
@Data
@Builder
@AllArgsConstructor
public class SpanishWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @Column(name = "word")
    private String word;

    @OneToMany(mappedBy = "spanishWord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SpanishTranslateWord> spanishTranslateWords;

    public SpanishWord() {}

}
