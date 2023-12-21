package ru.aleksandr.dictionaryweb.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpainWordModel {
    private Long id;
    @NotBlank(message = "Word shouldnt be empty")
    @Pattern(regexp = "[A-Z, a-z]{4}", message =
            "Word must be 4 characters long and should contain only latin letters")
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
