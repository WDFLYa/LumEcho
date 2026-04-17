CREATE TABLE chat_message (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              session_id BIGINT NOT NULL COMMENT '会话ID',
                              role VARCHAR(32) NOT NULL COMMENT 'user/assistant',
                              content TEXT NOT NULL COMMENT '消息内容',
                              create_time DATETIME DEFAULT NOW(),
                              INDEX idx_session (session_id)
);