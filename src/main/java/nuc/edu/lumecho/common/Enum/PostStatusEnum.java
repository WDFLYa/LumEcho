package nuc.edu.lumecho.common.Enum;

import lombok.Getter;

@Getter
public enum PostStatusEnum {

    NORMAL(1, "正常"),
    BANNED(2, "封禁");

    private final int code;
    private final String description;

    PostStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

}