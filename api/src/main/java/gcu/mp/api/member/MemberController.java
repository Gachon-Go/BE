package gcu.mp.api.member;

import gcu.mp.api.member.dto.request.ModifyNicknameReq;
import gcu.mp.api.member.dto.response.MyPageRes;
import gcu.mp.api.member.mapper.MemberMapper;
import gcu.mp.common.api.BaseResponse;
import gcu.mp.common.api.BaseResponseStatus;
import gcu.mp.common.exception.BaseException;
import gcu.mp.service.member.MemberService;
import gcu.mp.service.member.dto.MyPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static gcu.mp.common.api.BaseResponseStatus.INVALID_IMAGE_FILE;
import static gcu.mp.util.FileCheck.checkImage;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "유저")
@RequestMapping(value = "/user")
public class MemberController {
    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @Operation(summary = "닉네임 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "2101", description = "사용중인 유저 닉네임 입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @PatchMapping("/nickname")
    public ResponseEntity<BaseResponse<String>> logIn(@RequestBody ModifyNicknameReq modifyNicknameReq) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            if (memberService.existNickname(modifyNicknameReq.getNickname())) {
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.EXISTS_MEMBER_NICKNAME));
            }
            memberService.modifyNickname(memberMapper.toModifyNicknameDto(modifyNicknameReq, memberId));
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @Operation(summary = "회원탈퇴")
    @ApiResponses({
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @PatchMapping("/resign")
    public ResponseEntity<BaseResponse<String>> resign() {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            memberService.resignMember(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS));

        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @Operation(summary = "프로필 사진 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "3017", description = "이미지 파일이 아닙니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @PatchMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<String>> getImage(
            @RequestPart(value = "image") MultipartFile image) {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());
            if (image != null) {
                if (!checkImage(image)) {
                    return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(INVALID_IMAGE_FILE));
                }
            }
            String imageUrl = memberService.modifyProfileImage(memberId, image);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(imageUrl));

        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @Operation(summary = "마이 페이지")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "성공"),
            @ApiResponse(responseCode = "2004", description = "유효하지 않은 토큰입니다.", content = @Content),
            @ApiResponse(responseCode = "2012", description = "권한이 없는 유저의 접근입니다.", content = @Content),
            @ApiResponse(responseCode = "2103", description = "존재하지 않는 유저입니다.", content = @Content),
            @ApiResponse(responseCode = "4001", description = "서버 오류입니다.", content = @Content)
    })
    @GetMapping(value = "/my-page")
    public ResponseEntity<BaseResponse<MyPageRes>> getMyPage() {
        try {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            Long memberId = Long.parseLong(loggedInUser.getName());

            MyPageDto myPageDto = memberService.getMyPage(memberId);
            MyPageRes myPageRes = memberMapper.toMyPageRes(myPageDto);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(myPageRes));

        } catch (BaseException e) {
            return ResponseEntity.status(e.getStatus().getHttpCode()).body(new BaseResponse<>(e.getStatus()));
        }
    }
}
