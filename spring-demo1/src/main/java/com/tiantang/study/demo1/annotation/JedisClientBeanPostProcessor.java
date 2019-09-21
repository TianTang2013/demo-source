package com.tiantang.study.demo1.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author liujinkun
 * @Title: JedisClientBeanPostProcessor
 * @Description: 对JedisClient进行后置处理，主要是为JedisClient设置连接地址等属性
 * @date 2019/9/20 8:46 PM
 */
public class JedisClientBeanPostProcessor implements BeanPostProcessor,EnvironmentAware {

    private static String JEDIS_ADDRESS_PREFIX = "jedis.url";
    private static String JEDIS_PORT_PREFIX = "jedis.port";

    private Environment environment;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof Jedis){
            // 通过beanName获取到namespace
            String prefix = beanName.substring(0, beanName.indexOf(Jedis.class.getSimpleName()));
            // 获取配置文件的配置，配置的规则为： namespace + "." + jedis. + address|port
            // 示例：demo.jedis.url.address = 127.0.0.1
            String addressKey = prefix + "." + JEDIS_ADDRESS_PREFIX;
            String address = environment.getProperty(addressKey);
            Assert.isTrue(!StringUtils.isEmpty(address),String.format("%s can not be null!!! value = %s",addressKey,address));

            String portKey = prefix + "." + JEDIS_PORT_PREFIX;
            String port = environment.getProperty(portKey);
            Assert.isTrue(!StringUtils.isEmpty(port),String.format("%s can not be null!!! value = = %s",portKey,port));

            // 如果有需要，可以在从配置中添加redis的配置，然后在此处获取即可。
            JedisPool jedisPool = new JedisPool(address,Integer.parseInt(port));
            ((Jedis)bean).setDataSource(jedisPool);
        }
        return bean;
    }
}
