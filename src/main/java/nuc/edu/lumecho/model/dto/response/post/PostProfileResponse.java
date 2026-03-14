package nuc.edu.lumecho.model.dto.response.post;

import lombok.Data;

/**
 * @Author Dfly
 * @Date 2026/3/14 22:11
 */
@Data
public class PostProfileResponse {

    private Long id;

    private String title;

    private String coverImage;

    private String summary;

    private Long likeCount;

    private Long commentCount;
}