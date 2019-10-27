package org.linlinjava.litemall.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.cache.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisServicelTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void testSet(){
        String key = "test";
        String value = "hello world";
        redisService.set(key, value);
    }

    @Test
    public void testSetObject(){
        String key = "test";
        org.linlinjava.litemall.cache.Test test = new org.linlinjava.litemall.cache.Test();
        test.setId("123");
        test.setName("test");
        redisService.set(key, test);
    }

    @Test
    public void testGet(){
        String key = "test";
        Object o = redisService.get(key);
        org.linlinjava.litemall.cache.Test test = (org.linlinjava.litemall.cache.Test)o;
        System.out.println(o.toString());
    }

    @Test
    public void testGetObejct(){
        String key = "test";
        Object o = redisService.get(key, org.linlinjava.litemall.cache.Test.class);
        org.linlinjava.litemall.cache.Test test = (org.linlinjava.litemall.cache.Test)o;
        System.out.println((org.linlinjava.litemall.cache.Test)o);
    }

    @Test
    public void testExpire(){
        String key = "test";
        long time = 1000;
        System.out.println(redisService.expire(key, time));
    }
    @Test
    public void testGetExpire(){
        String key = "test";
        System.out.println(redisService.getExpire(key));
    }
    @Test
    public void testHasKey(){
        String key = "test";
        System.out.println(redisService.hasKey(key));
    }
    @Test
    public void testDel(){
        String key = "test";
        redisService.del(key);
    }
    @Test
    public void testlSet(){
        String key = "ltest";
        String[] strings = new String[]{"1","2","3"};
        System.out.println(redisService.lSet(key, Arrays.asList(strings)));
    }
    @Test
    public void testRangeList(){
        String key = "ltest";

        System.out.println(redisService.rangeList(key, 0,1));

    }


}
