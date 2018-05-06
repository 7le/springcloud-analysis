package com.cloud.common.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 扫描注册拦截器
 *
 * @author 7le
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({InterceptorRegistrar.class})
public @interface InterceptorScan {

    String[] value() default {};

    String[] basePackages() default {};
}
