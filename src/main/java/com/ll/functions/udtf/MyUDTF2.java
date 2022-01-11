package com.ll.functions.udtf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author lin_li
 * @Date 2022/1/11 11:07
 *
 *
 * 1.重写 initialize 方法，全局最开始调用一次，定义当前函数的返回值类型以及列名
 * 2.重写 process 方法，每处理一条数据调用一次，内部为处理数据的逻辑
 * 3.重写 close 方法，全局最后调用一次
 */
public class MyUDTF2 extends GenericUDTF {
    private ArrayList<String> outList = new ArrayList<>();

    //初始化方法：定义当前函数的返回值类型以及列名（可以被别名所替代）,全局最开始调用一次
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        //输出数据的默认列名
        List<String> fieldNames=new ArrayList<>();
        fieldNames.add("name");
        fieldNames.add("age");

        //输出数据的类型校验
        List<ObjectInspector> fieldOIs=new ArrayList<>();
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames,fieldOIs);
    }

    //处理数据的方法。每一条数据调用一次
    //tom:23,alice:22
    @Override
    public void process(Object[] objects) throws HiveException {
        String[] split = objects[0].toString().split(",");
        for (String line : split) {
            String[]  nameage= line.split(":");
            outList.clear();
            outList.add(nameage[0]);
            outList.add(nameage[1]);
            forward(outList);
        }
    }

    //全局最后调用一次
    @Override
    public void close() throws HiveException {

    }
}
