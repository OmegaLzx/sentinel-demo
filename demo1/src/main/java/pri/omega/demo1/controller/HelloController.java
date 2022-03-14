package pri.omega.demo1.controller;

import com.alibaba.csp.sentinel.AsyncEntry;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pri.omega.demo1.service.AsyncService;

/**
 * @author zexuan.Li  2022/3/14
 */
@RestController
@Slf4j
public class HelloController {
    @Autowired
    private AsyncService asyncService;


    @GetMapping("/hello")
    public String hello() {
        try (Entry entry = SphU.entry("hello")) {
            return "hello";
        } catch (BlockException ex) {
            log.error("block", ex);
            return "block";
        }
    }

    @GetMapping("/hello2")
    public String hello2() {
        if (SphO.entry("hello2")) {
            try {
                log.info("hello2");
                return "hello2";
            } finally {
                SphO.exit();
            }
        } else {
            log.info("block");
            return "block";
        }
    }

    @GetMapping("/hello3")
    public String hello3() {
        try {
            AsyncEntry entry = SphU.asyncEntry("hello3");
            asyncService.executeAsync(() -> {
                try {
                    log.info("ThreadName:{}", Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (entry != null) {
                        entry.exit();
                    }
                }
            });
            return "hello3";
        } catch (BlockException ex) {
            log.info("block");
            return "block";
        }
    }

//    @PostConstruct
//    public void initFlowRule() {
//        List<FlowRule> rules = new ArrayList<>();
//
//        FlowRule rule = new FlowRule();
//        rule.setResource("hello");
//        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule.setCount(1);
//        rules.add(rule);
//        FlowRuleManager.loadRules(rules);
//    }
}
