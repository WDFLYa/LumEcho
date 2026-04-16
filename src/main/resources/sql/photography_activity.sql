CREATE TABLE photography_activity
(
    id                   BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '活动ID',

    title                VARCHAR(100) NOT NULL COMMENT '活动标题',
    description          TEXT COMMENT '活动描述（富文本）',

    start_time           DATETIME     NOT NULL COMMENT '活动开始时间',
    end_time             DATETIME     NOT NULL COMMENT '活动结束时间',
    location             VARCHAR(200) NOT NULL COMMENT '活动地点（文字）',

    max_participants     INT          NOT NULL DEFAULT 10 COMMENT '最大参与人数',
    current_participants INT          NOT NULL DEFAULT 0 COMMENT '当前已通过审核人数',

    photographer_id      BIGINT       NOT NULL COMMENT '摄影师ID（发布者）',

    status               TINYINT      NOT NULL DEFAULT 0 COMMENT '活动状态：0-待开始, 1-进行中, 2-已结束',
    require_audit        TINYINT      NOT NULL DEFAULT 1 COMMENT '是否需要审核：0-免审, 1-需审核',

    create_time          DATETIME              DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time          DATETIME              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
) COMMENT '摄影活动表';


ALTER TABLE photography_activity
    ADD COLUMN latitude DEC(10,6) NULL COMMENT '活动纬度',
ADD COLUMN longitude DEC(10,6) NULL COMMENT '活动经度';