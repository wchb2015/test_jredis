package org.jredisDemo.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class AbstractBaseRedisDao<K, V> {

    @Autowired
    protected RedisTemplate<K, V> redisTemplate;

    /**
     * 设置redisTemplate
     *
     * @param redisTemplate
     */
    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取 RedisSerializer
     *
     * @return
     */
    protected RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }
}
