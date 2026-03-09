package nuc.edu.lumecho.common.Enum;

import lombok.Getter;

@Getter
public enum PostTypeEnum {
    GENERAL(1, "普通帖子"),
    ACTIVITY(2, "活动帖子"),
    CONTEST(3, "比赛帖子");

    private final Integer code;
    private final String description;

    PostTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
