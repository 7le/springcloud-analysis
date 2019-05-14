package com.cloud.common.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * advice 扫描器
 *
 * @author 7le
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({AdviceRegistrar.class})
public @interface AdviceScan {

    String[] value() default {};

    String[] basePackages() default {};
}
