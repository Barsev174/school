package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepositories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepositories studentRepositories;

    public StudentService(StudentRepositories studentRepositories) {
        this.studentRepositories = studentRepositories;
    }

    public Student addStudent(Student student) {
        return studentRepositories.save(student);
    }

    public Student findStudent(long id) {
        return studentRepositories.findById(id).get();
    }

    public Student editStudent(Student student) {
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

    public Collection<Student> findStudentByAge(int ageMin, int ageMax) {
        return studentRepositories.findByAgeBetween(ageMin, ageMax);
    }

    public Faculty getStudentFaculty(long id) {
        Student student = findStudent(id);
        if (student == null) {
            return null;
        }
        return student.getFaculty();
    }
}
