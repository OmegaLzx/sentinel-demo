package pri.omega.demo1.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zexuan.Li  2022/3/14
 */
@Service
@Slf4j
public class AsyncService {
    @Async
    public void executeAsync(Runnable runnable) {
        log.info("thread name:{}", Thread.currentThread().getName());
        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
