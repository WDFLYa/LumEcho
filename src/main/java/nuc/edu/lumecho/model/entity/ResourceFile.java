package nuc.edu.lumecho.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResourceFile {

    private Long id;
    private String bizType;  //业务类型
    private Long bizId;      //关联的业务ID（如用户ID、帖子ID）
    private String fileUrl;
    private String fileName;
    private Long createdBy;  //上传用户id
    private LocalDateTime createdTime;

}
