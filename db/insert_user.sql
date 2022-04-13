INSERT INTO sys_user (id,username,PASSWORD,user_type) VALUE(1,'admin','admin','0');

-- INSERT INTO sys_menu (id,menu_name,perms) value (1,'查看列表', 'system:user:list');
-- INSERT INTO sys_menu (id,menu_name,perms) value (2,'查看详情', 'system:user:info');
-- INSERT INTO sys_menu (id,menu_name,perms) value (3,'新增', 'system:user:save');
-- INSERT INTO sys_menu (id,menu_name,perms) value (4,'删除', 'system:user:delete');
-- INSERT INTO sys_menu (id,menu_name,perms) value (5,'修改', 'system:user:update');

-- ----------------------------
-- 角色表
-- ----------------------------
INSERT INTO sys_role (id, name, role_key, remark) VALUE (1, '学生', 'student', '普通学生所拥有的权限');
INSERT INTO sys_role (id, name, role_key, remark) VALUE (2, '学生会管理人员', 'manager', '普通学生，同时可以发布讲座信息，检查讲座情况');
INSERT INTO sys_role (id, name, role_key, remark) VALUE (3, '后台管理人员', 'admin', '拥有所有权限的管理人员');


-- ----------------------------
-- 用户角色关联表
-- ----------------------------
INSERT INTO sys_user_role (user_id, role_id) VALUE (1,3);

SELECT sr.role_key
FROM sys_user su
LEFT JOIN sys_user_role sur ON su.id = sur.user_id
LEFT JOIN sys_role sr ON sr.id = sur.role_id
WHERE su.id = 1;