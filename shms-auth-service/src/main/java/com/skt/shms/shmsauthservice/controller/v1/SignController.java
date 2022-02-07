package com.skt.shms.shmsauthservice.controller.v1;

import com.skt.shms.shmsauthservice.advice.exception.CCommunicationException;
import com.skt.shms.shmsauthservice.advice.exception.CSocialAgreementException;
import com.skt.shms.shmsauthservice.advice.exception.CUserExistException;
import com.skt.shms.shmsauthservice.advice.exception.CUserNotFoundException;
import com.skt.shms.shmsauthservice.config.security.JwtProvider;
import com.skt.shms.shmsauthservice.domain.user.User;
import com.skt.shms.shmsauthservice.domain.user.UserJpaRepo;
import com.skt.shms.shmsauthservice.dto.jwt.TokenDto;
import com.skt.shms.shmsauthservice.dto.jwt.TokenRequestDto;
import com.skt.shms.shmsauthservice.dto.sign.UserLoginRequestDto;
import com.skt.shms.shmsauthservice.dto.sign.UserSignupRequestDto;
import com.skt.shms.shmsauthservice.dto.sign.UserSocialLoginRequestDto;
import com.skt.shms.shmsauthservice.dto.sign.UserSocialSignupRequestDto;
import com.skt.shms.shmsauthservice.dto.social.KakaoProfile;
import com.skt.shms.shmsauthservice.model.response.CommonResult;
import com.skt.shms.shmsauthservice.model.response.SingleResult;
import com.skt.shms.shmsauthservice.service.response.ResponseService;
import com.skt.shms.shmsauthservice.service.security.SignService;
import com.skt.shms.shmsauthservice.service.user.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Api(tags = {"1. SignUp/LogIn"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class SignController {

    private final JwtProvider jwtProvider;
    private final UserJpaRepo userJpaRepo;
    private final KakaoService kakaoService;
    private final SignService signService;
    private final ResponseService responseService;

    @ApiOperation(value = "로그인", notes = "이메일로 로그인을 합니다.")
    @PostMapping("/login")
    public SingleResult<TokenDto> login(
            @ApiParam(value = "로그인 요청 DTO", required = true)
            @RequestBody UserLoginRequestDto userLoginRequestDto) {

        TokenDto tokenDto = signService.login(userLoginRequestDto);
        return responseService.getSingleResult(tokenDto);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public SingleResult<Long> signup(
            @ApiParam(value = "회원 가입 요청 DTO", required = true)
            @RequestBody UserSignupRequestDto userSignupRequestDto) {
        Long signupId = signService.signup(userSignupRequestDto);
        return responseService.getSingleResult(signupId);
    }

    @ApiOperation(
            value = "액세스, 리프레시 토큰 재발급",
            notes = "엑세스 토큰 만료시 회원 검증 후 리프레쉬 토큰을 검증해서 액세스 토큰과 리프레시 토큰을 재발급합니다.")
    @PostMapping("/reissue")
    public SingleResult<TokenDto> reissue(
            @ApiParam(value = "토큰 재발급 요청 DTO", required = true)
            @RequestBody TokenRequestDto tokenRequestDto) {
        return responseService.getSingleResult(signService.reissue(tokenRequestDto));
    }

    @ApiOperation(
            value = "소셜 로그인 - kakao",
            notes = "카카오로 로그인을 합니다.")
    @PostMapping("/social/login/kakao")
    public SingleResult<TokenDto> loginByKakao(
            @ApiParam(value = "소셜 로그인 dto", required = true)
            @RequestBody UserSocialLoginRequestDto socialLoginRequestDto) {

        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(socialLoginRequestDto.getAccessToken());
        if (kakaoProfile == null) throw new CUserNotFoundException();

        User user = userJpaRepo.findByEmailAndProvider(kakaoProfile.getKakao_account().getEmail(), "kakao")
                .orElseThrow(CUserNotFoundException::new);
        return responseService.getSingleResult(jwtProvider.createTokenDto(user.getUserId(), user.getRoles()));
    }

    @ApiOperation(
            value = "소셜 회원가입 - kakao",
            notes = "카카오로 회원가입을 합니다."
    )
    @PostMapping("/social/signup/kakao")
    public CommonResult signupBySocial(
            @ApiParam(value = "소셜 회원가입 dto", required = true)
            @RequestBody UserSocialSignupRequestDto socialSignupRequestDto) {

        KakaoProfile kakaoProfile =
                kakaoService.getKakaoProfile(socialSignupRequestDto.getAccessToken());
        if (kakaoProfile == null) throw new CUserNotFoundException();
        if (kakaoProfile.getKakao_account().getEmail() == null) {
            kakaoService.kakaoUnlink(socialSignupRequestDto.getAccessToken());
            throw new CSocialAgreementException();
        }

        Long userId = signService.socialSignup(UserSignupRequestDto.builder()
                .email(kakaoProfile.getKakao_account().getEmail())
                .name(kakaoProfile.getProperties().getNickname())
                .nickName(kakaoProfile.getProperties().getNickname())
                .provider("kakao")
                .build());

        return responseService.getSingleResult(userId);
    }
}
