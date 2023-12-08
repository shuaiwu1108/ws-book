SET FOREIGN_KEY_CHECKS = 0;
SET NAMES utf8mb4;

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) DEFAULT NULL,
                        `url` varchar(255) DEFAULT NULL COMMENT '书籍地址',
                        `icon` varchar(1000) DEFAULT NULL COMMENT '封面',
                        `author_id` bigint(20) DEFAULT NULL,
                        `description` varchar(2000) DEFAULT NULL COMMENT '书籍介绍',
                        `book_type` varchar(20) DEFAULT NULL COMMENT '书籍类别',
                        `work_status` varchar(10) DEFAULT NULL COMMENT '作品状态',
                        `book_source` varchar(255) DEFAULT NULL COMMENT '作品来源名称',
                        `book_source_id` varchar(255) DEFAULT NULL COMMENT '原始站中书籍的ID',
                        `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                        `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) DEFAULT NULL,
                          `description` varchar(2000) DEFAULT NULL COMMENT '作者介绍',
                          `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                          `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`  (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `dict_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编码',
                           `dict_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
                           `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `dict` (`id`, `dict_code`, `dict_name`, `sort`) VALUES (1, '300001', '连载中', 1);
INSERT INTO `dict` (`id`, `dict_code`, `dict_name`, `sort`) VALUES (2, '300002', '已完本', 2);
INSERT INTO `dict` (`id`, `dict_code`, `dict_name`, `sort`) VALUES (3, '100001', '玄幻', 1);
INSERT INTO `dict` (`id`, `dict_code`, `dict_name`, `sort`) VALUES (4, '100002', '修真', 2);
INSERT INTO `dict` (`id`, `dict_code`, `dict_name`, `sort`) VALUES (5, '100003', '都市', 3);
INSERT INTO `dict` (`id`, `dict_code`, `dict_name`, `sort`) VALUES (6, '100004', '历史', 4);
INSERT INTO `dict` (`id`, `dict_code`, `dict_name`, `sort`) VALUES (7, '100005', '网游', 5);
INSERT INTO `dict` (`id`, `dict_code`, `dict_name`, `sort`) VALUES (8, '100006', '科幻', 6);
INSERT INTO `dict` (`id`, `dict_code`, `dict_name`, `sort`) VALUES (9, '100007', '完本', 7);


DROP TABLE IF EXISTS `book_catalog`;
CREATE TABLE `book_catalog` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `book_id` bigint(20) DEFAULT NULL COMMENT '书籍id',
                                `order_no` varchar(255) DEFAULT NULL COMMENT '卷名称',
                                `catalog_name` varchar(255) DEFAULT NULL COMMENT '章节名称',
                                `catalog_url` varchar(255) DEFAULT NULL COMMENT '章节地址',
                                `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
