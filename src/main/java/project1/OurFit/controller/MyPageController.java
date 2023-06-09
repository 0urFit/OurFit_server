package project1.OurFit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project1.OurFit.response.MyLikeRes;
import project1.OurFit.response.MyRoutineRes;
import project1.OurFit.service.MyPageService;
import project1.constant.response.JsonResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MyPageController {
    private final MyPageService myPageService;

    /**
     * MyPage 들어갔을 때 등록한 루틴 조회
     * @return
     */
    @GetMapping("/mypage")
    public JsonResponse<List<MyRoutineRes>> getMyRoutine(){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<MyRoutineRes> myRoutineResList = myPageService.getMyRoutine(userEmail);
        return new JsonResponse<>(myRoutineResList);

    }

    /**
     * MyPage 들어갔을 때 카테고리로 등록한 루틴 조회
     * @param category
     * @return
     */
    @GetMapping("/mypage/{userId}/exercise/{category}")
    public JsonResponse<List<MyRoutineRes>> getMyRoutineByCate(@PathVariable String category){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<MyRoutineRes> myRoutineRes = myPageService.getMyRoutineByCate(userEmail, category);
        return new JsonResponse<>(myRoutineRes);
    }

    /**
     * MyPage 들어갔을 때 좋아요 한 루틴 조회
     * @return
     */
    @GetMapping("mypage/like")
    public JsonResponse<List<MyLikeRes>> getMyLikeRoutine(){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<MyLikeRes> myLikeRes = myPageService.getMyLikeRoutine(userEmail);
        return new JsonResponse<>(myLikeRes);
    }
}
