package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long count = 0;

    public Faculty addFaculty (Faculty faculty) {
        faculty.setId(count++);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    public Faculty editFaculty (Long id, Faculty faculty) {
        if (!faculties.containsKey(id)) {
            return null;
        }
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }


    public Collection<Faculty> findByColor(String color) {
        return faculties.entrySet().stream()
                .filter(e->e.getValue().getColor().equals(color))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values();
    }
}
