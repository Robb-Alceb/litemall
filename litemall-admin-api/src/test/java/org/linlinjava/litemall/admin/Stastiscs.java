package org.linlinjava.litemall.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author ：stephen
 * @date ：Created in 2020/3/13 15:23
 * @description：TODO
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Stastiscs {

    @Test
    public void test(){
        //输入路径,请在括号内输入路径。
        File f = new File("D:\\private\\workspace\\litemall\\litemall-db");
        Map map = new HashMap<String,Integer>();  //用来存放统计出来的行数
        Plus(f,map);
        //输出统计的结果
        getResult(map);
    }
    /*
    遍历文件夹
     */
    public static void Plus(File f , Map map){

        File[] files = f.listFiles();//获取传入路径的所有文件


        //遍历这些文件,需求是CVS中的不统计，所以加判断

        for (File a : files) {
            //如果a是文件的话就进入下一级目录，否则就统计行数

            if (a.isDirectory()) {
                Plus(a,map);
            }else{
                //如果文件名是CVS的就跳过a.getName()
                if(!a.getName().endsWith("java")) {
                    continue;
                }else{
                    map = lineNumber(a.getAbsolutePath(),map);  //否者调方法统计行数
                }
            }
        }


    }

    /*
    统计相应文件的行数
     */
    public static Map<String,Integer> lineNumber(String f,Map map){
        //定义字符流读取文件
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(f);
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("输入的路径不正确");
        }

        BufferedReader bufferedReader= new BufferedReader(fileReader);          //从字节流中升级为字符流，方便按行读取。
        int index = 0;


        try {
            while (bufferedReader.readLine()!=null){
                index++;
            }
            map.put(f,index);    //将结果放到map中


        }catch (IOException e){
            e.printStackTrace();
            System.out.println("这个文件读不到！");
        }finally {
            if(fileReader != null){
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return map;
        }
    }

    /*
    将文件的行数存放在一个map中，然后输出行数的和
     */
    public static void getResult(Map map){
        int sum = 0;
        //使用iterator遍历map集合
        Iterator<Map.Entry<String,Integer>> entries =
                map.entrySet().iterator();

        while (entries.hasNext()){
            Map.Entry<String,Integer> entry = entries.next();
            System.out.println(entry.getKey()+"的行数是:"+entry.getValue());
            sum += entry.getValue();
        }

        System.out.println("总行数是："+sum);

    }
}
