package com.lazydaze.weblog.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
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
public class Knife4jConfig {

    @Bean
    public GroupedOpenApi webApi() {
        return GroupedOpenApi.builder()
                .group("Web 前台接口")
                .packagesToScan("com.lazydaze.weblog.web.controller")
                .build();
    }

    @Bean
    public OpenAPI webOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Weblog 博客前台接口文档")
                        .description("Weblog 是一款由 Spring Boot + Vue 3 开发的前后端分离博客。")
                        .version("1.0")
                        .contact(new Contact()
                                .name("LazyDaze101")
                                .url("https://www.lazydaze101.dev")
                                .email("871361652@qq.com")));
    }
}
