package nuc.edu.lumecho.service;

public interface ResourceFileService {

    int countByBizIdAndType(Long userId, String bizType);

    void updateUserAvatar(String fileUrl, String bizType, Long userId);

    void bindBizIdByUrl(String fileUrl, String bizType, Long userId);
}
