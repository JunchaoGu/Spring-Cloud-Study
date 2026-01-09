# Nacos Spring Cloud 学习指南

## 📚 项目概述

本项目包含两个模块，用于学习Spring Cloud和Nacos：

- **nacos-service**: 服务提供者（Provider），类似于dubbo-service
- **nacos-web**: 服务消费者（Consumer），类似于dubbo-web

### 🎯 学习目标

通过本项目，你将学会：

1. ✅ Nacos服务发现和注册
2. ✅ OpenFeign服务间调用
3. ✅ Spring Cloud负载均衡
4. ✅ Nacos配置中心（可选）
5. ✅ Spring Cloud与Dubbo的区别

---

## 🔍 Spring Cloud vs Dubbo 对比

### 架构对比

| 特性 | Dubbo | Spring Cloud |
|------|-------|--------------|
| **通信协议** | RPC（基于TCP） | HTTP RESTful |
| **服务调用** | `@DubboReference` + 接口 | `@FeignClient` + REST接口 |
| **服务发布** | `@DubboService` | `@RestController` + `@EnableDiscoveryClient` |
| **序列化** | Hessian、Fastjson等 | JSON（Jackson）
| **性能** | 高性能，适合内部服务调用 | 稍低，但更通用，易调试 |
| **学习曲线** | 需要理解RPC概念 | 基于HTTP，更容易上手 |

### 代码对比

#### Dubbo方式（你已有的项目）

**Provider (dubbo-service):**
```java
// 服务提供者
@DubboService  // 将服务发布到注册中心
public class UserServiceImpl implements UserService {
    @Override
    public String sayHello() {
        return "hello";
    }
}
```

**Consumer (dubbo-web):**
```java
// 服务消费者
@RestController
public class UserController {
    @DubboReference  // 引用远程服务
    private UserService userService;

    @GetMapping("/sayHello")
    public String sayHello() {
        return userService.sayHello();  // 直接调用，像本地方法一样
    }
}
```

**注册中心配置:**
```yaml
dubbo:
  registry:
    address: zookeeper://192.168.10.102:2181,...
```

#### Spring Cloud方式（新建的项目）

**Provider (nacos-service):**
```java
// 服务提供者
@EnableDiscoveryClient  // 启用服务发现，自动注册到Nacos
@SpringBootApplication
public class NacosServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosServiceApplication.class, args);
    }
}

@Service
public class UserServiceImpl implements UserService {
    // 实现业务逻辑
}

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/sayHello")
    public String sayHello() {
        return userService.sayHello();  // 暴露REST接口
    }
}
```

**Consumer (nacos-web):**
```java
// 服务消费者
@EnableFeignClients  // 启用Feign客户端
@EnableDiscoveryClient
@SpringBootApplication
public class NacosWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosWebApplication.class, args);
    }
}

// Feign客户端定义
@FeignClient(name = "nacos-service", path = "/user")
public interface UserServiceClient {
    @GetMapping("/sayHello")
    String sayHello();
}

// Controller调用
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceClient userServiceClient;

    @GetMapping("/sayHello")
    public String sayHello() {
        return userServiceClient.sayHello();  // 通过Feign调用
    }
}
```

**注册中心配置:**
```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.10.102:8848,...
```

### 核心区别总结

1. **服务发布方式**
   - Dubbo: 通过 `@DubboService` 注解发布RPC服务
   - Spring Cloud: 通过 `@RestController` 暴露REST接口，`@EnableDiscoveryClient` 自动注册

2. **服务调用方式**
   - Dubbo: 通过 `@DubboReference` 注入服务接口，直接调用方法
   - Spring Cloud: 通过 `@FeignClient` 定义Feign客户端，调用REST接口

3. **通信协议**
   - Dubbo: RPC协议（基于TCP），二进制传输，性能更高
   - Spring Cloud: HTTP协议，JSON格式，更通用，易调试

4. **适用场景**
   - Dubbo: 高性能、内部服务调用
   - Spring Cloud: 微服务、跨语言调用、需要监控和治理

---

## 🏗️ 项目结构

