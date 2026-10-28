package com.lazydaze.weblog.admin.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-08-16 7:53
 * @description: Knife4j 配置
 **/
@Configuration
@Profile("dev")
public class Knife4jAdminConfig {

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("Admin 后台接口")
                .packagesToScan("com.lazydaze.weblog.admin.controller")
                .build();
    }
}
