package nuc.edu.lumecho.model.dto.response.post;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDetailResponse {
    private Long id;
    private String username;
    private String avatar;
    private String title;
    private String content;
    private String categoryName;
    private Long likeCount;
    private Long commentCount;
    private Long userId;
    private LocalDateTime createTime;
    private List<String> imageUrls;
    private List<String> videoUrls;
}