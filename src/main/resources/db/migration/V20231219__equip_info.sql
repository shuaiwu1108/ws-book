CREATE TABLE IF NOT EXISTS  `equip`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `equip_code` varchar(255) NULL COMMENT '设备编码',
    `equip_name` varchar(255) NULL COMMENT '设备名称',
    `model_id` bigint NULL COMMENT '设备型号id',
    `type_id` bigint NULL COMMENT '设备类型id',
    `manufactory_id` bigint NULL COMMENT '制造商',
    `create_time` datetime NULL,
    `update_time` datetime NULL,
    `create_by` bigint NULL,
    `update_by` bigint NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='设备表' ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `equip_model` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `type_id` bigint NULL COMMENT '设别类型id',
    `model_code` varchar(255) NULL COMMENT '型号编码',
    `model_name` varchar(255) NULL COMMENT '型号名称',
    `create_time` datetime NULL,
    `update_time` datetime NULL,
    `create_by` bigint NULL,
    `update_by` bigint NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='设备型号' ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `equip_type` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `type_code` varchar(255) NULL COMMENT '类型编码',
    `type_name` varchar(255) NULL COMMENT '类型名称',
    `create_time` datetime NULL,
    `update_time` datetime NULL,
    `create_by` bigint NULL,
    `update_by` bigint NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='设备类型' ROW_FORMAT=DYNAMIC;

