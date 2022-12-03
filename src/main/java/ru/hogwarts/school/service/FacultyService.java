package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepositories;
import ru.hogwarts.school.repositories.StudentRepositories;

import java.util.Collection;

@Service
public class FacultyService {
    private final FacultyRepositories facultyRepositories;
    private final StudentRepositories studentRepositories;

    public FacultyService(FacultyRepositories facultyRepositories, StudentRepositories studentRepositories) {
        this.facultyRepositories = facultyRepositories;
        this.studentRepositories = studentRepositories;

    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepositories.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepositories.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepositories.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepositories.deleteById(id);
    }

    public Collection<Faculty> findFacultyByColorOrName(String name, String color) {
        return facultyRepositories.findFacultyByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }


    public Collection<Student> getFacultyStudents(long id) {
        Faculty faculty = findFaculty(id);
        if (faculty == null) {
            return null;
        }
        return studentRepositories.findByFacultyId(faculty.getId());
    }
}
