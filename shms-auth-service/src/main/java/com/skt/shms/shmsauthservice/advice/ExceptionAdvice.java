package com.skt.shms.shmsauthservice.advice;

import com.skt.shms.shmsauthservice.advice.exception.*;
import com.skt.shms.shmsauthservice.model.response.CommonResult;
import com.skt.shms.shmsauthservice.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    /***
     * -9999
     * default Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        log.info(String.valueOf(e));
        return responseService.getFailResult("unKnown.code", "unKnown.msg");
    }

    /***
     * -1000
     * 유저를 찾지 못했을 때 발생시키는 예외
     */
    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
        return responseService.getFailResult("userNotFound.code", "userNotFound.msg");
    }

    /***
     * -1001
     * 유저 아이디 로그인 실패 시 발생시키는 예외
     */
    @ExceptionHandler(CUserIdLoginFailedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected CommonResult userIdLoginFailedException(HttpServletRequest request, CUserIdLoginFailedException e) {
        return responseService.getFailResult("userIdLoginFailed.code", "userIdLoginFailed.msg");
    }

    /***
     * -1002
     * 회원 가입 시 이미 로그인 된 아이디인 경우 발생 시키는 예외
     */
    @ExceptionHandler(CUserIdJoinFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult userIdJoinFailedException(HttpServletRequest request, CUserIdJoinFailedException e) {
        return responseService.getFailResult("userIdJoinFailed.code", "userIdJoinFailed.msg");
    }

    /**
     * -1003
     * 전달한 Jwt 이 정상적이지 않은 경우 발생 시키는 예외
     */
    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected CommonResult authenticationEntrypointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResult("authenticationEntrypoint.code", "authenticationEntrypoint.msg");
    }

    /**
     * -1004
     * 권한이 없는 리소스를 요청한 경우 발생 시키는 예외
     */
    @ExceptionHandler(CAccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected CommonResult accessDeniedException(HttpServletRequest request, CAccessDeniedException e) {
        return responseService.getFailResult("accessDenied.code", "accessDenied.msg");
    }

    /**
     * -1005
     * refresh token 에러시 발생 시키는 에러
     */
    @ExceptionHandler(CRefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected CommonResult refreshTokenException(HttpServletRequest request, CRefreshTokenException e) {
        return responseService.getFailResult("refreshTokenInValid.code", "refreshTokenInValid.msg");
    }

    /**
     * -1006
     * 액세스 토큰 만료시 발생하는 에러
     */
    @ExceptionHandler(CExpiredAccessTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected CommonResult expiredAccessTokenException(HttpServletRequest request, CExpiredAccessTokenException e) {
        return responseService.getFailResult("expiredAccessToken.code", "expiredAccessToken.msg");
    }

    /***
     * -1007
     * 기 가입자 에러
     */
    @ExceptionHandler(CUserExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected CommonResult existUserException(HttpServletRequest request, CUserExistException e) {
        return responseService.getFailResult("userExistException.code", "userExistException.msg");
    }
}