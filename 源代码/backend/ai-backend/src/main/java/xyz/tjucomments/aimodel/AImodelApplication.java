package xyz.tjucomments.aimodel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "xyz")
@MapperScan("xyz.mapper")
public class AImodelApplication {
    public static void main(String[] args) {
        SpringApplication.run(AImodelApplication.class, args);
    }
}
