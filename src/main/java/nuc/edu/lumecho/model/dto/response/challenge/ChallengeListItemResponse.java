package nuc.edu.lumecho.model.dto.response.challenge;
import lombok.Data;
import java.time.LocalDateTime;
/**
 * @Author Dfly
 * @Date 2026/3/15 10:29
 */



@Data
public class ChallengeListItemResponse {
    // --- 基础信息 ---
    private Long id;
    private String title;
    private String description;
    private String cover;
    /**
     * 状态文案 (例如: "报名中", "作品评审中", "已结束")
     * 前端直接使用此字段显示标签，无需转换数字
     */
    private String statusText;

    /**
     * 状态码 (0:待开始, 1:进行中/报名中, 2:评审中, 3:已结束)
     * 用于前端控制标签颜色 (蓝/绿/橙/灰)
     */
    private Integer statusCode;

    /**
     * 是否即将结束 (true: 剩余时间 < 3天)
     * 前端可用于显示 "🔥 紧急" 图标或红色倒计时样式
     */
    private Boolean isUrgent;

    /**
     * 剩余时间描述 (例如: "剩 2天5小时", "已结束")
     * 前端直接显示在卡片上，无需自己计算时间差
     */
    private String remainingTimeDesc;

    /**
     * 参与进度百分比 (0 - 100)
     * 前端直接绑定 :style="width: progressPercent + '%'"
     */
    private Integer progressPercent;

    // --- 统计数据 ---
    private Integer maxParticipants;
    private Integer participantCount;

    // --- 时间信息 ---
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime reviewEndTime;

    // --- 可选扩展 (如果以后需要显示主办方) ---
    // private String organizerName;
    // private String organizerAvatar;

    /**
     * 友好提示语
     * 例如："还差 5 人满员，快来！", "🔥 热度飙升中", "✅ 已满员", "🌟 无人数限制"
     */
    private String friendlyHint;

    /** 是否已满员 (用于前端显示绿色对勾或禁用按钮) */
    private Boolean isFull;

    /** 是否无人数限制 (用于前端显示无限图标) */
    private Boolean isUnlimited;
}