package com.example.rekrutacja.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MarkDto {
    private Long markId;
    private String subject;
    private int markValue;
}
