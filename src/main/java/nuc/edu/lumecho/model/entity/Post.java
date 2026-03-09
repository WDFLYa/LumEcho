package nuc.edu.lumecho.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    private Long id;
    private Integer postType;          // 帖子类型
    private Long categoryId;        // 分类ID
    private Long userId;            // 作者用户ID
    private String title;           // 帖子标题
    private String content;         // 帖子内容
    private Long viewCount;         // 浏览量
    private Long likeCount;         // 点赞数
    private Long commentCount;      // 评论数
    private Boolean pinned;         // 是否置顶
    private Integer postStatus;        // 帖子状态
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime deletedAt;
}
