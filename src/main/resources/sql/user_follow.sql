CREATE TABLE `user_follow` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                               `follower_id` bigint(20) NOT NULL COMMENT '关注者ID (谁发起的关注)',
                               `following_id` bigint(20) NOT NULL COMMENT '被关注者ID (被关注的人)',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`),


                               UNIQUE KEY `uk_follower_following` (`follower_id`, `following_id`),

    -- 辅助索引：加速查询 "我关注了谁" (根据 follower_id 查)
                               KEY `idx_follower` (`follower_id`),

    -- 辅助索引：加速查询 "谁关注了我" (根据 following_id 查粉丝列表)
                               KEY `idx_following` (`following_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户关注关系表';