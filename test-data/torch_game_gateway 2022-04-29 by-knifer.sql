/*
Navicat MySQL Data Transfer

Source Server         : 192.168.231.132
Source Server Version : 50728
Source Host           : 192.168.231.132:6670
Source Database       : torch_game_gateway

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2022-04-29 21:57:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for call_record
-- ----------------------------
DROP TABLE IF EXISTS `call_record`;
CREATE TABLE `call_record` (
  `id` char(32) NOT NULL,
  `gateway_id` char(32) NOT NULL,
  `gateway_host` varchar(64) NOT NULL,
  `gateway_port` mediumint(9) NOT NULL,
  `gateway_uri` varchar(255) NOT NULL,
  `route_id` char(32) NOT NULL,
  `route_desc` varchar(255) DEFAULT NULL,
  `route_host` varchar(255) NOT NULL,
  `route_port` mediumint(9) NOT NULL,
  `route_url` varchar(255) DEFAULT NULL,
  `route_creation_datetime` datetime DEFAULT NULL,
  `route_update_datetime` datetime DEFAULT NULL,
  `service_id` varchar(255) NOT NULL,
  `success` tinyint(1) NOT NULL DEFAULT '0',
  `start_timestamp` bigint(20) NOT NULL DEFAULT '-1',
  `call_time` smallint(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of call_record
-- ----------------------------
INSERT INTO `call_record` VALUES ('0aebf8281a6d409cabaac12f53d161c9', '3c27737a9eac435c84700a55cedc32da', 'eureka8888.com', '8081', '/service/service-99', '123', null, 'www.10086.cn', '80', 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', '1', '1651165104783', '213');
INSERT INTO `call_record` VALUES ('e2965de2cfaf4fd3887c3457527b4c36', '3c27737a9eac435c84700a55cedc32da', 'eureka8888.com', '8081', '/service/service-99', '123', null, 'www.10086.cn', '80', 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', '1', '1651165085236', '6144');

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `kee` varchar(255) NOT NULL,
  `val` varchar(255) NOT NULL,
  PRIMARY KEY (`kee`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES ('backup_enable_threshold', '2');
INSERT INTO `config` VALUES ('enable_fusing_on_limiting', 'true');
INSERT INTO `config` VALUES ('fusing_threshold', '3');
INSERT INTO `config` VALUES ('fusing_timeout', '1');
INSERT INTO `config` VALUES ('fusing_timeunit', 'MINUTES');
INSERT INTO `config` VALUES ('limiting_threshold', '5');
INSERT INTO `config` VALUES ('limiting_timeout', '0');
INSERT INTO `config` VALUES ('limiting_time_unit', 'SECONDS');

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('8');

-- ----------------------------
-- Table structure for route
-- ----------------------------
DROP TABLE IF EXISTS `route`;
CREATE TABLE `route` (
  `id` int(11) NOT NULL,
  `creation_datetime` datetime(6) DEFAULT NULL,
  `extra` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `host` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `service_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `protocol` int(11) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `update_datetime` datetime(6) DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of route
-- ----------------------------
INSERT INTO `route` VALUES ('1', '2022-04-26 18:49:24.000000', '123', 'www.10086.cn', 'service-99', '80', '1', '', '2022-04-26 18:49:27.000000', 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '中国移动-用户协议获取服务');
INSERT INTO `route` VALUES ('2', '2022-04-26 18:49:24.000000', '123', 'localhost', 'service-01', '3000', '0', '', '2022-04-26 18:49:27.000000', '/test', '测试');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `pwd` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', null, '$2a$10$ObNqvVuRKu7cNM2F5RWd7.Z.P1tD9Xx89VOklryG8J3lrNy/F3aBq', null, 'kuizuo');
INSERT INTO `user` VALUES ('2', null, '$2a$10$ObNqvVuRKu7cNM2F5RWd7.Z.P1tD9Xx89VOklryG8J3lrNy/F3aBq', null, 'zeyu12');

-- ----------------------------
-- Table structure for user_auth
-- ----------------------------
DROP TABLE IF EXISTS `user_auth`;
CREATE TABLE `user_auth` (
  `user_id` bigint(20) NOT NULL,
  `auth` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  KEY `FKgw89yp0rylh67df48m4hhutkq` (`user_id`) USING BTREE,
  CONSTRAINT `FKgw89yp0rylh67df48m4hhutkq` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_auth
-- ----------------------------
INSERT INTO `user_auth` VALUES ('1', 'admin');
INSERT INTO `user_auth` VALUES ('2', 'service-99');
INSERT INTO `user_auth` VALUES ('2', 'service-01');
INSERT INTO `user_auth` VALUES ('1', 'service-99');
INSERT INTO `user_auth` VALUES ('1', 'service-05');
