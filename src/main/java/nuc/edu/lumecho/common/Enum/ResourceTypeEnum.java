package nuc.edu.lumecho.common.Enum;

import lombok.Getter;

@Getter
public enum ResourceTypeEnum {
    AVATAR("AVATAR", "用户头像"),
    POST_IMAGE("POST_IMAGE", "帖子图片"),
    POST_COVER("POST_COVER", "帖子封面"),
    POST_VIDEO("POST_VIDEO", "帖子视频"),
    ACTIVITY_COVER("ACTIVITY_COVER", "活动封面");

    private final String code;
    private final String description;

    ResourceTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}