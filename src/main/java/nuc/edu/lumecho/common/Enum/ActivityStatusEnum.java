package nuc.edu.lumecho.common.Enum;

import lombok.Getter;

@Getter
public enum ActivityStatusEnum {
    PENDING(0, "待开始"),
    ONGOING(1, "进行中"),
    FINISHED(2, "已结束"),
    CANCELLED(3, "已取消");


    private final int code;
    private final String description;

    ActivityStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
