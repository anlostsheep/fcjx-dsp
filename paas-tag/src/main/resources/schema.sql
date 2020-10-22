CREATE TABLE IF NOT EXISTS `tags` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                      `name` char(128) UNIQUE COMMENT '标签名称',
                                      `alias` char(128) DEFAULT NULL COMMENT '标签别名',
                                      `status` tinyint(4) DEFAULT NULL COMMENT '状态：0-已失效，1-有效',
                                      `created_time` datetime DEFAULT NULL COMMENT '创建时间',
                                      `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
                                      `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `tag_bitmaps` (
                                             `tag` CHAR(128) COMMENT '标签名称',
                                             `bitmap_object` LONGBLOB DEFAULT NULL COMMENT '序列化后的bitmap对象',
                                             `status` tinyint(4) DEFAULT NULL COMMENT '状态：0-已失效，1-有效',
                                             `created_time` datetime DEFAULT NULL COMMENT '创建时间',
                                             `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
                                             `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                                             PRIMARY KEY (`tag`)
) ENGINE=InnoDB;

show VARIABLES like '%max_allowed_packet%';
set global max_allowed_packet = 100*1024*1024