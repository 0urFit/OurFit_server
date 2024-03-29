package project1.OurFit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project1.OurFit.entity.ExerciseEnroll;
import project1.OurFit.entity.ExerciseRoutine;
import project1.OurFit.entity.Member;

import java.util.List;
import java.util.Optional;

public interface ExerciseEnrollRepository extends JpaRepository<ExerciseEnroll, Long> {

    @Query("SELECT ee FROM ExerciseEnroll ee JOIN FETCH ee.member m WHERE m.email = :email")
    List<ExerciseEnroll> findByMemberEmail(@Param("email") String email);

    List<ExerciseEnroll> findAllByMemberEmail(String userEmail);

    boolean existsByMemberIdAndExerciseRoutineId(Long memberId, Long routineId);

    Optional<ExerciseEnroll> findByMemberIdAndExerciseRoutineId(Long id, Long routineId);

    List<ExerciseEnroll> findByMemberEmailAndExerciseRoutine_Category(String userEmail, String cate);

    @Query("SELECT ee.exerciseRoutine.id FROM ExerciseEnroll ee WHERE ee.member.email = :email AND ee.exerciseRoutine.id IN :routineIds")
    List<Long> findEnrolledRoutineIdsByMemberEmail(@Param("email") String email, @Param("routineIds") List<Long> routineIds);
}
