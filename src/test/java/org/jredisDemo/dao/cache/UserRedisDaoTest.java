package org.jredisDemo.dao.cache;

import org.jredisDemo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class UserRedisDaoTest {

    @Autowired
    private UserRedisDao userRedisDao;

    @Test
    public void testRedis() {
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.keys("*");
    }

    @Test
    public void getUser() throws Exception {

    }

    @Test
    public void putUser() throws Exception {
        User user = new User();

        user.setId(888l);
        user.setUserName("zhangsan");
        userRedisDao.putUser(user);
    }

}