package project1.OurFit.controller;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import project1.OurFit.jwt.JwtTokenProvider;
import project1.OurFit.repository.JwtTokenRepository;
import project1.OurFit.request.RefreshTokenRequest;
import project1.OurFit.response.PostLoginDto;
import project1.constant.response.JsonResponse;
import project1.constant.response.JsonResponseStatus;

@Controller
@RequiredArgsConstructor
public class JwtTokenController {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenRepository jwtTokenRepository;

    @PostMapping("/newtoken")
    @ResponseBody
    public JsonResponse<PostLoginDto> refreshAccessToken(
            @RequestBody RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (!jwtTokenRepository.existsByRefreshToken(refreshToken)) {
            return new JsonResponse<>(JsonResponseStatus.REFRESH_TOKEN_NOT_FOUND);
        }

        Claims claims = jwtTokenProvider.parseToken(refreshToken);
        String email = claims.getSubject();
        System.out.println("email = " + email);

        String accessToken = jwtTokenProvider.createToken(email);

        return new JsonResponse<>(new PostLoginDto(accessToken, null, null, null));
    }

}
