package org.linlinjava.litemall.core.util;

import java.util.Arrays;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/19 10:28
 * @description：比较器
 */
public class CompareUtils {

    /**
     * 比较两个不为空的integer数组的值是否相等
     * @param arg1
     * @param arg2
     * @return
     */
    public static boolean intArrComparator(Integer[] arg1, Integer[] arg2){
        boolean rtn = false;
        /**
         * 任意一个为null，返回false
         */
        if(arg1 == null || arg2 == null){
            return rtn;
        }
        if(arg1.length != arg2.length){
            return rtn;
        }
        Arrays.sort(arg1);
        Arrays.sort(arg2);
        for(int i=0; i < arg1.length;i++){
            if(arg1[i] != arg2[i]){
                return rtn;
            }
        }
        return !rtn;
    }
}
