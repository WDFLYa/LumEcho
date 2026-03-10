package nuc.edu.lumecho.model.dto.response.post;

import lombok.Data;

import java.util.List;

@Data
public class PostHomePageResponse {

    private List<PostHomeItemResponse> data;
    private Boolean hasMore;
    private Long total;
}
