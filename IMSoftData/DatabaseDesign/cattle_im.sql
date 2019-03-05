/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : cattle_im

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 29/01/2019 15:42:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_group
-- ----------------------------
DROP TABLE IF EXISTS `auth_group`;
CREATE TABLE `auth_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auth_group_permissions
-- ----------------------------
DROP TABLE IF EXISTS `auth_group_permissions`;
CREATE TABLE `auth_group_permissions`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `auth_group_permissions_group_id_permission_id_0cd325b0_uniq`(`group_id`, `permission_id`) USING BTREE,
  INDEX `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm`(`permission_id`) USING BTREE,
  CONSTRAINT `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `auth_group_permissions_group_id_b120cbf9_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auth_permission
-- ----------------------------
DROP TABLE IF EXISTS `auth_permission`;
CREATE TABLE `auth_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content_type_id` int(11) NOT NULL,
  `codename` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `auth_permission_content_type_id_codename_01ab375a_uniq`(`content_type_id`, `codename`) USING BTREE,
  CONSTRAINT `auth_permission_content_type_id_2f476e4b_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 80 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_permission
-- ----------------------------
INSERT INTO `auth_permission` VALUES (1, 'Can add log entry', 1, 'add_logentry');
INSERT INTO `auth_permission` VALUES (2, 'Can change log entry', 1, 'change_logentry');
INSERT INTO `auth_permission` VALUES (3, 'Can delete log entry', 1, 'delete_logentry');
INSERT INTO `auth_permission` VALUES (4, 'Can view log entry', 1, 'view_logentry');
INSERT INTO `auth_permission` VALUES (5, 'Can add permission', 2, 'add_permission');
INSERT INTO `auth_permission` VALUES (6, 'Can change permission', 2, 'change_permission');
INSERT INTO `auth_permission` VALUES (7, 'Can delete permission', 2, 'delete_permission');
INSERT INTO `auth_permission` VALUES (8, 'Can view permission', 2, 'view_permission');
INSERT INTO `auth_permission` VALUES (9, 'Can add group', 3, 'add_group');
INSERT INTO `auth_permission` VALUES (10, 'Can change group', 3, 'change_group');
INSERT INTO `auth_permission` VALUES (11, 'Can delete group', 3, 'delete_group');
INSERT INTO `auth_permission` VALUES (12, 'Can view group', 3, 'view_group');
INSERT INTO `auth_permission` VALUES (13, 'Can add content type', 4, 'add_contenttype');
INSERT INTO `auth_permission` VALUES (14, 'Can change content type', 4, 'change_contenttype');
INSERT INTO `auth_permission` VALUES (15, 'Can delete content type', 4, 'delete_contenttype');
INSERT INTO `auth_permission` VALUES (16, 'Can view content type', 4, 'view_contenttype');
INSERT INTO `auth_permission` VALUES (17, 'Can add session', 5, 'add_session');
INSERT INTO `auth_permission` VALUES (18, 'Can change session', 5, 'change_session');
INSERT INTO `auth_permission` VALUES (19, 'Can delete session', 5, 'delete_session');
INSERT INTO `auth_permission` VALUES (20, 'Can view session', 5, 'view_session');
INSERT INTO `auth_permission` VALUES (21, 'Can add 用户', 6, 'add_user');
INSERT INTO `auth_permission` VALUES (22, 'Can change 用户', 6, 'change_user');
INSERT INTO `auth_permission` VALUES (23, 'Can delete 用户', 6, 'delete_user');
INSERT INTO `auth_permission` VALUES (24, 'Can view 用户', 6, 'view_user');
INSERT INTO `auth_permission` VALUES (25, 'Can add apply', 7, 'add_apply');
INSERT INTO `auth_permission` VALUES (26, 'Can change apply', 7, 'change_apply');
INSERT INTO `auth_permission` VALUES (27, 'Can delete apply', 7, 'delete_apply');
INSERT INTO `auth_permission` VALUES (28, 'Can view apply', 7, 'view_apply');
INSERT INTO `auth_permission` VALUES (29, 'Can add event', 8, 'add_event');
INSERT INTO `auth_permission` VALUES (30, 'Can change event', 8, 'change_event');
INSERT INTO `auth_permission` VALUES (31, 'Can delete event', 8, 'delete_event');
INSERT INTO `auth_permission` VALUES (32, 'Can view event', 8, 'view_event');
INSERT INTO `auth_permission` VALUES (33, 'Can add friends', 9, 'add_friends');
INSERT INTO `auth_permission` VALUES (34, 'Can change friends', 9, 'change_friends');
INSERT INTO `auth_permission` VALUES (35, 'Can delete friends', 9, 'delete_friends');
INSERT INTO `auth_permission` VALUES (36, 'Can view friends', 9, 'view_friends');
INSERT INTO `auth_permission` VALUES (37, 'Can add group', 10, 'add_group');
INSERT INTO `auth_permission` VALUES (38, 'Can change group', 10, 'change_group');
INSERT INTO `auth_permission` VALUES (39, 'Can delete group', 10, 'delete_group');
INSERT INTO `auth_permission` VALUES (40, 'Can view group', 10, 'view_group');
INSERT INTO `auth_permission` VALUES (41, 'Can add group member', 11, 'add_groupmember');
INSERT INTO `auth_permission` VALUES (42, 'Can change group member', 11, 'change_groupmember');
INSERT INTO `auth_permission` VALUES (43, 'Can delete group member', 11, 'delete_groupmember');
INSERT INTO `auth_permission` VALUES (44, 'Can view group member', 11, 'view_groupmember');
INSERT INTO `auth_permission` VALUES (45, 'Can add link comment', 12, 'add_linkcomment');
INSERT INTO `auth_permission` VALUES (46, 'Can change link comment', 12, 'change_linkcomment');
INSERT INTO `auth_permission` VALUES (47, 'Can delete link comment', 12, 'delete_linkcomment');
INSERT INTO `auth_permission` VALUES (48, 'Can view link comment', 12, 'view_linkcomment');
INSERT INTO `auth_permission` VALUES (49, 'Can add link complete', 13, 'add_linkcomplete');
INSERT INTO `auth_permission` VALUES (50, 'Can change link complete', 13, 'change_linkcomplete');
INSERT INTO `auth_permission` VALUES (51, 'Can delete link complete', 13, 'delete_linkcomplete');
INSERT INTO `auth_permission` VALUES (52, 'Can view link complete', 13, 'view_linkcomplete');
INSERT INTO `auth_permission` VALUES (53, 'Can add link member', 14, 'add_linkmember');
INSERT INTO `auth_permission` VALUES (54, 'Can change link member', 14, 'change_linkmember');
INSERT INTO `auth_permission` VALUES (55, 'Can delete link member', 14, 'delete_linkmember');
INSERT INTO `auth_permission` VALUES (56, 'Can view link member', 14, 'view_linkmember');
INSERT INTO `auth_permission` VALUES (57, 'Can add link task', 15, 'add_linktask');
INSERT INTO `auth_permission` VALUES (58, 'Can change link task', 15, 'change_linktask');
INSERT INTO `auth_permission` VALUES (59, 'Can delete link task', 15, 'delete_linktask');
INSERT INTO `auth_permission` VALUES (60, 'Can view link task', 15, 'view_linktask');
INSERT INTO `auth_permission` VALUES (61, 'Can add profile', 16, 'add_profile');
INSERT INTO `auth_permission` VALUES (62, 'Can change profile', 16, 'change_profile');
INSERT INTO `auth_permission` VALUES (63, 'Can delete profile', 16, 'delete_profile');
INSERT INTO `auth_permission` VALUES (64, 'Can view profile', 16, 'view_profile');
INSERT INTO `auth_permission` VALUES (65, 'Can add push history', 17, 'add_pushhistory');
INSERT INTO `auth_permission` VALUES (66, 'Can change push history', 17, 'change_pushhistory');
INSERT INTO `auth_permission` VALUES (67, 'Can delete push history', 17, 'delete_pushhistory');
INSERT INTO `auth_permission` VALUES (68, 'Can view push history', 17, 'view_pushhistory');
INSERT INTO `auth_permission` VALUES (69, 'Can add tag', 18, 'add_tag');
INSERT INTO `auth_permission` VALUES (70, 'Can change tag', 18, 'change_tag');
INSERT INTO `auth_permission` VALUES (71, 'Can delete tag', 18, 'delete_tag');
INSERT INTO `auth_permission` VALUES (72, 'Can view tag', 18, 'view_tag');
INSERT INTO `auth_permission` VALUES (73, 'Can add tag member', 19, 'add_tagmember');
INSERT INTO `auth_permission` VALUES (74, 'Can change tag member', 19, 'change_tagmember');
INSERT INTO `auth_permission` VALUES (75, 'Can delete tag member', 19, 'delete_tagmember');
INSERT INTO `auth_permission` VALUES (76, 'Can view tag member', 19, 'view_tagmember');
INSERT INTO `auth_permission` VALUES (77, 'Can add time line', 20, 'add_timeline');
INSERT INTO `auth_permission` VALUES (78, 'Can change time line', 20, 'change_timeline');
INSERT INTO `auth_permission` VALUES (79, 'Can delete time line', 20, 'delete_timeline');
INSERT INTO `auth_permission` VALUES (80, 'Can view time line', 20, 'view_timeline');

