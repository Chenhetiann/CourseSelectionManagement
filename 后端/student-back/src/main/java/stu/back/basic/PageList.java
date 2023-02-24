package stu.back.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageList<T> {
    private Long total = 0L ;
    private List<T> rows = new ArrayList<>();
}
