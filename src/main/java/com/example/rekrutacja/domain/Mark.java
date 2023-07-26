package com.example.rekrutacja.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MARK")
public class Mark {
    private Long markId;
    private String subject;
    private int markValue;
    private Student student;
    private Lecturer lecturer;

    public Mark(Long markId, String subject, int markValue) {
        this.markId = markId;
        this.subject = subject;
        this.markValue = markValue;
    }

    @Id
    @GeneratedValue
    @NonNull
    @Column(name = "MARK_ID", unique = true)
    public Long getMarkId() {
        return markId;
    }

    @NonNull
    @Column(name = "SUBJECT")
    public String getSubject() {
        return subject;
    }

    @NonNull
    @Column(name = "MARK_VALUE")
    public int getMarkValue() {
        return markValue;
    }

    @ManyToOne
    @JoinColumn(name = "Student_ID")
    public Student getStudent() {
        return student;
    }

    @ManyToOne
    @JoinColumn(name = "LECTURER_ID")
    public Lecturer getLecturer() {
        return lecturer;
    }
}
