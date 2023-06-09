package project1.OurFit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project1.OurFit.response.PostLoginDto;
import project1.OurFit.service.JwtService;
import project1.constant.exception.DuplicateException;
import project1.constant.response.JsonResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project1.OurFit.request.LoginDTO;
import project1.OurFit.request.MemberDTO;
import project1.OurFit.request.OAuthTokenDTO;
import project1.OurFit.response.PostKakaoProfile;
import project1.OurFit.service.KakaoService;
import project1.OurFit.service.MemberService;
import project1.constant.response.JsonResponseStatus;

@Controller
@RequiredArgsConstructor
public class SignInUpController {
    private final MemberService memberService;
    private final KakaoService kakaoService;
    private final JwtService jwtService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonResponse<PostLoginDto> login(@RequestBody LoginDTO login) {
        return new JsonResponse<>(memberService.findEmailAndPassword(login.getEmail(), login.getPassword()));
    }

    @GetMapping("/checkemail/{email}")
    @ResponseBody
    public JsonResponse<JsonResponseStatus> checkEmail(@PathVariable String email) {
        if (memberService.findEmail(email))
            throw new DuplicateException(JsonResponseStatus.EMAIL_CONFLICT);
        return new JsonResponse<>(JsonResponseStatus.SUCCESS);
    }

    @GetMapping("/checknick/{nickname}")
    @ResponseBody
    public JsonResponse<JsonResponseStatus> checkNickname(@PathVariable String nickname) {
        if (memberService.findNickname(nickname))
            throw new DuplicateException(JsonResponseStatus.NICKNAME_CONFLICT);
        return new JsonResponse<>(JsonResponseStatus.SUCCESS);
    }

    //회원가입
    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonResponse<JsonResponseStatus> signup(@RequestBody MemberDTO member) {
        memberService.join(member);
        return new JsonResponse<>(JsonResponseStatus.SUCCESS);
    }

    @GetMapping("/kakao")
    @ResponseBody
    public synchronized ResponseEntity<JsonResponse<PostLoginDto>> oauthKakaoLogin(@RequestParam("authorizationCode") String code) {
        OAuthTokenDTO oAuthToken = kakaoService.getToken(code);
        PostKakaoProfile info =  kakaoService.getUserInfo(oAuthToken);

        Boolean isEmailExist = memberService.findEmail(info.getKakao_account().getEmail());
        if (isEmailExist)
            return ResponseEntity.ok(new JsonResponse<>(jwtService.authorize(info.getKakao_account().getEmail())));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new JsonResponse<>(
                        new PostLoginDto(null, null,
                            info.getKakao_account().getEmail(), info.getKakao_account().getGender()),
                    JsonResponseStatus.UNAUTHORIZED));
    }
}
