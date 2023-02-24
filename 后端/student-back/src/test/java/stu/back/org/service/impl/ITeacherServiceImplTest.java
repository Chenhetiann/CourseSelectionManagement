package stu.back.org.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import stu.back.SMApplication;
import stu.back.basic.PageList;
import stu.back.org.domain.Course;
import stu.back.org.domain.Student;
import stu.back.org.domain.Teacher;
import stu.back.org.query.StudentQuery;
import stu.back.org.query.TeacherQuery;
import stu.back.org.service.IStudentService;
import stu.back.org.service.ITeacherService;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SMApplication.class)
public class ITeacherServiceImplTest {

    @Autowired
    private ITeacherService iTeacherService;

    @Test
    public void add() {
        Teacher teacher = iTeacherService.queryById(1L);
        teacher.setId(null);
        Course course = new Course();
        course.setId(2L);
        teacher.setCourse(course);
        iTeacherService.add(teacher);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void queryById() {
    }

    @Test
    public void queryAll() {
        List<Teacher> departments = iTeacherService.queryAll();
//        System.out.println(departments);
        departments.forEach(System.out::println);
    }

    @Test
    public void queryPage() {
    }

    @Test
    public void batchRemove() {
    }
}