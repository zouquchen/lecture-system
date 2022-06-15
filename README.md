# Lecture — 校园讲座预约系统

## 📚 1 项目简介

Lecture是一款前后端分离的校园讲座预约系统，基于目前主流的技术栈
（SpringBoot + MyBatis + MySQL + Redis + RabbitMQ + Spring Security + ...），提供详细的学习开发文档。

## 🎬 2 快速开始

- 配置mysql
  - 修改每一个项目中application.yml中mysql的url, username以及password。
- 配置redis
  - 修改每一个项目中application.yml中redis的host, port以及password。
- 启动Nginx
  - 方式1：Window10环境下，并保证所有项目都在该环境下运行时，启动nginx-lecture目录下的niginx.exe
  - 方式2：自定义Nginx，并仿照nginx-lecture/conf/nginx.conf对自己的Nginx进行配置。
- 配置Zookeeper
  - 修改每一个项目中application.yml中zookeeper的ip地址。
- 配置RabbitMQ
  - 修改每一个项目中application.yml中rabbitmq的host, username以及password。
  
## 💻 3 核心技术栈

## 🔨 4 开发环境

## 🎀 5 界面展示

## 🌱 6 本地运行

## 🌌 7 部署架构

## 🎯 8 功能逻辑图

### 8.1 用户模块

用户模块使用Spring Security框架实现认证、授权两大功能。

>认证（Authentication）：验证当前访问系统的是不是本系统的用户，并且确认具体是哪个用户。
> 
>授权（Authoritarian）：经过认证后判断当前用户是否有权限进行某个操作。

#### 8.1.1 认证

认证模块是验证当前访问系统的是不是本系统的用户，并且确认具体是哪个用户。
如果用户未登录就需要让用户先登录再认证。

下图是登录认证模块，使用Spring Security提供的认证逻辑。

![structure_login.png](doc/images/structure_login.png)

#### 8.1.2 授权

#### 8.1.3 Spring Security原理

下面对Spring Security的原理进行简单的介绍：

登录
- 请求携带用户信息请求 /login 接口，在配置类中配置Spring Security对 /login 接口进行匿名访问， 
  所以Spring Security会对 /login 接口放行并准备执行登陆业务。
- 登录业务中，使用Spring Security提供的AuthenticationManager进行认证。
- 通过实现UserDetailService接口，重写loadUserByUsername方法，该方法可以从数据库中获取用户信息返回UserDetails对象（LoginUser）。
- 认证成功后，将用户信息（LoginUser对象）存储在redis中，将userId封装成jwt返回到前端，也就是前端每次访问需要携带的token。

认证
- 所有的请求将经过Spring Security过滤链，过滤链中包含自定义的jwt认证过滤器，用于解析从请求头中获取的token，
并使用jwt工具解析为userId后从redis中获取用户信息。
- 如果从redis中成功获取LoginUser用户信息，就将该信息存储到SecurityContextHolder中的SecurityContext中的Authentication中，
表示当前用户认证通过。
- 如果用户不存在，则返回认证失败的异常。

异常处理
- 认证失败异常，状态码（401）。实现AuthenticationEntryPoint，捕获并处理认证失败的异常。
- 权限不足，状态码（403）。实现AccessDeniedHandler接口，捕获并处理权限不足的异常。


  
![structure_login.png](doc/images/structure_SpringSecurity.png)