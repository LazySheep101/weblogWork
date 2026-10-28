package com.lazydaze.weblog.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author: LazyDaze101
 * @Date: 2026/1/8 19:29
 * @Version: v1.0.0
 * @Description: 用于在非 Spring 管理的类中，获取 Spring 容器中的 Bean
 **/
@Component
public class SpringContext {

    private static ApplicationContext context;

    @Autowired
    public SpringContext(ApplicationContext applicationContext) {
        SpringContext.context = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
