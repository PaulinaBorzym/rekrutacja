//package com.example.rekrutacja.domain;
//
//import com.example.rekrutacja.repository.LecturerRepository;
//import com.example.rekrutacja.repository.MarkRepository;
//import com.example.rekrutacja.repository.StudentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//    private final StudentRepository studentRepository;
//    private final MarkRepository markRepository;
//    private final LecturerRepository lecturerRepository;
//
//    @Autowired
//    public DataLoader(StudentRepository studentRepository, MarkRepository markRepository, LecturerRepository lecturerRepository) {
//        this.studentRepository = studentRepository;
//        this.markRepository = markRepository;
//        this.lecturerRepository = lecturerRepository;
//    }
//
//    public void run(String... args) {
//        Student student1 = new Student(1L,"Ania","Kania","ania@kania");
//        Student student2 = new Student(2L,"Hania","Bania","hania@bania");
//        Lecturer lecturer1 = new Lecturer(1L,"Adam","Kot","adam@kot","Java");
//        Lecturer lecturer2 = new Lecturer(2L,"Artur","Smok","artur@smok","Python");
//        Mark mark1 = new Mark(1L,"Java",4);
//        Mark mark2 = new Mark(2L,"Java",3);
//        studentRepository.save(student1);
//        studentRepository.save(student2);
//        lecturerRepository.save(lecturer1);
//        lecturerRepository.save(lecturer2);
//        markRepository.save(mark1);
//        markRepository.save(mark2);
//    }
//}
