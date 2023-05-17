package gcu.mp.util;

import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.common.exception.BaseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCheck {
    public static boolean checkImage(MultipartFile image) {
        String originalName = image.getOriginalFilename();
        assert originalName != null;
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
        try {
            Path savePath = Paths.get(fileName);
            return Files.probeContentType(savePath).startsWith("image");
        } catch (IOException e) {
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }
    }
