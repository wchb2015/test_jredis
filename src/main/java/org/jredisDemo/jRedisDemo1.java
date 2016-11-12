package org.jredisDemo;

import redis.clients.jedis.Jedis;

public class jRedisDemo1 {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.keys("*");
    }
}
