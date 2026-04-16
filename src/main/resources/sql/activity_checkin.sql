CREATE TABLE activity_checkin (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  activity_id BIGINT NOT NULL COMMENT '活动ID',
                                  user_id BIGINT NOT NULL COMMENT '用户ID',
                                  latitude DECIMAL(10,6) NOT NULL COMMENT '纬度',
                                  longitude DECIMAL(10,6) NOT NULL COMMENT '经度',
                                  address VARCHAR(255) COMMENT '签到地址',
                                  checkin_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '签到时间',
                                  UNIQUE KEY uk_activity_user (activity_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动签到表';