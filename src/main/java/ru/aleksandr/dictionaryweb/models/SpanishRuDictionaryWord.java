package ru.aleksandr.dictionaryweb.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpanishRuDictionaryWord {
    @NotBlank(message = "Word shouldn't be empty")
    @Pattern(regexp = "[A-Z, a-z]{4}", message = "Word must be 4 characters long and should contain only latin letters")
    private String spanishWord;
    @NotBlank(message = "Translate shouldn't be empty")
    private String ruWord;
}
