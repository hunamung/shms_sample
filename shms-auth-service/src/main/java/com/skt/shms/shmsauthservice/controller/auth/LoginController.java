package com.skt.shms.shmsauthservice.controller.auth;

import com.skt.shms.shmsauthservice.dto.auth.join.UserJoinRequestDto;
import com.skt.shms.shmsauthservice.dto.auth.jwt.TokenRequestDto;
import com.skt.shms.shmsauthservice.dto.auth.jwt.TokenResponseDto;
import com.skt.shms.shmsauthservice.dto.auth.login.UserLoginRequestDto;
import com.skt.shms.shmsauthservice.model.response.CommonResult;
import com.skt.shms.shmsauthservice.model.response.SingleResult;
import com.skt.shms.shmsauthservice.service.auth.LoginService;
import com.skt.shms.shmsauthservice.service.response.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. Join/LogIn"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class LoginController {

    private final LoginService loginService;
    private final ResponseService responseService;

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/join")
    public CommonResult join(
            @ApiParam(value = "회원 가입 요청 DTO", required = true)
            @RequestBody UserJoinRequestDto requestDto) {
        loginService.join(requestDto);
        return responseService.getSuccessResult(null,null);
    }

    @ApiOperation(value = "로그인", notes = "아이디로 로그인을 합니다.")
    @PostMapping("/login")
    public SingleResult<TokenResponseDto> login(
            @ApiParam(value = "로그인 요청 DTO", required = true)
            @RequestBody UserLoginRequestDto requestDto) {
        TokenResponseDto tokenDto = loginService.login(requestDto);
        return responseService.getSingleResult(tokenDto,null,null);
    }

    @ApiOperation(
            value = "리프레시 토큰으로 엑세스 토큰 재발급",
            notes = "리프레쉬 토큰을 검증해서 액세스 토큰을 재발급합니다.")
    @PostMapping("/reissue")
    public SingleResult<TokenResponseDto> reissue(
            @ApiParam(value = "토큰 재발급 요청 DTO", required = true)
            @RequestBody TokenRequestDto requestDto) {
        return responseService.getSingleResult(loginService.reissue(requestDto),null,null);
    }

    @ApiOperation(
            value = "회원인증 -> 액세스, 리프레시 토큰 재발급",
            notes = "엑세스 토큰 만료시 회원 검증 후 리프레쉬 토큰을 검증해서 액세스 토큰과 리프레시 토큰을 재발급합니다.")
    @PostMapping("/reissue_v2")
    public SingleResult<TokenResponseDto> reissue_v2(
            @ApiParam(value = "토큰 재발급 요청 DTO", required = true)
            @RequestBody TokenRequestDto requestDto) {
        return responseService.getSingleResult(loginService.reissue_v2(requestDto),null,null);
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃을 합니다.")
    @PostMapping("/logout")
    public CommonResult logout(
            @ApiParam(value = "로그아웃 요청 DTO", required = true)
            @RequestBody UserLoginRequestDto requestDto) {
        loginService.logout(requestDto);
        return responseService.getSuccessResult(null,null);
    }
}
