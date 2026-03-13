CREATE TABLE challenge
(
    id                BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',

    title             VARCHAR(100) NOT NULL COMMENT '挑战主题',
    description       TEXT COMMENT '挑战详情与规则',

    start_time        DATETIME     NOT NULL COMMENT '开始时间',
    end_time          DATETIME     NOT NULL COMMENT '结束时间（投稿截止）',
    review_end_time   DATETIME COMMENT '评审结束时间',

    status            TINYINT  DEFAULT 0 COMMENT '状态: 0-未开始, 1-进行中, 2-评审中, 3-已结束, 4-已取消',

    max_participants  INT      DEFAULT -1 COMMENT '最大参与人数 (-1表示无限制)',

    participant_count INT      DEFAULT 0 COMMENT '当前报名人数',

    created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='摄影挑战赛主表';