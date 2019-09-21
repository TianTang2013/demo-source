package com.tiantang.study.demo1;

import com.tiantang.study.demo1.config.AppConfig;
import com.tiantang.study.demo1.jedis.JedisClusterClient;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;


/**
 * @author liujinkun
 * @Title: MainApplication
 * @Description: 启动类
 * @date 2019/9/20 8:13 PM
 */
public class MainApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("============ 测试单机版redis =================");
        Jedis jedis = applicationContext.getBean(Jedis.class);
        String key = "test:key1";
        String value = "value1";
        String writeResult = jedis.setex(key, 3600, value);
        System.out.println("向redis中写入数据，result = " + writeResult);
        String readResult = jedis.get(key);
        System.out.println("从redis中读取数据，result = " + readResult);

        // 测试jedis-cluster
        System.out.println("============= 测试集群版redis ================");
        JedisClusterClient jedisCluster = applicationContext.getBean(JedisClusterClient.class);
        String clusterKey = "test:{1000005}";
        String clusterValue = "" + System.currentTimeMillis();
        String res1 = jedisCluster.setex(clusterKey, 3600, clusterValue);
        System.out.println("向redis集群中写数据，result = " + res1);
        String res2 = jedisCluster.get(clusterKey);
        System.out.println("从redis集群中获取数据，result = " + res2);

        // 测试redis事务
        System.out.println("============= 测试redis集群事务 ==============");
        Jedis resource = jedisCluster.getResource(clusterKey);
        try {
            if(resource.watch(clusterKey).equalsIgnoreCase("OK")){
                Transaction transaction = resource.multi();
                String tmp = System.currentTimeMillis() + "";
                System.out.println("tmp = " + tmp);
                transaction.setex(clusterKey, 3600, tmp);
                List<Object> exec = transaction.exec();
                System.out.println(exec);
            }
        }finally {
           if(resource != null){
               resource.unwatch();
               resource.close();
           }
        }
        // 在此获取clusterKey的值，验证通过事务是否更新了缓存中的值
        System.out.println("after watch, result = " + jedisCluster.get(clusterKey));

    }
}
