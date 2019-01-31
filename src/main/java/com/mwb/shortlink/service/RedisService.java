package com.mwb.shortlink.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Joey
 * Desc: redis服务
 * Date: 2016/9/6
 * Version: 1.0
 */
public interface RedisService {
    String set(String key, String value);

    String set(String key, String value, String nxxx, String expx, long time);

    String setex(String key, int seconds, String value);

    String get(String key);

    long del(String key);

    long expire(String key, int seconds);

    long incrBy(String key, long inc);

    long decrBy(String key, long dec);

    boolean exists(String key);

    boolean sismember(String key, String member);

    long hset(String key, String field, String value);

    String hget(String key, String field);

    long hdel(String key, String field);

    Map<String, String> hgetAll(String key);

    long hincrBy(String key, String field, long value);

    long hincr(String key, String field);

    String hmset(String key, Map<String, String> hash);

    boolean hexists(String key, String field);

    Long zadd(String key, Map<String, Double> scoreMembers);

    Set<String> zrange(String key, long start, long end);

    Long zcard(String key);

    Long pfadd(String key, String element);

    Long pfcount(String key);

    Boolean setbit(String key, long offset, boolean value);

    Boolean getbit(String key, long offset);

    String eval(String script, List<String> keys, List<String> args);

    void hincrPipled(String key, Map<String, Long> kv);

    Long setnx(String key, String val);

    Long ttl(String key);

    Long sadd(String key, String... members);

    Long scard(String key);

    Set<String> smembers(String key);

    Long llen(String key);

    String rpop(String key);

    boolean ping();
}
