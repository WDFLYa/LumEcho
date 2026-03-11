-- 帖子评论表
CREATE TABLE `comment`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `post_id`     BIGINT       NOT NULL COMMENT '被评论的帖子ID',
    `user_id`     BIGINT       NOT NULL COMMENT '评论人用户ID',
    `content`     VARCHAR(500) NOT NULL COMMENT '评论内容',
    `parent_id`   BIGINT                DEFAULT NULL COMMENT '父评论ID：NULL表示直接评论帖子，否则回复某条评论',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

    PRIMARY KEY (`id`),
    KEY           `idx_post_create` (`post_id`, `create_time`),
    KEY           `idx_parent` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帖子评论表';