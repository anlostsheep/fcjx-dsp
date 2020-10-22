package com.richstonedt.fcjx.advertisement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * <b><code>Swagger2Config</code></b>
 * <p/>
 * Swagger 接口文档配置类
 * <p/>
 * <b>Creation Time:</b> 2020/4/1 23:42.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Configuration
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.richstonedt.smartpush.cmgddr.dsp.be.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DSP 系统 API 文档")
                .description("DSP 系统 API 接口文档, https://app.hivedata.express.com:8443/dsp/swagger-ui.html")
                .termsOfServiceUrl("http://127.0.0.1:9443/swagger-ui.html")
                .version("0.1.0")
                .build();
    }
}
