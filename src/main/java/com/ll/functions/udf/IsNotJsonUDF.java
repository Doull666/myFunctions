package com.ll.functions.udf;

import com.alibaba.fastjson.JSON;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @Author lin_li
 * @Date 2022/3/3 17:42
 *
 *  1.判断输入的字符串是否为json
 *  2.若为json则直接输出
 *  3.若不满足json格式，则是接置空
 */
public class IsNotJsonUDF extends UDF {

    //判断输入字符串是否为json格式
    public static String evaluate(String str) {

        try {
            Object parse = JSON.parse(str);
            return null;
        } catch (Exception e) {
            return str;
        }
    }


    public static void main(String[] args) {
        String str="dasf[{\"freezing_targets_ids\":\"\",\"freezing_desc\":\"\",\"freezing_behavior\":-1,\"freezing_amount\":null,\"freezing_targets_names\":\"\"}]";
        System.out.println(IsNotJsonUDF.evaluate(str));
    }

}
