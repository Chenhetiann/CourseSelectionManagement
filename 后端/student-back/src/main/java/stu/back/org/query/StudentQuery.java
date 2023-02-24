package stu.back.org.query;

import lombok.Data;
import stu.back.basic.PageQuery;

@Data
public class StudentQuery extends PageQuery{
    private String keyword;
}
