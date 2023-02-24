package stu.back;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("stu.back.*.mapper")
public class SMApplication {
    public static void main(String[] args){
        SpringApplication.run(SMApplication.class);
    }
}
