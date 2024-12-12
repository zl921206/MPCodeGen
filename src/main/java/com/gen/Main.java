package com.gen;

import com.gen.config.Config;
import com.gen.manager.GenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhanglei_18
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Config.class);
        ctx.refresh();

        GenManager bean = ctx.getBean(GenManager.class);
        bean.generate();
        log.info("生成结束，genCode.zip为生成文件");
    }
}
