package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepositories;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepositories studentRepositories;
    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    public StudentService(StudentRepositories studentRepositories) {
        this.studentRepositories = studentRepositories;
    }

    public Student addStudent(Student student) {
        return studentRepositories.save(student);
    }

    public Student findStudent(long id) {
        return studentRepositories.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        if (studentRepositories.findById(student.getId()).orElse(null) == null) {
            return null;
        }
        return studentRepositories.save(student);
    }

    public void deleteStudent(long id) {
        studentRepositories.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        return studentRepositories.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int ageMin, int ageMax) {
        return studentRepositories.findByAgeBetween(ageMin, ageMax);
    }

    public Faculty getStudentFaculty(long id) {
        Student student = findStudent(id);
        if (student == null) {
            return null;
        }
        return student.getFaculty();
    }

    public Long getAmountOfAllStudent() {
        return studentRepositories.getAmountOfAllStudent();
    }

    public double getAverageOfAllStudent() {
        return studentRepositories.getAverageOfAllStudent();
    }

    public List<Student> getLastStudentsById() {
        return studentRepositories.getLastFiveStudent();
    }
    public Collection<String> getFilteredByName() {
        return studentRepositories.findAll().stream()
                //        .parallel()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    public Double getAllStudentsAvgAge() {
        return studentRepositories.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    public void getAllStudentName() {
        List<String> names = studentRepositories.findAll().stream()
                .map(user -> user.getName())
                .collect(Collectors.toList());

        printName(names.get(0));
        printName(names.get(1));

        new Thread(() -> {
            printName(names.get(2));
            printName(names.get(3));
        }).start();

        new Thread(() -> {
            printName(names.get(4));
            printName(names.get(5));
        }).start();

    }

    public void getAllStudentNameSync() {
        List<String> names = studentRepositories.findAll().stream()
                .map(user -> user.getName())
                .collect(Collectors.toList());

        printNameSync(names.get(0));
        printNameSync(names.get(1));

        new Thread(() -> {
            printNameSync(names.get(2));
            printNameSync(names.get(3));
        }).start();

        new Thread(() -> {
            printNameSync(names.get(4));
            printNameSync(names.get(5));
        }).start();
    }

    private void printName(String str) {
        System.out.println(str);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private synchronized void printNameSync(String str) {
        System.out.println(str);
    }
}
