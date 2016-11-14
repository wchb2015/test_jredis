package org.jredisDemo.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jredisDemo.model.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class UserRedisDao {

    //private final Log LOG = LogFactory.getLog(this.getClass());
    private static final Logger LOG = LoggerFactory.getLogger(UserRedisDao.class);
    private final JedisPool jedisPool;

    private RuntimeSchema<User> schema = RuntimeSchema.createFrom(User.class);

    public UserRedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    public User getUser(long userId) {
        //redis逻辑操作
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "user:" + userId;
                //并没有实现内部序列化操作
                //get:byte[]->反序列化->Object(User)
                //采用自定义序列化

                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    //空对象
                    User user = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, user, schema);
                    //User 被反序列化
                    return user;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    /**
     * Uer 对象传递到redis中
     *
     * @param user
     * @return
     */
    public String putUser(User user) {
        //set:Object(User)->序列化->byte[] ->发送给redis
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "user:" + user.getId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(user, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeOut = 60 * 60;
                String result = jedis.setex(key.getBytes(), timeOut, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

}
