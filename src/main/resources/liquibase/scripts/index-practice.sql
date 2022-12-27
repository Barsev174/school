-- liquibase formatted sql

-- changeset Sergey:1
CREATE INDEX student_name_index ON student (name);

-- changeset Sergey:2
CREATE INDEX faculty_nameOrColor_index ON faculty (name, color);