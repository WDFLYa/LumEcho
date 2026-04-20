package nuc.edu.lumecho.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ActivityApplicationVO {
    private Long id;
    private Long activityId;
    private Long userId;
    private Integer status;
    private LocalDateTime createTime;
    private Integer applicationCount;
    // 关联查询字段
    private String username;
    private String avatar;
    private String activityTitle;
}