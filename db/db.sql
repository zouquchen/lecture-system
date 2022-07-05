/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.25 : Database - lecture_system
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`lecture_system` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `lecture_system`;

/*Table structure for table `lecture` */

DROP TABLE IF EXISTS `lecture`;

CREATE TABLE `lecture` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '讲座id',
  `title` varchar(64) NOT NULL COMMENT '讲座名称',
  `type_id` bigint DEFAULT NULL COMMENT '讲座类型id',
  `creator_id` bigint NOT NULL COMMENT '创建者id，只有创建者和管理才能修改信息',
  `state` tinyint NOT NULL COMMENT '讲座状态 0（已发布） 1 （已结束）',
  `organizer` varchar(64) DEFAULT NULL COMMENT '主办方',
  `undertaker` varchar(64) DEFAULT NULL COMMENT '承办方',
  `sponsor` varchar(64) DEFAULT NULL COMMENT '协办方',
  `space` varchar(64) NOT NULL COMMENT '讲座地点',
  `speaker` varchar(32) NOT NULL COMMENT '主讲人姓名',
  `reservation` smallint NOT NULL COMMENT '讲座容纳人数',
  `store` smallint NOT NULL COMMENT '剩余预约数量',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '讲座描述信息',
  `poster` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '海报url',
  `order_start_time` datetime NOT NULL COMMENT '讲座预约开始时间',
  `order_end_time` datetime NOT NULL COMMENT '讲座预约结束时间',
  `lecture_start_time` datetime NOT NULL COMMENT '讲座开始时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除 0（false）未删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20015 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='讲座发布表';

/*Data for the table `lecture` */

