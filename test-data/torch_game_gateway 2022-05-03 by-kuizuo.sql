/*
 Navicat Premium Data Transfer

 Source Server         : phpstudy
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : torch_game_gateway

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 03/05/2022 18:09:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for call_log
-- ----------------------------
DROP TABLE IF EXISTS `call_log`;
CREATE TABLE `call_log`  (
  `id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `gateway_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `gateway_host` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `gateway_port` mediumint(9) NOT NULL,
  `gateway_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `route_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `route_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `route_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `route_port` mediumint(9) NOT NULL,
  `route_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `route_creation_datetime` datetime(0) NULL DEFAULT NULL,
  `route_update_datetime` datetime(0) NULL DEFAULT NULL,
  `service_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `success` tinyint(1) NOT NULL DEFAULT 0,
  `start_timestamp` bigint(20) NOT NULL DEFAULT -1,
  `consume_time` smallint(6) NOT NULL,
  `timestamp` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of call_log
-- ----------------------------
INSERT INTO `call_log` VALUES ('4b715a8a7ca4421f9ee62c8008cf8b52', 'b1e04ff22e1d45909bb3433edd7004e9', 'eureka8888.com', 8081, '/service/service-99', '123', NULL, 'www.10086.cn', 80, 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', 1, 1651327069893, 73, 1651327069893);
INSERT INTO `call_log` VALUES ('4e344fda6f2b4ddcb468506e1d03df6c', 'e2184dcad7fe47e8ba37bf6d3c055272', 'eureka8888.com', 8081, '/service/service-99', '123', '中国移动-用户协议获取服务', 'www.10086.cn', 80, 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', 1, 1651331785525, 268, 1651327069893);
INSERT INTO `call_log` VALUES ('5cd5c166937745fe97a2cf0db4e9ebc9', '8eac454fa62b4be8af019ba7c473df86', 'eureka8888.com', 8081, '/service/service-99', '123', '中国移动-用户协议获取服务', 'www.10086.cn', 80, 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', 1, 1651331500387, 5598, 1651327069893);
INSERT INTO `call_log` VALUES ('72ef83a519e24204bf65a0f3c4f52fe6', 'e2184dcad7fe47e8ba37bf6d3c055272', 'eureka8888.com', 8081, '/service/service-99', '123', '中国移动-用户协议获取服务', 'www.10086.cn', 80, 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', 1, 1651331784962, 80, 1651331784962);
INSERT INTO `call_log` VALUES ('77b510bc1d7b4495a7bfb33818120f33', '8eac454fa62b4be8af019ba7c473df86', 'eureka8888.com', 8081, '/service/service-99', '123', '中国移动-用户协议获取服务', 'www.10086.cn', 80, 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', 1, 1651331519529, 66, 1651331784962);
INSERT INTO `call_log` VALUES ('8518f6601139494bb4c8f0a61995cbf5', 'b1e04ff22e1d45909bb3433edd7004e9', 'eureka8888.com', 8081, '/service/service-99', '123', NULL, 'www.10086.cn', 80, 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', 1, 1651327061101, 5625, 1651331784962);
INSERT INTO `call_log` VALUES ('c71caf98a993447bb5999da15a875500', 'e2184dcad7fe47e8ba37bf6d3c055272', 'eureka8888.com', 8081, '/service/service-99', '123', '中国移动-用户协议获取服务', 'www.10086.cn', 80, 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json', '2022-02-02 19:10:10', '2022-02-02 19:10:10', 'service-99', 1, 1651331778523, 5672, 1651331784962);

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `kee` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `val` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES (1, '备用集群激活阈值', 'backup_enable_threshold', '2', NULL);
INSERT INTO `config` VALUES (2, '启用限流时熔断', 'enable_fusing_on_limiting', 'true', NULL);
INSERT INTO `config` VALUES (3, '熔断阈值', 'fusing_threshold', '3', NULL);
INSERT INTO `config` VALUES (4, '调用失败计数周期', 'fusing_timeout', '2', NULL);
INSERT INTO `config` VALUES (5, '熔断功能时间单位', 'fusing_timeunit', 'MINUTES', NULL);
INSERT INTO `config` VALUES (6, '限流阈值', 'limiting_threshold', '5', '到达即触发限流');
INSERT INTO `config` VALUES (7, '限流等待时长', 'limiting_timeout', '0', '令牌不足时，可以设置此项让请求者额外等待一段时间');
INSERT INTO `config` VALUES (8, '限流等待时间单位', 'limiting_time_unit', 'SECONDS', NULL);

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
INSERT INTO `hibernate_sequence` VALUES (8);

-- ----------------------------
-- Table structure for route
-- ----------------------------
DROP TABLE IF EXISTS `route`;
CREATE TABLE `route`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `extra` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `port` int(11) NULL DEFAULT NULL,
  `protocol` int(11) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `service_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `status` bit(1) NULL DEFAULT NULL,
  `update_time` datetime(6) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `service_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKgqvo1b979ul6f5b26ye33br26`(`service_id`) USING BTREE,
  CONSTRAINT `FKgqvo1b979ul6f5b26ye33br26` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of route
-- ----------------------------
INSERT INTO `route` VALUES ('46b1982ea2224df4b44668425ec2ab93', '2022-05-03 15:10:02.804000', NULL, '2', 2, 2, NULL, 'service-01', b'1', '2022-05-03 15:10:02.804000', '2', 1);
INSERT INTO `route` VALUES ('6fd7543ea55d417a807250b62901be0c', '2022-05-03 15:09:51.584000', NULL, '1', 1, 0, NULL, 'service-01', b'1', '2022-05-03 15:09:51.584000', '1', 1);
INSERT INTO `route` VALUES ('bb162029506446b0adfe1f722d353318', '2022-04-30 22:57:41.394000', NULL, 'localhost', 1234, 1, NULL, 'service-01', b'1', '2022-04-30 22:57:41.394000', '/test', 1);

-- ----------------------------
-- Table structure for service
-- ----------------------------
DROP TABLE IF EXISTS `service`;
CREATE TABLE `service`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `status` bit(1) NULL DEFAULT NULL,
  `update_time` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service
-- ----------------------------
INSERT INTO `service` VALUES (1, NULL, '2022-04-30 22:45:53.539000', 'service-01', NULL, b'1', '2022-04-30 22:45:53.539000');
INSERT INTO `service` VALUES (2, NULL, '2022-04-30 22:52:22.072000', 'service-02', NULL, b'1', '2022-04-30 22:52:22.072000');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NULL DEFAULT NULL,
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `updated_at` datetime(6) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `update_time` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, NULL, '$2a$10$V4MOsyoRe41d8l9fnqIPyO6dnctK7QJ9VHTqU7pFcunVpOVZzMBga', NULL, 'admin', NULL, '2022-05-01 15:29:49.040000');
INSERT INTO `user` VALUES (2, NULL, '$2a$10$ObNqvVuRKu7cNM2F5RWd7.Z.P1tD9Xx89VOklryG8J3lrNy/F3aBq', NULL, 'zeyu12', NULL, '2022-05-01 15:29:57.946000');
INSERT INTO `user` VALUES (4, NULL, '$2a$10$z9.UM/omYfFRFV/MSBNTU.TsDHUSDymm9kV8pkFogUPdydNDV8Dbe', NULL, 'kuizuo1', '2022-05-01 15:29:51.966000', '2022-05-01 15:29:51.966000');

-- ----------------------------
-- Table structure for user_auth
-- ----------------------------
DROP TABLE IF EXISTS `user_auth`;
CREATE TABLE `user_auth`  (
  `user_id` int(11) NOT NULL,
  `auth` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_auth
-- ----------------------------
INSERT INTO `user_auth` VALUES (1, 'admin');

-- ----------------------------
-- Table structure for user_service
-- ----------------------------
DROP TABLE IF EXISTS `user_service`;
CREATE TABLE `user_service`  (
  `user_id` bigint(20) NOT NULL,
  `service_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `service_id`) USING BTREE,
  INDEX `FKh3j8qx28ovbd1pxoyetawnns6`(`service_id`) USING BTREE,
  CONSTRAINT `FKh3j8qx28ovbd1pxoyetawnns6` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKptefqjlemxwhxfuqa23ht4tos` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_service
-- ----------------------------
INSERT INTO `user_service` VALUES (1, 1);
INSERT INTO `user_service` VALUES (2, 1);
INSERT INTO `user_service` VALUES (1, 2);

SET FOREIGN_KEY_CHECKS = 1;
