package stu.back.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stu.back.basic.PageList;
import stu.back.basic.utils.JsonResult;
import stu.back.org.domain.Student;
import stu.back.org.query.StudentQuery;
import stu.back.org.service.IStudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @GetMapping("/{id}")
    public JsonResult queryOne(@PathVariable Long id){
        try {
            Student student = studentService.queryById(id);
            return new JsonResult().setData(student);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult().setSuccess(false).setCode(500).setMessage("系统繁忙稍后再试!");
        }
    }

    @PostMapping("/add")
    public JsonResult addOrUpdate(@RequestBody Student student){
        try {
            if(student.getId() == null){
                studentService.add(student);
                return new JsonResult().setMessage("新增成功");
            }else {
                studentService.update(student);
                return new JsonResult().setMessage("修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().setSuccess(false).setMessage("操作失败");
        }
    }

    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable Long id){
        try {
            studentService.delete(id);
            return new JsonResult().setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().setCode(500).setSuccess(false).setMessage("删除失败");
        }
    }

    @PostMapping("/query")
    public JsonResult queryPage(@RequestBody StudentQuery query){
        PageList<Student> studentPageList = studentService.queryPage(query);
        return new JsonResult().setData(studentPageList);
    }

    @PostMapping("/all")
    public JsonResult allData(){
        List<Student> students = studentService.queryAll();
        return new JsonResult().setData(students);
    }

    @PatchMapping("/batch")
    public JsonResult batchRemove(@RequestBody List<Long> ids){
        try {
            studentService.batchRemove(ids);
            return new JsonResult().setMessage("批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().setSuccess(false).setCode(500).setMessage("批量删除失败");
        }
    }
}
