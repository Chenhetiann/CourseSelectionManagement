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
import stu.back.org.query.StudentQuery;
import stu.back.org.service.IStudentService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SMApplication.class)
public class IStudentServiceImplTest {

    @Autowired
    private IStudentService studentService;

    @Test
    public void add() {
        Student student = studentService.queryById(1L);
        student.setId(null);
        Course course = new Course();
        course.setId(2L);
        student.setCourse(course);
        studentService.add(student);
    }

    @Test
    public void update() {
        Student student = studentService.queryById(3L);
        student.setGrade("2015");
        studentService.update(student);
    }

    @Test
    public void delete() {
        studentService.delete(3L);
    }

    @Test
    public void queryById() {
    }

    @Test
    public void queryAll() {
        List<Student> departments = studentService.queryAll();
//        System.out.println(departments);
        departments.forEach(System.out::println);
    }

    @Test
    public void queryPage() {
        //查第二页，5条数据
        StudentQuery studentQuery = new StudentQuery();
        //设置当前页为第二页
        studentQuery.setCurrentPage(2);
        //设置查询5条
        studentQuery.setPageSize(5);
        //PageList对象
        PageList<Student> studentPageList = studentService.queryPage(studentQuery);
        //具体我们拿到的数据
        List<Student> rows = studentPageList.getRows();
        rows.forEach(System.out::println);
    }
}