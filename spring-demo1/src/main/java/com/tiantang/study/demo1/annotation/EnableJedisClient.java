package com.tiantang.study.demo1.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liujinkun
 * @Title: EnableJedisClient
 * @Description: 开启jedis
 * @date 2019/9/20 8:45 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JedisClientImportRegistrar.class)
public @interface EnableJedisClient {

    String namespace() default "default";
}
