package nuc.edu.lumecho.common.Enum;

import lombok.Getter;

@Getter
public enum SubmissionStatusEnum {

    SUBMITTED(0, "已提交"),
    SCORED(1, "已评分"),
    DISQUALIFIED(2, "取消资格");

    private final int code;
    private final String description;

    SubmissionStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
