package org.linlinjava.litemall.wx;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/2 17:51
 * @description：TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class GeneratorTest {
    @Test
    public void generatorTest(){
        System.out.println("generatorTest:"+RandomStringUtils.randomAlphabetic(8));
    }

    @Test
    public void generatorCardTest(){
        System.out.println("generatorCardTest:"+ StringUtils.leftPad(String.valueOf(1237).substring(String.valueOf(1234567).length()-6,String.valueOf(1234567).length()), 6, "0"));
    }


    @Test
    public void generatorArrTest(){
        Integer[] ids = new Integer[]{1,2};
        System.out.println("generatorArrTest:"+ String.valueOf(ids));
    }
}
