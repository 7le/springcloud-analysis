# springcloud-analysis

> 使用springcloud重新设计原先基于vert.x的分布式统计系统

### 🌈 注册中心 

#### consul 

[consul download](http://www.consul.io/)

如果是开发环境，部署在windows,解压后配置环境变量，启动命令为``consul agent -dev``

#### eureka （server 暂不用） 

startup :

```
java -jar server-1.0.0-SNAPSHOT.jar --spring.profiles.active=eureka1
java -jar server-1.0.0-SNAPSHOT.jar --spring.profiles.active=eureka2
java -jar server-1.0.0-SNAPSHOT.jar --spring.profiles.active=eureka3
```

如果是本地测试，还需要设置下host:

```
127.0.0.1   eureka1
127.0.0.1   eureka2
127.0.0.1   eureka3
```

### 🍀 配置中心 （config） 

### 🐧 网关 （gateway）

#### 目前使用zuul

路由重试配置：

```
spring:
  cloud:
    loadbalancer:
      retry:
        enabled: true
zuul:
  retryable: true
ribbon:
  connectTimeout: 500                 # 请求连接的超时时间
  readTimeout:  1000                  # 请求处理的超时时间
  maxAutoRetries: 2                   # 对当前实例的重试次数
  maxAutoRetriesNextServer: 1         # 切换实例的重试次数
  okToRetryOnAllOperations: true      # 对所有操作请求都进行重试
```