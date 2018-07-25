/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50714
Source Host           : 127.0.0.1:3306
Source Database       : short_url_server

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-07-25 09:29:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for short_url
-- ----------------------------
DROP TABLE IF EXISTS `short_url`;
CREATE TABLE `short_url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hash_key` varchar(255) NOT NULL DEFAULT '' COMMENT '原始url 哈希码',
  `short_key` varchar(255) NOT NULL COMMENT '原始url 短码',
  `url` text NOT NULL COMMENT '原始 url',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modify_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7rdus2xj81gnokmvnotygdvk4` (`hash_key`),
  UNIQUE KEY `UK_r8ede0oqkbe2lqx3hrovujl1i` (`short_key`)
) ENGINE=MyISAM AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for short_url_visit_record
-- ----------------------------
DROP TABLE IF EXISTS `short_url_visit_record`;
CREATE TABLE `short_url_visit_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) NOT NULL COMMENT '客户端 ip',
  `short_key` varchar(255) NOT NULL COMMENT '访问短码',
  `device` varchar(255) NOT NULL COMMENT '客户端设备',
  `browser` varchar(255) NOT NULL COMMENT '客户端浏览器',
  `first_visit_flag` bit(1) NOT NULL COMMENT '是否首次访问',
  `visit_date` varchar(255) NOT NULL COMMENT '访问日期',
  `visit_time` varchar(255) NOT NULL COMMENT '访问时间',
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ip_search_index` (`ip`),
  KEY `short_key_search_index` (`short_key`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
