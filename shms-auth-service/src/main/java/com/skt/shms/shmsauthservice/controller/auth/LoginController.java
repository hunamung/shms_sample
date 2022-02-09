package com.skt.shms.shmsauthservice.controller.auth;

import com.skt.shms.shmsauthservice.dto.join.UserJoinRequestDto;
import com.skt.shms.shmsauthservice.dto.join.UserLoginRequestDto;
import com.skt.shms.shmsauthservice.dto.jwt.TokenDto;
import com.skt.shms.shmsauthservice.dto.jwt.TokenRequestDto;
import com.skt.shms.shmsauthservice.model.response.SingleResult;
import com.skt.shms.shmsauthservice.service.response.ResponseService;
import com.skt.shms.shmsauthservice.service.security.JoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@Api(tags = {"1. Join/LogIn"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class LoginController {

    private final JoinService joinService;
    private final ResponseService responseService;
    private final MessageSource messageSource;

    @ApiOperation(value = "로그인", notes = "이메일로 로그인을 합니다.")
    @PostMapping("/login")
    public SingleResult<TokenDto> login(
            @ApiParam(value = "로그인 요청 DTO", required = true)
            @RequestBody UserLoginRequestDto userLoginRequestDto) {

        TokenDto tokenDto = joinService.login(userLoginRequestDto);
        return responseService.getSingleResult(tokenDto
                , Integer.parseInt(getMessage("success.code")), getMessage("success.msg"));
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/join")
    public SingleResult<Long> join(
            @ApiParam(value = "회원 가입 요청 DTO", required = true)
            @RequestBody UserJoinRequestDto userJoinRequestDto) {
        Long joinId = joinService.join(userJoinRequestDto);
        return responseService.getSingleResult(joinId
                , Integer.parseInt(getMessage("success.code")), getMessage("success.msg"));
    }

    @ApiOperation(
            value = "액세스, 리프레시 토큰 재발급",
            notes = "엑세스 토큰 만료시 회원 검증 후 리프레쉬 토큰을 검증해서 액세스 토큰과 리프레시 토큰을 재발급합니다.")
    @PostMapping("/reissue")
    public SingleResult<TokenDto> reissue(
            @ApiParam(value = "토큰 재발급 요청 DTO", required = true)
            @RequestBody TokenRequestDto tokenRequestDto) {
        return responseService.getSingleResult(joinService.reissue(tokenRequestDto)
                , Integer.parseInt(getMessage("success.code")), getMessage("success.msg"));
    }

    @ApiOperation(value = "로그아웃(구현중)", notes = "이메일로 로그아웃을 합니다.")
    @PostMapping("/logout")
    public SingleResult<Long> logout(
            @ApiParam(value = "로그아웃 요청 DTO", required = true)
            @RequestBody UserLoginRequestDto userLoginRequestDto) {

        //Long joinId = joinService.joinup(userJoinupRequestDto);
        return responseService.getSingleResult(999l
                , Integer.parseInt(getMessage("success.code")), getMessage("success.msg")); //temparary
    }

    private String getMessage(String code) {
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
