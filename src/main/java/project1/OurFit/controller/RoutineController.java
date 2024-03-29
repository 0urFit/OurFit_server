package project1.OurFit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project1.OurFit.response.ExerciseDetailDto;
import project1.OurFit.response.ExerciseViewDto;
import project1.OurFit.response.ExerciseRoutineListDto;
import project1.OurFit.service.RoutineService;
import project1.constant.response.JsonResponse;
import project1.constant.response.JsonResponseStatus;

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
    public JsonResponse<Boolean> postLike(@PathVariable Long routineId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return new JsonResponse<>(routineService.postLike(userEmail,routineId));
    }

    @DeleteMapping("/exercise/{routineId}/likes")
    public JsonResponse<Boolean> deleteLike(@PathVariable Long routineId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return new JsonResponse<>(routineService.deleteLike(userEmail, routineId));
    }

    @GetMapping("/exercise/liked/{routineId}")
    public JsonResponse<Boolean> inquiryLike(@PathVariable Long routineId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return new JsonResponse<>(routineService.inquiryLike(userEmail, routineId));
    }

    @GetMapping("/exercise/enrolled/{routineId}")
    public JsonResponse<Boolean> inquiryEnroll(@PathVariable Long routineId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        routineService.inquiryEnroll(userEmail, routineId);
        return new JsonResponse<>(JsonResponseStatus.SUCCESS);
    }

    /**
     * 운동 카테고리 조회 API
     * @param category
     * @return
     */
    @GetMapping("/exercise/{category}")
    @ResponseBody
    public JsonResponse<List<ExerciseRoutineListDto>> getExerciseRoutine(@PathVariable String category) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (category.equals("all")) {
            List<ExerciseRoutineListDto> exercises = routineService.getExerciseRoutine(userEmail);
            return new JsonResponse<>(exercises);
        }
        List<ExerciseRoutineListDto> exerciseRoutineListDtoList =
                routineService.getExerciseRoutineByCategory(category,userEmail);
        return new JsonResponse<>(exerciseRoutineListDtoList);
    }

    @GetMapping("/exercise/{routineId}/view")
    @ResponseBody
    public JsonResponse<ExerciseViewDto> getExerciseOverview(@PathVariable final long routineId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return new JsonResponse<>(routineService.getExerciseRoutineView(email, routineId));
    }


    /**
     * 운동 상세 루틴 조회 API
     * @param routineId
     * @param weekNumber
     * @return
     */
    @GetMapping("/exercise/{routineId}/week/{weekNumber}")
    @ResponseBody
    public JsonResponse<List<ExerciseDetailDto>> getExerciseDetails(
            @PathVariable final long routineId, @PathVariable final int weekNumber) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return new JsonResponse<>(routineService.getExerciseDetails(email, routineId, weekNumber));
    }

    /**
     * 운동 루틴 등록 API
     * @param id
     * @return
     */
    @PostMapping("/exercise/{id}")
    @ResponseBody
    public JsonResponse<JsonResponseStatus> enrollExercise(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        routineService.enrollExercise(email, id);
        return new JsonResponse<>(JsonResponseStatus.SUCCESS);
    }

    /**
     * 운동 루틴 등록 삭제 API
     * @param id
     * @return
     */
    @DeleteMapping("/exercise/{id}")
    @ResponseBody
    public JsonResponse<JsonResponseStatus> deleteEnrollExercise(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        routineService.deleteEnrollExercise(email, id);
        return new JsonResponse<>(JsonResponseStatus.SUCCESS);
    }
}
