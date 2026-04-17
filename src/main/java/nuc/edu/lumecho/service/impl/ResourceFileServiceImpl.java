package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.mapper.ResourceFileMapper;
import nuc.edu.lumecho.service.ResourceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceFileServiceImpl implements ResourceFileService {

    @Autowired
    private ResourceFileMapper resourceFileMapper;

    @Override
    public int countByBizIdAndType(Long userId, String bizType) {
        return resourceFileMapper.countByBizIdAndType(userId, bizType);
    }

    @Override
    public void updateUserAvatar(String fileUrl, String bizType, Long userId) {
        resourceFileMapper.updateUserAvatar(fileUrl, bizType, userId);
    }

    @Override
    public void bindBizIdByUrl(String fileUrl, String bizType, Long userId) {
        resourceFileMapper.bindBizIdByUrl(fileUrl, bizType, userId);
    }
}