package stu.back.basic;

import lombok.Data;

@Data
public class PageQuery {
    private Integer currentPage = 1;
    private Integer pageSize = 10;
    public Integer getPage(){
        return (this.currentPage - 1)*this.pageSize;
    }
}
