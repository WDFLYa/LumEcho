package nuc.edu.lumecho.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Dfly
 * @Date 2026/3/17 19:40
 */
@Data
public class UserFollow {
    private Long id;

    private Long followerId;

    private Long followingId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}