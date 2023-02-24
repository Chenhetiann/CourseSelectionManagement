/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : studentmanager

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 04/12/2022 04:04:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'Java程序设计');
INSERT INTO `course` VALUES (2, 'Web基础');
INSERT INTO `course` VALUES (3, '数据库教程');
INSERT INTO `course` VALUES (4, 'C语言程序设计');
INSERT INTO `course` VALUES (5, '计算机组成原理');
INSERT INTO `course` VALUES (6, '数据结构');
INSERT INTO `course` VALUES (7, '操作系统');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `sno` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` int(10) NOT NULL,
  `grade` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` int(10) NOT NULL,
  `courseId` bigint(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '19101780', '陈鹤天', 1, '2019', 1, 1);
INSERT INTO `student` VALUES (2, '19124382', '养夏真', 0, '2020', 0, NULL);
INSERT INTO `student` VALUES (27, '19127191', '刀新文', 1, '2019', 1, 4);
INSERT INTO `student` VALUES (28, '19178941', '景涵柳', 1, '2021', 0, NULL);
INSERT INTO `student` VALUES (29, '19125021', '源骊颖', 0, '2022', 0, NULL);
INSERT INTO `student` VALUES (30, '19144443', '牧怜双', 0, '2019', 1, 7);
INSERT INTO `student` VALUES (31, '19193387', '\r\n塔丽容', 0, '2022', 1, 1);
INSERT INTO `student` VALUES (32, '19192975', '御格', 0, '2021', 0, NULL);
INSERT INTO `student` VALUES (33, '19192467', '\r\n充志学', 1, '2020', 1, 5);
INSERT INTO `student` VALUES (34, '19156325', '益菡梅', 0, '2022', 0, NULL);
INSERT INTO `student` VALUES (35, '19158980', '\r\n澹台杉', 1, '2019', 0, NULL);
INSERT INTO `student` VALUES (36, '19170126', '家春华', 0, '2021', 0, NULL);
INSERT INTO `student` VALUES (37, '19181356', '方贝', 1, '2019', 0, NULL);
INSERT INTO `student` VALUES (38, '19146754', '荤生', 1, '2019', 0, NULL);
INSERT INTO `student` VALUES (39, '19165585', '\r\n桐晴美', 0, '2019', 0, NULL);
INSERT INTO `student` VALUES (40, '19138746', '沙诗文', 1, '2020', 0, NULL);
INSERT INTO `student` VALUES (41, '19180274', '\r\n节静婉', 0, '2021', 0, NULL);
INSERT INTO `student` VALUES (42, '19175733', '环旭', 1, '2020', 0, NULL);
INSERT INTO `student` VALUES (43, '19139011', '\r\n宜清婉', 0, '2022', 0, NULL);
INSERT INTO `student` VALUES (44, '19179888', '勤子辰', 1, '2022', 0, NULL);
INSERT INTO `student` VALUES (45, '19172097', '悉文昌', 1, '2019', 0, NULL);
INSERT INTO `student` VALUES (46, '19170976', '斐璇玑', 1, '2021', 0, NULL);
INSERT INTO `student` VALUES (47, '19150898', '盈秋巧', 0, '2021', 0, NULL);
INSERT INTO `student` VALUES (48, '19118090', '索清舒', 0, '2021', 0, NULL);
INSERT INTO `student` VALUES (49, '19161108', '熊子昂', 1, '2020', 0, NULL);
INSERT INTO `student` VALUES (50, '19145598', '夕沉', 1, '2021', 0, NULL);
INSERT INTO `student` VALUES (52, '19161171', '邓曼安', 0, '2022', 0, NULL);
INSERT INTO `student` VALUES (53, '19102491', '业吉帆', 1, '2019', 0, NULL);
INSERT INTO `student` VALUES (59, '19105075', '谷南烟', 0, '2021', 0, NULL);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `tname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `courseId` bigint(11) NOT NULL,
  `tstate` bigint(11) NOT NULL,
  `classes` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mode` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (2, '文浩', 2, 1, '12', '线下');
INSERT INTO `teacher` VALUES (3, '林雨帆', 4, 0, '13', '线上');
INSERT INTO `teacher` VALUES (5, '万宙绪', 5, 1, '11', '线上');

SET FOREIGN_KEY_CHECKS = 1;
