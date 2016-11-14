>参考: http://redis.cn/topics/data-types-intro.html
http://blog.csdn.net/u013256816/article/details/51120023
http://docs.spring.io/spring-data/redis/docs/1.8.0.M1/reference/html/#why-spring-redis
http://www.cnblogs.com/luochengqiuse/p/4638988.html

> 用过jredis操作的都知道.所有connection的操作方法,都是传入字节数组.那么,一个对象和字节相互转换,就需要通过序列化和反序列化.

> 模版方法中,Spring提供了默认的StringSerializer和JdkSerializer,第一个很简单,就是通过String.getBytes()来实现的.
且在Redis中,所有存储的值都是字符串类型的.所以这种方法保存后,通过Redis-cli控制台,是可以清楚的查看到我们保存了什么key,value是什么.
但是对于JdkSerializationRedisSerializer来说,这个序列化方法就是Jdk提供的了.
首先要求我们要被序列化的类继承自Serializeable接口,然后通过Jdk对象序列化的方法保存.(
注:这个序列化保存的对象,即使是个String类型的,在redis控制台,也是看不出来的,因为它保存了一些对象的类型什么的额外信息,）




