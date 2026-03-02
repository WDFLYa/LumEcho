package nuc.edu.lumecho.common.Enum;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    DISABLED(0, "禁用"),
    ENABLED(1, "正常");

    private final Integer code;
    private final String description;

    UserStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}