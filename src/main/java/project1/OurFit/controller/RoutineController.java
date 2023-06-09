package project1.OurFit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project1.OurFit.response.ExerciseRoutineWithEnrollmentStatusDto;
import project1.OurFit.service.RoutineService;
import project1.constant.response.JsonResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RoutineController {
    private final RoutineService routineService;

    /**
     * 운동루틴 좋아요 등록 API
     * @param routineId
     * @return
     */
    @PostMapping("/exercise/{routineId}/likes")
    public JsonResponse<String> postLike(@PathVariable Long routineId){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        routineService.postLike(userEmail,routineId);
        return new JsonResponse<>("좋아요 등록");
    }

    /**
     * 운동루틴 좋아요 취소 API
     * @param routineId
     * @return
     */
    @DeleteMapping("/exercise/{memberId}/{routineId}/likes")
    public JsonResponse<String> deleteLike(@PathVariable Long routineId){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        routineService.deleteLike(userEmail,routineId);
        return new JsonResponse<>("좋아요 취소");
    }

    /**
     * 운동 카테고리 조회 API
     * @param category
     * @return
     */
    @GetMapping("/exercise")
    @ResponseBody
    public JsonResponse<List<ExerciseRoutineWithEnrollmentStatusDto>> getExerciseRoutine(
            @RequestParam(required = false) String category) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (category == null) {
            List<ExerciseRoutineWithEnrollmentStatusDto> exercises = routineService.getExerciseRoutine(userEmail);
            return new JsonResponse<>(exercises);
        }
        List<ExerciseRoutineWithEnrollmentStatusDto> exerciseRoutineWithEnrollmentStatusDtoList =
                routineService.getExerciseRoutineByCategory(category,userEmail);
        return new JsonResponse<>(exerciseRoutineWithEnrollmentStatusDtoList);
    }
}