-- ----------------------------
-- Table structure for db_apply
-- ----------------------------
DROP TABLE IF EXISTS `db_apply`;
CREATE TABLE `db_apply`  (
  `aid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `targetId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `attach` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(11) NOT NULL,
  `create_time` datetime(6) NOT NULL,
  `update_time` datetime(6) NOT NULL,
  `application_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`aid`) USING BTREE,
  INDEX `db_apply_application_id_412dd2c9_fk_db_user_uid`(`application_id`) USING BTREE,
  CONSTRAINT `db_apply_application_id_412dd2c9_fk_db_user_uid` FOREIGN KEY (`application_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_event
-- ----------------------------
DROP TABLE IF EXISTS `db_event`;
CREATE TABLE `db_event`  (
  `eid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `attach` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(11) NOT NULL,
  `update_time` datetime(6) NOT NULL,
  `group_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `link_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `receive_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `send_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tag_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`eid`) USING BTREE,
  INDEX `db_event_group_id_e4ae43e3_fk_db_group_gid`(`group_id`) USING BTREE,
  INDEX `db_event_link_id_c5bec343_fk_db_linktask_lid`(`link_id`) USING BTREE,
  INDEX `db_event_receive_id_ab81525b_fk_db_user_uid`(`receive_id`) USING BTREE,
  INDEX `db_event_send_id_cdfb44ac_fk_db_user_uid`(`send_id`) USING BTREE,
  INDEX `db_event_tag_id_cc004c84_fk_db_tag_tid`(`tag_id`) USING BTREE,
  CONSTRAINT `db_event_group_id_e4ae43e3_fk_db_group_gid` FOREIGN KEY (`group_id`) REFERENCES `db_group` (`gid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_event_link_id_c5bec343_fk_db_linktask_lid` FOREIGN KEY (`link_id`) REFERENCES `db_linktask` (`lid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_event_receive_id_ab81525b_fk_db_user_uid` FOREIGN KEY (`receive_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_event_send_id_cdfb44ac_fk_db_user_uid` FOREIGN KEY (`send_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_event_tag_id_cc004c84_fk_db_tag_tid` FOREIGN KEY (`tag_id`) REFERENCES `db_tag` (`tid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_friends
-- ----------------------------
DROP TABLE IF EXISTS `db_friends`;
CREATE TABLE `db_friends`  (
  `fid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `alias` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `origin_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `target_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`fid`) USING BTREE,
  INDEX `db_friends_origin_id_02f9196c_fk_db_user_uid`(`origin_id`) USING BTREE,
  INDEX `db_friends_target_id_f15f7d14_fk_db_user_uid`(`target_id`) USING BTREE,
  CONSTRAINT `db_friends_origin_id_02f9196c_fk_db_user_uid` FOREIGN KEY (`origin_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_friends_target_id_f15f7d14_fk_db_user_uid` FOREIGN KEY (`target_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_friends
-- ----------------------------
INSERT INTO `db_friends` VALUES ('033deef771d341b9be4ee97ef44f3be3', 'njkkkame', 'c255d40bd992438eb4f1896cce4bc072', 'c255d40bd992438eb4f1896cce4bc072');
INSERT INTO `db_friends` VALUES ('24bc5a375c3643be97a5a246a955a120', '', '49b7f14296e04891bdbee4f78068f760', 'c255d40bd992438eb4f1896cce4bc072');
INSERT INTO `db_friends` VALUES ('2fee0e7b195b4189834efd828b94061f', '', 'd433d9f6edb74b25b7e8f969dfb5bcda', '49b7f14296e04891bdbee4f78068f760');
INSERT INTO `db_friends` VALUES ('8773d86a2d074800807ad62597f12828', 'najklme', '49b7f14296e04891bdbee4f78068f760', '49b7f14296e04891bdbee4f78068f760');
INSERT INTO `db_friends` VALUES ('8dc596e47b18496a95dab49fb70101d3', 'na哦噢me', 'd433d9f6edb74b25b7e8f969dfb5bcda', 'd433d9f6edb74b25b7e8f969dfb5bcda');
INSERT INTO `db_friends` VALUES ('bca17de0b399430d9cdfba48fddb1c08', '', 'd433d9f6edb74b25b7e8f969dfb5bcda', 'c255d40bd992438eb4f1896cce4bc072');

-- ----------------------------
-- Table structure for db_group
-- ----------------------------
DROP TABLE IF EXISTS `db_group`;
CREATE TABLE `db_group`  (
  `gid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `picture` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_at` datetime(6) NOT NULL,
  `owner_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`gid`) USING BTREE,
  INDEX `db_group_owner_id_7d7cf589_fk_db_user_uid`(`owner_id`) USING BTREE,
  CONSTRAINT `db_group_owner_id_7d7cf589_fk_db_user_uid` FOREIGN KEY (`owner_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_groupmember
-- ----------------------------
DROP TABLE IF EXISTS `db_groupmember`;
CREATE TABLE `db_groupmember`  (
  `mid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `alias` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permission_type` int(11) NOT NULL,
  `notify_level` int(11) NOT NULL,
  `createAt` datetime(6) NOT NULL,
  `upDateAt` datetime(6) NOT NULL,
  `group_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`mid`) USING BTREE,
  INDEX `db_groupmember_group_id_5af73f65_fk_db_group_gid`(`group_id`) USING BTREE,
  INDEX `db_groupmember_user_id_77583520_fk_db_user_uid`(`user_id`) USING BTREE,
  CONSTRAINT `db_groupmember_group_id_5af73f65_fk_db_group_gid` FOREIGN KEY (`group_id`) REFERENCES `db_group` (`gid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_groupmember_user_id_77583520_fk_db_user_uid` FOREIGN KEY (`user_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_linkcomment
-- ----------------------------
DROP TABLE IF EXISTS `db_linkcomment`;
CREATE TABLE `db_linkcomment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) NOT NULL,
  `comment_id` int(11) NOT NULL,
  `link_from_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `link_id_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `link_to_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `db_linkcomment_comment_id_7589c4ef_fk_db_linkcomment_id`(`comment_id`) USING BTREE,
  INDEX `db_linkcomment_link_from_id_1b58957a_fk_db_linkmember_mid`(`link_from_id`) USING BTREE,
  INDEX `db_linkcomment_link_id_id_c225e2e1_fk_db_linktask_lid`(`link_id_id`) USING BTREE,
  INDEX `db_linkcomment_link_to_id_6d84f383_fk_db_linkmember_mid`(`link_to_id`) USING BTREE,
  CONSTRAINT `db_linkcomment_comment_id_7589c4ef_fk_db_linkcomment_id` FOREIGN KEY (`comment_id`) REFERENCES `db_linkcomment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_linkcomment_link_from_id_1b58957a_fk_db_linkmember_mid` FOREIGN KEY (`link_from_id`) REFERENCES `db_linkmember` (`mid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_linkcomment_link_id_id_c225e2e1_fk_db_linktask_lid` FOREIGN KEY (`link_id_id`) REFERENCES `db_linktask` (`lid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_linkcomment_link_to_id_6d84f383_fk_db_linkmember_mid` FOREIGN KEY (`link_to_id`) REFERENCES `db_linkmember` (`mid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_linkcomplete
-- ----------------------------
DROP TABLE IF EXISTS `db_linkcomplete`;
CREATE TABLE `db_linkcomplete`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attach` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(11) NOT NULL,
  `create_time` datetime(6) NOT NULL,
  `receive_id_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `db_linkcomplete_receive_id_id_d68ab894_fk_db_linktask_lid`(`receive_id_id`) USING BTREE,
  CONSTRAINT `db_linkcomplete_receive_id_id_d68ab894_fk_db_linktask_lid` FOREIGN KEY (`receive_id_id`) REFERENCES `db_linktask` (`lid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_linkmember
-- ----------------------------
DROP TABLE IF EXISTS `db_linkmember`;
CREATE TABLE `db_linkmember`  (
  `mid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_up` tinyint(1) NOT NULL,
  `is_remind` tinyint(1) NOT NULL,
  `link_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`mid`) USING BTREE,
  INDEX `db_linkmember_link_id_76bda7a3_fk_db_linktask_lid`(`link_id`) USING BTREE,
  INDEX `db_linkmember_user_id_e468f371_fk_db_user_uid`(`user_id`) USING BTREE,
  CONSTRAINT `db_linkmember_link_id_76bda7a3_fk_db_linktask_lid` FOREIGN KEY (`link_id`) REFERENCES `db_linktask` (`lid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_linkmember_user_id_e468f371_fk_db_user_uid` FOREIGN KEY (`user_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_linktask
-- ----------------------------
DROP TABLE IF EXISTS `db_linktask`;
CREATE TABLE `db_linktask`  (
  `lid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(11) NOT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `attach` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `member_count` int(11) NOT NULL,
  `create_time` datetime(6) NOT NULL,
  `start_time` datetime(6) NOT NULL,
  `end_time` datetime(6) NOT NULL,
  `create_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `group_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tag_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`lid`) USING BTREE,
  INDEX `db_linktask_create_id_dd5b6f96_fk_db_user_uid`(`create_id`) USING BTREE,
  INDEX `db_linktask_group_id_110e9a93_fk_db_group_gid`(`group_id`) USING BTREE,
  INDEX `db_linktask_tag_id_be9fbaa5_fk_db_tag_tid`(`tag_id`) USING BTREE,
  CONSTRAINT `db_linktask_create_id_dd5b6f96_fk_db_user_uid` FOREIGN KEY (`create_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_linktask_group_id_110e9a93_fk_db_group_gid` FOREIGN KEY (`group_id`) REFERENCES `db_group` (`gid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_linktask_tag_id_be9fbaa5_fk_db_tag_tid` FOREIGN KEY (`tag_id`) REFERENCES `db_tag` (`tid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_profile
-- ----------------------------
DROP TABLE IF EXISTS `db_profile`;
CREATE TABLE `db_profile`  (
  `push_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_bind` tinyint(1) NOT NULL,
  `sex` int(11) NULL DEFAULT NULL,
  `desc` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`push_id`) USING BTREE,
  UNIQUE INDEX `app_id`(`app_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_profile
-- ----------------------------
INSERT INTO `db_profile` VALUES ('1892959ae3d34e35881fdf0d952d3580', NULL, 0, NULL, NULL);
INSERT INTO `db_profile` VALUES ('1c224cf1e95d482db6f30b1412294912', NULL, 0, NULL, NULL);
INSERT INTO `db_profile` VALUES ('86e3b462c9da47f99d3f235e916af6ca', NULL, 0, NULL, NULL);
INSERT INTO `db_profile` VALUES ('ee416756278e46fe9e0e771c063791ea', NULL, 0, NULL, '');

-- ----------------------------
-- Table structure for db_pushhistory
-- ----------------------------
DROP TABLE IF EXISTS `db_pushhistory`;
CREATE TABLE `db_pushhistory`  (
  `pid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `entity` bigint(20) NOT NULL AUTO_INCREMENT,
  `entity_type` int(11) NOT NULL,
  `receive_push` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `arrival_time_plan` datetime(6) NOT NULL,
  `arrival_time` datetime(6) NOT NULL,
  `create_time` datetime(6) NOT NULL,
  `update_time` datetime(6) NOT NULL,
  `receive_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `send_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`entity`) USING BTREE,
  INDEX `db_pushhistory_receive_id_67490e04_fk_db_user_uid`(`receive_id`) USING BTREE,
  INDEX `db_pushhistory_send_id_c0ef7b57_fk_db_user_uid`(`send_id`) USING BTREE,
  CONSTRAINT `db_pushhistory_receive_id_67490e04_fk_db_user_uid` FOREIGN KEY (`receive_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_pushhistory_send_id_c0ef7b57_fk_db_user_uid` FOREIGN KEY (`send_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_tag
-- ----------------------------
DROP TABLE IF EXISTS `db_tag`;
CREATE TABLE `db_tag`  (
  `tid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tag_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_at` datetime(6) NOT NULL,
  `create_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `group_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tid`) USING BTREE,
  INDEX `db_tag_create_id_968a573b_fk_db_user_uid`(`create_id`) USING BTREE,
  INDEX `db_tag_group_id_84ba7873_fk_db_group_gid`(`group_id`) USING BTREE,
  CONSTRAINT `db_tag_create_id_968a573b_fk_db_user_uid` FOREIGN KEY (`create_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_tag_group_id_84ba7873_fk_db_group_gid` FOREIGN KEY (`group_id`) REFERENCES `db_group` (`gid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_tagmember
-- ----------------------------
DROP TABLE IF EXISTS `db_tagmember`;
CREATE TABLE `db_tagmember`  (
  `tmId` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tag_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`tmId`) USING BTREE,
  INDEX `db_tagmember_tag_id_1fa627a1_fk_db_tag_tid`(`tag_id`) USING BTREE,
  INDEX `db_tagmember_user_id_9fdb47ae_fk_db_user_uid`(`user_id`) USING BTREE,
  CONSTRAINT `db_tagmember_tag_id_1fa627a1_fk_db_tag_tid` FOREIGN KEY (`tag_id`) REFERENCES `db_tag` (`tid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_tagmember_user_id_9fdb47ae_fk_db_user_uid` FOREIGN KEY (`user_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_timeline
-- ----------------------------
DROP TABLE IF EXISTS `db_timeline`;
CREATE TABLE `db_timeline`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(6) NOT NULL,
  `start_time` datetime(6) NOT NULL,
  `end_time` datetime(6) NOT NULL,
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `db_timeline_start_time_end_time_26bfca36_uniq`(`start_time`, `end_time`) USING BTREE,
  INDEX `db_timeline_user_id_d062f217_fk_db_user_uid`(`user_id`) USING BTREE,
  CONSTRAINT `db_timeline_user_id_d062f217_fk_db_user_uid` FOREIGN KEY (`user_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_user
-- ----------------------------
DROP TABLE IF EXISTS `db_user`;
CREATE TABLE `db_user`  (
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_login` datetime(6) NULL DEFAULT NULL,
  `is_superuser` tinyint(1) NOT NULL,
  `uid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(6) NOT NULL,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(254) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_staff` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `date_joined` datetime(6) NOT NULL,
  `profile_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `profile_id`(`profile_id`) USING BTREE,
  CONSTRAINT `db_user_profile_id_e0210cab_fk_db_profile_push_id` FOREIGN KEY (`profile_id`) REFERENCES `db_profile` (`push_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_user
-- ----------------------------
INSERT INTO `db_user` VALUES ('pbkdf2_sha256$120000$Z6duf3vOUXAA$4g8Ckrw1cSBxukjqzzFN7CT7pgjbYYTphdTXT2uyYQ0=', '2019-01-28 04:40:08.607337', 1, '2378900e41164b4bb434695fae9b690b', '18870742138', 'avatars/catalog.png', '2018-11-14 11:21:57.643199', 'lkk', '96767@qq.com', 1, 1, '2018-11-14 11:20:51.000000', 'ee416756278e46fe9e0e771c063791ea');
INSERT INTO `db_user` VALUES ('pbkdf2_sha256$120000$2vTAIh35mwqE$xY3rtJRMwIn5PN9hgG+A2vZ077QYYkU9HLjsn2UoW0E=', '2018-11-19 09:01:47.574829', 0, '49b7f14296e04891bdbee4f78068f760', '18870742222', 'avatars/android/4874062.jpg', '2018-11-18 03:21:58.645634', 'najklme', '', 0, 1, '2018-11-18 03:21:58.563853', '1892959ae3d34e35881fdf0d952d3580');
INSERT INTO `db_user` VALUES ('pbkdf2_sha256$120000$CjN8FJb1h4dQ$8IyNzR6HwdTAVpqtDoETDVIq19YPDbdCnknZf40Sr4c=', '2018-11-14 17:10:35.649614', 0, 'c255d40bd992438eb4f1896cce4bc072', '18870742133', 'avatars/android/7454723.jpg', '2018-11-14 17:10:35.414672', 'njkkkame', '', 0, 1, '2018-11-14 17:10:35.359083', '86e3b462c9da47f99d3f235e916af6ca');
INSERT INTO `db_user` VALUES ('pbkdf2_sha256$120000$Tif67nttFICH$yiPCRNolAX7omlPST+vtCnawPRISzyqg16ksRK7QqWI=', '2018-11-30 14:47:37.264605', 0, 'd433d9f6edb74b25b7e8f969dfb5bcda', '18870746666', 'avatars/android/274221.jpg', '2018-11-18 08:05:52.239264', 'na哦噢me', '', 0, 1, '2018-11-18 08:05:52.158480', '1c224cf1e95d482db6f30b1412294912');

-- ----------------------------
-- Table structure for db_user_groups
-- ----------------------------
DROP TABLE IF EXISTS `db_user_groups`;
CREATE TABLE `db_user_groups`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `db_user_groups_user_id_group_id_4e607823_uniq`(`user_id`, `group_id`) USING BTREE,
  INDEX `db_user_groups_group_id_b432fca5_fk_auth_group_id`(`group_id`) USING BTREE,
  CONSTRAINT `db_user_groups_group_id_b432fca5_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_user_groups_user_id_6ae08316_fk_db_user_uid` FOREIGN KEY (`user_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for db_user_user_permissions
-- ----------------------------
DROP TABLE IF EXISTS `db_user_user_permissions`;
CREATE TABLE `db_user_user_permissions`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `db_user_user_permissions_user_id_permission_id_ff552965_uniq`(`user_id`, `permission_id`) USING BTREE,
  INDEX `db_user_user_permiss_permission_id_f4793bd3_fk_auth_perm`(`permission_id`) USING BTREE,
  CONSTRAINT `db_user_user_permiss_permission_id_f4793bd3_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `db_user_user_permissions_user_id_ca919cec_fk_db_user_uid` FOREIGN KEY (`user_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for django_admin_log
-- ----------------------------
DROP TABLE IF EXISTS `django_admin_log`;
CREATE TABLE `django_admin_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_time` datetime(6) NOT NULL,
  `object_id` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `object_repr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `action_flag` smallint(5) UNSIGNED NOT NULL,
  `change_message` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content_type_id` int(11) NULL DEFAULT NULL,
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `django_admin_log_content_type_id_c4bce8eb_fk_django_co`(`content_type_id`) USING BTREE,
  INDEX `django_admin_log_user_id_c564eba6_fk_db_user_uid`(`user_id`) USING BTREE,
  CONSTRAINT `django_admin_log_content_type_id_c4bce8eb_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `django_admin_log_user_id_c564eba6_fk_db_user_uid` FOREIGN KEY (`user_id`) REFERENCES `db_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of django_admin_log
-- ----------------------------
INSERT INTO `django_admin_log` VALUES (1, '2018-11-14 11:21:53.892703', 'ee416756-278e-46fe-9e0e-771c063791ea', 'Profile object (ee416756-278e-46fe-9e0e-771c063791ea)', 1, '[{\"added\": {}}]', 16, '2378900e41164b4bb434695fae9b690b');
INSERT INTO `django_admin_log` VALUES (2, '2018-11-14 11:21:57.648159', '2378900e-4116-4b4b-b434-695fae9b690b', 'lkk', 2, '[{\"changed\": {\"fields\": [\"phone\", \"avatar\", \"profile\"]}}]', 6, '2378900e41164b4bb434695fae9b690b');

-- ----------------------------
-- Table structure for django_content_type
-- ----------------------------
DROP TABLE IF EXISTS `django_content_type`;
CREATE TABLE `django_content_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_label` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `model` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `django_content_type_app_label_model_76bd3d3b_uniq`(`app_label`, `model`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of django_content_type
-- ----------------------------
INSERT INTO `django_content_type` VALUES (1, 'admin', 'logentry');
INSERT INTO `django_content_type` VALUES (3, 'auth', 'group');
INSERT INTO `django_content_type` VALUES (2, 'auth', 'permission');
INSERT INTO `django_content_type` VALUES (4, 'contenttypes', 'contenttype');
INSERT INTO `django_content_type` VALUES (7, 'db', 'apply');
INSERT INTO `django_content_type` VALUES (8, 'db', 'event');
INSERT INTO `django_content_type` VALUES (9, 'db', 'friends');
INSERT INTO `django_content_type` VALUES (10, 'db', 'group');
INSERT INTO `django_content_type` VALUES (11, 'db', 'groupmember');
INSERT INTO `django_content_type` VALUES (12, 'db', 'linkcomment');
INSERT INTO `django_content_type` VALUES (13, 'db', 'linkcomplete');
INSERT INTO `django_content_type` VALUES (14, 'db', 'linkmember');
INSERT INTO `django_content_type` VALUES (15, 'db', 'linktask');
INSERT INTO `django_content_type` VALUES (16, 'db', 'profile');
INSERT INTO `django_content_type` VALUES (17, 'db', 'pushhistory');
INSERT INTO `django_content_type` VALUES (18, 'db', 'tag');
INSERT INTO `django_content_type` VALUES (19, 'db', 'tagmember');
INSERT INTO `django_content_type` VALUES (20, 'db', 'timeline');
INSERT INTO `django_content_type` VALUES (6, 'db', 'user');
INSERT INTO `django_content_type` VALUES (5, 'sessions', 'session');

-- ----------------------------
-- Table structure for django_migrations
-- ----------------------------
DROP TABLE IF EXISTS `django_migrations`;
CREATE TABLE `django_migrations`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `applied` datetime(6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of django_migrations
-- ----------------------------
INSERT INTO `django_migrations` VALUES (1, 'contenttypes', '0001_initial', '2018-11-14 11:18:24.259796');
INSERT INTO `django_migrations` VALUES (2, 'contenttypes', '0002_remove_content_type_name', '2018-11-14 11:18:24.378478');
INSERT INTO `django_migrations` VALUES (3, 'auth', '0001_initial', '2018-11-14 11:18:24.765445');
INSERT INTO `django_migrations` VALUES (4, 'auth', '0002_alter_permission_name_max_length', '2018-11-14 11:18:24.857198');
INSERT INTO `django_migrations` VALUES (5, 'auth', '0003_alter_user_email_max_length', '2018-11-14 11:18:24.867171');
INSERT INTO `django_migrations` VALUES (6, 'auth', '0004_alter_user_username_opts', '2018-11-14 11:18:24.878143');
INSERT INTO `django_migrations` VALUES (7, 'auth', '0005_alter_user_last_login_null', '2018-11-14 11:18:24.887118');
INSERT INTO `django_migrations` VALUES (8, 'auth', '0006_require_contenttypes_0002', '2018-11-14 11:18:24.892105');
INSERT INTO `django_migrations` VALUES (9, 'auth', '0007_alter_validators_add_error_messages', '2018-11-14 11:18:24.901081');
INSERT INTO `django_migrations` VALUES (10, 'auth', '0008_alter_user_username_max_length', '2018-11-14 11:18:24.910057');
INSERT INTO `django_migrations` VALUES (11, 'auth', '0009_alter_user_last_name_max_length', '2018-11-14 11:18:24.918036');
INSERT INTO `django_migrations` VALUES (12, 'db', '0001_initial', '2018-11-14 11:18:28.923327');
INSERT INTO `django_migrations` VALUES (13, 'admin', '0001_initial', '2018-11-14 11:18:29.121798');
INSERT INTO `django_migrations` VALUES (14, 'admin', '0002_logentry_remove_auto_add', '2018-11-14 11:18:29.139749');
INSERT INTO `django_migrations` VALUES (15, 'admin', '0003_logentry_add_action_flag_choices', '2018-11-14 11:18:29.159696');
INSERT INTO `django_migrations` VALUES (16, 'sessions', '0001_initial', '2018-11-14 11:18:29.211557');

-- ----------------------------
-- Table structure for django_session
-- ----------------------------
DROP TABLE IF EXISTS `django_session`;
CREATE TABLE `django_session`  (
  `session_key` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `session_data` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `expire_date` datetime(6) NOT NULL,
  PRIMARY KEY (`session_key`) USING BTREE,
  INDEX `django_session_expire_date_a5c62663`(`expire_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
