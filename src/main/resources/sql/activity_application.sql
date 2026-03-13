CREATE TABLE `activity_application`
(
    `id`          BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键ID',

    `activity_id` BIGINT   NOT NULL COMMENT '活动ID',
    `user_id`     BIGINT   NOT NULL COMMENT '用户ID',
    `apply_time`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
    `status`      TINYINT  NOT NULL DEFAULT 0 COMMENT '状态: 0-待审核, 1-已通过, 2-已拒绝, 3-已取消',

    -- 索引
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_activity_user` (`activity_id`, `user_id`) COMMENT '防止重复报名',
    KEY           `idx_user_id` (`user_id`),
    KEY           `idx_activity_id` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='活动报名申请表';