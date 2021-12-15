/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : chat

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 15/12/2021 11:01:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for leavemessage
-- ----------------------------
DROP TABLE IF EXISTS `leavemessage`;
CREATE TABLE `leavemessage`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `friendname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sendtime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of leavemessage
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pwd` blob NOT NULL,
  `onlineStatus` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (34, 'Frank', 0x16448540F6800CE72E1FDC4977AB3D4A, 0);
INSERT INTO `user` VALUES (35, 'Lucy', 0x9B007767C1FCD959984CF2EC7F323BC6, 0);
INSERT INTO `user` VALUES (36, 'Kevin', 0x18DBE3D7312317D18FE758C4D1B2AD42, 0);
INSERT INTO `user` VALUES (37, 'Kenny', 0x1CAAEFB55A6FE0117A6E3F662F7C024C, 0);
INSERT INTO `user` VALUES (38, 'Alice', 0xA9A75BDED88051BB796F3E17D57BE23B, 0);
INSERT INTO `user` VALUES (39, 'Tom', 0x01D493AFAA424BFCF51233ED3A155C99, 0);
INSERT INTO `user` VALUES (40, 'Gary', 0x35FD243C75FB21ADAB3899B224AF329E, 0);

SET FOREIGN_KEY_CHECKS = 1;
