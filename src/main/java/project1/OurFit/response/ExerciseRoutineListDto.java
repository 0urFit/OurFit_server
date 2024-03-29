package project1.OurFit.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project1.OurFit.entity.ExerciseRoutine;

@Getter @Setter
@AllArgsConstructor
public class ExerciseRoutineListDto {
    private Long id;
    private String category;
    private String imgpath;
    private int fewTime;
    private int level;
    private int period;
    private String routineName;
    private boolean isEnrolled;
    private boolean isLiked;

    public ExerciseRoutineListDto(ExerciseRoutine exerciseRoutine, boolean enrolled, boolean liked) {
        this.id = exerciseRoutine.getId();
        this.category = exerciseRoutine.getCategory();
        this.imgpath = exerciseRoutine.getImgpath();
        this.fewTime = exerciseRoutine.getDaysPerWeek();
        this.level = exerciseRoutine.getLevel();
        this.period = exerciseRoutine.getProgramLength();
        this.routineName = exerciseRoutine.getRoutineName();
        this.isEnrolled = enrolled;
        this.isLiked = liked;
    }
}
