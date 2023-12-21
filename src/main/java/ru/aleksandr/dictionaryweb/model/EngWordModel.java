package ru.aleksandr.dictionaryweb.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class EngWordModel {
    private Long id;
    @Max(value = 99999, message = "Word should be 5 digits in length")
    @NotNull(message = "Word should not be empty")
    private Integer word;
    private String translations;

    @Override
    public String toString() {
        if (translations == null) {
            return word + " - перевода пока нет";
        }
        return word + " - " + translations;
    }
}