```
DubboPorject/
├── dubbo-service/          # Dubbo服务提供者
├── dubbo-web/              # Dubbo服务消费者
├── nacos-service/          # Spring Cloud服务提供者（新增）
│   ├── src/main/java/com/xidian/nacosservice/
│   │   ├── NacosServiceApplication.java    # 主启动类
│   │   ├── controller/
│   │   │   └── UserController.java          # REST接口
│   │   ├── service/
│   │   │   ├── UserService.java            # 服务接口
│   │   │   └── impl/
│   │   │       └── UserServiceImpl.java    # 服务实现
│   │   └── model/
│   │       └── User.java                    # 实体类
│   └── src/main/resources/
│       └── application.yml                 # 配置文件
├── nacos-web/              # Spring Cloud服务消费者（新增）
│   ├── src/main/java/com/xidian/nacosweb/
│   │   ├── NacosWebApplication.java         # 主启动类
│   │   ├── controller/
│   │   │   └── UserController.java          # 消费者Controller
│   │   └── feign/
│   │       └── UserServiceClient.java      # Feign客户端
│   └── src/main/resources/
│       └── application.yml                 # 配置文件
└── NACOS_LEARNING_GUIDE.md                  # 本文档
```

---

## 🚀 快速开始

### 前置条件

1. ✅ JDK 17+
2. ✅ Maven 3.6+
3. ✅ Nacos Server（已部署在 192.168.10.102:8848, 192.168.10.103:8848, 192.168.10.104:8848）

### Nacos Server安装（如果还没有）

#### 方式一：Docker安装（推荐）

```bash
# 单机模式
docker run -d \
  --name nacos \
  -e MODE=standalone \
  -p 8848:8848 \
  -p 9848:9848 \
  nacos/nacos-server:v2.2.0

# 访问：http://localhost:8848/nacos
# 默认账号：nacos/nacos
```

#### 方式二：本地安装

```bash
# 下载Nacos
wget https://github.com/alibaba/nacos/releases/download/2.2.0/nacos-server-2.2.0.zip

# 解压
unzip nacos-server-2.2.0.zip -d nacos

# 启动（单机模式）
cd nacos/bin
./startup.sh -m standalone

# 访问：http://localhost:8848/nacos
```

### 启动步骤

#### 1. 启动Nacos Server

确保Nacos Server已启动并可以访问：
- URL: http://192.168.10.102:8848/nacos
- 账号密码: nacos/nacos

#### 2. 启动nacos-service（服务提供者）

```bash
cd nacos-service
mvn clean package
mvn spring-boot:run
```

或者使用IDE直接运行 `NacosServiceApplication` 主类。

**验证启动成功：**
- 控制台输出：`Nacos服务提供者启动成功！`
- 访问：http://localhost:9081/user/sayHello，返回 `Hello from Spring Cloud Nacos Service!`
- 登录Nacos控制台，在"服务管理" → "服务列表"中可以看到 `nacos-service`

#### 3. 启动nacos-web（服务消费者）

```bash
cd nacos-web
mvn clean package
mvn spring-boot:run
```

或者使用IDE直接运行 `NacosWebApplication` 主类。

**验证启动成功：**
- 控制台输出：`Nacos服务消费者启动成功！`
- 访问：http://localhost:9080/user/sayHello，返回 `Hello from Spring Cloud Nacos Service!`
- 登录Nacos控制台，在"服务管理" → "服务列表"中可以看到 `nacos-web`

---

## 🧪 测试接口

### 基础测试

#### 1. sayHello（问候接口）

**直接调用Provider:**
```bash
curl http://localhost:9081/user/sayHello
```

**通过Consumer调用:**
```bash
curl http://localhost:9080/user/sayHello
```

**预期返回:**
```
Hello from Spring Cloud Nacos Service!
```

#### 2. 获取用户列表

```bash
curl http://localhost:9080/user/list
```

