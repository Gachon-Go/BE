package gcu.project.gachongo.api.test;

import gcu.project.gachongo.global.common.api.ApiResponse;
import gcu.project.gachongo.global.common.api.ResponseCode;
import gcu.project.gachongo.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EmailService emailService;

        @ResponseBody
    @GetMapping("/log")
    public ResponseEntity<ApiResponse<String>> getAll() {
        System.out.println("테스트");
//        trace, debug 레벨은 Console X, 파일 로깅 X
//        logger.trace("TRACE Level 테스트");
//        logger.debug("DEBUG Level 테스트");

//        info 레벨은 Console 로깅 O, 파일 로깅 X
        logger.info("INFO Level 테스트");
//        warn 레벨은 Console 로깅 O, 파일 로깅 O
        logger.warn("Warn Level 테스트");
//        error 레벨은 Console 로깅 O, 파일 로깅 O (app.log 뿐만 아니라 error.log 에도 로깅 됨)
//        app.log 와 error.log 는 날짜가 바뀌면 자동으로 *.gz 으로 압축 백업됨
        logger.error("ERROR Level 테스트");

//        system.out.println(sentenceLikeRepository.findByStatus( "A", PageRequest.of(0,1,Sort.by(Sort.Order.desc("id")))).getContent());
//        sentenceLikeMappings.forEach(system.out::println);
        LocalDateTime now = LocalDateTime.now();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseCode.TEST, now.toString()));
    }

}
