ALTER TABLE `book_catalog`
    MODIFY COLUMN `catalog_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '章节地址' AFTER `catalog_name`,
    ADD COLUMN `catalog_file_url` varchar(500) NULL COMMENT '章节文件地址，minio地址' AFTER `catalog_url`;