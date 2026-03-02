package nuc.edu.lumecho.common.Enum;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ADMIN("ADMIN", "管理员"),
    USER("USER", "普通用户"),
    PHOTOGRAPHER("PHOTOGRAPHER", "摄影师");

    private final String code;
    private final String description;

    UserRoleEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}