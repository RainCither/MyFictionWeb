# 一个基于springboot的小说网站

本项目以springboot框架搭建。

通过webmagic自动爬取网络上的数据存储到mysql数据库，并缓存到redis。

使用thymeleaf模板渲染界面
    

### 数据库配置

``` mysql 
create schema fiction collate utf8mb4_general_ci;
 
 create table chapter
 (
 	c_id int auto_increment comment '主键id'
 		primary key,
 	b_id int null comment '外键id',
 	title varchar(125) null comment '文章标题',
 	chapter longtext null comment '文章内容',
 	update_time varchar(25) charset utf8 null comment '更新时间',
 	chapter_which int not null comment '第几章',
 	chapter_link varchar(255) charset utf8 not null comment '来源链接'
 );
 
 create index b_id
 	on chapter (b_id);
 
 create table detail
 (
 	b_id int auto_increment comment '主键id'
 		primary key,
 	b_name varchar(60) charset utf8 null comment '书名',
 	author varchar(60) charset utf8 default '未知' null comment '作者',
 	`desc` text null comment '书籍描述',
 	cat varchar(20) charset utf8 default '未知' null comment '分类',
 	cover varchar(255) charset utf8 null comment '封面地址',
 	state varchar(20) charset utf8 default '未知' null comment '状态',
 	link varchar(255) charset utf8 null comment '来源地址'
 );
```
自行修改application.yaml下数据库链接账号密码等


