package stu.back.org.service;

import stu.back.basic.PageList;
import stu.back.org.domain.Student;
import stu.back.org.query.StudentQuery;

import java.util.List;

public interface IStudentService {
    //新增
    void add(Student student);
    //修改
    void update(Student student);
    //删除
    void delete(Long id);
    //根据id查询
    Student queryById(Long id);
    //查询全部
    List<Student> queryAll();

    PageList<Student> queryPage(StudentQuery query);

    void batchRemove(List<Long> ids);
}
