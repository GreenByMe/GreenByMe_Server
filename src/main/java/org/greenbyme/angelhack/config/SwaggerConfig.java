package org.greenbyme.angelhack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.greenbyme.angelhack"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
      return new ApiInfoBuilder()
                .title("GreenByMe REST API")
                .description("Green by me, Green by earth(us)")
                .version("2.2")
                .termsOfServiceUrl("Terms of service")
                .contact(new Contact("Tae Jeong, Da hoon", "https://github.com/GreenByMe/GreenByMe_Server", "xowjd41@naver.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();

    }
}
