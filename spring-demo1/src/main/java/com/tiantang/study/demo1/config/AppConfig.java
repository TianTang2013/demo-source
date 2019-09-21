package com.tiantang.study.demo1.config;

import com.tiantang.study.demo1.annotation.EnableJedisClient;
import com.tiantang.study.demo1.annotation.EnableJedisClusterClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author liujinkun
 * @Title: AppConfig
 * @Description: 配置类
 * @date 2019/9/20 8:26 PM
 */
@Configuration
@EnableJedisClient(namespace = "demo")
@EnableJedisClusterClient(namespace = "demo-cluster")
@PropertySource("config.properties")
public class AppConfig {
}
