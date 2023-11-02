# webflux + r2dbc

Spring MVC 的前端控制器是 DispatcherServlet, 而 WebFlux 是 DispatcherHandler，它实现了 WebHandler 接口

在 WebFlux 中，除了 Mono 外，还有一个 Flux，能充当响应式编程中发布者的角色，不同的是：
    Mono：返回 0 或 1 个元素，即单个对象。
    Flux：返回 N 个元素，即 List 列表对象。

## webflux dependency

```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webflux</artifactId>
		</dependency>
```

## example

https://github.com/jasync-sql/jasync-mysql-example

https://github.com/jasync-sql/jasync-postgresql-example

## Query

```
    Using Like: select ... like :username
     List<User> findByUsernameLike(String username);
    StartingWith: select ... like :username%
     List<User> findByUsernameStartingWith(String username);
    EndingWith: select ... like %:username
     List<User> findByUsernameEndingWith(String username);
    Containing: select ... like %:username%
     List<User> findByUsernameContaining(String username);
```
  https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

## 初始化数据
```
@Bean
ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);
    initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

    return initializer;
}

TODO not verified
# ConnectionFactoryInitializer 配置
#启用自动初始化数据库
spring.datasource.initialize=true
#数据库初始化脚本位置
spring.datasource.schema=classpath:schema.sql
spring.datasource.data=classpath:data-init.sql
```

## open api doc for webflux
```
        rest api
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-ui</artifactId>
            <version>1.6.6</version> <!-- 请使用最新版本 -->
        </dependency>

        webflux
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
			<version>2.1.0</version>
		</dependency>
```

##
指定该实体bean被加载的顺序，注解中的值越小越优先被加载注入

we set the order of our global error handler to -2. 
This is to give it a higher priority than the DefaultErrorWebExceptionHandler, 
which is registered at @Order(-1).

```java

@Component
@Order(-2)
public class RestExceptionHandler  implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ...       
    }
}

```
## RestControllerAdvice方式实现
```java
@RestControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Map<String, Object>> exceptions(Throwable e)
    {
        return Mono.just(Map.of("message", "bad request."));
    }
}

```
