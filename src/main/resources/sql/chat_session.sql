CREATE TABLE chat_session (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              user_id BIGINT NOT NULL COMMENT '用户ID',
                              photographer_id BIGINT NOT NULL COMMENT '摄影师ID（1号摄影师、2号摄影师...）',
                              update_time DATETIME DEFAULT NOW() ON UPDATE NOW(),
                              create_time DATETIME DEFAULT NOW(),
                              UNIQUE KEY uk_user_photographer (user_id, photographer_id)
);