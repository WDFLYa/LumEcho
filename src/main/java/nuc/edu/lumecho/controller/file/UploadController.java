package nuc.edu.lumecho.controller.file;

import io.minio.errors.*;
import nuc.edu.lumecho.common.Enum.ResourceTypeEnum;
import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api")
public class UploadController {
    @Autowired
    private FileService fileService;

    /**
     * 通用文件上传接口
     */
    @PostMapping("/upload")
    public Result<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("bizType") String bizType
    ) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        String url = fileService.uploadFile(file, bizType);
        return Result.ok(url);
    }

    /**
     * 用户头像上传/更新接口
     * 此接口不仅上传文件，还会关联当前登录用户 ID (bizId)
     */
    @PostMapping("/avatar/upload")
    public Result<String> uploadAvatar(
            @RequestParam("file") MultipartFile file
    ) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {


        // 调用 Service 层的更新头像方法
        // 该方法内部会自动获取当前用户 ID 并更新数据库记录
        String url = fileService.updateUserAvatar(file);
        return Result.ok(url);
    }
}