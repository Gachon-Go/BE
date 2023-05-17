package gcu.mp.s3client;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import gcu.mp.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static gcu.mp.common.api.BaseResponseStatus.FAILED_UPLOAD_S3;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadProfileImage(MultipartFile multipartFile) {
        String fileName = "profile-image/" + multipartFile.getOriginalFilename();
        UUID fileNameUUID = UUID.randomUUID();
        //파일 형식 구하기
        String ext = Objects.requireNonNull(fileName).split("\\.")[1];
        fileName = fileNameUUID + "." + ext;
        String contentType = switch (ext) {
            case "jpeg" -> "image/jpeg";
            case "jpg" -> "image/jpg";
            case "png" -> "image/png";
            default -> "";

            //content type 지정
        };

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        //S3 upload
        log.info("upload before");
        try {
            byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
            metadata.setContentLength(bytes.length);
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new BaseException(FAILED_UPLOAD_S3);
        }
        log.info("upload after");
        //이미지 주소 리턴
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}