CREATE TABLE `user` (
                        `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                        `account` VARCHAR(50) DEFAULT NULL COMMENT '登录账号',
                        `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
                        `password` VARCHAR(100) DEFAULT NULL COMMENT '密码',
                        `username` VARCHAR(50) DEFAULT NULL COMMENT '用户名',
                        `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
                        `bio` VARCHAR(255) DEFAULT NULL COMMENT '简介',
                        `status` TINYINT DEFAULT NULL DEFAULT 1 COMMENT '状态',
                        `role` VARCHAR(20) DEFAULT NULL DEFAULT 'USER' COMMENT '角色',
                        `create_time` DATETIME DEFAULT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` DATETIME DEFAULT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `deleted_at` DATETIME DEFAULT NULL COMMENT '逻辑删除时间',
                        PRIMARY KEY (`id`)
);