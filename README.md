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

#### 监控 --Actuator

spring-boot 2.x 需要自己开发端点，配置如下：
```
management:
  endpoints:
    web:
      exposure:
        include: '*'   # 代表全部放开，可以自行选择
      base-path: /application
```
Actuator 部分端点：

| HTTP 方法|     路径|   描述|
| :-------- | --------:| :------: |
|GET|/beans|描述应用程序上下文里全部的Bean，以及它们的关系|
|GET|/health|健康检查     |
|GET|/env|获取全部环境属性     |
|GET|/env/{toMatch}|根据名称获取特定的环境属性值     |
|GET|/configprops|描述配置属性(包含默认值)如何注入Bean     |
|GET|/mappings| 描述全部的URI路径，以及它们和控制器(包含Actuator端点)的映射关系    |
|GET|/metrics|报告各种应用程序度量信息，比如内存用量和HTTP请求计数   |
|GET|/metrics/{requiredMetricName}|报告指定名称的应用程序度量值     |
|POST|/bus-refresh|端点手动刷新配置     |
|GET|/httptrace|提供基本的HTTP请求跟踪信息(时间戳、HTTP头等)     |

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

### 🐳 链路跟踪 （zipkin）

#### linux 部署：

```
wget -O zipkin.jar 'https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec'
nohup java -jar zipkin.jar &  
```

web页面：http://your_ip:9411 ，默认9411 可以自行修改