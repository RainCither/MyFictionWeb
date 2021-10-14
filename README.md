# 一个基于springboot的小说网站

- 独立开发的一个基于SpringBoot的小说阅读网站。
- 使用WebMagic 框架替代HttpClient爬取网络数据，大大提高了提高爬虫效率。
- 采用Redis 实现了高频信息缓存，加快响应速度，极大的降低了数据库压力。
- 以Docker为容器，实现了集群的快速部署。
- 使用 Docke-Compose 打包，实现一键部署应用。
- 使用后端模板引擎 Thymeleaf 渲染界面

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
 
CREATE TABLE `detail` (
  `b_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `b_name` varchar(60) CHARACTER SET utf8 DEFAULT NULL COMMENT '书名',
  `author` varchar(60) CHARACTER SET utf8 DEFAULT '未知' COMMENT '作者',
  `desc` text COMMENT '书籍描述',
  `cat` varchar(20) CHARACTER SET utf8 DEFAULT '未知' COMMENT '分类',
  `cover` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '封面地址',
  `state` varchar(20) CHARACTER SET utf8 DEFAULT '未知' COMMENT '状态',
  `link` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '来源地址',
  PRIMARY KEY (`b_id`),
  UNIQUE KEY `link` (`link`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4
```
自行修改application.yaml下数据库链接账号密码等


