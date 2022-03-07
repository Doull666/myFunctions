# myFunctions
hive自定义函数测试

## UDF
一进一出：操作单个数据行，产生单个数据行
1. 继承 UDF 函数
2. 定义方法 evaluate，方法名称需一致，内部存储数据处理逻辑

## UDAF
多进一出：操作多个数据行，产生一个数据行
1. 函数类需要继承 UDAF 类，计算类 Evaluator 实现 UDAFEvaluator 接口
2. 实现函数 init，对UDAF进行初始化
3. 实现函数 iterate，iterate接收传入的参数，并进行内部的轮转。其返回类型为boolean
4. 实现函数 terminatePartial，terminatePartial无参数，其为iterate函数遍历结束后，返回轮转数据，terminatePartial类似于hadoop的Combiner
5. 实现函数 merge，merge接收terminatePartial的返回结果，进行数据merge操作，其返回类型为boolean
6. 实现函数 terminate，terminate返回最终的聚集函数结果

## UDTF
一进多出：操作一个数据行，产生多个数据行一个表作为输出
1. 重写 initialize 方法，全局最开始调用一次，定义当前函数的返回值类型以及列名
2. 重写 process 方法，每处理一条数据调用一次，内部为处理数据的逻辑
3. 重写 close 方法，全局最后调用一次

*注意：输出数据类型定义为List<String>，故forward中需也用一个List<String>*

## 创建函数步骤
1. 继承UDF或者UDAF或者UDTF，实现特定的方法；
2. 将写好的类打包为jar，如myfunction.jar；
3. 将 myfunction.jar 上传到 hdfs 路径上(避免使用 Beeline 出现找不到的情况)，`hdfs dfs -put myfunction.jar /tmp/lin_li/
4. 为函数命名
- create function database.myfunction ：函数的名称；
- as 'com.ll.functions.udf.MyUDF' ：该函数指定的类
- using jar 'hdfs:///tmp/lin_li/myfunction.jar' ：根据hdfs上的哪个jar包
```
# 创建临时临时函数，只在当前会话内有效；database.myfunction 为函数的名称；'com.ll.functions.udf.MyUDF' 为该函数指定的类
    create temporary function database.myfunction as 'com.ll.functions.udf.MyUDF' using jar 'hdfs:///tmp/lin_li/myfunction.jar';
# 创建永久函数
create function database.myfunction as 'com.ll.functions.udf.MyUDF' using jar 'hdfs:///tmp/lin_li/myfunction.jar';
```
5. 使用自定义函数：`select database.myfunction;`


## 知识点
1. hive构建function会自动带上库名
- 构建function时，需添加database名称，即即 `[database].[function]`
- 如果不填写database，这会取当前database自动补全function。

## 使用累积
1. 使用外部第三方 jar 包
    - 当使用外部 jar 包时
    - 需将外部第三方 jar 包上传至 hdfs 上
    - 使用命令 `add jars hdfs:///tmp/fastjson-1.2.41.jar;`
    - 否则在使用自定义函数时，会报错找不到类