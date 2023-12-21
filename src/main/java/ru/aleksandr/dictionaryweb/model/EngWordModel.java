package ru.aleksandr.dictionaryweb.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class EngWordModel {
    private Long id;
    @Pattern(regexp = "[0-9]{5}", message =
            "Word must be 5 digits long")
    @NotNull(message = "Word should not be empty")
    private String word;
    private String translations;

    @Override
    public String toString() {
        if (translations == null) {
            return word + " - перевода пока нет";
        }
        return word + " - " + translations;
    }
}