**预期返回:**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "username": "张三",
      "email": "zhangsan@example.com",
      "age": 25
    },
    {
      "id": 2,
      "username": "李四",
      "email": "lisi@example.com",
      "age": 30
    }
  ],
  "total": 2
}
```

#### 3. 根据ID获取用户

```bash
curl http://localhost:9080/user/1
```

**预期返回:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "username": "张三",
    "email": "zhangsan@example.com",
    "age": 25
  }
}
```

#### 4. 创建用户

```bash
curl -X POST http://localhost:9080/user \
  -H "Content-Type: application/json" \
  -d '{"username":"测试用户","email":"test@example.com","age":20}'
```

**预期返回:**
```json
{
  "success": true,
  "data": {
    "id": 4,
    "username": "测试用户",
    "email": "test@example.com",
    "age": 20
  },
  "message": "用户创建成功"
}
```

### 高级测试

#### 5. 获取服务实例信息（测试服务发现）

```bash
curl http://localhost:9080/user/instance
```

**预期返回:**
```json
{
  "consumer": "nacos-web",
  "consumerPort": "9080",
  "providerInfo": {
    "serviceName": "nacos-service",
    "port": "9081",
    "instanceCount": 1,
    "instances": [
      {
        "host": "192.168.1.100",
        "port": "9081",
        "uri": "http://192.168.1.100:9081"
      }
    ]
  }
}
```

#### 6. 健康检查

```bash
curl http://localhost:9080/user/health
```

**预期返回:**
```json
{
  "consumer": "nacos-web",
  "consumerPort": "9080",
  "providerHealth": {
    "status": "UP",
    "service": "nacos-service",
    "port": "9081",
    "timestamp": 1736251200000
  }
}
```

---

## 📖 核心概念

### 1. Nacos服务发现

**什么是服务发现？**
- 服务提供者启动时，自动注册到Nacos
- 服务消费者启动时，从Nacos拉取服务列表
- Nacos负责维护服务的健康状态

**配置说明:**
```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.10.102:8848,192.168.10.103:8848,192.168.10.104:8848
        enabled: true
```

**关键注解:**
- `@EnableDiscoveryClient`: 启用服务发现功能

### 2. OpenFeign

**什么是Feign？**
- Feign是Spring Cloud提供的声明式HTTP客户端
- 通过接口和注解定义HTTP请求，简化服务间调用
- 自动集成了Ribbon负载均衡

**Feign客户端定义:**
```java
@FeignClient(name = "nacos-service", path = "/user")
public interface UserServiceClient {
    @GetMapping("/sayHello")
    String sayHello();
}
```

**关键注解:**
- `@EnableFeignClients`: 启用Feign客户端
- `@FeignClient`: 定义Feign客户端
  - `name`: 服务名称（Nacos注册的服务名）
  - `path`: Controller的基础路径

### 3. 负载均衡

Spring Cloud使用Spring Cloud LoadBalancer实现客户端负载均衡。

**负载均衡策略（配置示例）:**
```yaml
spring:
  cloud:
    loadbalancer:
      retry:
        enabled: true
        max-retries: 2
```

**测试负载均衡:**
1. 启动多个nacos-service实例（修改端口多次启动）
2. 多次调用nacos-web接口
3. 观察请求分发到不同的实例

### 4. Nacos配置中心（可选）

**什么是配置中心？**
- 统一管理微服务的配置
- 支持配置的动态刷新
- 实现配置的环境隔离

**配置示例:**
```yaml
spring:
  cloud:
    nacos:
      config:
        server-addr: 192.168.10.102:8848,...
        file-extension: yaml
```

---

## 🔄 与Dubbo的对比练习

为了更好地理解Spring Cloud和Dubbo的区别，建议进行以下对比练习：

### 练习1：调用链路对比

**Dubbo调用链路:**
```
Consumer (@DubboReference) → Zookeeper (服务发现) → Provider (@DubboService) → RPC方法
```

**Spring Cloud调用链路:**
```
Consumer (Feign Client) → Nacos (服务发现) → Provider (REST API) → HTTP请求 → 处理并返回
```

### 练习2：接口定义对比

**Dubbo:**
- Provider定义接口
- Consumer引用接口
- 直接调用方法

