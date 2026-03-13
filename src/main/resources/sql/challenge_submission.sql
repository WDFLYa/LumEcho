CREATE TABLE challenge_submission
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    application_id BIGINT       NOT NULL COMMENT '关联报名表ID (唯一)',
    title          VARCHAR(255) NOT NULL COMMENT '作品标题',
    content        TEXT         NOT NULL COMMENT '作品内容/描述',
    location       VARCHAR(255)  DEFAULT NULL COMMENT '参赛地点/学校/团队所在地',

    -- 分数冗余字段 (可选，为了查询性能)
    final_score    DECIMAL(5, 2) DEFAULT 0.00 COMMENT '当前最终总分',

    status         TINYINT       DEFAULT 0 COMMENT '状态: 0-已提交, 1-已评分, 2- disqualified(取消资格)',

    submit_time    DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
    update_time    DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    UNIQUE KEY uk_application (application_id) COMMENT '每个报名记录只能提交一次作品'
) COMMENT '挑战赛作品提交表';