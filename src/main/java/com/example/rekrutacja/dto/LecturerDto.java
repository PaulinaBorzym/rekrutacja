package com.example.rekrutacja.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LecturerDto {
    private Long lecturerId;
    private String firstName;
    private String lastName;
    private String email;
    private String subject;
}