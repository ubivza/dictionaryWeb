package ru.aleksandr.dictionaryweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EngWordModel {
    private Integer word;
    private String translations;
}
