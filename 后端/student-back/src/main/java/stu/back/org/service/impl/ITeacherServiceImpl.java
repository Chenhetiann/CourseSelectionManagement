package stu.back.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import stu.back.basic.PageList;
import stu.back.org.domain.Student;
import stu.back.org.domain.Teacher;
import stu.back.org.mapper.StudentMapper;
import stu.back.org.mapper.TeacherMapper;
import stu.back.org.query.TeacherQuery;
import stu.back.org.service.ITeacherService;

import java.util.List;

@Service
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class ITeacherServiceImpl implements ITeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    @Transactional
    public void add(Teacher teacher) {
        teacherMapper.insert(teacher);
    }

    @Override
    @Transactional
    public void update(Teacher teacher) {
        teacherMapper.update(teacher);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        teacherMapper.delete(id);
    }

    @Override
    public Teacher queryById(Long id) {
        return teacherMapper.selectById(id);
    }

    @Override
    public List<Teacher> queryAll() {
        return teacherMapper.selectAll();
    }

    @Override
    public PageList<Teacher> queryPage(TeacherQuery query) {
        Long total = teacherMapper.count(query.getKeyword());
        List<Teacher> teachers = teacherMapper.pageData(query);
        return new PageList<>(total,teachers);
    }

    @Override
    public void batchRemove(List<Long> ids) {
        teacherMapper.batchRemove(ids);
    }
}
