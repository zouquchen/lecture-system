# Lecture — 校园讲座预约系统

::: tip 项目暂未开源

:::

## 📚 1 项目简介

Lecture 是一款前后端分离的校园讲座预约系统，基于目前主流的技术栈（SpringBoot + MyBatis + MySQL + Redis + RabbitMQ + Spring Security + ...），现已支持面向管理员的讲座发布与修订，面向学生的讲座预约与取消等功能。

## 📺 2 功能介绍

### 2.1 测试账号

| 账号     | 密码      | 权限角色                              |
| -------- | --------- | ------------------------------------- |
| admin    | needoffer | admin，拥有所有权限（不包含预约讲座） |
| manager  | needoffer | manger，拥有发布和修改讲座等权限      |
| student0 | needoffer | student，拥有预约讲座等权限           |
| student1 | needoffer | student，拥有预约讲座等权限           |
| student2 | needoffer | student，拥有预约讲座等权限           |
| student3 | needoffer | student，拥有预约讲座等权限           |

### 2.2 主要功能

- 登录|登出
- 个人中心
  - 修改个人信息
  - 修改密码
  - 查看讲座统计（总预约次数、未开始场次、出席次数、缺席次数）
- 关于
  - 自定义显示 markdown 文件
- 讲座管理「admin权限」
  - 分页条件查询讲座列表
  - 讲座签到
    - 根据用户名进行讲座签到
    - 显示已签到用户
  - 讲座详情查看
    - 查看讲座详情
    - 查看预约详情
    - 评论或点赞内容
  - 添加、修改或删除讲座
- 用户列表「admin权限」
  - 分页条件查询用户
  - 添加或删除用户
- 讲座预约「student权限」
  - 查看讲座详情
  - 预约或取消预约讲座
  - 评论或点赞内容
  - 查看我参与的讲座
- 消息通知
  - 系统自动通知用户被评论或被点赞消息
- 其他
  - 前后端的密码传输进行了加密、数据库中使用密文进行存储
  - 前后端对填入数据的校验


## 💻 3 核心技术栈
后端：
- Spring
- Spring MVC
- Spring Boot 2.2.1.RELEASE
- ORM：MyBatis
- RPC：Dubbo 2.7.8
- 注册中心：Zookeeper
- 数据库：MySQL 8.0
- 缓存：Redis
- 消息队列：RabbitMQ
- 权限：Spring Security

