USE `lecture_system`;
-- ------------------------------------------------------------------------------
-- RBAC权限模型（Role-Based Access Control）
--
-- 基于角色的权限控制。这是目前最常被开发者使用也是相对易用、通用权限模型。
-- 用户表 sys_user：存储用户信息
-- 权限表 sys_menu：各种权限信息
-- 角色表 sys_role：规定了每一种角色具有哪些权限。
-- 用户角色关联表 sys_user_role：规定了用户所对应的角色。
-- 角色权限关联表 sys_role_menu：规定了该角色具有哪些权限。
-- ------------------------------------------------------------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
CREATE TABLE `sys_user` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `username` VARCHAR(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
    `password` VARCHAR(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
    `status` CHAR(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
    `email` VARCHAR(64) DEFAULT NULL COMMENT '邮箱',
    `phone_number` VARCHAR(32) DEFAULT NULL COMMENT '手机号',
    `sex` TINYINT(1) DEFAULT NULL COMMENT '用户性别（0男，1女）',
    `avatar` VARCHAR(128) DEFAULT NULL COMMENT '头像',
    `user_type` CHAR(1) NOT NULL DEFAULT '1' COMMENT '用户类型（0管理员，1普通用户）',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
CREATE TABLE `sys_menu` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
    `menu_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '菜单名',
    `path` varchar(200) DEFAULT NULL COMMENT '路由地址',
    `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
    `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
    `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';


-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
CREATE TABLE `sys_role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(128) DEFAULT NULL,
    `role_key` varchar(100) DEFAULT NULL COMMENT '角色权限字符串',
    `status` char(1) DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
CREATE TABLE `sys_role_menu` (
     `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
     `menu_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '菜单id',
     PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
CREATE TABLE `sys_user_role` (
     `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
     `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
     PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

