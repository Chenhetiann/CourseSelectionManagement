package stu.back.org.service;

import stu.back.basic.PageList;
import stu.back.org.domain.Teacher;
import stu.back.org.query.TeacherQuery;

import java.util.List;

public interface ITeacherService {
    void add(Teacher teacher);
    //修改
    void update(Teacher teacher);
    //删除
    void delete(Long id);
    //根据id查询
    Teacher queryById(Long id);
    //查询全部
    List<Teacher> queryAll();

    PageList<Teacher> queryPage(TeacherQuery query);

    void batchRemove(List<Long> ids);
}
