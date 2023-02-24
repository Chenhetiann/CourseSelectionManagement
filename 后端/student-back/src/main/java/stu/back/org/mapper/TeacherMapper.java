package stu.back.org.mapper;

import stu.back.org.domain.Teacher;
import stu.back.org.query.TeacherQuery;

import java.util.List;

public interface TeacherMapper {
    //新增
    void insert(Teacher teacher);
    //修改
    void update(Teacher teacher);
    //删除
    void delete(Long id);
    //根据id查询
    Teacher selectById(Long id);
    //查询全部
    List<Teacher> selectAll();

    Long count(String keyword);//查出来的是条数

    List<Teacher> pageData(TeacherQuery query);//分页数据

    void batchRemove(List<Long> ids);//批量删除
}
