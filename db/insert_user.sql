-- ----------------------------
-- 用户表
-- ----------------------------
INSERT INTO sys_user (id,username,PASSWORD,user_type, avatar)
VALUES
       (1,'admin','$2a$10$ha5kcAOTEswbCPjB9lXQ8uyep6QpbP7VusfBFUdKiEbigDk5j6uFe', '0', 'http://www.weixintouxiang.cn/weixin/20140607090832328.gif'),
       (2,'manager','$2a$10$AJUfqCEQrI1H1k88eRlddOHGVEucaqn9L.lDFvM4YZFqMZ2x0zGou', '0', 'http://www.weixintouxiang.cn/weixin/20140607090832328.gif'),
       (3,'student','$2a$10$NOIGGyky/vYSxgc2j6z3FuEVP8nMxZk5OuaWVjN4FUNRcqK2XEsYe', '1', 'http://www.weixintouxiang.cn/weixin/20140607090832328.gif');

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
INSERT INTO sys_user_role (user_id, role_id) VALUE (2,2);
INSERT INTO sys_user_role (user_id, role_id) VALUE (3,1);