insert  into `lecture`(`id`,`title`,`type_id`,`creator_id`,`state`,`organizer`,`undertaker`,`sponsor`,`space`,`speaker`,`reservation`,`store`,`description`,`poster`,`order_start_time`,`order_end_time`,`lecture_start_time`,`is_deleted`,`create_time`,`update_time`) values 
(1,'PUA的艺术2',1,1,1,'东南大学学生会','东南大学学生会','东南大学自动化学院','四牌楼大礼堂','小张',500,497,'这是第一场精彩绝伦的讲座！！！','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F200704%2F1-200F41J910.jpg&refer=http%3A%2F%2Fimg.daimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652614434&t=a01ae28aa294ee04c2b4d30ec91a7829','2022-04-15 19:00:00','2022-06-25 19:00:00','2022-06-28 19:00:00',0,'2022-04-13 22:06:05','2022-06-26 10:11:49'),
(2,'中国的建设和中国自信3',3,1,1,'东南大学学生会','东南大学学生会','东南大学自动化学院','四牌楼大礼堂','xxx',500,497,'2这是第一场精彩绝伦的讲座！！！','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F200704%2F1-200F41J910.jpg&refer=http%3A%2F%2Fimg.daimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652614434&t=a01ae28aa294ee04c2b4d30ec91a7829','2022-04-15 19:00:00','2022-06-25 19:00:00','2022-06-28 19:00:00',0,'2022-04-13 22:06:05','2022-04-16 23:41:11'),
(3,'戏曲百戏盛典',4,1,0,'共青团东南大学委员会','东南大学学生会','','四牌楼大礼堂','xxx',400,396,'3这是第一场精彩绝伦的讲座！！！','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F200704%2F1-200F41J910.jpg&refer=http%3A%2F%2Fimg.daimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652614434&t=a01ae28aa294ee04c2b4d30ec91a7829','2022-06-15 19:00:00','2022-09-01 19:00:00','2022-09-01 19:00:00',0,'2022-04-13 22:06:05','2022-06-21 15:53:39'),
(4,'公共卫生治理',1,1,0,'东南大学学生会','东南大学学生会','','四牌楼大礼堂','xxx2',400,397,'4这是第一场精彩绝伦的讲座！！！','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F200704%2F1-200F41J910.jpg&refer=http%3A%2F%2Fimg.daimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652614434&t=a01ae28aa294ee04c2b4d30ec91a7829','2022-06-15 19:00:00','2022-09-01 19:00:00','2022-09-01 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(5,'控制情绪成就人生',5,1,0,'东南大学学生会','东南大学学生会','','四牌楼大礼堂','xxx3',400,398,'5这是第一场精彩绝伦的讲座！！！','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F200704%2F1-200F41J910.jpg&refer=http%3A%2F%2Fimg.daimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652614434&t=a01ae28aa294ee04c2b4d30ec91a7829','2022-06-15 19:00:00','2022-09-01 19:00:00','2022-09-01 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(6,'这是你熟悉的汉语吗',3,1,0,'东南大学学生会','东南大学学生会','','中山院','xxx4',400,398,'6这是第一场精彩绝伦的讲座！！！','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F200704%2F1-200F41J910.jpg&refer=http%3A%2F%2Fimg.daimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652614434&t=a01ae28aa294ee04c2b4d30ec91a7829','2022-06-15 19:00:00','2022-09-01 19:00:00','2022-09-01 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(7,'零售商业模式',1,1,0,'东南大学学生会','东南大学学生会','','四牌楼大礼堂','xxx5',400,400,'7这是第一场精彩绝伦的讲座！！！','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F200704%2F1-200F41J910.jpg&refer=http%3A%2F%2Fimg.daimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652614434&t=a01ae28aa294ee04c2b4d30ec91a7829','2022-12-30 19:00:00','2023-05-13 19:00:00','2023-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(8,'当代艺术与当代设计',4,1,0,'东南大学学生会','东南大学学生会','','四牌楼大礼堂','xxx6',400,400,'8这是第一场精彩绝伦的讲座！！！','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F200704%2F1-200F41J910.jpg&refer=http%3A%2F%2Fimg.daimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652614434&t=a01ae28aa294ee04c2b4d30ec91a7829','2022-12-30 19:00:00','2023-05-13 19:00:00','2023-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(9,'数智时代基本权利的演变与保护',2,1,0,'东南大学学生会','东南大学学生会','','四牌楼大礼堂','xxx7',400,400,'9这是第一场精彩绝伦的讲座！！！','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F200704%2F1-200F41J910.jpg&refer=http%3A%2F%2Fimg.daimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652614434&t=a01ae28aa294ee04c2b4d30ec91a7829','2022-12-30 19:00:00','2023-05-13 19:00:00','2023-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(10,'国际隐秘斗争形势和特点',3,1,0,'东南大学学生会','东南大学学生会','','线上','xxx8',400,400,'10这是第一场精彩绝伦的讲座！！！','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F200704%2F1-200F41J910.jpg&refer=http%3A%2F%2Fimg.daimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652614434&t=a01ae28aa294ee04c2b4d30ec91a7829','2022-12-30 19:00:00','2023-05-13 19:00:00','2023-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(20004,'PUA的艺术3',3,1,0,'东南大学自动化学院','东南大学自动化学院','东南大学自动化学院','撕破脸大礼堂','小张',400,400,'教你如何进行PUA','https://lecture-system.oss-cn-shanghai.aliyuncs.com/2022/05/16/20bd4a7d5bc54a808af4d1b5317cdc2c1.png','2022-12-30 19:00:00','2023-05-13 19:00:00','2023-05-13 19:00:00',0,'2022-05-16 20:18:58','2022-05-18 15:16:23'),
(20014,'12321',1,2,0,'321',NULL,NULL,'dasdas','ada',33,33,NULL,'https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/noPicture.png','2022-06-29 14:43:36','2022-06-29 14:43:37','2022-06-29 14:43:38',0,'2022-06-29 14:43:41','2022-06-29 14:43:41');

/*Table structure for table `lecture_comment` */

DROP TABLE IF EXISTS `lecture_comment`;

CREATE TABLE `lecture_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `lecture_id` bigint NOT NULL COMMENT '讲座id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `parent_id` bigint DEFAULT NULL COMMENT '父评论id',
  `root_parent_id` bigint DEFAULT NULL COMMENT '最顶级父评论',
  `content` text NOT NULL COMMENT '评论内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='讲座签到表';

/*Data for the table `lecture_comment` */

insert  into `lecture_comment`(`id`,`lecture_id`,`user_id`,`parent_id`,`root_parent_id`,`content`,`create_time`,`update_time`) values 
(1,3,3,NULL,NULL,'我觉得讲座不错~','2022-07-03 12:48:37','2022-07-01 12:48:37'),
(2,3,4,NULL,NULL,'讲座真好~','2022-07-01 22:21:20','2022-07-02 22:21:21'),
(3,3,5,1,1,'我觉得你说的不对！','2022-07-01 22:21:23','2022-07-03 22:21:24'),
(4,3,3,3,1,'你说不对就不对吗','2022-07-01 22:22:00','2022-07-03 22:22:00'),
(5,3,5,4,1,'是呀！就是不对！','2022-07-01 22:22:30','2022-07-03 22:22:30'),
(6,3,1,5,1,'不要吵了。','2022-07-03 14:27:13','2022-07-04 14:27:13'),
(21,4,1,NULL,NULL,'欢迎大家积极报名参加讲座~','2022-07-03 14:53:37','2022-07-03 14:53:37'),
(22,3,3,NULL,NULL,'嘿嘿~','2022-07-04 14:34:13','2022-07-04 14:34:13');

/*Table structure for table `lecture_data_analysis` */

DROP TABLE IF EXISTS `lecture_data_analysis`;

CREATE TABLE `lecture_data_analysis` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '讲座数据分析id',
  `lecture_id` bigint NOT NULL COMMENT '讲座id',
  `attendance` smallint DEFAULT '0' COMMENT '出席人数',
  `non_attendance` smallint DEFAULT '0' COMMENT '未出席人数',
  `count_late` smallint DEFAULT '0' COMMENT '迟到人数',
  `lecture_real_start_time` datetime DEFAULT NULL COMMENT '讲座真实开始时间',
  `lecture_real_end_time` datetime DEFAULT NULL COMMENT '讲座真实结束时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除 0（false）未删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='讲座数据分析表';

/*Data for the table `lecture_data_analysis` */

insert  into `lecture_data_analysis`(`id`,`lecture_id`,`attendance`,`non_attendance`,`count_late`,`lecture_real_start_time`,`lecture_real_end_time`,`is_deleted`,`create_time`,`update_time`) values 
(1,1,0,0,0,'2022-06-13 19:00:00','2022-06-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(2,2,0,0,0,'2022-06-13 19:00:00','2022-06-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(3,3,0,0,0,'2022-06-13 19:00:00','2022-06-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(4,4,0,0,0,'2022-06-13 19:00:00','2022-06-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(5,5,0,0,0,'2022-06-13 19:00:00','2022-06-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(6,6,0,0,0,'2022-06-13 19:00:00','2022-06-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(7,7,0,0,0,'2022-06-13 19:00:00','2022-06-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(8,8,0,0,0,'2022-06-13 19:00:00','2022-06-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(9,9,0,0,0,'2022-06-13 19:00:00','2022-06-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(10,10,0,0,0,'2022-06-13 19:00:00','2022-06-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05');

/*Table structure for table `lecture_order` */

DROP TABLE IF EXISTS `lecture_order`;

CREATE TABLE `lecture_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `lecture_id` bigint NOT NULL COMMENT '讲座id',
  `title` varchar(64) NOT NULL COMMENT '讲座名称',
  `store` smallint NOT NULL COMMENT '剩余预约数量',
  `order_start_time` datetime NOT NULL COMMENT '讲座预约开始时间',
  `order_end_time` datetime NOT NULL COMMENT '讲座预约结束时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除 0（false）未删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='讲座预约表';

/*Data for the table `lecture_order` */

insert  into `lecture_order`(`id`,`lecture_id`,`title`,`store`,`order_start_time`,`order_end_time`,`is_deleted`,`create_time`,`update_time`) values 
(1,1,'PUA的艺术',500,'2022-04-15 19:00:00','2022-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(2,2,'中国的建设和中国自信',500,'2022-04-15 19:00:00','2022-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(3,3,'戏曲百戏盛典',400,'2022-04-15 19:00:00','2022-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(4,4,'公共卫生治理',400,'2022-04-15 19:00:00','2022-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(5,5,'控制情绪成就人生',400,'2022-04-15 19:00:00','2022-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(6,6,'这是你熟悉的汉语吗',400,'2022-04-15 19:00:00','2022-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(7,7,'零售商业模式',400,'2022-04-15 19:00:00','2022-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(8,8,'当代艺术与当代设计',400,'2022-04-15 19:00:00','2022-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(9,9,'数智时代基本权利的演变与保护',400,'2022-04-15 19:00:00','2022-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05'),
(10,10,'国际隐秘斗争形势和特点',400,'2022-04-15 19:00:00','2022-05-13 19:00:00',0,'2022-04-13 22:06:05','2022-04-13 22:06:05');

/*Table structure for table `lecture_type` */

DROP TABLE IF EXISTS `lecture_type`;

CREATE TABLE `lecture_type` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '讲座类型id',
  `name` varchar(64) NOT NULL COMMENT '讲座类型名称',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除  0（false）未删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='讲座类型表';

/*Data for the table `lecture_type` */

insert  into `lecture_type`(`id`,`name`,`is_deleted`,`create_time`,`update_time`) values 
(1,'其他',0,'2022-04-13 22:06:06','2022-04-13 22:06:06'),
(2,'法律',0,'2022-04-13 22:06:06','2022-04-13 22:06:06'),
(3,'人文',0,'2022-04-13 22:06:06','2022-04-13 22:06:06'),
(4,'艺术',0,'2022-04-13 22:06:06','2022-04-13 22:06:06'),
(5,'心理',0,'2022-04-13 22:06:06','2022-04-13 22:06:06'),
(6,'政治',0,'2022-04-13 22:06:06','2022-04-13 22:06:06'),
(7,'哲学',0,'2022-04-13 22:06:06','2022-04-13 22:06:06');

/*Table structure for table `lecture_user_record` */

DROP TABLE IF EXISTS `lecture_user_record`;

CREATE TABLE `lecture_user_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `lecture_id` bigint NOT NULL COMMENT '讲座id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `sign_code_id` bigint DEFAULT NULL COMMENT '签到码id',
  `order_time` datetime NOT NULL COMMENT '预约时间',
  `sign_time` datetime DEFAULT NULL COMMENT '签到时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除  0（false）未删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `lecture_id` (`lecture_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10320 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='讲座预约记录表';

/*Data for the table `lecture_user_record` */

insert  into `lecture_user_record`(`id`,`lecture_id`,`user_id`,`sign_code_id`,`order_time`,`sign_time`,`is_deleted`,`create_time`,`update_time`) values 
(10291,3,5,12345678,'2022-06-23 16:19:10',NULL,0,'2022-06-23 16:19:10','2022-06-23 16:19:10'),
(10292,4,5,12345678,'2022-06-23 16:19:14',NULL,0,'2022-06-23 16:19:14','2022-06-23 16:19:14'),
(10293,5,5,12345678,'2022-06-23 16:19:16',NULL,0,'2022-06-23 16:19:16','2022-06-23 16:19:16'),
(10294,6,5,12345678,'2022-06-23 16:19:22',NULL,0,'2022-06-23 16:19:22','2022-06-23 16:19:22'),
(10295,3,6,12345678,'2022-06-23 16:19:40',NULL,0,'2022-06-23 16:19:40','2022-06-23 16:19:40'),
(10296,4,6,12345678,'2022-06-23 16:19:44',NULL,0,'2022-06-23 16:19:44','2022-06-23 16:19:44'),
(10297,5,6,12345678,'2022-06-23 16:19:49',NULL,0,'2022-06-23 16:19:49','2022-06-23 16:19:49'),
(10298,6,6,12345678,'2022-06-23 16:19:52',NULL,0,'2022-06-23 16:19:52','2022-06-23 16:19:52'),
(10299,1,4,12345678,'2022-06-23 16:23:19','2022-06-23 16:24:45',0,'2022-06-23 16:23:19','2022-06-23 16:23:19'),
(10300,2,4,12345678,'2022-06-23 16:23:31','2022-06-23 16:26:05',0,'2022-06-23 16:23:31','2022-06-23 16:23:31'),
(10301,1,5,12345678,'2022-06-23 16:23:46','2022-06-23 16:24:50',0,'2022-06-23 16:23:46','2022-06-23 16:23:46'),
(10302,2,5,12345678,'2022-06-23 16:23:51',NULL,0,'2022-06-23 16:23:51','2022-06-23 16:23:51'),
(10303,1,6,12345678,'2022-06-23 16:24:10',NULL,0,'2022-06-23 16:24:10','2022-06-23 16:24:10'),
(10304,2,6,12345678,'2022-06-23 16:24:14',NULL,0,'2022-06-23 16:24:14','2022-06-23 16:24:14'),
(10314,3,4,12345678,'2022-06-25 16:20:00',NULL,0,'2022-06-25 16:20:00','2022-06-25 16:20:00'),
(10318,3,3,12345678,'2022-07-03 14:50:38',NULL,0,'2022-07-03 14:50:38','2022-07-03 14:50:38'),
(10319,4,4,12345678,'2022-07-04 14:19:18',NULL,0,'2022-07-04 14:19:18','2022-07-04 14:19:18');

/*Table structure for table `sys_md` */

DROP TABLE IF EXISTS `sys_md`;

CREATE TABLE `sys_md` (
  `id` bigint NOT NULL,
  `name` varchar(60) NOT NULL COMMENT '文件名',
  `md` text COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_md` */

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `menu_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '菜单名',
  `path` varchar(200) DEFAULT NULL COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单表';

/*Data for the table `sys_menu` */

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `role_key` varchar(100) DEFAULT NULL COMMENT '角色权限字符串',
  `status` char(1) DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`role_key`,`status`,`is_deleted`,`create_time`,`update_time`,`remark`) values 
(1,'学生','student','0',0,NULL,NULL,'普通学生所拥有的权限'),
(2,'学生会管理人员','manager','0',0,NULL,NULL,'普通学生，同时可以发布讲座信息，检查讲座情况'),
(3,'后台管理人员','admin','0',0,NULL,NULL,'拥有所有权限的管理人员');

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `menu_id` bigint NOT NULL DEFAULT '0' COMMENT '菜单id',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_role_menu` */

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `password` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phone_number` varchar(32) DEFAULT NULL COMMENT '手机号',
  `sex` tinyint(1) DEFAULT NULL COMMENT '用户性别（0男，1女）',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `user_type` char(1) NOT NULL DEFAULT '1' COMMENT '用户类型（0管理员，1普通用户）',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`status`,`email`,`phone_number`,`sex`,`avatar`,`user_type`,`is_deleted`,`create_time`,`update_time`) values 
(1,'admin','$2a$10$pCusntRi6u/nlIlxY2jRT.TjiRQqCXcUyFZOsTZfTK1OaKj2hK57u','0','670953242@qq.com','13063615252',0,'http://www.weixintouxiang.cn/weixin/20140607090832328.gif','0',0,NULL,NULL),
(2,'manager','$2a$10$AJUfqCEQrI1H1k88eRlddOHGVEucaqn9L.lDFvM4YZFqMZ2x0zGou','0',NULL,NULL,0,'http://www.weixintouxiang.cn/weixin/201406061603126277.jpg','0',0,NULL,NULL),
(3,'student0','$2a$10$NOIGGyky/vYSxgc2j6z3FuEVP8nMxZk5OuaWVjN4FUNRcqK2XEsYe','0',NULL,NULL,NULL,'http://www.weixintouxiang.cn/weixin/201406070927382979.jpg','1',0,NULL,NULL),
(4,'student1','$2a$10$NOIGGyky/vYSxgc2j6z3FuEVP8nMxZk5OuaWVjN4FUNRcqK2XEsYe','0',NULL,NULL,0,'http://www.weixintouxiang.cn/weixin/201406070935054859.jpg','1',0,NULL,NULL),
(5,'student2','$2a$10$NOIGGyky/vYSxgc2j6z3FuEVP8nMxZk5OuaWVjN4FUNRcqK2XEsYe','0',NULL,NULL,NULL,'http://www.weixintouxiang.cn/weixin/201406070934044443.jpg','1',0,NULL,NULL),
(6,'student3','$2a$10$NOIGGyky/vYSxgc2j6z3FuEVP8nMxZk5OuaWVjN4FUNRcqK2XEsYe','0',NULL,NULL,NULL,'http://www.weixintouxiang.cn/weixin/201406070924012606.jpg','1',0,NULL,NULL);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `role_id` bigint NOT NULL DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`) values 
(1,3),
(2,2),
(3,1),
(4,1),
(5,1),
(6,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
