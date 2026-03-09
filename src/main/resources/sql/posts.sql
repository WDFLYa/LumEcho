CREATE TABLE posts (
                       id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                       post_type TINYINT NOT NULL COMMENT '帖子类型',
                       category_id BIGINT NOT NULL COMMENT '分类ID，关联categories表',
                       user_id BIGINT NOT NULL COMMENT '作者用户ID，关联users表',
                       title VARCHAR(255) NOT NULL COMMENT '帖子标题',
                       content TEXT NOT NULL COMMENT '帖子正文内容',
                       view_count BIGINT NOT NULL DEFAULT 0 COMMENT '浏览量',
                       like_count BIGINT NOT NULL DEFAULT 0 COMMENT '点赞数',
                       comment_count BIGINT NOT NULL DEFAULT 0 COMMENT '评论数',
                       pinned TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否置顶：0-否, 1-是',
                       post_status TINYINT NOT NULL DEFAULT 1 COMMENT '帖子状态：0-封禁 1-正常',
                       create_time DATETIME NOT NULL COMMENT '创建时间',
                       update_time DATETIME NOT NULL COMMENT '最后更新时间',
                       deleted_at DATETIME NULL COMMENT '软删除时间，NULL表示未删除',

                       PRIMARY KEY (id),

                       INDEX idx_user_id (user_id) COMMENT '用于查询某用户的所有帖子',
                       INDEX idx_category_id (category_id) COMMENT '用于按分类查询帖子',
                       INDEX idx_deleted_post_status (deleted_at, post_status) COMMENT '软删除+状态联合查询，避免全表扫描',
                       INDEX idx_post_status_create (post_status, create_time DESC) COMMENT '按状态筛选并按创建时间倒序（如首页列表）',
                       INDEX idx_pinned_post_status (pinned, post_status) COMMENT '查询置顶且已发布的帖子'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帖子表';