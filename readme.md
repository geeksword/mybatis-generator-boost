##mybatis 逆向工程增强


* 添加数据库comment
```text
将数据库表的注释添加到对应的字段和类上
```
   
* 添加 javax.validation.constraints.NotNull注释
```text
对于非空字段，添加javax.validation.constraints.NotNull注解标示
```

* 添加Lombok注解
```text
添加lombok注解，避免生成大量的getter/setter
```

* model和Example添加序列化接口
```text
对mybatis原生提供的序列化插件增强，对于生成的Example类和相应的内部类也添加上序列化接口
```

* 对于自增主键，insert完之后会将id设置到实体类中
```text
对于自增主键，在insert/inertSelective的mapper文件中添加主键id的返回
```

