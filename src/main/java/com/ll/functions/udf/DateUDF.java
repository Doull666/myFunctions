package com.ll.functions.udf;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author lin_li
 * @Date 2022/1/21 14:22
 *
 * 将所有bigint日期类型，均转成 标准日期 类型，即 yyyy/mm/dd HH/MM/ss
 *1532936335714 13
 * 2021-05-20 02:16:43 19
 * 2018-09-17 10
 * -1 1
 * 空 0
 */
public class DateUDF extends UDF {

    public String evaluate(String strDate){
        return strToStdDate(strDate);
    }

    public String strToStdDate(String strDate){
        int len = strDate.length();
        long date=0;
        try {
            if(len==0 || len==2){
                return "0000/00/00 00:00:00";
            }else if(len==10){
                date=new SimpleDateFormat("yyyy-MM-dd").parse(strDate).getTime();
            }else if(len==19) {
                date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate).getTime();
            }else if(len==13) {
                date=Long.parseLong(strDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return DateFormatUtils.format(date,"yyyy/MM/dd HH:mm:ss");
    }
}
