package stu.back.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stu.back.org.domain.Course;
import stu.back.org.service.ICourseService;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private ICourseService courseService;

    @PostMapping("/all")
    public List<Course> queryAll(){
        return courseService.loadAll();
    }

    @GetMapping("/{id}")
    public Course queryOne(@PathVariable Long id){
        return courseService.loadOne(id);
    }
}
