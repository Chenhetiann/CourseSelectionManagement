package stu.back.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stu.back.basic.PageList;
import stu.back.basic.utils.JsonResult;
import stu.back.org.domain.Teacher;
import stu.back.org.query.TeacherQuery;
import stu.back.org.service.ITeacherService;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private ITeacherService iTeacherService;

    @GetMapping("/{id}")
    public JsonResult queryOne(@PathVariable Long id){
        try {
            Teacher teacher = iTeacherService.queryById(id);
            return new JsonResult().setData(teacher);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult().setSuccess(false).setCode(500).setMessage("系统繁忙稍后再试!");
        }
    }

    @PostMapping("/add")
    public JsonResult addOrUpdate(@RequestBody Teacher teacher){
        try {
            if(teacher.getId() == null){
                iTeacherService.add(teacher);
                return new JsonResult().setMessage("新增成功");
            }else {
                iTeacherService.update(teacher);
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
            iTeacherService.delete(id);
            return new JsonResult().setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().setCode(500).setSuccess(false).setMessage("删除失败");
        }
    }

    @PostMapping("/query")
    public JsonResult queryPage(@RequestBody TeacherQuery query){
        PageList<Teacher> teacherPageList = iTeacherService.queryPage(query);
        return new JsonResult().setData(teacherPageList);
    }

    @PostMapping("/all")
    public JsonResult allData(){
        List<Teacher> teachers = iTeacherService.queryAll();
        return new JsonResult().setData(teachers);
    }

    @PatchMapping("/batch")
    public JsonResult batchRemove(@RequestBody List<Long> ids){
        try {
            iTeacherService.batchRemove(ids);
            return new JsonResult().setMessage("批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().setSuccess(false).setCode(500).setMessage("批量删除失败");
        }
    }
}
