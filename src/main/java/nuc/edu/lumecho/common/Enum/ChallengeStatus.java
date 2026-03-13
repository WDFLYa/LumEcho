package nuc.edu.lumecho.common.Enum;

import lombok.Getter;

@Getter
public enum ChallengeStatus {
    NOT_STARTED(0, "未开始"),
    IN_PROGRESS(1, "进行中"),
    REVIEWING(2, "评审中"),
    ENDED(3, "已结束"),
    CANCELLED(4, "已取消");

    private final int code;
    private final String description;

    ChallengeStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

}
