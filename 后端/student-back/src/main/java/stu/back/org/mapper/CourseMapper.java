package stu.back.org.mapper;

import stu.back.org.domain.Course;

import java.util.List;

public interface CourseMapper {
    List<Course> selectAll();

    Course selectOne(Long id);
}
