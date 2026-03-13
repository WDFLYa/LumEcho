CREATE TABLE `challenge_application`
(
    `id`           BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',

    `challenge_id` BIGINT(20) NOT NULL COMMENT '挑战赛ID',
    `user_id`      BIGINT(20) NOT NULL COMMENT '参赛用户ID (选手)',

    `apply_time`   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
    `status`       TINYINT(4) NOT NULL DEFAULT '0' COMMENT '状态: 0-待审核, 1-已通过(报名成功), 2-已拒绝, 3-已取消',

    `remark`       VARCHAR(255)      DEFAULT NULL COMMENT '备注/拒绝原因 (可选，方便管理员填写理由)',

    -- 索引与约束
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_challenge_user` (`challenge_id`, `user_id`) COMMENT '防止同一用户重复报名同一比赛',
    KEY            `idx_user_id` (`user_id`) COMMENT '快速查询用户报名记录',
    KEY            `idx_challenge_id` (`challenge_id`) COMMENT '快速查询比赛所有报名者',

    -- 外键约束 (可选，建议加上以保证数据一致性)
    CONSTRAINT `fk_ca_challenge` FOREIGN KEY (`challenge_id`) REFERENCES `challenge` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_ca_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='挑战赛报名申请表';