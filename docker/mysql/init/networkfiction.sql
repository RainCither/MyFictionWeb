/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.7.30 : Database - fiction
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fiction` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `fiction`;

/*Table structure for table `chapter` */

DROP TABLE IF EXISTS `chapter`;

CREATE TABLE `chapter` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `b_id` int(11) DEFAULT NULL COMMENT '外键id',
  `title` varchar(125) DEFAULT NULL COMMENT '文章标题',
  `chapter` longtext COMMENT '文章内容',
  `update_time` varchar(25) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新时间',
  `chapter_which` int(11) NOT NULL COMMENT '第几章',
  `chapter_link` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '来源链接',
  PRIMARY KEY (`c_id`),
  KEY `b_id` (`b_id`)
) ENGINE=InnoDB AUTO_INCREMENT=95854 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `detail` */

DROP TABLE IF EXISTS `detail`;

CREATE TABLE `detail` (
  `b_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `b_name` VARCHAR(60) CHARACTER SET utf8 DEFAULT NULL COMMENT '书名',
  `author` VARCHAR(60) CHARACTER SET utf8 DEFAULT '未知' COMMENT '作者',
  `desc` TEXT COMMENT '书籍描述',
  `cat` VARCHAR(20) CHARACTER SET utf8 DEFAULT '未知' COMMENT '分类',
  `cover` VARCHAR(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '封面地址',
  `state` VARCHAR(20) CHARACTER SET utf8 DEFAULT '未知' COMMENT '状态',
  `link` VARCHAR(255) CHARACTER SET utf8 NOT NULL UNIQUE COMMENT '来源地址',
  PRIMARY KEY (`b_id`)
) ENGINE=INNODB AUTO_INCREMENT=1328 DEFAULT CHARSET=utf8mb4;


