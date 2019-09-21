
> Redis与Spring整合的插件源码

### 注意(很重要)
1. 运行demo时，先修改/resources/config.properties文件中的配置。将redis的连接地址改为自己的地址。
2. `JedisClusterClient`类是自己定义的类，实现类redis中接口，重写了里面的方法，里面提供了redis操作的所有方法。但是由于方法太多，里面的方法的逻辑是空的，如果有需要，需要自己完善逻辑。操作很简单，只需要调用jedisCluster的对应方法即可，可参考已完善的方法示例。
3. 上面第2点很重要。不过不看上面第二点，可能测试时就会出现从redis中获取的值为`null`，或者往redis中写入值失败等现象。
4. redis的属性配置其实有很多，在demo里面只是配置了几个常见的值。如果有需要，可以自己完善这些。那么就需要自己需改`JedisClientBeanPostProcessor`和`JedisClusterClientBeanPostProcessor`类中的代码。
5. 有问题可以发送邮件到`13161637551@163.com` 或者关注下方微信公众号，私信给我。

### 属性配置规则
> 关于redis的属性配置，需要着重解释一下。
* 单机版redis的配置规则为：namespace + ".jedis." + "url|port"。namespace的值和@EnableJedisClient注解的使用有关。
```java
@EnableJedisClient(namespace = "demo")
public class AppConfig {
}
```
* 例如如上在@EnableJedisClient中指定了namespace属性的值为demo。那么redis的连接地址就应该配置为：demo.jedis.url = 你自己的ip地址。端口号应该配置为：demo.jedis.port = 你自己端口号

* 集群版的redis的配置规则为：namespace + ".jedis.cluster." + "address|minIdel|maxIdelmaxTotal"。namespace的值和@EnableJedisClusterClient注解的使用有关
```java
@EnableJedisClusterClient(namespace = "demo-cluster")
public class AppConfig {
}
```
* 例如如上在@EnableJedisClusterClient中指定了namespace属性的值为demo-cluster。那么redis集群的连接地址就应该配置为：demo-cluster..jedis.cluster.address = 你自己的ip地址。



> 扫描下方二维码或者微信搜索公众号`菜鸟飞呀飞`，即可关注微信公众号，阅读更多Spring源码分析文章

![微信公众号](https://img-blog.csdnimg.cn/20190917233245941.jpg)

