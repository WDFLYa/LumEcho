package nuc.edu.lumecho.common.Enum;

import lombok.Getter;

@Getter
public enum PhotographerApplyStatusEnum {

    PENDING(0, "待审核"),
    APPROVED(1, "已通过"),
    REJECTED(2, "已拒绝");

    private final int code;
    private final String description;

    PhotographerApplyStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}