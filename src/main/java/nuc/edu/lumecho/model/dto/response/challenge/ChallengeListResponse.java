package nuc.edu.lumecho.model.dto.response.challenge;

import lombok.Data;

import java.util.List;

/**
 * @Author Dfly
 * @Date 2026/3/15 10:24
 */
@Data
public class ChallengeListResponse {

    private List<ChallengeListItemResponse> data;

    private Boolean hasMore;

    private Long total;
}