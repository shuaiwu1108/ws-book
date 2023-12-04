SET FOREIGN_KEY_CHECKS = 0;
SET NAMES utf8mb4;

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '书籍地址',
                         `author_id` bigint(20) NULL DEFAULT NULL,
                         `description` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '书籍介绍',
                         `work_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作品状态',
                         `catalog_size` bigint(20) NULL DEFAULT NULL COMMENT '章节数量',
                         `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                         `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `author`;
CREATE TABLE `author`  (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         `description` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者介绍',
                         `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                         `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`  (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `dict_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编码',
                           `dict_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
                           `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `book_catalog`;
CREATE TABLE `book_catalog`  (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `book_id` bigint(20) NULL DEFAULT NULL COMMENT '书籍id',
                         `roll_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卷名称',
                         `catalog_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '章节名称',
                         `catalog_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '章节地址',
                         `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                         `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
