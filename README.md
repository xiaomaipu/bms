# bms
企业内部图书管理系统

###QQ交流群
705057231

## 简述
    bms分后台管理系统和小程序两部分

## 技术选型

### 后端
- JDK：1.8
- 数据库：Mysql
- 项目构建工具：Maven 3.3.1
- MVC框架：SpringMVC 4.2.1.RELEASE
- 核心框架：Spring 4.2.1.RELEASE
- ORM框架：MyBatis
- 日志管理：log4j2

### 前端
- 基础代码库：Jquery 2.1.1


###后台系统 默认登录
用户名密码：admin/13579

###后台系统运行
1.执行doc/sql文件
2.修改src/main/resources/application-dev.properties中的mysql连接池的ip、redis的ip
3.运行src/main/java/com/imxiaomai/bms/Application
4.默认启动是application-dev.properties模块

###小程序运行
详见src/main/resources/webapp/libray/README.md文件








