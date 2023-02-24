package stu.back.org.service;

import stu.back.org.domain.Course;

import java.util.List;

public interface ICourseService {
    List<Course> loadAll();

    Course loadOne(Long id);
}
