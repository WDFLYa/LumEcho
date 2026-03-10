CREATE TABLE resource_file
(
    id           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',

    biz_type     VARCHAR(32)  NOT NULL COMMENT '业务类型（代码枚举）：AVATAR, POST_IMAGE, POST_VIDEO等',
    biz_id       BIGINT       NOT NULL COMMENT '关联的业务ID（如用户ID、帖子ID）',

    file_url     VARCHAR(512) NOT NULL COMMENT 'MinIO 完整可访问URL',
    file_name    VARCHAR(255) NOT NULL COMMENT '原始文件名（如 avatar.jpg）',

    created_by   BIGINT       NOT NULL COMMENT '上传人用户ID',
    created_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

    -- 核心索引
    PRIMARY KEY (id),
    KEY          idx_biz (biz_type, biz_id),
    KEY          idx_created_by (created_by)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源文件表';