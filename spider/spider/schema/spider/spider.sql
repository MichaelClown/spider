CREATE TABLE `account` (
  `customer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '帐户名',
  `password` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  `cell_phone` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户账户';

CREATE TABLE `actor` (
  `customer_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户在本库ID',
  `actor_id` int(11) DEFAULT NULL COMMENT '共同定义用户ID',
  `nick_name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `real_name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户真实姓名',
  `sex` tinyint(1) DEFAULT NULL COMMENT '用户性别',
  `cell_phone` varchar(16) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户电话',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳字段',
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `address` (
  `address_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '地址ID',
  `province` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '市',
  `district` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区',
  `county` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '县',
  `town` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '镇',
  `detail` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '详细地址',
  `zip_code` int(11) DEFAULT NULL COMMENT '邮编',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `company` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '单位名称',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`address_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `actor` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `basicdict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '编码',
  `name` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `type` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '类型',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='字典表';

CREATE TABLE `company` (
  `company_id` bigint(20) NOT NULL COMMENT '企业ID',
  `company_name` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '企业名称',
  `company_type` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '企业类型',
  `contact` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `create_date` datetime DEFAULT NULL COMMENT '合作时间',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `destination_actor` bigint(20) DEFAULT NULL COMMENT '收件人ID',
  `destination_address` bigint(20) DEFAULT NULL COMMENT '收件人地址ID',
  `origin_actor` bigint(20) DEFAULT NULL COMMENT '发件人ID',
  `origin_addresss` bigint(20) DEFAULT NULL COMMENT '发件人地址ID',
  `goods` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品名称',
  `fee` decimal(15,4) DEFAULT NULL COMMENT '物流费用',
  `create_date` datetime DEFAULT NULL COMMENT '订单创建时间',
  `end_date` datetime DEFAULT NULL COMMENT '订单结束时间',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳字段',
  `e-commerce` bigint(20) DEFAULT NULL COMMENT '订单所属电商ID',
  `logistics` bigint(20) DEFAULT NULL COMMENT '订单所属物流ID',
  `status` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `destination_actor` (`destination_actor`),
  KEY `destination_address` (`destination_address`),
  KEY `origin_actor` (`origin_actor`),
  KEY `origin_addresss` (`origin_addresss`),
  KEY `e-commerce` (`e-commerce`),
  KEY `logistics` (`logistics`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`destination_actor`) REFERENCES `actor` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_ibfk_2` FOREIGN KEY (`destination_address`) REFERENCES `address` (`address_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_ibfk_3` FOREIGN KEY (`origin_actor`) REFERENCES `actor` (`customer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_ibfk_4` FOREIGN KEY (`origin_addresss`) REFERENCES `address` (`address_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_ibfk_5` FOREIGN KEY (`e-commerce`) REFERENCES `company` (`company_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_ibfk_6` FOREIGN KEY (`logistics`) REFERENCES `company` (`company_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `record` (
  `record_id` bigint(20) NOT NULL COMMENT '物流记录ID',
  `from` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '物流记录起点',
  `to` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '物流记录终点',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳字段',
  `order_id` bigint(20) DEFAULT NULL COMMENT '记录对应订单ID',
  PRIMARY KEY (`record_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `record_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into types(code,name,type) values('110000','北京市','PROVINCE');
insert into types(code,name,type) values('120000','天津市','PROVINCE');
insert into types(code,name,type) values('130000','河北省','PROVINCE');
insert into types(code,name,type) values('140000','山西省','PROVINCE');
insert into types(code,name,type) values('150000','内蒙古自治区','PROVINCE');
insert into types(code,name,type) values('210000','辽宁省','PROVINCE');
insert into types(code,name,type) values('220000','吉林省','PROVINCE');
insert into types(code,name,type) values('230000','黑龙江省','PROVINCE');
insert into types(code,name,type) values('310000','上海市','PROVINCE');
insert into types(code,name,type) values('320000','江苏省','PROVINCE');
insert into types(code,name,type) values('330000','浙江省','PROVINCE');
insert into types(code,name,type) values('340000','安徽省','PROVINCE');
insert into types(code,name,type) values('350000','福建省','PROVINCE');
insert into types(code,name,type) values('360000','江西省','PROVINCE');
insert into types(code,name,type) values('370000','山东省','PROVINCE');
insert into types(code,name,type) values('410000','河南省','PROVINCE');
insert into types(code,name,type) values('420000','湖北省','PROVINCE');
insert into types(code,name,type) values('430000','湖南省','PROVINCE');
insert into types(code,name,type) values('440000','广东省','PROVINCE');
insert into types(code,name,type) values('450000','广西壮族自治区','PROVINCE');
insert into types(code,name,type) values('460000','海南省','PROVINCE');
insert into types(code,name,type) values('500000','重庆市','PROVINCE');
insert into types(code,name,type) values('510000','四川省','PROVINCE');
insert into types(code,name,type) values('520000','贵州省','PROVINCE');
insert into types(code,name,type) values('530000','云南省','PROVINCE');
insert into types(code,name,type) values('540000','西藏自治区','PROVINCE');
insert into types(code,name,type) values('610000','陕西省','PROVINCE');
insert into types(code,name,type) values('620000','甘肃省','PROVINCE');
insert into types(code,name,type) values('630000','青海省','PROVINCE');
insert into types(code,name,type) values('640000','宁夏回族自治区','PROVINCE');
insert into types(code,name,type) values('650000','新疆维吾尔自治区','PROVINCE');
insert into types(code,name,type) values('710000','台湾省','PROVINCE');
insert into types(code,name,type) values('810000','香港特别行政区','PROVINCE');
insert into types(code,name,type) values('820000','澳门特别行政区','PROVINCE');

