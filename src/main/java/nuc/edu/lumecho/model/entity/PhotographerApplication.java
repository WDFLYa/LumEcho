package nuc.edu.lumecho.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PhotographerApplication {

    private Long id;

    private Long userId;

    private String description; // 申请原因，简介

    private Integer status; // 审核状态 0 - 待审核 1 - 已通过 2 - 已拒绝

    private String rejectReason; // 拒绝原因 （仅当 status = 2 时有值）

    private LocalDateTime applyTime; // 申请时间

    private LocalDateTime reviewTime; // 审核时间

    private Long reviewerId;  // 审核人 ID（通常是管理员 ID）
}
