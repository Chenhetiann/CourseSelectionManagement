package stu.back.org.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Long id;
    private String sno;
    private String sname;
    private Integer sex;
    private String grade;
    private Integer state;
    private Course course;
    private Long courseId;

}
