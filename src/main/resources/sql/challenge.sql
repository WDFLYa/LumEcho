-- 1. 如果表已存在，则删除（谨慎操作，生产环境请备份数据！）
DROP TABLE IF EXISTS challenge;

-- 2. 创建新表
CREATE TABLE challenge
(
    id                BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',

    -- 基础信息
    title             VARCHAR(100) NOT NULL COMMENT '挑战主题',
    description       TEXT COMMENT '挑战详情介绍 (支持HTML)',

    -- ✨ 新增：挑战规则 (存储 JSON 数组，例如: ["规则1", "规则2", "规则3"])
    rules             JSON COMMENT '挑战规则列表',

    -- 时间控制
    start_time        DATETIME     NOT NULL COMMENT '开始时间',
    end_time          DATETIME     NOT NULL COMMENT '结束时间（投稿截止）',
    review_end_time   DATETIME COMMENT '评审结束时间',

    -- 状态控制
    status            TINYINT      DEFAULT 0 COMMENT '状态: 0-未开始, 1-进行中, 2-评审中, 3-已结束, 4-已取消',

    -- 人数控制
    max_participants  INT          DEFAULT -1 COMMENT '最大参与人数 (-1表示无限制)',
    participant_count INT          DEFAULT 0 COMMENT '当前报名人数',

    -- 奖项设置 (可选扩展：存储JSON，如 {"first": "奖金", "second": "奖品"})
    prizes            JSON COMMENT '奖项设置',

    -- 系统字段
    created_at        DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='摄影挑战赛主表';
