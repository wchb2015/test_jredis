package org.jredisDemo.dao;

import org.jredisDemo.dao.cache.AbstractBaseRedisDao;
import org.jredisDemo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractBaseRedisDao<String, User> implements IUserDao {
    public boolean add(final User user) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(user.getUserId() + "");
                byte[] name = serializer.serialize(user.getUserName());
                return connection.setNX(key, name);
            }
        });
        return result;
    }


    public boolean add(final List<User> list) {
        Assert.notEmpty(list);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                for (User user : list) {
                    byte[] key = serializer.serialize(user.getUserId() + "");
                    byte[] name = serializer.serialize(user.getUserName());
                    connection.setNX(key, name);
                }
                return true;
            }
        }, false, true);
        return result;
    }


    public int delete(String key) {
        List<String> list = new ArrayList<String>();
        list.add(key);
        delete(list);
        return 1;
    }


    public int delete(List<String> keys) {
        redisTemplate.delete(keys);
        return 1;
    }


    public boolean update(final User user) {
        String key = user.getUserId() + "";
        if (get(key) == null) {
            throw new NullPointerException("数据行不存在, key = " + key);
        }
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(user.getUserId() + "");
                byte[] name = serializer.serialize(user.getUserName());
                connection.set(key, name);
                return true;
            }
        });
        return result;
    }

    /**
     * 通过key获取
     *
     * @param keyId
     * @return
     */
    public User get(final String keyId) {
        User result = redisTemplate.execute(new RedisCallback<User>() {
            public User doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(keyId);
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                String name = serializer.deserialize(value);
//                return new User(keyId, name, null);
                return null;
            }
        });
        return result;
    }
}
