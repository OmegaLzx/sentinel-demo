package pri.omega.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zexuan.Li  2022/3/14
 */
@SpringBootApplication
@EnableAsync
public class SentinelDemo1 {
    public static void main(String[] args) {
        SpringApplication.run(SentinelDemo1.class, args);
    }
}
