package stu.back.org.query;

import lombok.Data;
import stu.back.basic.PageQuery;

import javax.management.Query;

@Data
public class TeacherQuery extends PageQuery {
    private String keyword;
}
