/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50617
Source Host           : 123.57.228.214:3306
Source Database       : bms

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2018-05-09 13:13:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `book_name` varchar(50) NOT NULL COMMENT '图书名称',
  `book_des` varchar(255) DEFAULT NULL COMMENT '图书简介',
  `img` varchar(255) DEFAULT NULL COMMENT '图书照片',
  `type` int(4) DEFAULT NULL COMMENT '图书分类',
  `kind` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '类型：电子书:1;纸质书:0',
  `status` int(4) DEFAULT NULL COMMENT '状态：10可借  20 已借 40 已下架',
  `author_name` varchar(50) DEFAULT NULL COMMENT '作者名称',
  `author_des` varchar(255) DEFAULT NULL COMMENT '作者简介',
  `ebook_url` varchar(255) DEFAULT NULL COMMENT '电子书链接',
  `ebook_pwd` varchar(25) DEFAULT NULL COMMENT '电子书密码',
  `borrow_num` int(4) DEFAULT NULL COMMENT '借书天数',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `updator` varchar(20) DEFAULT NULL COMMENT '更新者',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '修改时间',
  `yn` tinyint(1) unsigned DEFAULT NULL COMMENT '是否有效 1:有效 0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='图书';

-- ----------------------------
-- Table structure for book_discuss
-- ----------------------------
DROP TABLE IF EXISTS `book_discuss`;
CREATE TABLE `book_discuss` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `book_id` int(8) NOT NULL COMMENT '图书id',
  `user_id` int(8) NOT NULL COMMENT '用户id',
  `content` text NOT NULL COMMENT '评论内容',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `updator` varchar(20) DEFAULT NULL COMMENT '更新者',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '修改时间',
  `yn` tinyint(1) unsigned DEFAULT NULL COMMENT '是否有效 1:有效 0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COMMENT='图书评论表';

-- ----------------------------
-- Table structure for book_type
-- ----------------------------
DROP TABLE IF EXISTS `book_type`;
CREATE TABLE `book_type` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` int(8) NOT NULL COMMENT '图书分类-例子：10 科技 20 技术',
  `name` varchar(50) DEFAULT NULL COMMENT '分类名称',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `updator` varchar(20) DEFAULT NULL COMMENT '更新者',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '修改时间',
  `yn` tinyint(1) unsigned DEFAULT NULL COMMENT '是否有效 1:有效 0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='图书分类';

-- ----------------------------
-- Table structure for book_user_record
-- ----------------------------
DROP TABLE IF EXISTS `book_user_record`;
CREATE TABLE `book_user_record` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `book_id` int(8) NOT NULL COMMENT '图书ID',
  `user_id` int(8) NOT NULL COMMENT '用户ID',
  `des` text COMMENT '读后感',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `updator` varchar(20) DEFAULT NULL COMMENT '更新者',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '修改时间',
  `yn` tinyint(1) unsigned DEFAULT NULL COMMENT '是否有效 1:有效 0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8 COMMENT='读后感';

-- ----------------------------
-- Table structure for borrow_book
-- ----------------------------
DROP TABLE IF EXISTS `borrow_book`;
CREATE TABLE `borrow_book` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `book_id` int(8) NOT NULL COMMENT '图书ID',
  `user_id` int(8) NOT NULL COMMENT '用户ID',
  `status` int(4) DEFAULT NULL COMMENT '状态：10已借  20 已还 30 已预约',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `updator` varchar(20) DEFAULT NULL COMMENT '更新者',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '修改时间',
  `yn` tinyint(1) unsigned DEFAULT NULL COMMENT '是否有效 1:有效 0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='借书';

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` tinyint(1) NOT NULL AUTO_INCREMENT,
  `role_key` varchar(16) NOT NULL COMMENT '角色key值',
  `role_name` varchar(8) DEFAULT NULL COMMENT '角色名称',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `yn` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `user_id` int(8) DEFAULT NULL COMMENT '用户id',
  `role_id` int(8) DEFAULT NULL COMMENT '角色id',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `yn` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(16) DEFAULT NULL COMMENT '用户密码',
  `nick_name` varchar(8) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `yn` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users_book_collect
-- ----------------------------
DROP TABLE IF EXISTS `users_book_collect`;
CREATE TABLE `users_book_collect` (
  `id` int(24) NOT NULL AUTO_INCREMENT,
  `book_id` int(8) DEFAULT NULL,
  `user_id` int(8) DEFAULT NULL,
  `type` int(1) DEFAULT NULL COMMENT '状态 1收藏 0取消收藏',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `yn` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users_detail
-- ----------------------------
DROP TABLE IF EXISTS `users_detail`;
CREATE TABLE `users_detail` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `user_id` int(8) DEFAULT NULL,
  `max_borrow_num` int(2) DEFAULT NULL,
  `max_keep_time` int(2) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `yn` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