前端：
- Vue 2.6.10
- Node 16.15.0
- 模板：[vue-admin-template](https://github.com/PanJiaChen/vue-admin-template)
- 样式：[element-ui@2.15.7](https://element.eleme.cn/#/zh-CN)

## 🔨 4 开发环境
- 操作系统：Windows 10
- 后端IDE：IDEA
- 前端IDE：VS code
- 构建工具：Apache Maven
- 接口测试工具：Postman
- 压力测试工具：Apache JMeter
- 版本控制工具：Git
- Java 版本：8

## 📑 5 目录结构
```
lecture-system
├── db （数据库脚本）
├── doc （文档相关）
│
├── lecture-common （公共部分）
│   ├── config （配置类）
│   ├── constant （常量）
│   ├── entity （实体类）
│   ├── exception （自定义异常）
│   ├── filter （过滤器）
│   ├── handler （自定义处理器）
│   ├── service （service接口）
│   ├── utils （工具类）
│   └── vo
│
├── lecture-lecture （讲座微服务，核心）
├── lecture-order （讲座预约微服务）
├── lecture-user （用户登录微服务）
├── nginx-lecture （win10测试nginx文件）
├── vue-admin-template （前端工程）
│ 
└── README.md
```

## 🌌 6 部署架构

Nginx：通过反向代理访问不同的服务器

前端服务：vue框架搭建的前端项目

微服务：处理各类业务、通过操作redis缓存数据、MySQL 存储数据

RabbitMQ：通过消息队列异步解决并发问题

Zookeeper：实现微服务的注册

阿里云OSS：存储用户上传的图片

<img src="https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/deployment_architecture.png" style="zoom: 50%;" />

由于资金问题，实际运行部署情况

<img src="https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/deployment_real_architecture.png" style="zoom: 50%;" />

本地测试的部署情况

<img src="https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/deployment_local_architecture.png" style="zoom: 50%;" />

## 🎬 7 部署项目

- 配置 mysql
  - 修改每一个项目中 application.yml 中 mysql的url, username 以及 password。
  - 运行 db/data.sql 添加数据到数据库
- 配置 redis
  - 修改每一个项目中 application.yml 中 redis 的 host, port 以及 password。
- 启动 Nginx
  - 方式1：Window10 环境下，并保证所有项目都在该环境下运行时，启动 nginx-lecture 目录下的 niginx.exe
  - 方式2：自定义 Nginx，并仿照 nginx-lecture/conf/nginx.conf 对自己的 Nginx 进行配置。
- 配置 Zookeeper
  - 修改每一个项目中 application.yml 中 zookeeper 的 ip 地址。
- 配置 RabbitMQ
  - 修改每一个项目中 application.yml 中 rabbitmq 的 host, username 以及 password。
- 配置 OSS
  - 修改 lecture-oss 服务中的 application.yml 的阿里云OSS配置
- 配置前端
  - 修改 `.env.* `中的前端请求路径 `VUE_APP_BASE_API`, 即 Nginx 监听端口。

## 📢 7 功能说明

在该系统中分为三个身份：「管理员Admin」、「管理员Manager」和「用户Student」，不同身份有不同的权限，不同的需求和任务。

- 「管理员Admin」拥有发布讲座、修改和签到统计所有讲座的权限；拥有用户管理的权限。
- 「管理员Manager」拥有发布讲座、修改和签到统计他所发布讲座的权限。
- 「用户Student」拥有讲座预约、取消预约的权限。

### 7.1 讲座状态

对于讲座来说，具有两种状态：「0发布」与「1结束」，这两种状态由管理员进行切换。

对于用户来说，每一个讲座都有如下的状态：未开放、未预约（已开放）、已预约、已签到、缺席、已结束。
通过分析讲座预约开始的时间、讲座的状态、预约记录以及预约记录上的签到时间得出这些状态。
![](doc/images/lecture_state.png)

### 7.2 用户操作

用户关于讲座的操作包含：

- 评论：在讲座评论区进行留言评论
- 点赞：对喜欢的评论进行点赞
- 预约讲座：在讲座开始预约后，可以预约讲座
- 取消预约讲座：讲座关闭之前，可以取消预约讲座
- 签到：现场由管理员根据用户提供的用户名完成签到

取消预约和预约之间可以反复操作，一旦完成签到就不能再取消预约了。

![](doc/images/user_operation.png)

## 🎀 8 界面展示

### 8.1 公共界面

🔶 **登录界面**

![UI1](https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/UI_login.png)

🔶 **首页**

![UI1](https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/UI_index.png)

🔶 **个人中心**

![](https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/UI_userCenter.png)

🔶 **评论模块**

![](https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/UI_comment.png)

### 8.2 Student界面展示

🔶 **讲座列表**

![](https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/UI_student_list.png)

🔶 **讲座详情**

![](https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/UI_student_info.png)

### 8.3 Admin界面展示

🔶 **讲座列表**

![](https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/UI_admin_list.png)

🔶 **添加讲座**

![](https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/UI_admin_add.png)

🔶 **讲座详情**

![](https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/UI_admin_info.png)

🔶 **讲座签到**

![](https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/UI_admin_sign.png)

🔶 **用户列表**

![](https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/UI_admin_userList.png)

## 📌 9 功能逻辑图

### 9.1 用户登录模块

用户模块使用Spring Security框架实现认证、授权两大功能。

>认证（Authentication）：验证当前访问系统的是不是本系统的用户，并且确认具体是哪个用户。
> 
>授权（Authoritarian）：经过认证后判断当前用户是否有权限进行某个操作。

#### 9.1.1 认证

认证模块是验证当前访问系统的是不是本系统的用户，并且确认具体是哪个用户。
如果用户未登录就需要让用户先登录再认证。

下图是登录认证模块，使用 Spring Security 提供的认证逻辑。

⭐ **登录**：SpringSecurity过滤链放行 `/login` 请求，成功调用 Controller 执行登录业务，通过Spring Security 模块来对用户的账号密码进行判断，若符合要求则登陆成功，最终将用户信息存储到 Redis 中，返回前端一个 token，之后每次的访问可以在请求头中携带这个 token，jwt 过滤器可以从 token 中解析出用户 id，通过用户 id 可以从 Redis 中获取用户登录信息判断用户是否登录。

⭐ **正常请求**：任何一次请求都将经过 Security 过滤链，在 jwt 过滤器中将会对请求头中的 token 进行解析：

- token 解析错误就是 token 非法或者 token 过期，将抛出异常，通过自定义的异常捕获器捕获异常，并将异常信息返回到前端。
- token 正常解析出用户 id，通过 id 从 Redis 中获取用户信息，若获取不到说明用户未登录；若获取到了说明用户为登录状态，将当前的用户信息存储到 Spring Securiy 的容器当中。

经过过滤器后，开始正常执行业务逻辑。

<img src="https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/structure_login.png" alt="structure_login.png" style="zoom: 50%;" />

#### 9.1.2 授权

#### 9.1.3 Spring Security原理

下面对 Spring Security 的原理进行简单的介绍：

⭐ **登录**
- 请求携带用户信息请求 `/login` 接口，在配置类中配置 Spring Security 对  `/login`  接口进行匿名访问， 所以 Spring Security 会对 `/login` 接口放行，之后执行登录业务。
- 登录业务中，使用 Spring Security 提供的 AuthenticationManager 进行认证。
- 通过实现 UserDetailService 接口，重写 loadUserByUsername 方法，该方法可以从数据库中获取用户信息返回 UserDetails 对象（LoginUser）。
- 通过继承 `DaoAuthenticationProvider` 重写 `additionalAuthenticationChecks` 方法完成自定义密码校验，首先将前端传入的密码进行AES解码获得密码明文，再将密码明文与数据库中的加密密码进行匹配。
- 认证成功后，将用户信息（LoginUser对象）存储在 redis 中，将 userId 封装成 jwt 返回到前端，也就是前端每次访问需要携带的 token。

⭐ **认证**
- 所有的请求将经过 Spring Security 过滤链，过滤链中包含自定义的 jwt 认证过滤器，可从请求头中获取的 token；使用 jwt 工具将 token 解析为 userId 后，从 redis 中获取用户信息。
- 如果从 redis 中成功获取 LoginUser 用户信息，就将该信息存储到 SecurityContextHolder 容器中，表示当前用户认证通过。
- 如果用户不存在，则返回认证失败的异常。

⭐ **异常处理**
- 认证失败异常，状态码（401）。实现 AuthenticationEntryPoint 接口，捕获并处理认证失败的异常。
- 权限不足，状态码（403）。实现 AccessDeniedHandler 接口，捕获并处理权限不足的异常。



![structure_login.png](https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/structure_SpringSecurity.png)

### 9.2 讲座管理模块

#### 9.2.1 讲座列表
支持分页、条件查询所有数据库中的所有讲座。
#### 9.2.2 讲座详情
根据讲座id获取讲座详情，根据讲座id获取预约该讲座的所有用户预约记录。
#### 9.2.3 添加更新讲座
发布讲座或修改讲座，海报的上传需要阿里云的Oss服务。
#### 9.2.4 讲座签到
根据讲座id查询讲座详情，根据讲座id查询所有已签到用户列表。

通过输入用户名并点击签到按钮完成用户的签到，单个用户完成签到后立即刷新签到用户列表。

关闭签到系统后，讲座的状态从「0发布」变为「1结束」，用户预约讲座列表中将无法查询到该讲座，该讲座将无法被预约。

### 9.3 预约讲座模块
#### 9.3.1 讲座列表
支持分页、条件查询所有状态为「0发布」的讲座。

#### 9.3.2 我的讲座
根据已登录用户的id查询所有已预约过的讲座。
#### 9.3.3 预约与取消
讲座预约模块主要实现两个功能： 用户预约讲座、用户取消讲座。

该过程需要 lecture-lecture 和 lecture-order 两个微服务共同完成。其中：

预约讲座

- lecture-order 微服务：从 redis 中查询用户是否重复预定讲座、讲座是否还有剩余的可预约数量，
  如果用户未预约该讲座，且该讲座剩余可预约数量大于 0，则把讲座 id 和用户 id 封装为一个对象发送到消息队列，最后返回前端预约成功。
  
- lecture-lecture 微服务：从消息队列中获取消息，操作数据库减少讲座可预约数量，添加用户预约记录；
  操作redis减少讲座可预约数量，添加用户预约记录。

取消预约讲座
- lecture-order 微服务：从 redis 中查询用户是否预约讲座，若用户未预约该讲座则抛出异常；若用户预约该讲座，则远程调用 lecture-lecture 微服务。
  
- lecture-lecture 微服务：查询讲座是否结束，若结束则抛出异常；查询用户是否签到，若签到则抛出异常；删除用户预约记录，
  增加给定id讲座的剩余可预约数量，最后删除 redis 内用户的预约记录。

流程图如下：

<img src="https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/structure_order.png" alt="structure_login.png"  />

### 9.4 用户列表模块

#### 9.4.1 分页查询

「管理员Admin」可以分页、条件查询数据库中所有用户信息，不包含被逻辑删除的用户；同时可以辑删除用户（数据库is_deleted = 1表示被删除）

#### 9.4.2 添加新用户

填写必要的用户字段添加用户。密码传输经过加密处理。

由于 http 是明文传输，为了保证密码的安全，前端在传输密码的时候需要对密码进行加密，在后端对密码进行解密，存储到数据库的时候再对密码进行加密。

### 9.5 个人中心

#### 9.5.1 修改密码

输入原密码，填写新密码。密码传输经过加密处理。

#### 9.5.2 修改信息

填写需要修改的字段。

#### 9.5.3 密码加密与传输

由于 http 是明文传输，为了保证密码的安全，前端在传输密码的时候需要对密码进行加密，在后端对密码进行解密，存储到数据库的时候再对密码进行加密。

![](https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/password_crpty.png)

数据库中的密码采用加密的方式存储，即使数据库被盗，黑客也不能用密文作为密码进行登录，确保了账号的安全。

### 9.6 评论模块

在数据库中，评论分为两类：父级评论和子级评论，子评论包含指向父评论和根评论的指针。

> 父评论：评论A、评论B、评论C
>
> 子评论：评论B1、评论B2、评论B3、评论1、评论2
>
> 评论2的根评论为评论B，父评论为评论1。

<img src="https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/comment_relation_db.png" style="zoom: 50%;" />

#### 9.6.1 获取评论

根据讲座id获取所有评论，根据评论之间的关系封装好评论数据发给前端。此时，将子评论同级之间进行封装，有利于前端显示。

<img src="https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/comment_relation_json.png" style="zoom: 50%;" />

#### 9.6.2 添加评论

根据讲座 id、用户 id、父评论 id、根评论 id 和评论内容添加讲座信息。异步产生消息通知给被评论者，加快响应速度（见9.7消息通知）。

<img src="https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/structure_add_comment.png" style="zoom: 50%;" />

#### 9.6.3. 删除评论

根据评论 id、用户 id 删除评论及其子评论。

#### 9.6.4 点赞功能

用户可以在讲座详情页面对每一个评论进行点赞或取消点赞，点赞的数据以 set 数据结构存储在 redis 缓存内，键为 `commentLikes:讲座id`，值为 `用户id`，使用 set 数据结构可以保证每一个用户只能点赞一次，并且可以快速判断该用户是否点赞以及某一条评论的点赞总数。同时，异步产生消息通知给被点赞者，加快响应速度（见9.7消息通知）。

<img src="https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/structure_likes.png" style="zoom: 60%;" />

### 9.7 消息通知模块

#### 9.7.1 用户获取消息

系统消息数据存储在 redis 内，采用 list 数据结构，针对每一个用户创建一个 list 存储消息，键为 `systemMessage:用户id`，值为包含消息内容、讲座 id、目标用户id属性的对象。用户在登录网站时每间隔 1 分钟获取系统消息数量，显示在导航栏右上角的头像位置。可在用户中心内查看系统消息，当查看完后，所有消息将pop 出 list 队列。

<img src="https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/structure_get_message.png" style="zoom: 50%;" />

#### 9.7.2 生产消息

当有用户点赞或评论时将产生系统消息，为了加快点赞和评论的响应速度，生成系统消息的过程采用异步处理，通过消息队列来实现异步操作。

<img src="https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/structure_add_message.png" style="zoom: 50%;" />

## ⌚ 10 待实现及优化

- [ ] 点赞数据存储到数据库
- [ ] 逐条消费系统消息（目前只能一次性查看所有系统消息）
- [ ] 讲座即将开始系统消息提示
- [ ] 管理员可以发布通知给预约某一场讲座的所有用户

## 🙇‍♂️ 11 学习参考

[1 尚硅谷全栈项目——谷粒学院](https://www.bilibili.com/video/BV1dQ4y1A75e?p=1)

描述：在线学习网站，管理员可以通过后台管理系统上传教师信息、课程大纲、课程视频等；学生可以在前台页面中查看教师、课程等内容。

参考学习内容：前端知识的学习、前端 vue 模板的使用，element-ui 的使用，前后端开发的流程。

[2 renren-fast](https://gitee.com/renrenio)

描述：前后端快速开发的框架

参考学习内容：代码生成器、权限管理的数据库设计、响应类的设计

[3 秒杀项目](https://www.bilibili.com/video/BV1sf4y1L7KE?p=2)

描述：简单的后端秒杀系统

参考学习内容：秒杀的架构设计和基本流程、压力测试；redis、rabbitMQ 的使用