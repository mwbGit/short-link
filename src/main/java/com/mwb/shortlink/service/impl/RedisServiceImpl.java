package com.mwb.shortlink.service.impl;

import com.mwb.shortlink.service.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Joey
 * Desc: redis服务实现
 * Date: 2016/9/6
 * Version: 1.0
 */
@Service
public class RedisServiceImpl implements RedisService {
    private static final Logger logger = LogManager.getLogger(RedisServiceImpl.class);

    @Qualifier("jedisPool")
    @Autowired
    private JedisPool jedisPool;

    @Override
    public String set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key, value);
        }
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, long time){
        try (Jedis jedis = jedisPool.getResource()){
            return jedis.set(key, value, nxxx, expx, time);
        }
    }

    @Override
    public String setex(String key, int seconds, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setex(key, seconds, value);
        }
    }

    @Override
    public String get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public long del(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.del(key);
        }
    }

    @Override
    public long expire(String key, int seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.expire(key, seconds);
        }
    }

    @Override
    public long incrBy(String key, long inc) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.incrBy(key, inc);
        }
    }

    @Override
    public long decrBy(String key, long dec) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.decrBy(key, dec);
        }
    }

    @Override
    public boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        }
    }

    @Override
    public boolean sismember(String key, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sismember(key, member);
        }
    }

    @Override
    public long hset(String key, String field, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hset(key, field, value);
        }
    }

    @Override
    public String hget(String key, String field) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hget(key, field);
        }
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hgetAll(key);
        }
    }

    @Override
    public long hincrBy(String key, String field, long value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hincrBy(key, field, value);
        }
    }

    @Override
    public long hincr(String key, String field) {
        return hincrBy(key, field, 1L);
    }

    @Override
    public long hdel(String key, String field) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hdel(key, field);
        }
    }

    @Override
    public boolean hexists(String key, String field) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hexists(key, field);
        }
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hmset(key, hash);
        }
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zadd(key, scoreMembers);
        }
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrange(key, start, end);
        }
    }

    @Override
    public Long zcard(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zcard(key);
        }
    }

    @Override
    public Long pfadd(String key, String element) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.pfadd(key, element);
        }
    }

    @Override
    public Long pfcount(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.pfcount(key);
        }
    }

    @Override
    public Boolean setbit(String key, long offset, boolean value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setbit(key, offset, value);
        }
    }

    @Override
    public Boolean getbit(String key, long offset) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.getbit(key, offset);
        }
    }

    @Override
    public String eval(String script, List<String> keys, List<String> args) {
        try (Jedis jedis = jedisPool.getResource()) {
            return (String)jedis.eval(script, keys, args);
        }
    }

    @Override
    public void hincrPipled(String key, Map<String, Long> kv) {
        if(null != kv && !kv.isEmpty()) {
            try (Jedis jedis = jedisPool.getResource()) {
                Pipeline pl = jedis.pipelined();

                Iterator<Map.Entry<String, Long>> iter = kv.entrySet().iterator();
                while(iter.hasNext()) {
                    Map.Entry<String, Long> entry = iter.next();
                    pl.hincrBy(key, entry.getKey(), entry.getValue());
                }

                pl.sync();
            }
        }
    }

    @Override
    public Long setnx(String key, String val) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setnx(key, val);
        }
    }

    @Override
    public Long ttl(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.ttl(key);
        }
    }

    @Override
    public Long sadd(String key,String... members){
        try (Jedis jedis = jedisPool.getResource()){
            return jedis.sadd(key,members);
        }
    }

    @Override
    public Long scard(String key) {
        try(Jedis jedis = jedisPool.getResource()){
            return jedis.scard(key);
        }
    }

    @Override
    public Set<String> smembers(String key) {
        try(Jedis jedis = jedisPool.getResource()){
            return jedis.smembers(key);
        }
    }

    @Override
    public Long llen(String key) {
        try(Jedis jedis = jedisPool.getResource()){
            return jedis.llen(key);
        }
    }
    @Override
    public String rpop(String key) {
        try(Jedis jedis = jedisPool.getResource()){
            return jedis.rpop(key);
        }
    }
    @Override
    public boolean ping() {
        try (Jedis jedis = jedisPool.getResource()) {
            String result = jedis.ping();
            return "PONG".equalsIgnoreCase(result) ? true : false;
        }
    }
}
