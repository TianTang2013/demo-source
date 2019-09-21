package com.tiantang.study.demo1.annotation;

import com.tiantang.study.demo1.jedis.JedisClusterClient;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author liujinkun
 * @Title: JedisClusterClientImportRegistrar
 * @Description: 通过import注解添加JedisClusterClient
 * @date 2019/9/20 8:52 PM
 */
public class JedisClusterClientImportRegistrar implements ImportBeanDefinitionRegistrar {

    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 注册JedisCluster的后置处理器，用来填充属性
        BeanDefinitionBuilder postProcessorBuilder = BeanDefinitionBuilder.genericBeanDefinition(JedisClusterClientBeanPostProcessor.class);
        beanDefinitionRegistry.registerBeanDefinition(JedisClusterClientBeanPostProcessor.class.getSimpleName(),postProcessorBuilder.getBeanDefinition());

        // 获取namespace，用来指定JedisCluster的beanName
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableJedisClusterClient.class.getName()));
        String namespace = attributes.getString("namespace");

        // 注册jedisCluster
        BeanDefinitionBuilder clusterBuilder = BeanDefinitionBuilder.genericBeanDefinition(JedisClusterClient.class);
        beanDefinitionRegistry.registerBeanDefinition(namespace+JedisClusterClient.class.getSimpleName(),clusterBuilder.getBeanDefinition());

    }
}
