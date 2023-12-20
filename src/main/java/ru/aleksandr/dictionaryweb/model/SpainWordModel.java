package ru.aleksandr.dictionaryweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.swing.*;

@Data
@Builder
@AllArgsConstructor
public class SpainWordModel {
    private Spring word;
    private String translations;
}