**Spring Cloud:**
- Provider暴露REST接口
- Consumer定义Feign客户端
- Feign客户端映射Provider的REST接口
- 通过Feign调用

### 练习3：性能对比（可选）

可以使用JMeter进行压力测试，对比Dubbo RPC和Spring Cloud HTTP的性能差异。

---

## 📊 Nacos控制台使用

### 登录Nacos控制台

访问：http://192.168.10.102:8848/nacos
账号密码：nacos/nacos

### 主要功能

1. **服务管理**
   - 服务列表：查看所有注册的服务
   - 服务详情：查看服务的实例信息、元数据等
   - 订阅者列表：查看服务被哪些消费者订阅

2. **配置管理**
   - 配置列表：查看所有配置
   - 配置详情：查看配置内容
   - 配置历史：查看配置变更历史
   - 监听查询：查看配置的监听者

### 实战操作

#### 查看服务注册

1. 登录Nacos控制台
2. 点击左侧"服务管理" → "服务列表"
3. 应该可以看到：
   - `nacos-service` (服务提供者)
   - `nacos-web` (服务消费者)

#### 查看服务实例

1. 点击服务名称（如 `nacos-service`）
2. 可以看到实例的详细信息：
   - IP地址
   - 端口
   - 健康状态
   - 元数据

#### 下线服务实例

1. 点击实例操作中的"下线"按钮
2. 再次调用接口，观察调用失败或自动切换到其他实例

---

## ❓ 常见问题

### 1. 服务注册失败

**问题现象:**
- 服务启动成功，但在Nacos控制台看不到服务

**解决方案:**
1. 检查Nacos Server是否启动
2. 检查 `application.yml` 中的 `server-addr` 配置
3. 检查防火墙是否开放8848端口
4. 查看日志，确认是否有连接错误

### 2. 服务调用失败

**问题现象:**
- Consumer调用Provider接口失败

**解决方案:**
1. 检查Provider是否成功注册到Nacos
2. 检查Feign客户端的 `name` 是否与Provider的服务名一致
3. 检查Feign客户端的 `path` 是否与Provider的Controller路径一致
4. 查看Feign客户端的日志，确认请求参数是否正确

### 3. 超时问题

**问题现象:**
- 调用接口时出现超时错误

**解决方案:**
1. 增加Feign客户端的超时配置：
```yaml
feign:
  client:
    config:
      nacos-service:
        connectTimeout: 10000
        readTimeout: 10000
```

### 4. 负载均衡不生效

**问题现象:**
- 多个Provider实例，但请求总是打到同一个实例

**解决方案:**
1. 确认多个Provider实例都已注册到Nacos
2. 确认Spring Cloud LoadBalancer依赖已引入
3. 检查负载均衡配置

---

## 📚 扩展学习

### 推荐学习路径

1. ✅ **基础**: Nacos服务发现和注册
2. ✅ **进阶**: OpenFeign服务调用
3. ⬜ **高级**: Nacos配置中心
4. ⬜ **高级**: 服务降级和熔断（Sentinel）
5. ⬜ **高级**: 网关（Spring Cloud Gateway）
6. ⬜ **高级**: 链路追踪（SkyWalking/Zipkin）

### 推荐资源

- [Spring Cloud官方文档](https://spring.io/projects/spring-cloud)
- [Nacos官方文档](https://nacos.io/zh-cn/docs/what-is-nacos.html)
- [OpenFeign官方文档](https://docs.spring.io/spring-cloud-openfeign/reference/singleton/)

---

## 📝 总结

通过本项目，你应该已经掌握了：

1. ✅ Nacos服务注册与发现的基本使用
2. ✅ OpenFeign声明式HTTP客户端的使用
3. ✅ Spring Cloud服务间调用的方式
4. ✅ Spring Cloud与Dubbo的区别和联系
5. ✅ 微服务架构的基本概念

下一步，你可以：
- 尝试使用Nacos配置中心
- 学习Sentinel服务降级和熔断
- 学习Spring Cloud Gateway网关
- 学习分布式事务（Seata）

祝你学习愉快！🎉

