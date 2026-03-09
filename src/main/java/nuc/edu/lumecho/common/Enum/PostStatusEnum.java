package nuc.edu.lumecho.common.Enum;

import lombok.Getter;

@Getter
public enum PostStatusEnum {

    NORMAL(1, "正常"),
    BANNED(0, "封禁");

    private final int value;
    private final String description;

    PostStatusEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

}