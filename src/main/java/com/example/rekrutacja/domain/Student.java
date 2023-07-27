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
@Table(name = "STUDENT")
public class Student {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String email;
    private List<Mark> marksList;
    private List<Lecturer> lecturersList;

    public Student(Long studentId, String firstName, String lastName, String email) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Id
    @GeneratedValue
    @NonNull
    @Column(name = "STUDENT_ID", unique = true)
    public Long getStudentId() {
        return studentId;
    }

    @NonNull
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    @NonNull
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    @NonNull
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    @OneToMany(
            targetEntity = Mark.class,
            mappedBy = "student",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    public List<Mark> getMarksList() {
        return marksList;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_STUDENT_LECTURER",
            joinColumns = {@JoinColumn(name = "STUDENT_ID", referencedColumnName = "STUDENT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "LECTURER_ID", referencedColumnName = "LECTURER_ID")}
    )
    public List<Lecturer> getLecturersList() {
        return lecturersList;
    }
}