package stu.back.org.mapper;

import stu.back.org.domain.Student;
import stu.back.org.query.StudentQuery;

import java.util.List;

public interface StudentMapper {
    //新增
    void insert(Student student);
    //修改
    void update(Student student);
    //删除
    void delete(Long id);
    //根据id查询
    Student selectById(Long id);
    //查询全部
    List<Student> selectAll();

    Long count(String keyword);//查出来的是条数

    List<Student> pageData(StudentQuery query);//分页数据

    void batchRemove(List<Long> ids);//批量删除
}
