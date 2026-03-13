package nuc.edu.lumecho.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
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

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private MinioClient minioClient;

    @Value("${spring.minio.bucket}")
    private String bucket;

    @Autowired
    private ResourceFileMapper resourceFileMapper;

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
        String fileUrl = "http://localhost:9000/" + bucket + "/" + objectName;
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

    private String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf(".")).toLowerCase();
        }
        return ".jpg";
    }
}
