/*
 Navicat Premium Data Transfer

 Source Server         : phpstudy
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : gateway

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 27/04/2022 01:28:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gateway_user
-- ----------------------------
DROP TABLE IF EXISTS `gateway_user`;
CREATE TABLE `gateway_user`  (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) NULL DEFAULT NULL,
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `updated_at` datetime(6) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gateway_user
-- ----------------------------
INSERT INTO `gateway_user` VALUES (1, '2022-04-27 01:27:49.000000', 'a123456', '2022-04-27 01:27:45.000000', 'admin');
INSERT INTO `gateway_user` VALUES (2, '2022-04-27 01:27:51.000000', 'a123456', '2022-04-27 01:27:48.000000', 'test');

-- ----------------------------
-- Table structure for gateway_user_route
-- ----------------------------
DROP TABLE IF EXISTS `gateway_user_route`;
CREATE TABLE `gateway_user_route`  (
  `user_id` bigint(20) NOT NULL,
  `route_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  INDEX `FKgw89yp0rylh67df48m4hhutkq`(`user_id`) USING BTREE,
  CONSTRAINT `FKgw89yp0rylh67df48m4hhutkq` FOREIGN KEY (`user_id`) REFERENCES `gateway_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gateway_user_route
-- ----------------------------
INSERT INTO `gateway_user_route` VALUES (1, '1');
INSERT INTO `gateway_user_route` VALUES (2, '2');
INSERT INTO `gateway_user_route` VALUES (2, '1');

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (10);

-- ----------------------------
-- Table structure for route
-- ----------------------------
DROP TABLE IF EXISTS `route`;
CREATE TABLE `route`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creation_datetime` datetime(6) NULL DEFAULT NULL,
  `extra` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `port` int(11) NULL DEFAULT NULL,
  `protocol` int(11) NULL DEFAULT NULL,
  `status` bit(1) NULL DEFAULT NULL,
  `update_datetime` datetime(6) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of route
-- ----------------------------
INSERT INTO `route` VALUES (1, '2022-04-26 18:49:24.000000', '123', 'www.10086.cn', 'service-99', 80, 1, b'1', '2022-04-26 18:49:27.000000', 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '中国移动-用户协议获取服务');
INSERT INTO `route` VALUES (2, '2022-04-26 18:49:24.000000', '123', 'localhost', 'service-01', 3000, 0, b'1', '2022-04-26 18:49:27.000000', '/test', '测试');

SET FOREIGN_KEY_CHECKS = 1;
