package stu.back.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import stu.back.basic.PageList;
import stu.back.org.domain.Student;
import stu.back.org.mapper.StudentMapper;
import stu.back.org.query.StudentQuery;
import stu.back.org.service.IStudentService;

import java.util.List;

@Service
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class IStudentServiceImpl implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    @Transactional
    public void add(Student student) {
        studentMapper.insert(student);
    }

    @Override
    @Transactional
    public void update(Student student) {
        studentMapper.update(student);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        studentMapper.delete(id);
    }

    @Override
    public Student queryById(Long id) {
        return studentMapper.selectById(id);
    }

    @Override
    public List<Student> queryAll() {
        return studentMapper.selectAll();
    }

    @Override
    public PageList<Student> queryPage(StudentQuery query) {
        Long total = studentMapper.count(query.getKeyword());
        List<Student> students = studentMapper.pageData(query);
        return new PageList<>(total,students);
    }

    @Override
    public void batchRemove(List<Long> ids) {
        studentMapper.batchRemove(ids);
    }
}
