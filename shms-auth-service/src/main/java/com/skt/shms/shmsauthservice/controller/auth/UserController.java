package com.skt.shms.shmsauthservice.controller.auth;

import com.skt.shms.shmsauthservice.dto.user.UserRResponseDto;
import com.skt.shms.shmsauthservice.dto.user.UserRequestDto;
import com.skt.shms.shmsauthservice.dto.user.UserResponseDto;
import com.skt.shms.shmsauthservice.model.response.CommonResult;
import com.skt.shms.shmsauthservice.model.response.ListResult;
import com.skt.shms.shmsauthservice.model.response.SingleResult;
import com.skt.shms.shmsauthservice.service.response.ResponseService;
import com.skt.shms.shmsauthservice.service.user.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

//@PreAuthorize("hasRole('ROLE_USER') and hasAnyRole('ROLE_IORN', 'ROLE_SILVER', 'ROLE_GOLD', 'ROLE_BRONZE')")
@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;
    private final MessageSource messageSource;

    //@PreAuthorize("hasRole('ROLE_PLATINUM') and hasRole('ROLE_GOLD')")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 검색", notes = "userId로 회원을 조회합니다.")
    @GetMapping("/user/id/{userId}")
    public SingleResult<UserRResponseDto> findUserById
            (@ApiParam(value = "회원 ID", required = true) @PathVariable Long userId,
             @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        
        // return responseService.getSingleResult(userService.findById(userId)
        //         , Integer.parseInt(getMessage("success.code")), getMessage("success.msg"));
        
        return responseService.getSingleResult(userService.findByIdUsingMapper(userId)
                        , Integer.parseInt(getMessage("success.code")), getMessage("success.msg"));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 검색 (이메일)", notes = "이메일로 회원을 조회합니다.")
    @GetMapping("/user/email/{email}")
    public SingleResult<UserResponseDto> findUserByEmail
            (@ApiParam(value = "회원 이메일", required = true) @PathVariable String email,
             @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        return responseService.getSingleResult(userService.findByEmail(email)
                , Integer.parseInt(getMessage("success.code")), getMessage("success.msg"));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 목록 조회", notes = "모든 회원을 조회합니다.")
    @GetMapping("/users")
    public ListResult<UserResponseDto> findAllUser() {
        return responseService.getListResult(userService.findAllUser()
                , Integer.parseInt(getMessage("success.code")), getMessage("success.msg"));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정합니다.")
    @PutMapping("/user")
    public SingleResult<Long> update (
            @ApiParam(value = "회원 ID", required = true) @RequestParam Long userId,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String nickName) {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .nickName(nickName)
                .build();
        return responseService.getSingleResult(userService.update(userId, userRequestDto)
                , Integer.parseInt(getMessage("success.code")), getMessage("success.msg"));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제합니다.")
    @DeleteMapping("/user/{userId}")
    public CommonResult delete(
            @ApiParam(value = "회원 아이디", required = true) @PathVariable Long userId) {
        userService.delete(userId);
        return responseService.getSuccessResult(Integer.parseInt(getMessage("success.code")), getMessage("success.msg"));
    }

    private String getMessage(String code) {
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
