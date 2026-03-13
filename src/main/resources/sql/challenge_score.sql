CREATE TABLE challenge_score
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    submission_id BIGINT        NOT NULL COMMENT '关联作品ID',
    judge_id      BIGINT        NOT NULL COMMENT '评委ID',
    judge_name    VARCHAR(50)  DEFAULT NULL COMMENT '评委姓名(冗余)',

    score         DECIMAL(5, 2) NOT NULL COMMENT '该评委给出的分数',
    comment       VARCHAR(500) DEFAULT NULL COMMENT '评委评语',

    create_time   DATETIME     DEFAULT CURRENT_TIMESTAMP,

    INDEX         idx_submission (submission_id) COMMENT '加速根据作品查所有分数',
    INDEX         idx_judge (judge_id) COMMENT '加速查某评委的所有打分'
) COMMENT '挑战赛评分记录表';