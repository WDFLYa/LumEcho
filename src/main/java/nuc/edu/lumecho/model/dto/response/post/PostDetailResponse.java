package nuc.edu.lumecho.model.dto.response.post;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createTime;
    private List<String> imageUrls;
    private List<String> videoUrls;
}