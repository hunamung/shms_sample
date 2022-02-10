package com.skt.shms.shmsauthservice.controller.user;

import com.skt.shms.shmsauthservice.dto.user.UserResponseDto;
import com.skt.shms.shmsauthservice.model.response.ListResult;
import com.skt.shms.shmsauthservice.model.response.SingleResult;
import com.skt.shms.shmsauthservice.service.response.ResponseService;
import com.skt.shms.shmsauthservice.service.user.UserService;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 검색", notes = "userId로 회원을 조회합니다.")
    @GetMapping("/user/id/{userId}")
    public SingleResult<UserResponseDto> findUserById
            (@ApiParam(value = "회원 ID", required = true) @PathVariable String userId,
             @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        
        return responseService.getSingleResult(userService.findByUserId(userId),null,null);
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
        return responseService.getListResult(userService.findAllUser(),null,null);
    }
}
