CREATE TABLE photographer_application
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT       NOT NULL COMMENT '申请人ID',
    description   TEXT COMMENT '个人简介',
    status        TINYINT      NOT NULL DEFAULT 0 COMMENT '0=待审,1=通过,2=拒绝',
    reject_reason VARCHAR(255) COMMENT '拒绝原因',
    apply_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    review_time   DATETIME COMMENT '审核时间',
    reviewer_id   BIGINT COMMENT '审核人ID',

    INDEX         idx_user_id (user_id),
    INDEX         idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;