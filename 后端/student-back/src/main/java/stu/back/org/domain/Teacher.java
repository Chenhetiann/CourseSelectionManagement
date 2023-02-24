package stu.back.org.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private Long id;
    private String tname;
    private Course course;
    private Long courseId;
    private Integer tstate;
    private String classes;
    private String mode;
}
