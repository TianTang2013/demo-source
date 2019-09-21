package com.tiantang.study.demo1.jedis;

import redis.clients.jedis.*;
import redis.clients.jedis.commands.JedisClusterCommands;
import redis.clients.jedis.commands.JedisClusterScriptingCommands;
import redis.clients.jedis.commands.MultiKeyJedisClusterCommands;
import redis.clients.jedis.params.GeoRadiusParam;
import redis.clients.jedis.params.SetParams;
import redis.clients.jedis.params.ZAddParams;
import redis.clients.jedis.params.ZIncrByParams;
import redis.clients.jedis.util.JedisClusterCRC16;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liujinkun
 * @Title: JedisClusterClient
 * @Description: Redis集群客户端
 * @date 2019/9/21 11:28 AM
 */
public class JedisClusterClient implements JedisClusterCommands,
        MultiKeyJedisClusterCommands, JedisClusterScriptingCommands {

    private JedisCluster jedisCluster;
    private JedisPoolConfig jedisPoolConfig;
    private JedisSlotBasedConnectionHandler handler;
    private final int defaultConnectTimeout = 2000;
    private final int defaultConnectMaxAttempts = 20;

    /**
     * 为什么在这里提供一个无参构造器呢？
     * 因为在Spring在实例化bean时，是推断出类的构造器，然后根据类的构造器来反射创建bean，
     * 如果不提供默认的无参构造器，那么Spring就会使用JedisClusterClient的有参构造器。
     * 然而，有参构造器中需要namespace,address,poolConfig等参数。
     * 此时，Spring就会从Spring容器中根据参数的类型去获取Bean，获取不到就会报错。
     * 所以这里特意提供了一个无参构造器
     */
    public JedisClusterClient(){}

    public JedisClusterClient(String address, JedisPoolConfig poolConfig) {
        this.jedisPoolConfig = poolConfig;
        // 解析redis配置的地址
        String[] addressArr = address.split(",");
        Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>(addressArr.length);
        for (String url : addressArr) {
            String[] split = url.split(":");
            String host = split[0];
            int port = Integer.parseInt(split[1]);
            hostAndPortSet.add(new HostAndPort(host,port));
        }

        this.jedisCluster = new JedisCluster(hostAndPortSet,defaultConnectTimeout,defaultConnectMaxAttempts,jedisPoolConfig);

        try {
            Field connectionHandlerField = BinaryJedisCluster.class.getDeclaredField("connectionHandler");
            connectionHandlerField.setAccessible(true);
            this.handler  = (JedisSlotBasedConnectionHandler) connectionHandlerField.get(jedisCluster);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在redis集群中，根据key计算出key在哪一个slot，然后获取该slot所属于哪一个台redis机器
     * @param key
     * @return
     */
    public Jedis getResource(String key){
        int slot = JedisClusterCRC16.getSlot(key);
        return handler.getConnectionFromSlot(slot);
    }


    public String set(String key, String value) {
        return jedisCluster.set(key,value);
    }

    public String set(String key, String value, SetParams params) {
        return jedisCluster.set(key, value, params);
    }

    public String get(String key) {
        return jedisCluster.get(key);
    }

    public Boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    public Long persist(String key) {
        return jedisCluster.persist(key);
    }

    public String type(String key) {
        return jedisCluster.type(key);
    }

    public byte[] dump(String key) {
        return jedisCluster.dump(key);
    }

    public String restore(String key, int ttl, byte[] serializedValue) {
        return jedisCluster.restore(key, ttl, serializedValue);
    }

    public Long expire(String key, int seconds) {
        return jedisCluster.expire(key,seconds);
    }

    public Long pexpire(String key, long milliseconds) {
        return jedisCluster.pexpire(key,milliseconds);
    }

    public Long expireAt(String key, long unixTime) {
        return jedisCluster.expireAt(key, unixTime);
    }

    public Long pexpireAt(String key, long millisecondsTimestamp) {
        return jedisCluster.pexpireAt(key, millisecondsTimestamp);
    }

    public Long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    public Long pttl(String key) {
        return jedisCluster.pttl(key);
    }

    public Long touch(String key) {
        return jedisCluster.touch(key);
    }

    public Boolean setbit(String key, long offset, boolean value) {
        return jedisCluster.setbit(key, offset, value);
    }

    public Boolean setbit(String key, long offset, String value) {
        return jedisCluster.setbit(key, offset, value);
    }

    public Boolean getbit(String key, long offset) {
        return jedisCluster.getbit(key, offset);
    }

    public Long setrange(String key, long offset, String value) {
        return jedisCluster.setrange(key, offset, value);
    }

    public String getrange(String key, long startOffset, long endOffset) {
        return jedisCluster.getrange(key, startOffset, endOffset);
    }

    public String getSet(String key, String value) {
        return jedisCluster.getSet(key, value);
    }

    public Long setnx(String key, String value) {
        return jedisCluster.setnx(key, value);
    }

    public String setex(String key, int seconds, String value) {
        return jedisCluster.setex(key, seconds, value);
    }

    public String psetex(String key, long milliseconds, String value) {
        return jedisCluster.psetex(key, milliseconds, value);
    }

    public Long decrBy(String key, long decrement) {
        return jedisCluster.decrBy(key, decrement);
    }

    public Long decr(String key) {
        return jedisCluster.decr(key);
    }

    public Long incrBy(String key, long increment) {
        return jedisCluster.incrBy(key, increment);
    }

    public Double incrByFloat(String key, double increment) {
        return jedisCluster.incrByFloat(key, increment);
    }

    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

    public Long append(String key, String value) {
        return jedisCluster.append(key, value);
    }

    public String substr(String key, int start, int end) {
        return jedisCluster.substr(key, start, end);
    }

    public Long hset(String key, String field, String value) {
        return jedisCluster.hset(key, field, value);
    }

    public Long hset(String key, Map<String, String> hash) {
        return jedisCluster.hset(key, hash);
    }

    public String hget(String key, String field) {
        return jedisCluster.hget(key, field);
    }

    public Long hsetnx(String key, String field, String value) {
        return jedisCluster.hsetnx(key, field, value);
    }

    public String hmset(String key, Map<String, String> hash) {
        return jedisCluster.hmset(key, hash);
    }

    public List<String> hmget(String key, String... fields) {
        return jedisCluster.hmget(key, fields);
    }

    public Long hincrBy(String key, String field, long value) {
        return jedisCluster.hincrBy(key, field, value);
    }

    public Boolean hexists(String key, String field) {
        return jedisCluster.hexists(key, field);
    }

    public Long hdel(String key, String... field) {
        return jedisCluster.hdel(key, field);
    }

    public Long hlen(String key) {
        return jedisCluster.hlen(key);
    }

    public Set<String> hkeys(String key) {
        return jedisCluster.hkeys(key);
    }

    public List<String> hvals(String key) {
        return jedisCluster.hvals(key);
    }

    public Map<String, String> hgetAll(String key) {
        return jedisCluster.hgetAll(key);
    }

    public Long rpush(String key, String... string) {
        return null;
    }

    public Long lpush(String key, String... string) {
        return jedisCluster.rpush(key, string);
    }

    public Long llen(String key) {
        return jedisCluster.llen(key);
    }

    public List<String> lrange(String key, long start, long stop) {
        return jedisCluster.lrange(key, start, stop);
    }

    public String ltrim(String key, long start, long stop) {
        return jedisCluster.ltrim(key, start, stop);
    }

    public String lindex(String key, long index) {
        return jedisCluster.lindex(key, index);
    }

    public String lset(String key, long index, String value) {
        return jedisCluster.lset(key, index, value);
    }

    public Long lrem(String key, long count, String value) {
        return jedisCluster.lrem(key, count, value);
    }

    public String lpop(String key) {
        return jedisCluster.lpop(key);
    }

    public String rpop(String key) {
        return jedisCluster.rpop(key);
    }

    // ============== 下面的方法就不填充的了，有需要的话，自己填充即可。全部都是直接调用jedisCluster的方法即可 ========
    public Long sadd(String key, String... member) {
        return null;
    }

    public Set<String> smembers(String key) {
        return null;
    }

    public Long srem(String key, String... member) {
        return null;
    }

    public String spop(String key) {
        return null;
    }

    public Set<String> spop(String key, long count) {
        return null;
    }

    public Long scard(String key) {
        return null;
    }

    public Boolean sismember(String key, String member) {
        return null;
    }

    public String srandmember(String key) {
        return null;
    }

    public List<String> srandmember(String key, int count) {
        return null;
    }

    public Long strlen(String key) {
        return null;
    }

    public Long zadd(String key, double score, String member) {
        return null;
    }

    public Long zadd(String key, double score, String member, ZAddParams params) {
        return null;
    }

    public Long zadd(String key, Map<String, Double> scoreMembers) {
        return null;
    }

    public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        return null;
    }

    public Set<String> zrange(String key, long start, long stop) {
        return null;
    }

    public Long zrem(String key, String... members) {
        return null;
    }

    public Double zincrby(String key, double increment, String member) {
        return null;
    }

    public Double zincrby(String key, double increment, String member, ZIncrByParams params) {
        return null;
    }

    public Long zrank(String key, String member) {
        return null;
    }

    public Long zrevrank(String key, String member) {
        return null;
    }

    public Set<String> zrevrange(String key, long start, long stop) {
        return null;
    }

    public Set<Tuple> zrangeWithScores(String key, long start, long stop) {
        return null;
    }

    public Set<Tuple> zrevrangeWithScores(String key, long start, long stop) {
        return null;
    }

    public Long zcard(String key) {
        return null;
    }

    public Double zscore(String key, String member) {
        return null;
    }

    public List<String> sort(String key) {
        return null;
    }

    public List<String> sort(String key, SortingParams sortingParameters) {
        return null;
    }

    public Long zcount(String key, double min, double max) {
        return null;
    }

    public Long zcount(String key, String min, String max) {
        return null;
    }

    public Set<String> zrangeByScore(String key, double min, double max) {
        return null;
    }

    public Set<String> zrangeByScore(String key, String min, String max) {
        return null;
    }

    public Set<String> zrevrangeByScore(String key, double max, double min) {
        return null;
    }

    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return null;
    }

    public Set<String> zrevrangeByScore(String key, String max, String min) {
        return null;
    }

    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        return null;
    }

    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return null;
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return null;
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return null;
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return null;
    }

    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        return null;
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        return null;
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        return null;
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        return null;
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        return null;
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        return null;
    }

    public Long zremrangeByRank(String key, long start, long stop) {
        return null;
    }

    public Long zremrangeByScore(String key, double min, double max) {
        return null;
    }

    public Long zremrangeByScore(String key, String min, String max) {
        return null;
    }

    public Long zlexcount(String key, String min, String max) {
        return null;
    }

    public Set<String> zrangeByLex(String key, String min, String max) {
        return null;
    }

    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        return null;
    }

    public Set<String> zrevrangeByLex(String key, String max, String min) {
        return null;
    }

    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        return null;
    }

    public Long zremrangeByLex(String key, String min, String max) {
        return null;
    }

    public Long linsert(String key, ListPosition where, String pivot, String value) {
        return null;
    }

    public Long lpushx(String key, String... string) {
        return null;
    }

    public Long rpushx(String key, String... string) {
        return null;
    }

    public List<String> blpop(int timeout, String key) {
        return null;
    }

    public List<String> brpop(int timeout, String key) {
        return null;
    }

    public Long del(String key) {
        return null;
    }

    public Long unlink(String key) {
        return null;
    }

    public String echo(String string) {
        return null;
    }

    public Long bitcount(String key) {
        return null;
    }

    public Long bitcount(String key, long start, long end) {
        return null;
    }

    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        return null;
    }

    public ScanResult<String> sscan(String key, String cursor) {
        return null;
    }

    public ScanResult<Tuple> zscan(String key, String cursor) {
        return null;
    }

    public Long pfadd(String key, String... elements) {
        return null;
    }

    public long pfcount(String key) {
        return 0;
    }

    public Long geoadd(String key, double longitude, double latitude, String member) {
        return null;
    }

    public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        return null;
    }

    public Double geodist(String key, String member1, String member2) {
        return null;
    }

    public Double geodist(String key, String member1, String member2, GeoUnit unit) {
        return null;
    }

    public List<String> geohash(String key, String... members) {
        return null;
    }

    public List<GeoCoordinate> geopos(String key, String... members) {
        return null;
    }

    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        return null;
    }

    public List<GeoRadiusResponse> georadiusReadonly(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        return null;
    }

    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return null;
    }

    public List<GeoRadiusResponse> georadiusReadonly(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return null;
    }

    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
        return null;
    }

    public List<GeoRadiusResponse> georadiusByMemberReadonly(String key, String member, double radius, GeoUnit unit) {
        return null;
    }

    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return null;
    }

    public List<GeoRadiusResponse> georadiusByMemberReadonly(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return null;
    }

    public List<Long> bitfield(String key, String... arguments) {
        return null;
    }

    public Long hstrlen(String key, String field) {
        return null;
    }

    public StreamEntryID xadd(String key, StreamEntryID id, Map<String, String> hash) {
        return null;
    }

    public StreamEntryID xadd(String key, StreamEntryID id, Map<String, String> hash, long maxLen, boolean approximateLength) {
        return null;
    }

    public Long xlen(String key) {
        return null;
    }

    public List<StreamEntry> xrange(String key, StreamEntryID start, StreamEntryID end, int count) {
        return null;
    }

    public List<StreamEntry> xrevrange(String key, StreamEntryID end, StreamEntryID start, int count) {
        return null;
    }

    public List<Map.Entry<String, List<StreamEntry>>> xread(int count, long block, Map.Entry<String, StreamEntryID>... streams) {
        return null;
    }

    public Long xack(String key, String group, StreamEntryID... ids) {
        return null;
    }

    public String xgroupCreate(String key, String groupname, StreamEntryID id, boolean makeStream) {
        return null;
    }

    public String xgroupSetID(String key, String groupname, StreamEntryID id) {
        return null;
    }

    public Long xgroupDestroy(String key, String groupname) {
        return null;
    }

    public String xgroupDelConsumer(String key, String groupname, String consumername) {
        return null;
    }

    public List<Map.Entry<String, List<StreamEntry>>> xreadGroup(String groupname, String consumer, int count, long block, boolean noAck, Map.Entry<String, StreamEntryID>... streams) {
        return null;
    }

    public List<StreamPendingEntry> xpending(String key, String groupname, StreamEntryID start, StreamEntryID end, int count, String consumername) {
        return null;
    }

    public Long xdel(String key, StreamEntryID... ids) {
        return null;
    }

    public Long xtrim(String key, long maxLen, boolean approximateLength) {
        return null;
    }

    public List<StreamEntry> xclaim(String key, String group, String consumername, long minIdleTime, long newIdleTime, int retries, boolean force, StreamEntryID... ids) {
        return null;
    }

    public Long waitReplicas(String key, int replicas, long timeout) {
        return null;
    }

    public Object eval(String script, int keyCount, String... params) {
        return null;
    }

    public Object eval(String script, List<String> keys, List<String> args) {
        return null;
    }

    public Object eval(String script, String sampleKey) {
        return null;
    }

    public Object evalsha(String sha1, String sampleKey) {
        return null;
    }

    public Object evalsha(String sha1, List<String> keys, List<String> args) {
        return null;
    }

    public Object evalsha(String sha1, int keyCount, String... params) {
        return null;
    }

    public Boolean scriptExists(String sha1, String sampleKey) {
        return null;
    }

    public List<Boolean> scriptExists(String sampleKey, String... sha1) {
        return null;
    }

    public String scriptLoad(String script, String sampleKey) {
        return null;
    }

    public String scriptFlush(String sampleKey) {
        return null;
    }

    public String scriptKill(String sampleKey) {
        return null;
    }

    public Long del(String... keys) {
        return null;
    }

    public Long unlink(String... keys) {
        return null;
    }

    public Long exists(String... keys) {
        return null;
    }

    public List<String> blpop(int timeout, String... keys) {
        return null;
    }

    public List<String> brpop(int timeout, String... keys) {
        return null;
    }

    public List<String> mget(String... keys) {
        return jedisCluster.mget(keys);
    }

    public String mset(String... keysvalues) {
        return jedisCluster.mset(keysvalues);
    }

    public Long msetnx(String... keysvalues) {
        return jedisCluster.msetnx(keysvalues);
    }

    public String rename(String oldkey, String newkey) {
        return jedisCluster.rename(oldkey, newkey);
    }

    public Long renamenx(String oldkey, String newkey) {
        return jedisCluster.renamenx(oldkey, newkey);
    }

    public String rpoplpush(String srckey, String dstkey) {
        return jedisCluster.rpoplpush(srckey, dstkey);
    }

    public Set<String> sdiff(String... keys) {
        return jedisCluster.sdiff(keys);
    }

    public Long sdiffstore(String dstkey, String... keys) {
        return jedisCluster.sdiffstore(dstkey, keys);
    }

    public Set<String> sinter(String... keys) {
        return jedisCluster.sinter(keys);
    }

    public Long sinterstore(String dstkey, String... keys) {
        return jedisCluster.sinterstore(dstkey,keys);
    }

    public Long smove(String srckey, String dstkey, String member) {
        return null;
    }

    public Long sort(String key, SortingParams sortingParameters, String dstkey) {
        return null;
    }

    public Long sort(String key, String dstkey) {
        return null;
    }

    public Set<String> sunion(String... keys) {
        return null;
    }

    public Long sunionstore(String dstkey, String... keys) {
        return null;
    }

    public Long zinterstore(String dstkey, String... sets) {
        return null;
    }

    public Long zinterstore(String dstkey, ZParams params, String... sets) {
        return null;
    }

    public Long zunionstore(String dstkey, String... sets) {
        return null;
    }

    public Long zunionstore(String dstkey, ZParams params, String... sets) {
        return null;
    }

    public String brpoplpush(String source, String destination, int timeout) {
        return null;
    }

    public Long publish(String channel, String message) {
        return null;
    }

    public void subscribe(JedisPubSub jedisPubSub, String... channels) {

    }

    public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {

    }

    public Long bitop(BitOP op, String destKey, String... srcKeys) {
        return null;
    }

    public String pfmerge(String destkey, String... sourcekeys) {
        return null;
    }

    public long pfcount(String... keys) {
        return 0;
    }

    public Long touch(String... keys) {
        return null;
    }

    public ScanResult<String> scan(String cursor, ScanParams params) {
        return null;
    }

    public Set<String> keys(String pattern) {
        return null;
    }
}
