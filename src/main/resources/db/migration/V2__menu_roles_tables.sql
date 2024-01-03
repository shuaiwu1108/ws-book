
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `parent_id` bigint(20) NULL DEFAULT NULL,
                         `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                         `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, NULL, 'index', '我的首页', '2023-12-13 08:33:33', '2023-12-13 16:33:36');
INSERT INTO `menu` VALUES (3, NULL, 'system', '系统配置', '2023-12-13 08:00:16', '2023-12-13 08:00:16');
INSERT INTO `menu` VALUES (4, 3, 'user', '用户管理', '2023-12-13 08:34:09', '2023-12-13 16:34:12');
INSERT INTO `menu` VALUES (5, 3, 'role', '角色管理', '2023-12-13 08:01:24', '2023-12-13 08:01:24');
INSERT INTO `menu` VALUES (6, 3, 'menu', '菜单管理', '2023-12-13 08:01:30', '2023-12-13 08:01:30');
INSERT INTO `menu` VALUES (9, NULL, 'form', '表单', '2023-12-13 08:00:19', '2023-12-13 08:00:19');
INSERT INTO `menu` VALUES (10, NULL, 'video', '监控视频', '2023-12-13 08:00:19', '2023-12-13 08:00:19');

-- ----------------------------
-- Table structure for roles_menu
-- ----------------------------
DROP TABLE IF EXISTS `roles_menu`;
CREATE TABLE `roles_menu`  (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles_menu
-- ----------------------------
INSERT INTO `roles_menu` VALUES (1, 1);
INSERT INTO `roles_menu` VALUES (1, 3);
INSERT INTO `roles_menu` VALUES (1, 4);
INSERT INTO `roles_menu` VALUES (1, 5);
INSERT INTO `roles_menu` VALUES (1, 6);
INSERT INTO `roles_menu` VALUES (1, 9);
INSERT INTO `roles_menu` VALUES (2, 1);
INSERT INTO `roles_menu` VALUES (2, 3);
INSERT INTO `roles_menu` VALUES (2, 4);
INSERT INTO `roles_menu` VALUES (2, 5);
INSERT INTO `roles_menu` VALUES (2, 6);

-- ----------------------------
-- Table structure for users_roles
-- ----------------------------
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户 ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_roles
-- ----------------------------
INSERT INTO `users_roles` VALUES (1, 1);
INSERT INTO `users_roles` VALUES (2, 2);
INSERT INTO `users_roles` VALUES (3, 3);

SET FOREIGN_KEY_CHECKS = 1;
