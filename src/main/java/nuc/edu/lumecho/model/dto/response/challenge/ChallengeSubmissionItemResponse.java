package nuc.edu.lumecho.model.dto.response.challenge;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author Dfly
 * @Date 2026/3/15 14:33
 */
@Data
public class ChallengeSubmissionItemResponse {

    private Long id;

    //作品标题
    private String title;

    //作品描述
    private String content;

    //拍摄地点
    private String location;

    //作者用户名
    private String authorName;

    //作者头像
    private String authorAvatar;

    //作品图片
    private String coverUrl;

    //最终评分
    private BigDecimal finalScore;

    //提交时间
    private LocalDateTime submitTime;

}