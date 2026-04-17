package nuc.edu.lumecho.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import nuc.edu.lumecho.common.Enum.ResourceTypeEnum;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.common.util.WdfUUIDUtil;
import nuc.edu.lumecho.mapper.ResourceFileMapper;
import nuc.edu.lumecho.model.entity.ResourceFile;
import nuc.edu.lumecho.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private MinioClient minioClient;

    @Value("${spring.minio.bucket}")
    private String bucket;

    // 建议将域名也配置到配置文件中，这里为了与 uploadFile 保持一致暂时硬编码
    @Value("${spring.minio.endpoint:http://localhost:9000}")
    private String minioEndpoint;

    @Autowired
    private ResourceFileMapper resourceFileMapper;

    @Override
    public String uploadFile(MultipartFile file, String bizType) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 1. 生成安全文件名
        String ext = getFileExtension(file.getOriginalFilename());
        String objectName = bizType.toLowerCase() + "/" + WdfUUIDUtil.generateUUID() + ext;

        // 2. 上传到 MinIO
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        // 3. 构建访问 URL
        String fileUrl = minioEndpoint + "/" + bucket + "/" + objectName;

        // 4. 保存记录 (初始 bizId 为 null，后续业务绑定)
        ResourceFile resourceFile = new ResourceFile();
        resourceFile.setBizType(bizType);
        resourceFile.setBizId(null);
        resourceFile.setFileUrl(fileUrl);
        resourceFile.setFileName(file.getOriginalFilename());
        resourceFile.setCreatedBy(WdfUserContext.getCurrentUserId());
        resourceFile.setCreatedTime(LocalDateTime.now());
        resourceFileMapper.insert(resourceFile);

        return fileUrl;
    }

    @Override
    public String updateUserAvatar(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 1. 获取当前用户 ID
        Long userId = WdfUserContext.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("User not logged in");
        }

        // 2. 上传新头像文件到 MinIO (逻辑与 uploadFile 类似)
        String ext = getFileExtension(file.getOriginalFilename());
        String objectName = ResourceTypeEnum.AVATAR.getCode().toLowerCase() + "/" + WdfUUIDUtil.generateUUID() + ext;

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        String newFileUrl = minioEndpoint + "/" + bucket + "/" + objectName;

        // 3. 尝试更新数据库中已存在的头像记录
        int updateCount = resourceFileMapper.updateUserAvatar(newFileUrl, ResourceTypeEnum.AVATAR.getCode(), userId);

        // 4. 如果没有更新成功（说明之前没有该用户的头像记录），则插入新记录
        if (updateCount == 0) {
            ResourceFile resourceFile = new ResourceFile();
            resourceFile.setBizType(ResourceTypeEnum.AVATAR.getCode());
            resourceFile.setBizId(userId); // 头像通常直接绑定用户 ID
            resourceFile.setFileUrl(newFileUrl);
            resourceFile.setFileName(file.getOriginalFilename());
            resourceFile.setCreatedBy(userId);
            resourceFile.setCreatedTime(LocalDateTime.now());
            resourceFileMapper.insert(resourceFile);
        } else {
            // 可选优化：如果更新了记录，理论上应该删除 MinIO 中的旧文件
            // 但由于需要先查询旧文件 URL 且涉及事务一致性，此处暂不处理物理删除，
            // 生产环境建议通过定时任务清理无引用的文件或在此处查询旧 URL 后删除
        }

        return newFileUrl;
    }

    private String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf(".")).toLowerCase();
        }
        return ".jpg";
    }
}
