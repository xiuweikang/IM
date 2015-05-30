/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50623
Source Host           : localhost:3306
Source Database       : myqq

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2015-05-30 12:51:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `friendlist`
-- ----------------------------
DROP TABLE IF EXISTS `friendlist`;
CREATE TABLE `friendlist` (
  `master` int(11) NOT NULL,
  `friendid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friendlist
-- ----------------------------

-- ----------------------------
-- Table structure for `savemsg`
-- ----------------------------
DROP TABLE IF EXISTS `savemsg`;
CREATE TABLE `savemsg` (
  `sendid` int(11) NOT NULL,
  `getid` int(11) DEFAULT NULL,
  `msg` varchar(1000) DEFAULT NULL,
  `trantype` tinyint(3) DEFAULT NULL,
  `time` varchar(30) DEFAULT NULL,
  `resulttype` tinyint(4) DEFAULT NULL,
  `messagetype` tinyint(4) DEFAULT NULL,
  `sendname` varchar(18) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of savemsg
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自动生成的ID',
  `account` varchar(18) NOT NULL,
  `gender` tinyint(4) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `photo` mediumblob,
  `location` varchar(50) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `isOnline` tinyint(4) DEFAULT '0',
  `password` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
