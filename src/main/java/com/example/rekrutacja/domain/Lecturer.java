package com.example.rekrutacja.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LECTURER")
public class Lecturer {
    private Long lecturerId;
    private String firstName;
    private String lastName;
    private String email;
    private String subject;
    private List<Mark> marksList;
    private List<Student> studentsList;

    public Lecturer(Long lecturerId, String firstName, String lastName, String email, String subject) {
        this.lecturerId = lecturerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.subject = subject;
    }

    @Id
    @GeneratedValue
    @NonNull
    @Column(name = "LECTURER_ID", unique = true)
    public Long getLecturerId() {
        return lecturerId;
    }

    @NonNull
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    @NonNull
    @Column(name = "LASTNAME")
    public String getLastName() {
        return lastName;
    }

    @NonNull
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    @NonNull
    @Column(name = "SUBJECT")
    public String getSubject() {
        return subject;
    }

    @OneToMany(
            targetEntity = Mark.class,
            mappedBy = "lecturer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    public List<Mark> getMarksList() {
        return marksList;
    }

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "lecturersList")
    public List<Student> getStudentsList() {
        return studentsList;
    }
}
