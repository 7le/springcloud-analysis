package com.cloud.ad.swagger;

import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author 7le
 * @since 2017-05-17
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket createRestApi() {
        java.util.function.Predicate<RequestHandler> predicate = input -> {
            Class<?> declaringClass = input.declaringClass();
            // 排除
            if (declaringClass == BasicErrorController.class) {
                return false;
            }
            // 被注解的类
            if (declaringClass.isAnnotationPresent(RestController.class)) {
                return true;
            }
            // 被注解的方法
            return input.isAnnotatedWith(ResponseBody.class);
        };
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(predicate::test)
                .paths(or(regex("/api/.*"),regex("/hi")))
                .build();
    }

    @Bean
    public Docket createRestWeb() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar.modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        ticketPar.name("Authorization").description("user token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("web")
                .apiInfo(webInfo())
                .useDefaultResponseMessages(false)
                .genericModelSubstitutes(DeferredResult.class)
                .forCodeGeneration(false)
                .pathMapping("/")
                .select()
                .paths(or(regex("/web/.*")))
                .build()
                .globalOperationParameters(pars);

    }

    private ApiInfo webInfo() {
        return new ApiInfoBuilder()
                .title("ymsg")
                .description("ymsg")
                .version("1.0.0")
                .termsOfServiceUrl("NO terms of service")
                .contact(new Contact("7le", "https://github.com/7le", "silk.heqian@gmail.com"))
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ymsg")
                .description("ymsg")
                .version("1.0.0")
                .contact(new Contact("7le", "https://github.com/7le", "silk.heqian@gmail.com"))
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }
}
