USE `lecture_system`;

-- ----------------------------
-- 讲座表：发布讲座时的详细信息
-- ----------------------------
CREATE TABLE `lecture` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '讲座id',
    `title` VARCHAR(64) NOT NULL COMMENT '讲座名称',
    `type_id` BIGINT(20) COMMENT '讲座类型id',
    `creator_id` BIGINT(20) COMMENT '创建者id，只有创建者和管理才能修改信息',
    `state` TINYINT(1) COMMENT '讲座状态 0（已发布） 1 （已结束）',
    `organizer` VARCHAR(64) COMMENT '主办方',
    `undertaker` VARCHAR(64) COMMENT '承办方',
    `sponsor` VARCHAR(64) COMMENT '协办方',
    `space` VARCHAR(64) NOT NULL COMMENT '讲座地点',
    `speaker` VARCHAR(32) NOT NULL COMMENT '主讲人姓名',
    `reservation` SMALLINT NOT NULL COMMENT '讲座容纳人数',
    `store` SMALLINT NOT NULL COMMENT '剩余预约数量',
    `description` TEXT COMMENT '讲座描述信息',
    `poster` VARCHAR(256) COMMENT '海报url',
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

-- ----------------------------
-- 讲座发布表：发布讲座时的详细信息
-- ----------------------------
INSERT INTO
    `lecture` (id, title, type_id, creator_id, state, organizer, undertaker, sponsor, space, speaker, reservation, store, description, poster, order_start_time, order_end_time, lecture_start_time)
VALUES
(1, 'PUA的艺术', 1, 1, 0, '东南大学学生会', '东南大学学生会', '东南大学自动化学院', '四牌楼大礼堂', '小张', 500, 500, '这是一场精彩绝伦的讲座！！！', 'null', '2022-4-15 19:00:00', '2022-5-13 19:00:00', '2022-6-13 19:00:00'),
(2, '中国的建设和中国自信', 3, 1, 0, '东南大学学生会', '东南大学学生会', '东南大学自动化学院', '四牌楼大礼堂', 'xxx', 500, 500, '这是一场精彩绝伦的讲座！！！', 'null', '2022-4-15 19:00:00', '2022-5-13 19:00:00', '2022-6-13 19:00:00'),
(3, '戏曲百戏盛典', 4, 1, 0, '共青团东南大学委员会', '东南大学学生会', '', '四牌楼大礼堂', 'xxx', 400, 400, '这是一场精彩绝伦的讲座！！！', 'null', '2022-4-15 19:00:00', '2022-5-13 19:00:00', '2022-6-13 19:00:00'),
(4, '公共卫生治理', 1, 1, 0, '东南大学学生会', '东南大学学生会', '', '四牌楼大礼堂', 'xxx2', 400, 400, '这是一场精彩绝伦的讲座！！！', 'null', '2022-10-15 19:00:00', '2022-5-13 19:00:00', '2022-6-13 19:00:00'),
(5, '控制情绪成就人生', 5, 1, 0, '东南大学学生会', '东南大学学生会', '', '四牌楼大礼堂', 'xxx3', 400, 400, '这是一场精彩绝伦的讲座！！！', 'null', '2022-4-15 19:00:00', '2022-5-13 19:00:00', '2022-6-13 19:00:00'),
(6, '这是你熟悉的汉语吗', 3, 1, 0, '东南大学学生会', '东南大学学生会', '', '中山院', 'xxx4', 400, 400, '这是一场精彩绝伦的讲座！！！', 'null', '2022-4-15 19:00:00', '2022-5-13 19:00:00', '2022-6-13 19:00:00'),
(7, '零售商业模式', 1, 1, 0, '东南大学学生会', '东南大学学生会', '', '四牌楼大礼堂', 'xxx5', 400, 400, '这是一场精彩绝伦的讲座！！！', 'null', '2022-4-15 19:00:00', '2022-5-13 19:00:00', '2022-6-13 19:00:00'),
(8, '当代艺术与当代设计', 4, 1, 0, '东南大学学生会', '东南大学学生会', '', '四牌楼大礼堂', 'xxx6', 400, 400, '这是一场精彩绝伦的讲座！！！', 'null', '2022-4-15 19:00:00', '2022-5-13 19:00:00', '2022-6-13 19:00:00'),
(9, '数智时代基本权利的演变与保护', 2, 1, 0, '东南大学学生会', '东南大学学生会', '', '四牌楼大礼堂', 'xxx7', 400, 400, '这是一场精彩绝伦的讲座！！！', 'null', '2022-4-15 19:00:00', '2022-5-13 19:00:00', '2022-6-13 19:00:00'),
(10, '国际隐秘斗争形势和特点', 3, 1, 0, '东南大学学生会', '东南大学学生会', '', '线上', 'xxx8', 400, 400, '这是一场精彩绝伦的讲座！！！', 'null', '2022-4-15 19:00:00', '2022-5-13 19:00:00', '2022-6-13 19:00:00');


