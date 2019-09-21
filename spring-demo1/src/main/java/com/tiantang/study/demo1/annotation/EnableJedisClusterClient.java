package com.tiantang.study.demo1.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liujinkun
 * @Title: EnableJedisClusterClient
 * @Description: 开启JedisCluster的注解
 * @date 2019/9/20 8:14 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JedisClusterClientImportRegistrar.class)
public @interface EnableJedisClusterClient {

    String namespace() default "default";
}
