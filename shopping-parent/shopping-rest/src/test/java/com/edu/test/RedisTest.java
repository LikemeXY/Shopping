package com.edu.test;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class RedisTest {
    @Test
    public void test(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("192.168.220.129");
        // 如果 Redis 服务设置来密码，需要下面这行，没有就不需要
        // jedis.auth("123456");
        jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
        jedis.close();
    }
    @Test
    public void test2(){
        //连接本地的 Redis 服务
        JedisPool jedisPool = new JedisPool("192.168.220.129",6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("key1", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
        jedis.close();
    }

    @Test
    public void test3(){
        //连接本地的 Redis集群服务
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.220.129",7001));
        nodes.add(new HostAndPort("192.168.220.129",7002));
        nodes.add(new HostAndPort("192.168.220.129",7003));
        nodes.add(new HostAndPort("192.168.220.129",7004));
        nodes.add(new HostAndPort("192.168.220.129",7005));
        nodes.add(new HostAndPort("192.168.220.129",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("cluster","cluster");
        String cluster = jedisCluster.get("cluster");
        // 获取存储的数据并输出
        System.out.println(cluster);
        jedisCluster.close();
    }
}
