package com.tiantang.study.demo1.annotation;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * @author liujinkun
 * @Title: JedisClientImportRegistrar
 * @Description: 通过import注解添加JedisClient
 * @date 2019/9/20 8:50 PM
 */
public class JedisClientImportRegistrar implements ImportBeanDefinitionRegistrar {

    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(EnableJedisClient.class.getName());
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationAttributes);
        String namespace = attributes.getString("namespace");
        // 创建jedis的BeanDefinition,然后注册进容器中，beanName为namespace + "Jedis"
        BeanDefinitionBuilder jedisBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Jedis.class);
        AbstractBeanDefinition jedisBeanDefinition = jedisBeanDefinitionBuilder.getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition(namespace+Jedis.class.getSimpleName(),jedisBeanDefinition);

        // 向容器注册一个Jedis的后置处理器,这是为了让后置处理器为Jedis的属性赋值
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(JedisClientBeanPostProcessor.class);
        beanDefinitionRegistry.registerBeanDefinition(JedisClientBeanPostProcessor.class.getSimpleName(),beanDefinitionBuilder.getBeanDefinition());
    }
}
