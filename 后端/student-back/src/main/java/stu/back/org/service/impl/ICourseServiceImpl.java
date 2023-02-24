package stu.back.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.back.org.domain.Course;
import stu.back.org.mapper.CourseMapper;
import stu.back.org.service.ICourseService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ICourseServiceImpl implements ICourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> loadAll() {
        return courseMapper.selectAll();
    }

    @Override
    public Course loadOne(Long id) {
        return courseMapper.selectOne(id);
    }
}
