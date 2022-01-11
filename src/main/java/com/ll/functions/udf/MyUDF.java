package com.ll.functions.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @Author lin_li
 * @Date 2022/1/10 17:14
 *
 * 1.继承 UDF 函数
 * 2.定义方法 evaluate，方法名称需一致，内部存储数据处理逻辑
 */
public class MyUDF extends UDF {

    public String evaluate(String str) {
        if (str.equals("lin_li")) {
            return "666";
        } else {
            return "What's wrong with you?";
        }
    }


    public String evaluate() {
        return "What's wrong with you?";
    }
}