-- ----------------------------
-- 讲座预约表：用于讲座预约
-- ----------------------------
INSERT INTO
    `lecture_order` (id, lecture_id, title, store, order_start_time, order_end_time)
VALUES
(1, 1, 'PUA的艺术', 500, '2022-4-15 19:00:00', '2022-5-13 19:00:00'),
(2, 2, '中国的建设和中国自信', 500, '2022-4-15 19:00:00', '2022-5-13 19:00:00'),
(3, 3, '戏曲百戏盛典', 400, '2022-4-15 19:00:00', '2022-5-13 19:00:00'),
(4, 4, '公共卫生治理', 400, '2022-4-15 19:00:00', '2022-5-13 19:00:00'),
(5, 5, '控制情绪成就人生', 400, '2022-4-15 19:00:00', '2022-5-13 19:00:00'),
(6, 6, '这是你熟悉的汉语吗', 400, '2022-4-15 19:00:00', '2022-5-13 19:00:00'),
(7, 7, '零售商业模式', 400, '2022-4-15 19:00:00', '2022-5-13 19:00:00'),
(8, 8, '当代艺术与当代设计', 400, '2022-4-15 19:00:00', '2022-5-13 19:00:00'),
(9, 9, '数智时代基本权利的演变与保护', 400, '2022-4-15 19:00:00', '2022-5-13 19:00:00'),
(10, 10, '国际隐秘斗争形势和特点', 400, '2022-4-15 19:00:00', '2022-5-13 19:00:00');

-- ----------------------------
-- 讲座数据分析表：用于记录讲座的相关数据
-- ----------------------------
INSERT INTO
    `lecture_data_analysis` (id, lecture_id, attendance, non_attendance, count_late, lecture_real_start_time, lecture_real_end_time)
VALUES
(1, 1, 0, 0, 0, '2022-6-13 19:00:00', '2022-6-13 19:00:00'),
(2, 2, 0, 0, 0, '2022-6-13 19:00:00', '2022-6-13 19:00:00'),
(3, 3, 0, 0, 0, '2022-6-13 19:00:00', '2022-6-13 19:00:00'),
(4, 4, 0, 0, 0, '2022-6-13 19:00:00', '2022-6-13 19:00:00'),
(5, 5, 0, 0, 0, '2022-6-13 19:00:00', '2022-6-13 19:00:00'),
(6, 6, 0, 0, 0, '2022-6-13 19:00:00', '2022-6-13 19:00:00'),
(7, 7, 0, 0, 0, '2022-6-13 19:00:00', '2022-6-13 19:00:00'),
(8, 8, 0, 0, 0, '2022-6-13 19:00:00', '2022-6-13 19:00:00'),
(9, 9, 0, 0, 0, '2022-6-13 19:00:00', '2022-6-13 19:00:00'),
(10, 10, 0, 0, 0, '2022-6-13 19:00:00', '2022-6-13 19:00:00');

-- ----------------------------
-- 讲座类型表
-- ----------------------------
INSERT INTO
    `lecture_type` (id, name)
VALUES
(1, '其他'),
(2, '法律'),
(3, '人文'),
(4, '艺术'),
(5, '心理'),
(6, '政治'),
(7, '哲学');

-- ----------------------------
-- 讲座预约记录表：用于记录用户预约讲座的记录
-- ----------------------------


-- ----------------------------
-- 讲座签到码表
-- ----------------------------