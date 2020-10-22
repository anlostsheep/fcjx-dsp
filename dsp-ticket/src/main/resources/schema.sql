CREATE TABLE IF NOT EXISTS `paas_tag_tickets` (
  `ticket_no` int NOT NULL COMMENT '工单号',
  `paas_tag` char(64) NOT NULL COMMENT 'paas平台用户标签',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态：0-已失效，1-有效',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ticket_no`,`paas_tag`)
) ENGINE=InnoDB;