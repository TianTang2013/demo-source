package com.tiantang.study.demo1.annotation;

import com.tiantang.study.demo1.jedis.JedisClusterClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author liujinkun
 * @Title: JedisClusterClientBeanPostProcessor
 * @Description: JedisClusterClient 后置处理器，主要是为JedisClusterClient设置从配置文件中获取到的属性，如连接地址等等
 * @date 2019/9/20 8:23 PM
 */
public class JedisClusterClientBeanPostProcessor implements BeanPostProcessor,EnvironmentAware {

    private static String JEDIS_ADDRESS_PREFIX = "jedis.cluster.address";
    private static String JEDIS_MIN_IDLE_PREFIX = "jedis.cluster.minIdle";
    private static String JEDIS_MAX_IDLE_PREFIX = "jedis.cluster.maxIdle";
    private static String JEDIS_MAX_TOTAL_PREFIX = "jedis.cluster.maxTotal";

    private Environment environment;

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof JedisClusterClient){
            String namespace = beanName.substring(0,beanName.indexOf(JedisClusterClient.class.getSimpleName()));
            String addressKey = namespace + "." + JEDIS_ADDRESS_PREFIX;
            String address = environment.getProperty(addressKey);
            Assert.isTrue(!StringUtils.isEmpty(address),String.format("%s can not be mull!!!! value = %s",addressKey,address));


            // 可以从配置文件中获取到redis的maxIdle、maxTotal、minIdle等配置，然后封装到poolConfig中
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            Integer minIdle = environment.getRequiredProperty(namespace + "." + JEDIS_MIN_IDLE_PREFIX, Integer.class);
            Integer maxIdle = environment.getRequiredProperty(namespace + "." + JEDIS_MAX_IDLE_PREFIX, Integer.class);
            Integer maxTotal = environment.getRequiredProperty(namespace + "." + JEDIS_MAX_TOTAL_PREFIX, Integer.class);
            poolConfig.setMinIdle(minIdle);
            poolConfig.setMaxIdle(maxIdle);
            poolConfig.setMaxTotal(maxTotal);
            // TODO 还有其他的一些属性，也可以在这儿设置
            JedisClusterClient jedisClusterClient = new JedisClusterClient(address,poolConfig);
            return jedisClusterClient;
        }
        return bean;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
