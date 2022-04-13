USE `lecture_system`;

-- ----------------------------
-- 讲座表：发布讲座时的详细信息
-- ----------------------------
CREATE TABLE `lecture` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '讲座id',
    `title` VARCHAR(64) NOT NULL COMMENT '讲座名称',
    `type_id` BIGINT(20) COMMENT '讲座类型id',
    `description_id` BIGINT(20) COMMENT '讲座描述信息id',
    `organizer` VARCHAR(64) COMMENT '主办方',
    `undertaker` VARCHAR(64) COMMENT '承办方',
    `sponsor` VARCHAR(64) COMMENT '协办方',
    `space` VARCHAR(64) NOT NULL COMMENT '讲座地点',
    `speaker` VARCHAR(32) NOT NULL COMMENT '主讲人姓名',
    `reservation` SMALLINT NOT NULL COMMENT '讲座容纳人数',
    `poster` VARCHAR(128) COMMENT '海报url',
    `order_start_time` DATETIME NOT NULL COMMENT '讲座预约开始时间',
    `order_end_time` DATETIME NOT NULL COMMENT '讲座预约结束时间',
    `lecture_start_time` DATETIME NOT NULL COMMENT '讲座开始时间',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除 0（false）未删除',
    `create_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='讲座发布表';

-- ----------------------------
-- 讲座预约表：用于讲座预约
-- ----------------------------
CREATE TABLE `lecture_order` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `lecture_id` BIGINT(20) NOT NULL COMMENT '讲座id',
    `title` VARCHAR(64) NOT NULL COMMENT '讲座名称',
    `store` SMALLINT NOT NULL COMMENT '剩余预约数量',
    `order_start_time` DATETIME NOT NULL COMMENT '讲座预约开始时间',
    `order_end_time` DATETIME NOT NULL COMMENT '讲座预约结束时间',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除 0（false）未删除',
    `create_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='讲座预约表';

-- ----------------------------
-- 讲座数据分析表：用于记录讲座的相关数据
-- ----------------------------
CREATE TABLE `lecture_data_analysis` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '讲座数据分析id',
    `lecture_id` BIGINT(20) NOT NULL COMMENT '讲座id',
    `attendance` SMALLINT DEFAULT 0 COMMENT '出席人数',
    `non_attendance` SMALLINT DEFAULT 0 COMMENT '未出席人数',
    `count_late` SMALLINT DEFAULT 0 COMMENT '迟到人数',
    `lecture_real_start_time` DATETIME COMMENT '讲座真实开始时间',
    `lecture_real_end_time` DATETIME COMMENT '讲座真实结束时间',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除 0（false）未删除',
    `create_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='讲座数据分析表';

-- ----------------------------
-- 讲座描述表：用于描述讲座详细信息
-- ----------------------------
CREATE TABLE `lecture_description` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '讲座描述id',
    `description` TEXT COMMENT '讲座描述',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除  0（false）未删除',
    `create_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='讲座描述表';

-- ----------------------------
-- 讲座类型表
-- ----------------------------
CREATE TABLE `lecture_type` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '讲座类型id',
    `name` VARCHAR(64) NOT NULL COMMENT '讲座类型名称',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除  0（false）未删除',
    `create_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='讲座类型表';

-- ----------------------------
-- 讲座预约记录表：用于记录用户预约讲座的记录
-- ----------------------------
CREATE TABLE `lecture_user_record` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `lecture_id` BIGINT(20) NOT NULL COMMENT '讲座id',
    `user_id` BIGINT(20) NOT NULL COMMENT '用户id',
    `sign_code_id` BIGINT(20) COMMENT '签到码id',
    `order_time` DATETIME NOT NULL COMMENT '预约时间',
    `sign_time` DATETIME COMMENT '签到时间',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除  0（false）未删除',
    `create_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`lecture_id`, `user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='讲座预约记录表';

-- ----------------------------
-- 讲座签到码表
-- ----------------------------
CREATE TABLE `lecture_sign_code` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `code` INT NOT NULL COMMENT '签到码',
    `qr_code` VARCHAR(128) COMMENT '签到二维码',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除  0（false）未删除',
    `create_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='讲座签到表';