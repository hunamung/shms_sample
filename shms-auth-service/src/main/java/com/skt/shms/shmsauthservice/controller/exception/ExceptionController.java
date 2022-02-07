package com.skt.shms.shmsauthservice.controller.exception;

import com.skt.shms.shmsauthservice.advice.exception.CAccessDeniedException;
import com.skt.shms.shmsauthservice.advice.exception.CAuthenticationEntryPointException;
import com.skt.shms.shmsauthservice.model.response.CommonResult;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"3. Exception"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/entryPoint")
    public CommonResult entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping("/accessDenied")
    public CommonResult accessDeniedException() {
        throw new CAccessDeniedException();
    }
}
